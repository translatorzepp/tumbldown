package zpalmer.tumbldown.api.tumblr;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;


public class TumblrSuccessResponseTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void tumblrSuccessResponseBlogFromJson() throws Exception {
        TumblrSuccessResponse response = MAPPER.readValue(
                fixture("fixtures/tumblrsuccessresponse-bloginfo.json"),
                TumblrSuccessResponse.class);

        assertThat(response.getMeta().getMessage()).isEqualTo("OK");
    }

    @Test
    public void tumblrSuccessResponsePostsFromJson() throws Exception {
        TumblrSuccessResponse response = MAPPER.readValue(
                fixture("fixtures/tumblrsuccessresponse-posts.json"),
                TumblrSuccessResponse.class);

        assertThat(response.getMeta().getMessage()).isEqualTo("OK");
        assertThat(response.getPosts()).hasSize(1);
    }
}
