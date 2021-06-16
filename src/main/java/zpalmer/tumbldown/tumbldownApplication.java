package zpalmer.tumbldown;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jersey.errors.ErrorEntityWriter;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.glassfish.jersey.logging.LoggingFeature;

import zpalmer.tumbldown.api.Post;
import zpalmer.tumbldown.client.Tumblr;
import zpalmer.tumbldown.core.LikedPostsCache;
import zpalmer.tumbldown.core.ResponseHeadersFilter;
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
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        bootstrap.addBundle(new ViewBundle<tumbldownConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(tumbldownConfiguration configuration) {
                return configuration.getViewRendererConfiguration();
            }
        });
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(final tumbldownConfiguration configuration,
                    final Environment environment) {
        // TODO: add healthchecks
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName())
                .register(new LoggingFeature(Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME)));

        // is this threadsafe? what happens if multiple requests are made at the same time?
        final Tumblr tumblr = new Tumblr(client);

        environment.jersey().register(new BlogResource(tumblr));
        environment.jersey().register(new SearchResource(tumblr, new LikedPostsCache()));
        environment.jersey().register(new RandomResource(tumblr));
        environment.jersey().register(new HomeResource());

        environment.jersey().register(ResponseHeadersFilter.class);

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
    }
}
