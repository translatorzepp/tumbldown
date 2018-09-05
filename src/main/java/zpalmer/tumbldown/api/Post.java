package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    // TODO: turn this into an abstract class and subclass the various post types, including an unknown type with only the common fields!

    private String creatorBlogName;
    private Integer id;
    private Long likedAt;
    private String likedFromBlogName;
    private String summary;
    private ArrayList<String> tags;
    private String type;
    private URL url;

    public Post() { }

    public Post(Integer id, String summary) {
        this.id = id;
        this.summary = summary;
    }

    @JsonProperty("blog_name")
    public String getLikedFromBlogName() { return likedFromBlogName; }

    @JsonProperty("source_title")
    public String getCreatorBlogName() { return creatorBlogName; }

    @JsonProperty
    public Integer getId() { return id; }
    public String getSummary() { return summary; }
    public ArrayList<String> getTags() { return tags; }
    public String getType() { return type; }

    @JsonProperty("post_url")
    public URL getUrl() { return url; }

    @JsonProperty("liked_timestamp")
    public Long getLikedAt() { return likedAt; }

    public boolean equals(Post otherPost) {
        if (getId() != null && otherPost.getId() != null) {
            return getId().equals(otherPost.getId());
        } else {
            return super.equals(otherPost);
        }
    }

    public boolean containsText( String searchText) {
        final String casedSearchText = searchText.toLowerCase();

        if (getSummary().toLowerCase().contains(casedSearchText)) {
            return true;
        }

        // TO DO: add searching of tags and other text-based fields

        return false;
    }
}
