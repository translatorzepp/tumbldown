package zpalmer.tumbldown.core;

import java.util.Collections;
import java.util.List;

public class SearchCriteria {
    private String blogName;
    private String searchText;
    private String nextBeforeTimestampSeconds;
    private String currentBeforeTimestampSeconds;
    private List<String> postTypes;

    public SearchCriteria(String blogName,
                          String searchText,
                          Long nextBeforeTimestampSeconds,
                          Long currentBeforeTimestampSeconds) {
        this.blogName = blogName;
        this.searchText = searchText;
        this.nextBeforeTimestampSeconds = convertToStringWithNull(nextBeforeTimestampSeconds);
        this.currentBeforeTimestampSeconds = convertToStringWithNull(currentBeforeTimestampSeconds);
        this.postTypes = Collections.emptyList();
    }

    public SearchCriteria(String blogName,
                          String searchText,
                          Long nextBeforeTimestampSeconds,
                          Long currentBeforeTimestampSeconds,
                          List<String> postTypes) {
        this.blogName = blogName;
        this.searchText = searchText;
        this.nextBeforeTimestampSeconds = convertToStringWithNull(nextBeforeTimestampSeconds);
        this.currentBeforeTimestampSeconds = convertToStringWithNull(currentBeforeTimestampSeconds);
        this.postTypes = postTypes;
    }

    public String getBlogName() {
        return blogName;
    }

    public String getSearchText() {
        return searchText;
    }

    public String getNextBeforeTimestampSeconds() { return nextBeforeTimestampSeconds; }

    public String getCurrentBeforeTimestampSeconds() {
        return currentBeforeTimestampSeconds;
    }

    public List<String> getPostTypes() { return postTypes; }

    private String convertToStringWithNull(Long timestamp) {
        if (timestamp != null) {
            return String.valueOf(timestamp);
        }
        return null;
    }
}