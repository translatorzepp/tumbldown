package zpalmer.tumbldown.core;

public class SearchCriteria {
    private String blogName;
    private String searchText;
    private String nextBeforeTimestampSeconds;

    public SearchCriteria(String blogName, String searchText, Long nextBeforeTimestampSeconds) {
        this.blogName = blogName;
        this.searchText = searchText;
        this.nextBeforeTimestampSeconds = String.valueOf(nextBeforeTimestampSeconds);
    }

    public String getBlogName() {
        return blogName;
    }

    public String getSearchText() {
        return searchText;
    }

    public String getNextBeforeTimestampSeconds() {
        return nextBeforeTimestampSeconds;
    }
}