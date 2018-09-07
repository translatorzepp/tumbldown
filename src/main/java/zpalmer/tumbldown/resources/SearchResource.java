package zpalmer.tumbldown.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("search")
@Produces(MediaType.TEXT_HTML)
public class SearchResource {

    @GET
    public SearchView displaySearchForm() {
        return new SearchView();
    }
}
