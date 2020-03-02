package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;
import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.core.SearchCriteria;

import java.util.List;

public class LikesResultPageView extends View {
    private final List<Post> posts;
    private final SearchCriteria criteria;

    public LikesResultPageView(List<Post> posts, SearchCriteria criteria) {
        super("results_page.ftlh");
        this.posts = posts;
        this.criteria = criteria;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }
}
