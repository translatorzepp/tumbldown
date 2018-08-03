package zpalmer.tumbldown.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.sun.tools.javac.util.List;
import org.junit.Test;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.client.Tumblr;


public class PostsResourceTest {
    private static PostsResource POSTS_RESOURCE = new PostsResource(mock(Tumblr.class));

    private static String searchString = "gay";
    private static Post postWithSearchString = new Post("I feel pretty and witty and gay");
    private static Post postWithoutSearchString = new Post("I feel pretty and witty and bright");

    @Test
    public void selectPostsWithSearchTermInSummary() {
        Collection<Post> originalPostCollection = new LinkedList<>();
        originalPostCollection.add(postWithoutSearchString);
        originalPostCollection.add(postWithSearchString);

        Collection<Post> searchResultsPostCollection = POSTS_RESOURCE.filterPostsBySearchString(originalPostCollection, searchString);

        assertThat(searchResultsPostCollection).contains(postWithSearchString);
        assertThat(searchResultsPostCollection).doesNotContain(postWithoutSearchString);
    }
}
