package zpalmer.tumbldown.resources;

import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.views.View;

import java.util.List;


public class ErrorView extends View {
    private final ErrorMessage error;
    private final List<String> validationErrors;

    public ErrorView(ErrorMessage error) {
        super("error.ftl");
        this.error = error;
        this.validationErrors = null;
    }

    public ErrorView(ValidationErrorMessage validationErrors) {
        super("error.ftl");
        this.error = null;
        this.validationErrors = validationErrors.getErrors();
    }

    public ErrorMessage getError() {
        return error;
    }
    public List<String> getValidationErrors() { return validationErrors; }
}
