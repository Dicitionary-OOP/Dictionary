package base.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

public class TextToIpaAPI {
    private static final String API = "https://png-text-to-ipa.onrender.com";

    public static String getIPA(final String text) throws IOException {
        final String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        final URI uri = URI.create(String.format("%s?text=%s", API, encodedText));

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            final JSONObject json = new JSONObject(response.body());
            return StringEscapeUtils.unescapeJava(json.getString("ipa"));
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
