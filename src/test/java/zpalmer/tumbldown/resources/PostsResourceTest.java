package zpalmer.tumbldown.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.Test;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.client.Tumblr;


public class PostsResourceTest {
    private static PostsResource POSTS_RESOURCE = new PostsResource(mock(Tumblr.class));

    private static Optional<String> searchString = Optional.of("gay");
    private static Post postWithSearchStringFirst = new Post(11235, "I feel pretty and witty and gay");
    private static Post postWithoutSearchStringFirst = new Post(35813, "I feel pretty and witty and bright");
    private static Post postWithoutSearchStringSecond = new Post(8132134, "and I pity any girl who isn't me to{day|night}");
    LinkedList<Post> originalPostCollection = new LinkedList<>();

    @Test
    public void selectPostsWithSearchTermInSummary() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(0, postWithoutSearchStringFirst);
        originalPostCollection.add(1, postWithSearchStringFirst);
        originalPostCollection.add(2, postWithoutSearchStringSecond);

        Post postWithSearchString = originalPostCollection.get(1);
        Post postWithoutSearchStringFirst = originalPostCollection.get(0);
        Post postWithoutSearchStringSecond = originalPostCollection.get(2);

        Collection<Post> searchResultsPostCollection = POSTS_RESOURCE
                .filterPostsBySearchString(originalPostCollection, searchString);

        assertThat(searchResultsPostCollection).contains(postWithSearchString);
        assertThat(searchResultsPostCollection).doesNotContain(postWithoutSearchStringFirst);
        assertThat(searchResultsPostCollection).doesNotContain(postWithoutSearchStringSecond);
    }

    @Test
    public void removeIfRemoves() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(postWithoutSearchStringFirst);
        originalPostCollection.add(postWithSearchStringFirst);

        boolean resultOfFilter = originalPostCollection.removeIf(post -> post.containsText(searchString.get()));

        assertThat(resultOfFilter).isTrue();
    }
}
