package zpalmer.tumbldown.resources;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;

@Path("random")
@Produces(MediaType.TEXT_HTML)
public class RandomResource {
    private Tumblr tumblrClient;

    public RandomResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    public RandomView displayRandomInput() {
        return new RandomView();
    }

    @GET
    @Timed
    @Path("like")
    public RandomLikedPostView randomLikedPost(@QueryParam("blogName") @NotEmpty @NotNull String blogName)
        throws WebApplicationException {

        tumblrClient.getBlog(blogName);

        TumblrResponse response = tumblrClient.getLikesByPage(blogName);

        // TODO: extract below to its own service style thingy
        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;
            Collection<Post> likedPosts = success.getPosts();

            Optional<Post> randomPost = likedPosts
                .stream()
                .skip(new Random().nextInt(likedPosts.size()))
                .findFirst();
            return new RandomLikedPostView(randomPost.get(), blogName);
            // TODO: handle getPosts being null and getPosts.stream.findFirst() being empty
        } else {
            int tumblrErrorStatusCode = response.getMeta().getStatus();
            String errorDetails;
            if (tumblrErrorStatusCode == 403) {
                errorDetails = blogName + "'s likes are not public.";
            } else if (tumblrErrorStatusCode == 404) {
                errorDetails = blogName + " does not exist.";
            } else if (tumblrErrorStatusCode == 429) {
                // TODO: extract time details from X-Ratelimit headers and suggest a time window in the error message
                errorDetails = "Tumblr thinks tumbldown is making too many requests. Wait a while and try again.";
            } else if(tumblrErrorStatusCode >= 500) {
                errorDetails= "Tumblr is down or unreachable.";
            } else {
                errorDetails = "Unknown error: " + tumblrErrorStatusCode + ".";
            }
            throw new WebApplicationException(errorDetails, tumblrErrorStatusCode);
        }
    }
}