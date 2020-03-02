package zpalmer.tumbldown.client;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.logging.LoggingFeature;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponseMeta;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tumblr {
    private static final String BASE_API_URL = "https://api.tumblr.com/v2";
    private static final String AUTHENTICATION = System.getenv("TUMBLR_API_KEY");
    private static final int LIMIT = 50;
    private static final int TUMBLR_LIMIT = LIMIT - 1; // because they zeroth this :|


    private Client client;

    public Tumblr(Client client) {
        this.client = client;
    }

    public TumblrResponse getBlog(String name) {
        // api.tumblr.com/v2/blog/{blog-identifier}/info?api_key={key}
        WebTarget target = target("blog/" + name + ".tumblr.com/" + "info");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        return makeRequestAndRespondToSuccessOrFailure(invocationBuilder);
    }

    public TumblrResponse getLikes(String blogName, Long beforeTime) {
        //api.tumblr.com/v2/blog/{blog-identifier}/likes?api_key={key}
        WebTarget likesTarget = target("blog/" + blogName + ".tumblr.com/" + "likes")
                .queryParam("limit", TUMBLR_LIMIT)
                .queryParam("before", beforeTime);

        Invocation.Builder invocationBuilder = likesTarget.request(MediaType.APPLICATION_JSON);

        return makeRequestAndRespondToSuccessOrFailure(invocationBuilder);
    }

    private TumblrResponse makeRequestAndRespondToSuccessOrFailure(Invocation.Builder invocationBuilder) {
        // TODO: do we need to handle exceptions thrown here?
        try {
            Response response = invocationBuilder.get();

            if (response.getStatus() == 200) {
                return response.readEntity(TumblrSuccessResponse.class);
            } else {
                Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME).log(Level.WARNING, response.getHeaders().toString());

                TumblrFailureResponse failResponse = response.readEntity(TumblrFailureResponse.class);
                Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME).log(Level.WARNING, failResponse.getMeta().getMessage());
                Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME).log(Level.WARNING, String.valueOf(failResponse.getMeta().getStatus()));

                return failResponse;
            }
        } catch (ProcessingException e) {
            Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME).log(Level.WARNING, e.toString());
            return new TumblrFailureResponse(new TumblrResponseMeta("Unable to complete request to tumblr", 504));
        }
    }

    private WebTarget target(String pathString) {
        return client.target(BASE_API_URL)
                .queryParam("api_key", AUTHENTICATION)
                .path(pathString);
    }
}
