package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TumblrResponseMeta {
    private String message;
    private int status;

    public TumblrResponseMeta() {}

    @JsonProperty("msg")
    public String getMessage() { return message; }

    @JsonProperty
    public int getStatus() { return status; }
}