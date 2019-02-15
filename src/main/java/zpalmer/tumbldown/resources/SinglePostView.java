package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;
import zpalmer.tumbldown.api.Post;

import java.nio.charset.Charset;

public class SinglePostView extends View {
    private final Post post;

    public SinglePostView(Post post) {
        super("post.ftlh", Charset.forName("UTF-8"));
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
