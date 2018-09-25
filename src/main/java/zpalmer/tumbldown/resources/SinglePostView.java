package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;
import zpalmer.tumbldown.api.Post;


public class SinglePostView extends View {
    private final Post post;

    public SinglePostView(Post post) {
        super("post.ftlh");
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
