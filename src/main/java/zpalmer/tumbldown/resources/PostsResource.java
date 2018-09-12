package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    private Long MAX_TIME_DELTA_SECONDS = 10 * 24 * 60 * 60L; // Tuned to get max posts without timing out

    public PostsResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    @Timed
    public PostsView getLikes(@QueryParam("blogName") @NotEmpty String blogName,
                                     @QueryParam("searchText") String searchText,
                                     @QueryParam("before") Long likedBeforeTimestampSeconds
    ) {
        if (likedBeforeTimestampSeconds == null) {
            likedBeforeTimestampSeconds = new Date().getTime() / 1000;
        }
        Long likedAfterTimestampSeconds = likedBeforeTimestampSeconds - MAX_TIME_DELTA_SECONDS;

        blogName = Blog.sanitizeBlogName(blogName);

        LinkedList<Post> posts = new LinkedList<>();
        return new PostsView(
                searchForLikes(blogName, searchText, likedBeforeTimestampSeconds, likedAfterTimestampSeconds, posts),
                searchText);
    }

    protected LinkedList<Post> searchForLikes(String blogName,
                                              String searchText,
                                              Long likedBeforeTimestampSeconds,
                                              Long likedAfterTimestampSeconds,
                                              LinkedList<Post> posts) {
        boolean morePosts = true;
        while (morePosts) {

            TumblrResponse response = tumblrClient.getLikes(blogName, likedBeforeTimestampSeconds);
            if (response instanceof TumblrSuccessResponse) {
                TumblrSuccessResponse success = (TumblrSuccessResponse) response;
                posts.addAll(success.getPosts());

                Long lastLikedBeforeTimestampSeconds = posts.getLast().getLikedAt();

                if (lastLikedBeforeTimestampSeconds.equals(likedBeforeTimestampSeconds) || lastLikedBeforeTimestampSeconds < likedAfterTimestampSeconds)
                {
                    morePosts = false;
                } else {
                    likedBeforeTimestampSeconds = lastLikedBeforeTimestampSeconds;
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

        return filterPostsBySearchString(posts, Optional.ofNullable(searchText));
    }

    protected LinkedList<Post> filterPostsBySearchString(LinkedList<Post> posts, Optional<String> searchText) {
        if (searchText.isPresent()) {
            posts.removeIf(post -> !post.containsText(searchText.get()));
        }
        return posts;
    }
}
