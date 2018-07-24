package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    private String name;
    private int numberOfLikes;

    public Blog() { }

    public Blog(String name, int numberOfLikes) {
        this.name = name;
        this.numberOfLikes = numberOfLikes;
    }

    @JsonProperty
    public String getName() { return name; }

    @JsonProperty("likes")
    public long getNumberOfLikes() { return numberOfLikes; }
}