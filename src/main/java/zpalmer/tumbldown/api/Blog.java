package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    private String name;
    private Long numberOfLikes;

    public Blog() { }

    public Blog(String name, Long numberOfLikes) {
        this.name = name;
        this.numberOfLikes = numberOfLikes;
    }

    @JsonProperty
    public String getName() { return name; }

    @JsonProperty("likes")
    public Long getNumberOfLikes() { return numberOfLikes; }

    public static String sanitizeBlogName(String name) {
        return name.toLowerCase().replace(".tumblr.com", "").trim();
    }
}
