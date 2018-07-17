package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TumblrSuccessResponse {
    private TumblrResponseMeta meta;
    private Map<String, Map<String, String>> response;

    public TumblrSuccessResponse() {}

    @JsonProperty
    public TumblrResponseMeta getMeta() { return meta; }
    public Map<String, Map<String, String>> getResponse() { return response; }
}
