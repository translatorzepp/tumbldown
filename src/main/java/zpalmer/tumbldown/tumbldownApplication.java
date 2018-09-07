package zpalmer.tumbldown;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import javax.ws.rs.client.Client;

import java.util.logging.Logger;
import org.glassfish.jersey.logging.LoggingFeature;

import zpalmer.tumbldown.client.Tumblr;
import zpalmer.tumbldown.resources.BlogResource;
import zpalmer.tumbldown.resources.HelloWorldResource;
import zpalmer.tumbldown.resources.PostsResource;
import zpalmer.tumbldown.resources.SearchResource;

public class tumbldownApplication extends Application<tumbldownConfiguration> {

    public static void main(final String[] args) throws Exception {
        new tumbldownApplication().run(args);
    }

    @Override
    public String getName() {
        return "tumbldown";
    }

    @Override
    public void initialize(final Bootstrap<tumbldownConfiguration> bootstrap) {
        // In the constructor of your Application you can add Bundles and Commands to your application.
        bootstrap.addBundle(new ViewBundle<tumbldownConfiguration>());
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(final tumbldownConfiguration configuration,
                    final Environment environment) {
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName())
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME)));

        final Tumblr tumblr = new Tumblr(client);

        final BlogResource blogResource = new BlogResource(tumblr);
        environment.jersey().register(blogResource);

        final PostsResource postsResource = new PostsResource(tumblr);
        environment.jersey().register(postsResource);

        final SearchResource searchResource = new SearchResource();
        environment.jersey().register(searchResource);

        final HelloWorldResource helloResource = new HelloWorldResource(
                configuration.getHelloWorldTemplate(),
                configuration.getHelloWorldDefaultName()
        );
        environment.jersey().register(helloResource);
    }
}
