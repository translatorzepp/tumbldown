package zpalmer.tumbldown.api.tumblr;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import zpalmer.tumbldown.api.Blog;

public class BlogTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void blogFromWrappedJson() throws Exception {
        Blog blog = MAPPER.readValue(
                fixture("fixtures/blogonly.json"),
                Blog.class
        );

        assertThat(blog.getName()).isEqualTo("ritterssport");
        assertThat(blog.getNumberOfLikes()).isEqualTo(9312);
    }
}
