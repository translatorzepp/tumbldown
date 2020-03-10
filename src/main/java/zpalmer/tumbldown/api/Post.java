package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    // TODO: turn this into an abstract class and subclass the various post types, including an unknown type with only the common fields!
    private String creatorBlogName;
    private Long id;
    private Long likedAt;
    private String likedFromBlogName;
    private String slug;
    private String summary;
    private ArrayList<String> tags;
    private String type;
    private URL url;
    private URI appUrl;

    public Post() { }

    public Post(Long id, String summary, ArrayList<String> tags) {
        this.id = id;
        this.summary = summary;
        this.tags = tags;
    }

    @JsonProperty("blog_name")
    public String getLikedFromBlogName() { return likedFromBlogName; }

    @JsonProperty("source_title")
    public String getCreatorBlogName() { return creatorBlogName; }

    @JsonProperty
    public Long getId() { return id; }
    public String getSummary() { return summary; }
    public ArrayList<String> getTags() { return tags; }
    public String getType() { return type; }

    @JsonProperty("post_url")
    public URL getUrl() { return url; }

    @JsonProperty("liked_timestamp")
    public Long getLikedAt() { return likedAt; }

    public URI getAppUrl() {
        String httpsSchemeLink = "https://www.tumblr.com/open/app?referrer=tumblr_new_iframe&app_args=blog?blogName=" + likedFromBlogName + "&page=permalink&postID=" + id;
        // ^ works but only on a Long Press > Open in Tumblr App
        try {
            return new URI(httpsSchemeLink);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public boolean equals(Post otherPost) {
        if (getId() != null && otherPost.getId() != null) {
            return getId().equals(otherPost.getId());
        } else {
            return super.equals(otherPost);
        }
    }

    public boolean containsText(String searchText) {
        final String casedSearchText = searchText.toLowerCase();

        if (getSummary().toLowerCase().contains(casedSearchText)) {
            return true;
        }

        if (getTags() != null) {
            Optional<String> matchingTag =
                    getTags().stream().filter(tag -> tag.toLowerCase().contains(casedSearchText)).findFirst();
            return matchingTag.isPresent();
        }

        return false;
    }
}
