package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class TumblrLikesSearchResource {

//    @GET
//    @Timed
//     public Posts searchATumblrsLikes(@QueryParam("blog") String blog, @QueryParam("search_terms") String pattern) {
//          // pass blog into TumblrLikesFetcher to get back a bunch of Posts
//          // pass Posts + search_terms into SomethingThatParsesPosts and returns matching posts
//          // return new Posts(resultOfTumblrLikesFetcher);
//    }
}