package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TumblrFailureResponse extends TumblrResponse {

    public TumblrFailureResponse() {}
    public TumblrFailureResponse(TumblrResponseMeta meta) {
        super(meta);
    }

}
