package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;
import zpalmer.tumbldown.core.SearchCriteria;


public class SearchView extends View {
    public SearchCriteria searchCriteria;

    public SearchView() {
        super("search.ftlh");
    }

    public SearchView(SearchCriteria searchCriteria) {
        super("search.ftlh");
        this.searchCriteria = searchCriteria;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }
}
