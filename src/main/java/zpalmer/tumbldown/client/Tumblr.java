package zpalmer.tumbldown.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;

public class Tumblr {
    private static final String BASE_API_URL = "https://api.tumblr.com/v2";
    private static final String AUTHENTICATION = "fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4";
    private static final int LIMIT = 50;

    private WebTarget baseTarget;

    private Client client;

    public Tumblr(Client client) {
        this.client = client;
        this.baseTarget = client.target(BASE_API_URL)
                .queryParam("api_key", AUTHENTICATION);
    }

    public TumblrResponse getBlog(String name) {
        // api.tumblr.com/v2/blog/{blog-identifier}/info?api_key={key}
        Invocation.Builder invocationBuilder = blogRequestInvocationBuilder(name, "info");
        Response response = invocationBuilder.get();

        return respondToSuccessOrFailure(response);
    }

    public TumblrResponse getLikes(String blogName) {
        //api.tumblr.com/v2/blog/{blog-identifier}/likes?api_key={key}
        WebTarget likesTarget = baseTarget.path("blog/" + blogName + ".tumblr.com/" + "likes");
        likesTarget = likesTarget.queryParam("limit", LIMIT);
        Invocation.Builder invocationBuilder = likesTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        return respondToSuccessOrFailure(response);
    }

    private Invocation.Builder blogRequestInvocationBuilder(String blogName, String blogSubPath) {
        WebTarget target = baseTarget.path("blog/" + blogName + ".tumblr.com/" + blogSubPath);
        return target.request(MediaType.APPLICATION_JSON);
    }

    private TumblrResponse respondToSuccessOrFailure(Response response) {
        if (response.getStatus() == 200) {
            TumblrSuccessResponse successResponse = response.readEntity(TumblrSuccessResponse.class);

            return successResponse;
        } else {
            TumblrFailureResponse failResponse = response.readEntity(TumblrFailureResponse.class);
            System.out.println(failResponse.getMeta().getMessage());

            return failResponse;
        }
    }
}
