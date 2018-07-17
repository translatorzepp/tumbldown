package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.client.Tumblr;

@Path("tumblr_blog")
@Produces(MediaType.APPLICATION_JSON)
public class BlogResource {

    private Tumblr tumblrClient;

    public BlogResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    @Timed
    public Blog getTumblrBlog(@QueryParam("name") @NotEmpty String name) {
        return tumblrClient.getBlog(name);
    }
    // To Do: add exception handling here for a failure
}