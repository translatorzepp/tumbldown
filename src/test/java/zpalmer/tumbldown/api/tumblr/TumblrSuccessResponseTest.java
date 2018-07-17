package zpalmer.tumbldown.api.tumblr;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;


public class TumblrSuccessResponseTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void tumblrSuccessResponseFromJson() throws Exception {
        TumblrSuccessResponse response = MAPPER.readValue(
                fixture("fixtures/tumblrsuccessresponse-bloginfo.json"),
                TumblrSuccessResponse.class);

        assertThat(response.getMeta().getMessage().equals("OK"));
    }

}
