package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import static java.lang.Integer.parseInt;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;

@Path("tumblr_blog")
@Produces(MediaType.APPLICATION_JSON)
public class BlogResource {

    private Tumblr tumblrClient;

    public BlogResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    @Timed
    public Blog getTumblrBlog(@QueryParam("name") @NotEmpty String name) {
        TumblrResponse response = tumblrClient.getBlog(name);
        if (response instanceof TumblrSuccessResponse) {
            Map<String, String> blog = ((TumblrSuccessResponse) response).getResponse().get("blog");
            return new Blog(blog.get("name"),
                    parseInt(blog.get("likes"), 10));
        } else {
            // To Do: add exception handling here for a failure
            String errorMessage = ((TumblrFailureResponse) response).getMeta().getMessage();
            return new Blog(errorMessage, 0);
        }
    }
}
