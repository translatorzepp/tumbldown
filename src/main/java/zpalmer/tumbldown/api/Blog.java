package zpalmer.tumbldown.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Blog {
    private String name;
    private int numberOfLikes;

    public Blog() {
    }

    public Blog(String name, int numberOfLikes) {
        this.name = name;
        this.numberOfLikes = numberOfLikes;
    }

    @JsonProperty
    public String getName() { return name; }
    public long getNumberOfLikes() { return numberOfLikes; }
}