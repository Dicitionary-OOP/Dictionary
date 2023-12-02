package base.api;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GoogleTranslateAPI {
    private final static String SCRIPT_WEB_APP = "https://script.google.com/macros/s/AKfycbzS8OiTKVPtZnUecN3oWzVCHLpAHxdyI2j01W_ZoP_byBqVRfnEIaGwaWmtV2WVrrRi3g/exec";

    public static String translate(final String source, final String target, final String text)
            throws IOException, InterruptedException {
        if (source.equals(target)) {
            return text;
        }

        final String scriptUrl = String.format("%s?q=%s&target=%s&source=%s", SCRIPT_WEB_APP,
                URLEncoder.encode(text, StandardCharsets.UTF_8), target, source);

        final HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(scriptUrl))
                .header("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() == 302) {
            final String redirectUrl = httpResponse.headers().firstValue("Location").orElse(null);
            if (redirectUrl != null) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(redirectUrl))
                        .header("User-Agent", "Mozilla/5.0")
                        .build();
                httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
        }

        return StringEscapeUtils.unescapeHtml4(httpResponse.body());
    }
}
