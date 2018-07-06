package zpalmer.tumbldown;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class tumbldownConfiguration extends Configuration {
    // Configuration is what knows to get all this from the yml

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
