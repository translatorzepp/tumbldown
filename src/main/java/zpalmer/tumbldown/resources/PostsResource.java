package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;


@Path("likes")
@Produces(MediaType.APPLICATION_JSON)
public class PostsResource {
    private Tumblr tumblrClient;

    public PostsResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    @Timed
    public LinkedList<Post> getLikes(@QueryParam("blogName") @NotEmpty String name,
                                     @QueryParam("searchText") String searchText
    ) {
        Long likedBeforeTimestamp = new Date().getTime();
        LinkedList<Post> posts = new LinkedList<>();
        boolean morePosts = true;

        while (morePosts) {

            TumblrResponse response = tumblrClient.getLikes(name, likedBeforeTimestamp);
            if (response instanceof TumblrSuccessResponse) {
                TumblrSuccessResponse success = (TumblrSuccessResponse) response;
                posts.addAll(success.getPosts());

                Long lastLikedBeforeTimestamp = posts.getLast().getLikedAt();

                if (likedBeforeTimestamp.equals(lastLikedBeforeTimestamp)) {
                    morePosts = false;
                } else {
                    likedBeforeTimestamp = lastLikedBeforeTimestamp;
                }
            } else {
                String errorMessage = response.getMeta().getMessage();
                // To Do: return an error instead
                posts.add(new Post(errorMessage));
                morePosts = false;
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
