package dictionary.base.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

public class TextToIpaAPI {

    private static final String API = "https://png-text-to-ipa.onrender.com";

    /**
     * Converts the given text to its IPA representation using an API.
     *
     * @param text The text to convert to IPA.
     * @return The IPA representation of the input text, or null if an error occurs.
     */
    public static String textToIPA(final String text) {
        try {
            final String encodedText = java.net.URLEncoder.encode(text, "UTF-8");
            final URL url = new URL(API + "?text=" + encodedText);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                final StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                final JSONObject json = new JSONObject(response.toString());
                return StringEscapeUtils.unescapeJava(json.getString("ipa"));
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
