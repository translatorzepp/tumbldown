package zpalmer.tumbldown.core;

public class SearchCriteria {
    private String blogName;
    private String searchText;
    private String nextBeforeTimestampSeconds;
    private String currentBeforeTimestampSeconds;

    public SearchCriteria(String blogName, String searchText, Long nextBeforeTimestampSeconds, Long currentBeforeTimestampSeconds) {
        this.blogName = blogName;
        this.searchText = searchText;
        this.nextBeforeTimestampSeconds = String.valueOf(nextBeforeTimestampSeconds);
        this.currentBeforeTimestampSeconds = String.valueOf(currentBeforeTimestampSeconds);
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
}