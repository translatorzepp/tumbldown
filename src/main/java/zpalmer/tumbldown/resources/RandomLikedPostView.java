package zpalmer.tumbldown.resources;

import java.nio.charset.Charset;

import io.dropwizard.views.View;
import zpalmer.tumbldown.api.Post;

public class RandomLikedPostView extends View {
    private final Post post;
    private final String blogName;

    public RandomLikedPostView(Post post, String blogName) {
        super("random_result.ftlh", Charset.forName("UTF-8"));
        this.post = post;
        this.blogName = blogName;
    }

    public Post getPost() {
        return post;
    }

    public String getBlogName() {
        return blogName;
    }
}
