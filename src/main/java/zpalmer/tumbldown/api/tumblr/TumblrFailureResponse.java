package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TumblrFailureResponse implements TumblrResponse {
    private TumblrResponseMeta meta;

    public TumblrFailureResponse() {}

    @JsonProperty
    public TumblrResponseMeta getMeta() { return meta; }
}
