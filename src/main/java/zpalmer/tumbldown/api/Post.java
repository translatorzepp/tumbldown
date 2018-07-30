package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private String creatorBlogName;
    private String likedFromBlogName;
    private String summary;
    private ArrayList<String> tags;
    private String type;
    private URL url;

    public Post() { }

    @JsonProperty("source_title")
    public String getCreatorBlogName() { return creatorBlogName; }

    @JsonProperty("blog_name")
    public String getLikedFromBlogName() { return likedFromBlogName; }

    @JsonProperty
    public String getSummary() { return summary; }
    public ArrayList<String> getTags() { return tags; }
    public String getType() { return type; }

    @JsonProperty("post_url")
    public URL getUrl() { return url; }
}
