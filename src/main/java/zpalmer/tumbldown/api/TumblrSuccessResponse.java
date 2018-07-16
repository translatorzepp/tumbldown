package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class TumblrSuccessResponse {
    private Map<String, ?> meta;
    private Map<String, Map<String, String>> response;

    public TumblrSuccessResponse() {}

    @JsonProperty
    public Map<String, ?> getMeta() {return meta; }
    public Map<String, Map<String, String>> getResponse() { return response; }
}
