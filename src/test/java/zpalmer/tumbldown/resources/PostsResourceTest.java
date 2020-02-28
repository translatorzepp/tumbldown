package zpalmer.tumbldown.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.*;
import javax.ws.rs.WebApplicationException;

import org.junit.Test;

import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponseMeta;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;


public class PostsResourceTest {
    private Tumblr fakeTumblr = mock(Tumblr.class);

    private static Post postWithSearchStringInSummary = new Post(12345L, "I feel pretty and witty and gay", new ArrayList<>());
    private static Post postWithoutSearchString = new Post(35813L, "I feel pretty and witty and bright", new ArrayList<>());
    private static Post postWithSearchStringInTag = new Post(11235L, "I love my wife!",
            new ArrayList<>(Collections.singletonList("gay")));

    private LinkedList<Post> posts = new LinkedList<>();

    @Test
    public void convertsStringDatesToEpoch() {
        Long expectedUtc = 1580601599L;
        Long actualUtc = PostsResource.convertDateStringToEpochTime("2020-02-01", "Africa/Abidjan");
        assertThat(actualUtc).isEqualTo(expectedUtc);

        Long expectedAltTimezone = 1577944799L;
        Long actualAltTimezone = PostsResource.convertDateStringToEpochTime("2020-01-01", "America/Chicago");
        assertThat(actualAltTimezone).isEqualTo(expectedAltTimezone);

        Long expectedAltTimezoneDaylightSavingsChange = 1601787599L;
        Long actualAltTimezoneDaylightSavingsChange = PostsResource.convertDateStringToEpochTime("2020-10-03", "America/Chicago");
        assertThat(expectedAltTimezoneDaylightSavingsChange).isEqualTo(actualAltTimezoneDaylightSavingsChange);

        Long expectedNoTimezone = 1577944799L;
        Long actualNoTimezone = PostsResource.convertDateStringToEpochTime("2020-01-01", "");
        assertThat(actualNoTimezone).isEqualTo(expectedNoTimezone);

        Long expectedNullInput = ZonedDateTime.now().toEpochSecond();
        Long actualNullInput = PostsResource.convertDateStringToEpochTime(null, null);
        assertThat(actualNullInput).isEqualTo(expectedNullInput);
    }

    @Test
    public void selectOnlyPostsWithSearchTerm() {
        posts.add(postWithSearchStringInSummary);
        posts.add(postWithoutSearchString);
        posts.add(postWithSearchStringInTag);

        Collection<Post> searchResults = new PostsResource(mock(Tumblr.class))
                .filterPostsBySearchString(posts, "gay");

        assertThat(searchResults).contains(postWithSearchStringInTag);
        assertThat(searchResults).doesNotContain(postWithoutSearchString);
        assertThat(searchResults).contains(postWithSearchStringInTag);
    }

    @Test
    public void closesOutputOnResults() {
        when(fakeTumblr.getLikes(eq("blog-for-all"), anyLong())).thenReturn(
                new TumblrSuccessResponse()
        );
        PostsResource goodPostsResource = new PostsResource(fakeTumblr);
        assertThat(
                goodPostsResource.searchLikes("blog-for-all", "", "", "").isClosed()
        ).isTrue();
    }

    @Test
    public void closesOutputOnErrors() {
        when(fakeTumblr.getLikes(eq("secret-blog"), anyLong())).thenReturn(
                new TumblrFailureResponse(new TumblrResponseMeta("Forbidden", 403)));
        PostsResource failPostsResource = new PostsResource(fakeTumblr);
        assertThat(
                failPostsResource.searchLikes("secret-blog", "", "", "").isClosed()
        ).isTrue();
    }

    @Test
    public void translatesExpectedErrors() {
        when(fakeTumblr.getLikes("secret-blog", 1L)).thenReturn(
                new TumblrFailureResponse(new TumblrResponseMeta("Forbidden", 403)));
        PostsResource failPostsResource = new PostsResource(fakeTumblr);

        assertThatExceptionOfType(WebApplicationException.class).isThrownBy(() -> {
            failPostsResource.getLikesBefore("secret-blog",1L);
        }).withMessageContaining("secret-blog's likes are not public.");
    }

    @Test
    public void translatesUnexpectedErrors() {
        when(fakeTumblr.getLikes("secret-blog", 1L)).thenReturn(
                new TumblrFailureResponse(new TumblrResponseMeta("???", 422)));
        PostsResource failPostsResource = new PostsResource(fakeTumblr);

        assertThatExceptionOfType(WebApplicationException.class).isThrownBy(() -> {
            failPostsResource.getLikesBefore("secret-blog",1L);
        }).withMessageContaining("Unknown error: 422.");
    }
}
