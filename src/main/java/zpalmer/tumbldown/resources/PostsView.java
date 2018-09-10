package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;
import zpalmer.tumbldown.api.Post;

import java.util.LinkedList;

public class PostsView extends View {
    private final LinkedList<Post> posts;
    private final String originalSearch;

    public PostsView(LinkedList<Post> posts, String originalSearchText) {
        super("liked-posts.ftl");
        this.posts = posts;
        this.originalSearch = originalSearchText;
    }

    public LinkedList<Post> getPosts() {
        return posts;
    }

    public String getOriginalSearch() {
        return originalSearch;
    }
}
