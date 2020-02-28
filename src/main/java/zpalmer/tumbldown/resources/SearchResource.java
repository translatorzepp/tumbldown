package zpalmer.tumbldown.resources;

import zpalmer.tumbldown.core.SearchCriteria;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("search")
@Produces(MediaType.TEXT_HTML)
public class SearchResource {

    @GET
    public SearchView displaySearchForm(@QueryParam("blogName") Optional<String> blogName,
                                        @QueryParam("searchText") Optional<String> searchText,
                                        @QueryParam("before") Optional<String> beforeDate
    ) {
        return blogName.map(blogToSearch -> new SearchView(
                new SearchCriteria(
                        blogToSearch,
                        searchText,
                        beforeDate
                ))
        ).orElse(new SearchView());
    }
}
