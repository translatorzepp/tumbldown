package zpalmer.tumbldown;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

import zpalmer.tumbldown.client.Tumblr;
import zpalmer.tumbldown.resources.BlogResource;
import zpalmer.tumbldown.resources.HelloWorldResource;

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
        // TODO: application initialization
    }

    @Override
    public void run(final tumbldownConfiguration configuration,
                    final Environment environment) {
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());

        final Tumblr tumblr = new Tumblr(client);

        final BlogResource blogResource = new BlogResource(tumblr);
        environment.jersey().register(blogResource);

        // this "registers" a resource as something that can be reached in the environment
        final HelloWorldResource helloResource = new HelloWorldResource(
                configuration.getHelloWorldTemplate(),
                configuration.getHelloWorldDefaultName()
        );
        environment.jersey().register(helloResource);
    }
}
