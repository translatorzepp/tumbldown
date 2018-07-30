package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import java.util.Arrays;
import java.util.Collection;
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
    public Collection<Post> getLikes(@QueryParam("blogName") @NotEmpty String name) {
        TumblrResponse response = tumblrClient.getLikes(name);
        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;
            return success.getPosts();
        } else {
            String errorMessage = ((TumblrFailureResponse) response).getMeta().getMessage();
            // To Do: return an error instead
            return Arrays.asList(new Post());
        }
    }
}
