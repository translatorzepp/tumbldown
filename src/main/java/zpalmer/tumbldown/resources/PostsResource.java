package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import java.io.IOException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    public ChunkedOutput<SinglePostView> getLikes(@QueryParam("blogName") @NotEmpty String blogName,
                                        @QueryParam("searchText") String searchText,
                                        @QueryParam("before") Long beforeTimestampSeconds
    ) {
        final String blogToSearch = Blog.sanitizeBlogName(blogName);

        final Long initialLikedBeforeTimestampSeconds;
        if (beforeTimestampSeconds == null) {
            initialLikedBeforeTimestampSeconds = new Date().getTime() / 1000;
        } else {
            initialLikedBeforeTimestampSeconds = beforeTimestampSeconds;
        }
        final Long likedAfterTimestampSeconds = initialLikedBeforeTimestampSeconds - MAX_TIME_DELTA_SECONDS;

        ChunkedOutput<SinglePostView> output = new ChunkedOutput<SinglePostView>(SinglePostView.class);

        new Thread() {
            public void run() {
                try {
                    LinkedList<Post> posts;
                    Long likedBeforeTimestampSeconds = initialLikedBeforeTimestampSeconds;

                    try {
                        while ((likedBeforeTimestampSeconds >= likedAfterTimestampSeconds) &&
                                (posts = getLikesBefore(blogToSearch, likedBeforeTimestampSeconds)) != null) {

                            likedBeforeTimestampSeconds = posts.getLast().getLikedAt();
                            filterPostsBySearchString(posts, Optional.ofNullable(searchText)).forEach(post -> {
                                try {
                                    output.write(new SinglePostView(post));
                                } catch (IOException e) {
                                    // TODO: better exceptions!
                                    throw new WebApplicationException("IOException: " + e.getMessage());
                                }
                            });
                        }
                    } catch (WebApplicationException e) {
                        throw e;
                    }
                } finally {
                    try {
                        output.close();
                    } catch (IOException e) {
                        throw new WebApplicationException("Unknown error.");
                    }
                }
            }
        }.start();

        return output;
    }

    private LinkedList<Post> getLikesBefore(String blogName, Long likedBeforeTimestampSeconds) {
        TumblrResponse response = tumblrClient.getLikes(blogName, likedBeforeTimestampSeconds);

        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;
            LinkedList<Post> posts = new LinkedList<Post>(success.getPosts());
            return posts;
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

    protected LinkedList<Post> filterPostsBySearchString(LinkedList<Post> posts, Optional<String> searchText) {
        if (searchText.isPresent()) {
            posts.removeIf(post -> !post.containsText(searchText.get()));
        }
        return posts;
    }
}
