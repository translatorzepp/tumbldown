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
    private static Post postWithSearchStringFirst = new Post("I feel pretty and witty and gay");
    private static Post postWithoutSearchStringFirst = new Post("I feel pretty and witty and bright");
    private static Post postWithoutSearchStringSecond = new Post("and I pity any girl who isn't me to{day|night}");


    @Test
    public void selectPostsWithSearchTermInSummary() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(postWithoutSearchStringFirst);
        originalPostCollection.add(postWithSearchStringFirst);
        originalPostCollection.add(postWithoutSearchStringSecond);

        Collection<Post> searchResultsPostCollection = POSTS_RESOURCE
                .filterPostsBySearchString(originalPostCollection, searchString);

        assertThat(originalPostCollection).contains(postWithSearchStringFirst);
        assertThat(originalPostCollection).contains(postWithoutSearchStringFirst);

        assertThat(searchResultsPostCollection).contains(postWithSearchStringFirst);
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
