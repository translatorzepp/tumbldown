package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;

public class HomeView extends View {
    private String likesSearchPath;
    private String randomLikePath;

    public HomeView(String likesSearchPath, String randomLikePath) {
        super("home.ftlh");
        this.likesSearchPath = likesSearchPath;
        this.randomLikePath = randomLikePath;
    }

    public String getLikesSearchPath() {
        return likesSearchPath;
    }

    public String getRandomLikePath() {
        return randomLikePath;
    }
}
