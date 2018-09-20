package zpalmer.tumbldown.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.Test;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrFailureResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponseMeta;
import zpalmer.tumbldown.client.Tumblr;

import javax.ws.rs.WebApplicationException;


public class PostsResourceTest {
    private static PostsResource POSTS_RESOURCE = new PostsResource(mock(Tumblr.class));

    private static Optional<String> searchString = Optional.of("gay");
    private static Post postWithSearchStringFirst = new Post(12345L, "I feel pretty and witty and gay");
    private static Post postWithoutSearchStringFirst = new Post(35813L, "I feel pretty and witty and bright");
    private static Post postWithoutSearchStringSecond = new Post(8132134L, "and I pity any girl who isn't me to{day|night}");

    private static Post postWithSearchStringInTag = new Post(11235L, "I love my wife!",
            new ArrayList<>(Arrays.asList("gay")));

    @Test
    public void selectPostsWithSearchTermInSummary() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(0, postWithoutSearchStringFirst);
        originalPostCollection.add(1, postWithSearchStringFirst);
        originalPostCollection.add(2, postWithoutSearchStringSecond);

        Post postWithSearchString = originalPostCollection.get(1);
        Post postWithoutSearchStringFirst = originalPostCollection.get(0);
        Post postWithoutSearchStringSecond = originalPostCollection.get(2);

        Collection<Post> searchResults = POSTS_RESOURCE
                .filterPostsBySearchString(originalPostCollection, searchString);

        assertThat(searchResults).contains(postWithSearchString);
        assertThat(searchResults).doesNotContain(postWithoutSearchStringFirst);
        assertThat(searchResults).doesNotContain(postWithoutSearchStringSecond);
    }

    @Test
    public void selectsPostsWithSearchTermInSummary() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(0, postWithSearchStringInTag);
        originalPostCollection.add(1, postWithoutSearchStringSecond);

        Post postWithSearchStringInTag = originalPostCollection.get(0);
        Post postWithoutSearchString = originalPostCollection.get(1);

        Collection<Post> searchResults = POSTS_RESOURCE
                .filterPostsBySearchString(originalPostCollection, searchString);

        assertThat(searchResults).contains(postWithSearchStringInTag);
        assertThat(searchResults).doesNotContain(postWithoutSearchString);
    }

    @Test
    public void removeIfRemoves() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(postWithoutSearchStringFirst);
        originalPostCollection.add(postWithSearchStringFirst);

        boolean resultOfFilter = originalPostCollection.removeIf(post -> post.containsText(searchString.get()));

        assertThat(resultOfFilter).isTrue();
    }

//    @Test
//    public void returnsErrors() {
//        Tumblr failTumblr = mock(Tumblr.class);
//        when(failTumblr.getLikes("secret-blog", 1L)).thenReturn(new TumblrFailureResponse(
//                new TumblrResponseMeta("Forbidden", 403)
//        ));
//        PostsResource failPostResource = new PostsResource(failTumblr);
//
//        assertThatExceptionOfType(WebApplicationException.class).isThrownBy(() -> {
//            failPostResource.getLikes("secret-blog", "test", 1L);
//        }).withMessageContaining("secret-blog's likes are not public.");
//    }
//
//    @Test
//    public void returnsUnexpectedErrors() {
//        Tumblr failTumblr = mock(Tumblr.class);
//        when(failTumblr.getLikes("unsecret-blog", 1L)).thenReturn(new TumblrFailureResponse(
//                new TumblrResponseMeta(":dull_surprise: tumblr is down.", 503)
//        ));
//        PostsResource failPostResource = new PostsResource(failTumblr);
//
//        assertThatExceptionOfType(WebApplicationException.class).isThrownBy(() -> {
//            failPostResource.getLikes("unsecret-blog", "test", 1L);
//        }).withMessageContaining("Tumblr is down or unreachable.");
//    }
}
