package zpalmer.tumbldown.core;

import java.util.Optional;

public class SearchCriteria {
    private String blogName;
    private Optional<String> searchText;
    private Optional<String> beforeDate;

    public SearchCriteria(String blogName, Optional<String> searchText, Optional<String> beforeDate) {
        this.blogName = blogName;
        this.searchText = searchText;
        this.beforeDate = beforeDate;
    }

    public String getBlogName() {
        return blogName;
    }

    public String getSearchText() {
        return searchText.orElse(null);
    }

    public String getBeforeDate() {
        return beforeDate.orElse(null);
    }
}