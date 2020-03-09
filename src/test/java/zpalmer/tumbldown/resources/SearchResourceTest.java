package zpalmer.tumbldown.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.*;
import javax.ws.rs.WebApplicationException;

import org.junit.Test;

import org.mockito.Mockito;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponseMeta;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;


public class SearchResourceTest {
    private Tumblr fakeTumblr = mock(Tumblr.class);

    private static Post postWithSearchStringInSummary = new Post(12345L, "I feel pretty and witty and gay", new ArrayList<>());
    private static Post postWithoutSearchString = new Post(35813L, "I feel pretty and witty and bright", new ArrayList<>());
    private static Post postWithSearchStringInTag = new Post(11235L, "I love my wife!",
            new ArrayList<>(Collections.singletonList("gay")));

    private LinkedList<Post> posts = new LinkedList<>();

    @Test
    public void selectOnlyPostsWithSearchTerm() {
        posts.add(postWithSearchStringInSummary);
        posts.add(postWithoutSearchString);
        posts.add(postWithSearchStringInTag);

        Collection<Post> searchResults = new SearchResource(fakeTumblr)
                .filterPostsBySearchString(posts, "gay");

        assertThat(searchResults).contains(postWithSearchStringInTag);
        assertThat(searchResults).doesNotContain(postWithoutSearchString);
        assertThat(searchResults).contains(postWithSearchStringInTag);
    }

    @Test
    public void searchHandleNullTimestamp() {
        when(fakeTumblr.getLikes(anyString(), anyLong())).thenReturn(new TumblrSuccessResponse());
        SearchResource searchResource = new SearchResource(fakeTumblr);

        searchResource.displayResultsPage("tumbldown", "text", null);
        Mockito.verify(fakeTumblr, Mockito.times(1))
                .getLikes("tumbldown", ZonedDateTime.now().toEpochSecond());
    }

    @Test
    public void translatesExpectedErrors() {
        when(fakeTumblr.getLikes("secret-blog", 1L)).thenReturn(
                new TumblrFailureResponse(new TumblrResponseMeta("Forbidden", 403)));
        SearchResource failSearchResource = new SearchResource(fakeTumblr);

        assertThatExceptionOfType(WebApplicationException.class).isThrownBy(() -> {
            failSearchResource.getLikesBefore("secret-blog",1L);
        }).withMessageContaining("secret-blog's likes are not public.");
    }

    @Test
    public void translatesUnexpectedErrors() {
        when(fakeTumblr.getLikes("secret-blog", 1L)).thenReturn(
                new TumblrFailureResponse(new TumblrResponseMeta("???", 422)));
        SearchResource failSearchResource = new SearchResource(fakeTumblr);

        assertThatExceptionOfType(WebApplicationException.class).isThrownBy(() -> {
            failSearchResource.getLikesBefore("secret-blog",1L);
        }).withMessageContaining("Unknown error: 422.");
    }
}
