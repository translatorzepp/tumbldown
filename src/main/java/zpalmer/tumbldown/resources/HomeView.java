package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;

public class HomeView extends View {
    private String likesSearchPath;

    public HomeView(String likesSearchPath) {
        super("home.ftlh");
        this.likesSearchPath = likesSearchPath;
    }

    public String getLikesSearchPath() {
        return likesSearchPath;
    }
}
