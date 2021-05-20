package zpalmer.tumbldown.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseHeadersFilter implements ContainerResponseFilter {
    public static final String GNU_NAMES = "GNU " + getNames();

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
        throws IOException {
            var headers = responseContext.getHeaders();
            headers.add("Strict-Transport-Security", "max-age: 63072000");

            headers.add("X-Clacks-Overhead", GNU_NAMES);
    }

    private static String getNames() {
        try {
            return Files.lines(Path.of("src", "main", "resources", "gnu_names.txt"))
                .collect(Collectors.joining(", "));
        } catch (IOException e) {
            return "Terry Pratchett";
        }
    }
}
