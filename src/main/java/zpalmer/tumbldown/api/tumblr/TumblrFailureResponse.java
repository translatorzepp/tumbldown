package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TumblrFailureResponse extends TumblrResponse {

    public TumblrFailureResponse() {}
    public TumblrFailureResponse(TumblrResponseMeta meta) {
        super(meta);
    }

}
