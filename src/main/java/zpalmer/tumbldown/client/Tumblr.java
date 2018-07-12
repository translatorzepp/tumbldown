package zpalmer.tumbldown.client;

import javax.ws.rs.client.Client;

import zpalmer.tumbldown.api.Blog;

public class Tumblr {
    private String baseUrl = "api.tumblr.com/v2/";
    private String authentication = "?api_key=fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4";

    private Client client;

    public Tumblr(Client client) {
        this.client = client;
    }

    public Blog getBlog(String name) {
        // api.tumblr.com/v2/blog/{blog-identifier}/info?api_key={key}
        String fullName = name + ".tumblr.com";
        String fullUrl = baseUrl + "blog/" + fullName + "/info" + authentication;
        client.target(fullUrl);
        // pass fullUrl into client in order to get JSON response

        // should we be able to make this happen automatically?
        //   like, if we just pass the full JSON response from the tumblr api in,
        //   should Blog, as a Jackson-annotated thing, be able to just grab the correct fields?
        //   I think yes
        return new Blog(name, 5);
    }

    //api.tumblr.com/v2/blog/{blog-identifier}/likes?api_key={key}
}
