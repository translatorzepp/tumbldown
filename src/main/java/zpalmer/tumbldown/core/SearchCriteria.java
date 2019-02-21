package zpalmer.tumbldown.core;

import java.util.Optional;

public class SearchCriteria {
    public String blogName;
    public Optional<String> searchText;
    public Optional<Long> beforeTimestampSeconds;

    public SearchCriteria(String blogName, Optional<String> searchText, Optional<Long> beforeTimestampSeconds) {
        this.blogName = blogName;
        this.searchText = searchText;
        this.beforeTimestampSeconds = beforeTimestampSeconds;
    }

    public String getBlogName() {
        return blogName;
    }

    public String getSearchText() {
        return searchText.orElse(null);
    }

    public Long getBeforeTimestampSeconds(){
        return beforeTimestampSeconds.orElse(0L);
    }

//    class SearchCriteriaBuilder {
//        String blogName;
//        String searchText;
//        Long beforeTimestampSeconds;
//
//        public SearchCriteriaBuilder() {}
//        public SearchCriteriaBuilder setBlogName(String blogName) {
//            this.blogName = blogName;
//            return this;
//        }
//        public SearchCriteriaBuilder setSearchText(String searchText) {
//            this.searchText = searchText;
//            return this;
//        }
//        public SearchCriteriaBuilder setBeforeTimestampSeconds(Long beforeTimestampSeconds) {
//            this.beforeTimestampSeconds = beforeTimestampSeconds;
//            return this;
//        }
//        public SearchCriteria build() {
//            return new SearchCriteria(blogName, Optional.of(searchText), Optional.of(beforeTimestampSeconds));
//        }
//    }
}