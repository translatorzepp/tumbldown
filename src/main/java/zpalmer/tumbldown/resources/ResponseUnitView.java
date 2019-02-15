package zpalmer.tumbldown.resources;

import io.dropwizard.views.View;

import java.nio.charset.Charset;

public abstract class ResponseUnitView extends View {
    public ResponseUnitView(String templateName) {
        super(templateName);
    }

    public ResponseUnitView(String templateName, Charset charset) {
        super(templateName, charset);
    }
}
