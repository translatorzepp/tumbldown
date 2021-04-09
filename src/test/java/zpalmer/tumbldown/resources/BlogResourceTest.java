package zpalmer.tumbldown.resources;

import org.junit.Test;
import zpalmer.tumbldown.api.Blog;
import static org.assertj.core.api.Assertions.assertThat;

public class BlogResourceTest {
    @Test
    public void sanitizeBlogName() {
        String fullBlogName = "tumbldown-app.tumblr.com";
        String spaceBlogName = "  tumbldown-app ";
        String shortBlogName = "tumbldown-app";
        String capitalizedBlogName = "TumblDown-app";

        assertThat(Blog.sanitizeBlogName(fullBlogName)).isEqualTo("tumbldown-app");
        assertThat(Blog.sanitizeBlogName(spaceBlogName)).isEqualTo("tumbldown-app");
        assertThat(Blog.sanitizeBlogName(shortBlogName)).isEqualTo("tumbldown-app");
        assertThat(Blog.sanitizeBlogName(capitalizedBlogName)).isEqualTo("tumbldown-app");
    }
}
