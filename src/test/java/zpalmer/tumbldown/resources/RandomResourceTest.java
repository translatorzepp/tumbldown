package zpalmer.tumbldown.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;

public class RandomResourceTest {
    private Tumblr fakeTumblr = mock(Tumblr.class);

    private Blog blog = new Blog("tumbldown", 222L);
    private Post post = new Post(11235L, "Times are bad. Children no longer listen to their parents and everyone is writing a book.", "quote");
    private TumblrSuccessResponse fakeResponse = mock(TumblrSuccessResponse.class);

    private RandomResource randomResource = new RandomResource(fakeTumblr);
    
    @Test
    public void retrievesBlogInfoForSpecifiedBlog() {
        when(fakeResponse.getBlog()).thenReturn(blog);
        when(fakeTumblr.getBlog("tumbldown")).thenReturn(fakeResponse);

        Optional<Long> blogNumberOfLikes = randomResource.fetchBlogNumberOfLikes("tumbldown");

        assertEquals(blog.getNumberOfLikes(), blogNumberOfLikes);
        Mockito.verify(fakeTumblr, Mockito.times(1)).getBlog("tumbldown");
        Mockito.verify(fakeTumblr, never()).getLikesByOffset(anyString(), anyInt());
    }

    @Test
    public void offsetIsLimitedByTumblrLimit() {
        int underLimit = randomResource.upperBoundForOffset(5L, 1000);
        assertEquals(4, underLimit);

        int overLimit = randomResource.upperBoundForOffset(2001L, 1000);
        assertEquals(999, overLimit);

        int atLimit = randomResource.upperBoundForOffset(1000L, 1000);
        assertEquals(999, atLimit);
    }

    @Test
    public void requestsLikesFromTumblr() {
        when(fakeResponse.getPosts()).thenReturn(Collections.singletonList(post));
        when(fakeTumblr.getLikesByOffset(eq("tumbldown"), anyInt())).thenReturn(fakeResponse);

        Post fetchedPost = randomResource.fetchRandomPost("tumbldown", 222L);
        assertEquals(post, fetchedPost);
        // TODO: can I assert that the number this is called with is 0 < x < 222 ?
        Mockito.verify(fakeTumblr, Mockito.times(1)).getLikesByOffset(eq("tumbldown"), anyInt());
    }
}
