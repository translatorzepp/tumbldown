package zpalmer.tumbldown;

import com.google.common.collect.ImmutableMap;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.Map;

public class tumbldownConfiguration extends Configuration {
    // Configuration is what knows to get all this from the yml

    @Valid
    @NotNull
    private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

    @JsonProperty("jerseyClient")
    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return jerseyClient;
    }

    @JsonProperty("jerseyClient")
    public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
        this.jerseyClient = jerseyClient;
    }

    @Valid
    private Map<String, Map<String, String>> viewRendererConfiguration = ImmutableMap.of();

    @JsonProperty("viewRendererConfiguration")
    public Map<String, Map<String, String>> getViewRendererConfiguration() {
        return viewRendererConfiguration;
    }

    @JsonProperty("viewRendererConfiguration")
    public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
        this.viewRendererConfiguration = viewRendererConfiguration;
    }

    @NotEmpty
    private String helloWorldTemplate;

    @NotEmpty
    private String helloWorldDefaultName = "Stranger";

    @JsonProperty
    public String getHelloWorldTemplate() {
        return helloWorldTemplate;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.helloWorldTemplate = helloWorldTemplate;
    }

    @JsonProperty
    public String getHelloWorldDefaultName() {
        return helloWorldDefaultName;
    }

    @JsonProperty
    public void setHelloWorldDefaultName(String name) {
        this.helloWorldDefaultName = name;
    }
}
