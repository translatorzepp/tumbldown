package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import zpalmer.tumbldown.api.Blog;

@Path("tumblr_blog")
@Produces(MediaType.APPLICATION_JSON)
public class BlogResource {
    @GET
    @Timed
    public Blog getTumblrBlog(@QueryParam("name") String name) {
        int madeupNmberOfLikes = 45;
        return new Blog(name, madeupNmberOfLikes);
    }
}