package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import java.io.IOException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.dropwizard.jersey.errors.ErrorMessage;
import org.glassfish.jersey.server.ChunkedOutput;
import org.hibernate.validator.constraints.NotEmpty;

import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;


@Path("likes")
@Produces(MediaType.TEXT_HTML)
public class PostsResource {
    private Tumblr tumblrClient;
    private Long MAX_TIME_DELTA_SECONDS = 14 * 24 * 60 * 60L; // Should be tuned to get max posts without the request from the client timing out

    public PostsResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    @Timed
    public ChunkedOutput<ResponseUnitView> searchLikes(@QueryParam("blogName") @NotEmpty String blogName,
                                                       @QueryParam("searchText") String searchText,
                                                       @QueryParam("before") Long beforeTimestampSeconds
    ) throws WebApplicationException {
        final String blogToSearch = Blog.sanitizeBlogName(blogName);

        final Long initialLikedBeforeTimestampSeconds;
        if (beforeTimestampSeconds == null) {
            initialLikedBeforeTimestampSeconds = new Date().getTime() / 1000;
        } else {
            initialLikedBeforeTimestampSeconds = beforeTimestampSeconds;
        }
        final Long likedAfterTimestampSeconds = initialLikedBeforeTimestampSeconds - MAX_TIME_DELTA_SECONDS;

        ChunkedOutput<ResponseUnitView> output = new ChunkedOutput<>(ResponseUnitView.class);

        new Thread(() -> {
            LinkedList<Post> posts;
            Long likedBeforeTimestampSeconds = initialLikedBeforeTimestampSeconds;
            try {
                while ((likedBeforeTimestampSeconds >= likedAfterTimestampSeconds) &&
                        (!(posts = getLikesBefore(blogToSearch, likedBeforeTimestampSeconds)).isEmpty())) {

                    likedBeforeTimestampSeconds = posts.getLast().getLikedAt();

                    if (!searchText.isEmpty()) {
                        posts = filterPostsBySearchString(posts, searchText);
                    }

                    posts.forEach(post -> {
                        try {
                            output.write(new SinglePostView(post));
                        } catch (IOException e) {
                            // TODO: exceptions need to go somewhere! close output with error?
                        }
                    });
                }
            } catch (WebApplicationException e) {
                try {
                    output.write(new ErrorView(new ErrorMessage(e.getMessage())));
                } catch (IOException ioe) {
                }
            }
            finally {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
        }).start();

        return output;
    }

    protected LinkedList<Post> getLikesBefore(String blogName, Long likedBeforeTimestampSeconds)
            throws WebApplicationException {
        TumblrResponse response = tumblrClient.getLikes(blogName, likedBeforeTimestampSeconds);

        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;

            if (!(success.getPosts() == null)) {
                return new LinkedList<>(success.getPosts());
            } else {
                return new LinkedList<>();
            }
        } else {
            int tumblrErrorStatusCode = response.getMeta().getStatus();
            String errorDetails;
            if (tumblrErrorStatusCode == 403) {
                errorDetails = blogName + "'s likes are not public.";
            } else if (tumblrErrorStatusCode == 404) {
                errorDetails = blogName + " does not exist.";
            } else if(tumblrErrorStatusCode >= 500) {
                errorDetails= "Tumblr is down or unreachable.";
            } else {
                errorDetails = "Unknown error: " + tumblrErrorStatusCode + ".";
            }
            throw new WebApplicationException(errorDetails, tumblrErrorStatusCode);
        }
    }

    protected LinkedList<Post> filterPostsBySearchString(LinkedList<Post> posts, String searchText) {
        posts.removeIf(post -> !post.containsText(searchText));
        return posts;
    }
}
