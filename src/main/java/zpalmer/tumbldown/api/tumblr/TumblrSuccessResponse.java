package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonProperty;

import zpalmer.tumbldown.api.Blog;

import java.util.Map;


public class TumblrSuccessResponse extends TumblrResponse {
    private Blog blog;

    public TumblrSuccessResponse() { }

    @JsonProperty
    public Blog getBlog() { return blog; }

    @JsonProperty("response")
    private void unpackBlogFromResponse(Map<String,Blog> response) {
        blog = response.get("blog");
    }
}
