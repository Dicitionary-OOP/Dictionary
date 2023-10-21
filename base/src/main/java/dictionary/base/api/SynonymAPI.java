package dictionary.base.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SynonymAPI {
    public static final String API = "https://api.datamuse.com/words?";
    public static final int DEFAULT_LIMIT = 50;

    /**
     * Get synonyms for a word using the default limit.
     *
     * @param word The word for which synonyms are to be retrieved.
     * @return An ArrayList of synonyms, or null if there is an error.
     */
    public static ArrayList<String> getSynonyms(final String word) {
        return getSynonyms(word, DEFAULT_LIMIT);
    }

    /**
     * Get synonyms for a word with a specified limit.
     *
     * @param word  The word for which synonyms are to be retrieved.
     * @param limit The maximum number of synonyms to retrieve.
     * @return An ArrayList of synonyms, or null if there is an error.
     */
    public static ArrayList<String> getSynonyms(final String word, final int limit) {
        try {
            final StringBuilder urlStr = new StringBuilder(API);
            urlStr.append(String.format("&ml=%s", URLEncoder.encode(word, StandardCharsets.UTF_8).replace("+", "%20")));
            urlStr.append(String.format("&max=%d", limit));

            final URL url = new URL(urlStr.toString());
            final HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");

            final BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = inputStream.readLine()) != null) {
                response.append(inputLine);
            }
            inputStream.close();

            final JSONArray jsonArray = new JSONArray(response.toString());
            final ArrayList<String> words = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj = jsonArray.getJSONObject(i);
                words.add(obj.getString("word"));
            }

            return words;
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
