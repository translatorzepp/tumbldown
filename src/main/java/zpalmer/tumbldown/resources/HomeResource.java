package zpalmer.tumbldown.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    @GET
    public HomeView displayHomePage() {
        return new HomeView("/search", "/random");
    }
}