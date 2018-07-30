package zpalmer.tumbldown.api.tumblr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.Post;

import java.util.Collection;
import java.util.Map;


public class TumblrSuccessResponse extends TumblrResponse {
    private Blog blog;
    private Collection<Post> posts;
    private ObjectMapper mapper = new ObjectMapper();

    public TumblrSuccessResponse() { }

    @JsonProperty
    public Blog getBlog() { return blog; }
    public Collection<Post> getPosts() { return posts; }

    @JsonProperty("response")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private void unpackFromResponse(Map<String, Object> response) {
        blog = mapper.convertValue(response.get("blog"), Blog.class);
        posts = (Collection<Post>)response.get("liked_posts");
    }
}
