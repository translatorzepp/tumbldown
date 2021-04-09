package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
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
        TumblrResponse response = tumblrClient.getBlog(name);

        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;
            return success.getBlog();
        } else {
            int tumblrErrorStatusCode = response.getMeta().getStatus();
            String errorDetails;
            if (tumblrErrorStatusCode == 403) {
                errorDetails = name + " has restricted access to their blog.";
            } else if (tumblrErrorStatusCode == 404) {
                errorDetails = name + " does not exist.";
            } else if(tumblrErrorStatusCode >= 500) {
                errorDetails= "Tumblr is down or unreachable.";
            } else {
                errorDetails = "Unknown error: " + tumblrErrorStatusCode + ".";
            }
            throw new WebApplicationException(errorDetails, tumblrErrorStatusCode);
        }
    }
}
