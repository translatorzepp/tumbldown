package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TumblrResponse {
    private TumblrResponseMeta meta;

    public TumblrResponse() {}
    public TumblrResponse(TumblrResponseMeta meta) {
        this.meta = meta;
    }

    @JsonProperty
    public TumblrResponseMeta getMeta() {
        return meta;
    }

}
