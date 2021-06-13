package zpalmer.tumbldown.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.*;
import javax.ws.rs.WebApplicationException;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import org.mockito.Mockito;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponseMeta;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;


public class SearchResourceTest {
    private Tumblr fakeTumblr = mock(Tumblr.class);

    private static Post postWithSearchStringInSummary = new Post(12345L, "I feel pretty and witty and gay", new ArrayList<>(), "quote");
    private static Post postWithoutSearchString = new Post(35813L, "I feel pretty and witty and bright", new ArrayList<>());
    private static Post postWithSearchStringInTag = new Post(11235L, "I love my wife!",
            new ArrayList<>(Collections.singletonList("gay")));

    private static Post postOfTypeText = new Post(4L, "summary", "text");
    private static Post postOfTypeVideo = new Post(5L, "summary", "video");
    private static Post postOfTypeImage = new Post(6L, "summary", "photo");
    private static Post postOfTypeQuote = new Post(7L, "gay summary", "quote");

    @Test
    public void selectOnlyPostsWithSearchTerm() {
        LinkedList<Post> posts = new LinkedList<>();
        posts.add(postWithSearchStringInSummary);
        posts.add(postWithoutSearchString);
        posts.add(postWithSearchStringInTag);

        Collection<Post> searchResults = new SearchResource(fakeTumblr)
                .filterPostsBySearchCriteria(posts, "gay", null);

        assertThat(searchResults).contains(postWithSearchStringInSummary);
        assertThat(searchResults).contains(postWithSearchStringInTag);
        assertThat(searchResults).doesNotContain(postWithoutSearchString);
    }

    @Test
    public void selectOnlyPostsOfMatchingType() {
        LinkedList<Post> posts = new LinkedList<>();
        posts.add(postOfTypeText);
        posts.add(postOfTypeVideo);
        posts.add(postOfTypeImage);
        posts.add(postOfTypeQuote);

        Collection<Post> searchResults = new SearchResource(fakeTumblr)
                .filterPostsBySearchCriteria(posts,
                        "",
                        new ImmutableList.Builder<String>()
                                .add("video")
                                .add("quote")
                                .build());

        assertThat(searchResults).containsExactly(postOfTypeVideo, postOfTypeQuote);
    }

    @Test
    public void selectOnlyPostsLikedFromMatchingUser() {
        var postLikedFromUser = new Post();
        var postLikedFromOtherUser = new Post();
        var postLikedFromUnknownUser = new Post();
        LinkedList<Post> posts = new LinkedList<>();
        posts.add(postLikedFromUnknownUser);
        posts.add(postLikedFromUser);
        posts.add(postLikedFromOtherUser);

        Collection<Post> searchResults = new SearchResource(fakeTumblr)
                .filterPostsBySearchCriteria(posts,
                        "",
                        new ImmutableList.Builder<String>()
                                .build());

        assertThat(searchResults).containsExactly(postLikedFromUser);
    }

    @Test
    public void selectOnlyPostsThatMeetMultipleCriteria() {
        LinkedList<Post> posts = new LinkedList<>();
        posts.add(postWithSearchStringInSummary); // match gay and quote
        posts.add(postWithoutSearchString); // match neither
        posts.add(postOfTypeText); // match neither
        posts.add(postOfTypeVideo); // match neither
        posts.add(postOfTypeImage); // match neither
        posts.add(postOfTypeQuote); // match gay and quote
        posts.add(postWithSearchStringInTag); // match gay but not quote
        posts.add(new Post(8L, "straight summary", "quote")); // match quote but not gay

        Collection<Post> searchResults = new SearchResource(fakeTumblr)
                .filterPostsBySearchCriteria(posts,
                        "gay",
                        new ImmutableList.Builder<String>()
                                .add("video")
                                .add("quote")
                                .build());

        assertThat(searchResults).containsExactly(postWithSearchStringInSummary, postOfTypeQuote);
    }

    @Test
    public void searchPassesThroughGoodTimestamp() {
        when(fakeTumblr.getLikes(anyString(), anyLong())).thenReturn(new TumblrSuccessResponse());
        SearchResource searchResource = new SearchResource(fakeTumblr);

        searchResource.displayResultsPage("tumbldown", "text", "1111111111", null);
        Mockito.verify(fakeTumblr, Mockito.times(1))
                .getLikes("tumbldown", 1111111111L);
    }

    @Test
    public void searchHandleBadTimestamp() {
        when(fakeTumblr.getLikes(anyString(), anyLong())).thenReturn(new TumblrSuccessResponse());
        SearchResource searchResource = new SearchResource(fakeTumblr);

        searchResource.displayResultsPage("tumbldown", "text", null, null);
        searchResource.displayResultsPage("tumbldown", "text", "NaN", null);
        searchResource.displayResultsPage("tumbldown", "text", "undefined", null);
        searchResource.displayResultsPage("tumbldown", "text", "0", null);
        Mockito.verify(fakeTumblr, Mockito.times(4))
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
        }).withMessageContaining("Unknown error: 422 - ???");
    }
}
