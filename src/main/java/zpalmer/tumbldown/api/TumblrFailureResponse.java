package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TumblrFailureResponse {
    private TumblrResponseMeta meta;

    public TumblrFailureResponse() {}

    @JsonProperty
    public TumblrResponseMeta getMeta() { return meta; }
}
