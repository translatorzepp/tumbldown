package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.tumblr.TumblrResponseHandler;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;


@Path("blog")
@Produces(MediaType.APPLICATION_JSON)
public class BlogResource {

    private Tumblr tumblrClient;

    public BlogResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    @Timed
    public Blog getTumblrBlog(@QueryParam("blogName") @NotEmpty String name) {
        name = Blog.sanitizeBlogName(name);

        TumblrSuccessResponse response = TumblrResponseHandler.returnSuccessOrThrow(tumblrClient.getBlog(name), name);
        return response.getBlog();
    }
}
