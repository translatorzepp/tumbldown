package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TumblrResponseMeta {
    private String message;
    private int status;

    public TumblrResponseMeta() {}

    public TumblrResponseMeta(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @JsonProperty("msg")
    public String getMessage() { return message; }

    @JsonProperty
    public int getStatus() { return status; }
}
