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
import zpalmer.tumbldown.api.tumblr.TumblrResponseHandler;
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

        TumblrSuccessResponse blogResponse = TumblrResponseHandler.returnSuccessOrThrow(
            tumblrClient.getBlog(blogName),
            blogName
        );

        Long numberOfLikes = blogResponse.getBlog().getNumberOfLikes();
        if (numberOfLikes <= 0) {
            throw new WebApplicationException(blogName + " doesn't appear to have liked anything.", 404);
        }
        int offset = randomOffset(numberOfLikes);

        TumblrSuccessResponse likesResponse = TumblrResponseHandler.returnSuccessOrThrow(
            tumblrClient.getLikesByOffset(blogName, offset),
            blogName
        );

        // TODO: handle getPosts being null and getPosts.stream.findFirst() being empty
        Collection<Post> likedPosts = likesResponse.getPosts();
        Optional<Post> randomPost = likedPosts
            .stream()
            .skip(new Random().nextInt(likedPosts.size()))
            .findFirst();

        return new RandomLikedPostView(randomPost.get(), blogName);
    }

    private int randomOffset(Long numberOfLikes) {
        final int tumblrOffsetLimit = 1000;
        int upperBound = upperBoundForOffset(numberOfLikes, tumblrOffsetLimit);

        return new Random().nextInt(upperBound) + 1;
    }

    protected int upperBoundForOffset(Long numberOfLikes, int limit) {
        int upperBound;

        // apparently it's legal to compare ints and longs like this
        if (numberOfLikes < limit) {
            upperBound = Math.toIntExact(numberOfLikes - 1);
        } else {
            upperBound = limit - 1;
        }
        // This is -1 to number because if you have X likes, searching for an offset of X might produce NO likes
        return upperBound;
    }
}