package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableList;
import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrResponseHandler;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;
import zpalmer.tumbldown.core.LikedPostsCache;
import zpalmer.tumbldown.core.SearchCriteria;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Path("search")
@Produces(MediaType.TEXT_HTML)
public class SearchResource {
    private Tumblr tumblrClient;
    private LikedPostsCache cache;
    private Long MAX_TIME_DELTA_SECONDS = 30 * 24 * 60 * 60L; // Should be tuned to get max posts without the request from the client timing out
    private int POSTS_PER_DISPLAY_PAGE = 10;

    public SearchResource(Tumblr client) { this.tumblrClient = client; }
    public SearchResource(Tumblr client, LikedPostsCache cache) {
        this.tumblrClient = client;
        this.cache = cache;
    }

    @GET
    public SearchView displaySearchForm() {
        return new SearchView();
    }

    @GET
    @Timed
    @Path("likes")
    public LikesResultPageView displayResultsPage(@QueryParam("blogName") @NotEmpty @NotNull String blogName,
                                                  @QueryParam("searchText") String searchText,
                                                  @QueryParam("beforeTimestamp") String beforeTimestamp,
                                                  @QueryParam("postTypes") final List<String> postTypes
    ) throws WebApplicationException {
        final Long TIMEOUT_IN_MS = 27500L; // heroku returns a 503 timeout after 30 seconds
        Long startTime = System.currentTimeMillis();

        final String blogToSearch = Blog.sanitizeBlogName(blogName);

        Long initialLikedBeforeTimestampSeconds = getTimestampFromParams(beforeTimestamp);
        Long likedBeforeTimestampSeconds = initialLikedBeforeTimestampSeconds;
        Long maxLikedBefore = likedBeforeTimestampSeconds - MAX_TIME_DELTA_SECONDS;

        LinkedList<Post> resultsPage = new LinkedList<>(Collections.emptyList());
        int additionalPostsNeeded = POSTS_PER_DISPLAY_PAGE;

        var cachedPosts = cache.get(blogToSearch);
        Long lastLikedAtTimestamp = getLastLikedAtTimestamp(cachedPosts, initialLikedBeforeTimestampSeconds);
        // start adding to cache:
        new Thread(new Runnable() {
            @Override run() {
                
            }
        })

        if (lastLikedAtTimestamp >= likedBeforeTimestampSeconds) {
            LinkedList<Post> matchingPosts = filterPostsBySearchCriteria(new LinkedList<Post>(cachedPosts), searchText, postTypes);
        }


        // time: before == lessThan
        
        // copy out current value of cache for blogname
        // get likedAt of last element in cache
            // new thread: requests more results based on ^
            // (if cache.get() is nil _or_ empty _or_ likedAt is nil, then just use timestamp supplied by the search)
        // compare likedAtOfLastResult with the timestamp supplied by the search
        // if likedAtOfLastResult >= timestampFromSearch:
            // start paging through copy of cache results
        // else:
            // loop




        // ok, so: we have a timestamp, some search criteria, and a target # of (filtered) posts 
        // and we're gonna have a cache, which is just going to be
        // key: blogname
        // value: list of liked posts, in order of likedAt datetime
        // start with timestamp:
        // new thread:
            // take the last item in the cache's list
            // get its timestamp
            // if empty, timestamp is timestamp from search
            // start adding to the cache by searching for likes before timestamp
            // keep going here until we've made ??? requests
        // back in OG Resource thread
        // ??? idk how to handle this
        // read cache results into queue
        // request cache 
        // wait til cache has results
        // page through cache results, applying filters
        // once we have POSTS_PER_DISPLAY_PAGE filtered results
        // return to 


        // TODO: Refactor and individual test components
        while (
            (additionalPostsNeeded > 0) &&
            (likedBeforeTimestampSeconds != null) &&
            (likedBeforeTimestampSeconds > maxLikedBefore) &&
            ((System.currentTimeMillis() - startTime) < TIMEOUT_IN_MS)
        ) {

            LinkedList<Post> likedPosts = getLikesBefore(blogToSearch, likedBeforeTimestampSeconds);

            if (likedPosts.size() == 0) {
                likedBeforeTimestampSeconds = null;

            } else {
                LinkedList<Post> matchingPosts = filterPostsBySearchCriteria(likedPosts, searchText, postTypes);

                if (matchingPosts.size() > additionalPostsNeeded) {
                    resultsPage.addAll(matchingPosts.subList(0, additionalPostsNeeded));
                    likedBeforeTimestampSeconds = resultsPage.getLast().getLikedAt();
                } else {
                    resultsPage.addAll(matchingPosts);
                    likedBeforeTimestampSeconds = likedPosts.getLast().getLikedAt();
                }
            }
            additionalPostsNeeded = POSTS_PER_DISPLAY_PAGE - resultsPage.size();
        }

        return new LikesResultPageView(
                resultsPage,
                new SearchCriteria(blogName,
                        searchText,
                        likedBeforeTimestampSeconds,
                        initialLikedBeforeTimestampSeconds,
                        postTypes)
        );
    }

    private Long getTimestampFromParams(String timestampSeconds) {
        if (timestampSeconds != null) {
            String digitOnlyTimestamp = timestampSeconds.replaceAll("\\D", "");
            if (!digitOnlyTimestamp.isEmpty() && !digitOnlyTimestamp.equals("0")) {
                return Long.valueOf(digitOnlyTimestamp);
            }
        }

        return ZonedDateTime.now().toEpochSecond();
    }

    private Long getLastLikedAtTimestamp(TreeSet<Post> posts, Long originalTimestamp) {
        if (posts.isEmpty()) {
            return originalTimestamp;
        } else {
            return posts.last().getLikedAt();
        }
    }

    LinkedList<Post> getLikesBefore(String blogName, Long likedBeforeTimestampSeconds)
            throws WebApplicationException {
        TumblrResponse response = tumblrClient.getLikes(blogName, likedBeforeTimestampSeconds);

        TumblrSuccessResponse success = TumblrResponseHandler.returnSuccessOrThrow(response, blogName);
        if (!(success.getPosts() == null)) {
            return new LinkedList<>(success.getPosts());
        } else {
            return new LinkedList<>();
        }
    }

    LinkedList<Post> filterPostsBySearchCriteria(LinkedList<Post> posts, String searchText, List<String> searchPostTypes) {
        ImmutableList.Builder<Predicate<Post>> searchCriteriaBuilder = ImmutableList.builder();

        if (searchText != null && !searchText.isEmpty()) {
            searchCriteriaBuilder.add((Post post) -> post.containsText(searchText));
        }

        if (searchPostTypes != null && !searchPostTypes.isEmpty()) {
            searchCriteriaBuilder.add((Post post) -> searchPostTypes.stream().anyMatch(post::isOfType));
        }

        ImmutableList<Predicate<Post>> searchCriteria = searchCriteriaBuilder.build();

        return posts
                .stream()
                .filter(post -> searchCriteria.stream().allMatch(criteria -> criteria.test(post)))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
