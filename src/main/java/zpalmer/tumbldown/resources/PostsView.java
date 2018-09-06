package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;
import zpalmer.tumbldown.api.Post;

import java.util.LinkedList;

public class PostsView extends View {
    private final LinkedList<Post> posts;

    public PostsView(LinkedList<Post> posts) {
        super("liked-posts.ftl");
        this.posts = posts;
    }

    public LinkedList<Post> getPosts() {
        return posts;
    }
}
