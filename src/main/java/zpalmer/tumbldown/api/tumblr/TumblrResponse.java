package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TumblrResponse {
    private TumblrResponseMeta meta;

    @JsonProperty
    public TumblrResponseMeta getMeta() {
        return meta;
    };

}
