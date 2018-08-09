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
    private static final String AUTHENTICATION = System.getenv("TUMBLR_API_KEY");
    private static final int LIMIT = 50;
    private static final int TUMBLR_LIMIT = LIMIT - 1; // because they zeroth this :|


    private Client client;

    public Tumblr(Client client) {
        this.client = client;
    }

    // TODO: handle exceptions thrown by the client

    public TumblrResponse getBlog(String name) {
        // api.tumblr.com/v2/blog/{blog-identifier}/info?api_key={key}
        WebTarget target = target("blog/" + name + ".tumblr.com/" + "info");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        return makeRequestAndRespondToSuccessOrFailure(invocationBuilder);
    }

    public TumblrResponse getLikes(String blogName, long beforeTime) {
        //api.tumblr.com/v2/blog/{blog-identifier}/likes?api_key={key}
        WebTarget likesTarget = target("blog/" + blogName + ".tumblr.com/" + "likes")
                .queryParam("limit", TUMBLR_LIMIT)
                .queryParam("before", beforeTime);

        Invocation.Builder invocationBuilder = likesTarget.request(MediaType.APPLICATION_JSON);

        return makeRequestAndRespondToSuccessOrFailure(invocationBuilder);
    }

    private TumblrResponse makeRequestAndRespondToSuccessOrFailure(Invocation.Builder invocationBuilder) {
        Response response = invocationBuilder.get();

        if (response.getStatus() == 200) {
            TumblrSuccessResponse successResponse = response.readEntity(TumblrSuccessResponse.class);

            return successResponse;
        } else {
            System.out.println(response.getHeaders().toString());

            TumblrFailureResponse failResponse = response.readEntity(TumblrFailureResponse.class);
            System.out.println(failResponse.getMeta().getMessage());

            return failResponse;
        }
    }

    private WebTarget target(String pathString) {
        return client.target(BASE_API_URL)
                .queryParam("api_key", AUTHENTICATION)
                .path(pathString);
    }
}
