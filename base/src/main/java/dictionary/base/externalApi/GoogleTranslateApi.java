package dictionary.base.externalApi;

import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleTranslateApi {
    /**
     * Translates text from one language to another using the Google Translate API.
     *
     * @param sourceLanguage The language to translate from.
     * @param targetLanguage The language to translate to.
     * @param text           The text to be translated.
     * @return The translated text.
     * @throws IOException If an error occurs during the translation process.
     */
    public static String translate(final String sourceLanguage, final String targetLanguage, final String text)
            throws IOException {
        final String DEPLOYMENT_ID = "AKfycbzS8OiTKVPtZnUecN3oWzVCHLpAHxdyI2j01W_ZoP_byBqVRfnEIaGwaWmtV2WVrrRi3g";
        final URL GOOGLE_SCRIPT_WEB_APP = new URL(String.format(
                "https://script.google.com/macros/s/%s/exec?q=%s&target=%s&source=%s",
                DEPLOYMENT_ID, URLEncoder.encode(text, StandardCharsets.UTF_8), targetLanguage, sourceLanguage));

        final HttpURLConnection request = (HttpURLConnection) GOOGLE_SCRIPT_WEB_APP.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0");

        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String inputLine;
        final StringBuilder response = new StringBuilder();
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close();

        return StringEscapeUtils.unescapeHtml4(response.toString());
    }
}
