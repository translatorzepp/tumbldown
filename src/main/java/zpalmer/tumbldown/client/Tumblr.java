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
    private String baseUrl = "https://api.tumblr.com/v2";
    private String authentication = "fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4";

    private WebTarget baseTarget;

    private Client client;

    public Tumblr(Client client) {
        this.client = client;
        this.baseTarget = client.target(baseUrl)
                .queryParam("api_key", authentication);
    }

    public TumblrResponse getBlog(String name) {
        // api.tumblr.com/v2/blog/{blog-identifier}/info?api_key={key}
        Invocation.Builder invocationBuilder = blogRequestInvocationBuilder(name, "info");
        Response response = invocationBuilder.get();

        return respondToSuccessOrFailure(response);
    }

    public TumblrResponse getLikes(String blogName) {
        //api.tumblr.com/v2/blog/{blog-identifier}/likes?api_key={key}
        Invocation.Builder invocationBuilder = blogRequestInvocationBuilder(blogName, "likes");
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
