package zpalmer.tumbldown.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.JSONPObject;
import zpalmer.tumbldown.api.Blog;

public class Tumblr {
    private String baseUrl = "https://api.tumblr.com/v2";
    private String authentication = "fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4";

    private WebTarget baseTarget;

    private Client client;

    public Tumblr(Client client) {
        this.client = client;
        this.baseTarget = client.target(baseUrl)
                .queryParam("api_key", authentication);
    }

    public Blog getBlog(String name) {
        // api.tumblr.com/v2/blog/{blog-identifier}/info?api_key={key}
        String fullName = name + ".tumblr.com";

        WebTarget blogTarget = baseTarget.path("blog/" + fullName + "/info");
        Invocation.Builder invocationBuilder = blogTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        System.out.println(response.getStatus());
        System.out.println(response.readEntity(String.class));

        //return response.readEntity(Blog.class);
        return new Blog(name, 5);

    }
}
