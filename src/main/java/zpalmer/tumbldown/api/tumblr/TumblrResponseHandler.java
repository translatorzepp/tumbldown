package zpalmer.tumbldown.api.tumblr;

import javax.ws.rs.WebApplicationException;

public class TumblrResponseHandler {
    public static TumblrSuccessResponse returnSuccessOrThrow(TumblrResponse response, String blogName) throws WebApplicationException {
        if (response instanceof TumblrSuccessResponse) {
            return (TumblrSuccessResponse) response;

        } else {
            int tumblrErrorStatusCode = response.getMeta().getStatus();
            String errorDetails;

            if (tumblrErrorStatusCode == 403) {
                errorDetails = blogName + "'s likes are not public.";
            } else if (tumblrErrorStatusCode == 404) {
                errorDetails = blogName + " does not exist.";
            } else if (tumblrErrorStatusCode == 429) {
                errorDetails = "Tumblr thinks tumbldown is making too many requests. Wait a minute or two and refresh the page to try again.";
            } else if (tumblrErrorStatusCode >= 500) {
                errorDetails= "Tumblr is down or unreachable.";
            } else {
                errorDetails = "Unknown error: " + tumblrErrorStatusCode + " - " + response.getMeta().getMessage();
            }

            throw new WebApplicationException(errorDetails, tumblrErrorStatusCode);
        }
   } 
}
