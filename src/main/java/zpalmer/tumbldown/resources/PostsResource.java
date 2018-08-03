package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
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
    public Collection<Post> getLikes(@QueryParam("blogName") @NotEmpty String name, @QueryParam("searchText") String searchText) {
        TumblrResponse response = tumblrClient.getLikes(name);
        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;
            Collection<Post> posts = success.getPosts();
            return filterPostsBySearchString(posts, searchText);
        } else {
            String errorMessage = ((TumblrFailureResponse) response).getMeta().getMessage();
            // To Do: return an error instead
            return Arrays.asList(new Post());
        }
    }

    protected Collection<Post> filterPostsBySearchString(Collection<Post> posts, String searchText) {
        Iterator postIterator = posts.iterator();

        postIterator.forEachRemaining(post -> {
            if (!(((Post) post).containsText(searchText))) {
                // THIS IS THE WORST
                postIterator.remove();
            }
        });

        return posts;
    }
}
