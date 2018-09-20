package zpalmer.tumbldown;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jersey.errors.ErrorEntityWriter;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

import java.util.logging.Logger;
import org.glassfish.jersey.logging.LoggingFeature;

import zpalmer.tumbldown.client.Tumblr;
import zpalmer.tumbldown.resources.*;

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
        // TODO: add healthchecks
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

        environment.jersey().register(new ErrorEntityWriter<ErrorMessage, View>(MediaType.TEXT_HTML_TYPE, View.class) {
            @Override
            protected View getRepresentation(ErrorMessage errorMessage) {
                return new ErrorView(errorMessage);
            }
        });

        environment.jersey().register(new ErrorEntityWriter<ValidationErrorMessage,View>(MediaType.TEXT_HTML_TYPE, View.class) {
            @Override
            protected View getRepresentation(ValidationErrorMessage validationErrorMessage) {
                return new ErrorView(validationErrorMessage);
            }
        });

        final HelloWorldResource helloResource = new HelloWorldResource(
                configuration.getHelloWorldTemplate(),
                configuration.getHelloWorldDefaultName()
        );
        environment.jersey().register(helloResource);
    }
}
