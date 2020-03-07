package zpalmer.tumbldown.resources;

import com.codahale.metrics.annotation.Timed;
import org.hibernate.validator.constraints.NotEmpty;
import zpalmer.tumbldown.api.Blog;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.api.tumblr.TumblrResponse;
import zpalmer.tumbldown.api.tumblr.TumblrSuccessResponse;
import zpalmer.tumbldown.client.Tumblr;
import zpalmer.tumbldown.core.SearchCriteria;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedList;

@Path("search")
@Produces(MediaType.TEXT_HTML)
public class SearchResource {
    private Tumblr tumblrClient;
    private Long MAX_TIME_DELTA_SECONDS = 30 * 24 * 60 * 60L; // Should be tuned to get max posts without the request from the client timing out
    private int POSTS_PER_PAGE = 10;

    public SearchResource(Tumblr client) { this.tumblrClient = client; }

    @GET
    public SearchView displaySearchForm() {
        return new SearchView();
    }

    @GET
    @Timed
    @Path("likes")
    public LikesResultPageView displayResultsPage(@QueryParam("blogName") @NotEmpty String blogName,
                                                  @QueryParam("searchText") String searchText,
                                                  @QueryParam("beforeTimestamp") String beforeTimestamp
    ) throws WebApplicationException {
        final String blogToSearch = Blog.sanitizeBlogName(blogName);

        Long initialLikedBeforeTimestampSeconds = getTimestampFromParams(null, null, beforeTimestamp);
        Long likedBeforeTimestampSeconds = initialLikedBeforeTimestampSeconds;
        Long maxLikedBefore = likedBeforeTimestampSeconds - MAX_TIME_DELTA_SECONDS;

        LinkedList<Post> resultsPage = new LinkedList<>(Collections.emptyList());
        int additionalPostsNeeded = POSTS_PER_PAGE;

        // Refactor and individual test components
        while ((additionalPostsNeeded > 0) && (likedBeforeTimestampSeconds != null) && likedBeforeTimestampSeconds > maxLikedBefore) {

            LinkedList<Post> likedPosts = getLikesBefore(blogToSearch, likedBeforeTimestampSeconds);

            if (likedPosts.size() == 0) {
                likedBeforeTimestampSeconds = null;

            } else {
                LinkedList<Post> matchingPosts = filterPostsBySearchString(likedPosts, searchText);

                if (matchingPosts.size() > additionalPostsNeeded) {
                    resultsPage.addAll(matchingPosts.subList(0, additionalPostsNeeded));
                    likedBeforeTimestampSeconds = resultsPage.getLast().getLikedAt();
                } else {
                    resultsPage.addAll(matchingPosts);
                    likedBeforeTimestampSeconds = likedPosts.getLast().getLikedAt();
                }
            }
            additionalPostsNeeded = POSTS_PER_PAGE - resultsPage.size();
        }

        return new LikesResultPageView(resultsPage,
                new SearchCriteria(blogName, searchText, likedBeforeTimestampSeconds, initialLikedBeforeTimestampSeconds));
    }

    static Long getTimestampFromParams(String date, String timezoneId, String timestampSeconds) {
        if (timestampSeconds == null || timestampSeconds.isEmpty()) {
            return convertDateStringToEpochTime(date, timezoneId);
        }

        return Long.valueOf(timestampSeconds.replaceAll("\\D", ""));
    }

    static Long convertDateStringToEpochTime(String date, String timezoneId) {
        if (date == null || date.isEmpty()) {
            return ZonedDateTime.now().toEpochSecond();
        }

        if (timezoneId == null || timezoneId.isEmpty() || timezoneId.equals("undefined")) {
            timezoneId = "America/Chicago";
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDate.parse(date),
                LocalTime.of(23, 59, 59),
                ZoneId.of(timezoneId));

        return zonedDateTime.toEpochSecond();
    }

    LinkedList<Post> getLikesBefore(String blogName, Long likedBeforeTimestampSeconds)
            throws WebApplicationException {
        TumblrResponse response = tumblrClient.getLikes(blogName, likedBeforeTimestampSeconds);

        if (response instanceof TumblrSuccessResponse) {
            TumblrSuccessResponse success = (TumblrSuccessResponse) response;

            if (!(success.getPosts() == null)) {
                return new LinkedList<>(success.getPosts());
            } else {
                return new LinkedList<>();
            }
        } else {
            int tumblrErrorStatusCode = response.getMeta().getStatus();
            String errorDetails;
            if (tumblrErrorStatusCode == 403) {
                errorDetails = blogName + "'s likes are not public.";
            } else if (tumblrErrorStatusCode == 404) {
                errorDetails = blogName + " does not exist.";
            } else if (tumblrErrorStatusCode == 429) {
                // TODO: extract time details from X-Ratelimit headers and suggest a time window in the error message
                errorDetails = "Tumblr thinks tumbldown is making too many requests. Wait a while and try again.";
            } else if(tumblrErrorStatusCode >= 500) {
                errorDetails= "Tumblr is down or unreachable.";
            } else {
                errorDetails = "Unknown error: " + tumblrErrorStatusCode + ".";
            }
            throw new WebApplicationException(errorDetails, tumblrErrorStatusCode);
        }
    }

    protected LinkedList<Post> filterPostsBySearchString(LinkedList<Post> posts, String searchText) {
        LinkedList<Post> matchingPosts = new LinkedList<>();
        matchingPosts.addAll(posts);

        if (searchText != null && !searchText.isEmpty()) {
            matchingPosts.removeIf(post -> !post.containsText(searchText));
        }

        return matchingPosts;
    }
}
