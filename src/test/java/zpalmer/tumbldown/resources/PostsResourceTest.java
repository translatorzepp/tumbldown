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
    private static Post postWithSearchString = new Post("I feel pretty and witty and gay");
    private static Post postWithoutSearchString = new Post("I feel pretty and witty and bright");

    @Test
    public void selectPostsWithSearchTermInSummary() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(postWithoutSearchString);
        originalPostCollection.add(postWithSearchString);

        Collection<Post> searchResultsPostCollection = POSTS_RESOURCE
                .filterPostsBySearchString(originalPostCollection, searchString);

        assertThat(originalPostCollection).contains(postWithSearchString);
        assertThat(originalPostCollection).contains(postWithoutSearchString);

        assertThat(searchResultsPostCollection).contains(postWithSearchString);
        assertThat(searchResultsPostCollection).doesNotContain(postWithoutSearchString);
    }

    @Test
    public void removeIfRemoves() {
        LinkedList<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(postWithoutSearchString);
        originalPostCollection.add(postWithSearchString);

        boolean resultOfFilter = originalPostCollection.removeIf(post -> post.containsText(searchString.get()));

        assertThat(resultOfFilter).isTrue();
    }
}
