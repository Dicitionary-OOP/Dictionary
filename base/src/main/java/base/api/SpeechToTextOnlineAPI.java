package base.api;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SpeechToTextOnlineAPI {
    static final String TOKEN = "5e280d2f2b544015a8202120692f1550";
    static final String UPLOAD_URL = "https://api.assemblyai.com/v2/upload";
    static final String TRANSCRIPT_URL = "https://api.assemblyai.com/v2/transcript";

    public static String uploadFileSpeech(final String recorded) throws IOException {
        final HttpURLConnection request = (HttpURLConnection) new URL(UPLOAD_URL).openConnection();
        request.setRequestProperty("authorization", TOKEN);
        request.setRequestProperty("Transfer-Encoding", "chunked");
        request.setRequestMethod("POST");
        request.setDoOutput(true);

        final File file = new File(recorded);
        final byte[] bytes = new byte[(int) file.length()];
        try (OutputStream os = request.getOutputStream()) {
            final FileInputStream fis = new FileInputStream(file);
            fis.read(bytes);
            os.write(bytes, 0, bytes.length);
            fis.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        final StringBuilder response = new StringBuilder();
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine.trim());
        }
        inputStream.close();

        final JSONObject url_path = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
        return url_path.getString("upload_url");
    }

    public static String postSpeechToTextApi(final String wordForm) throws IOException {
        final URL url = new URL(TRANSCRIPT_URL);
        final HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestProperty("authorization", TOKEN);
        request.setRequestProperty("content-type", "application/json; utf-8");
        request.setRequestMethod("POST");
        request.setDoOutput(true);

        final String jsonInputString = "{\"audio_url\": " + "\"" + wordForm + "\"}";
        try (OutputStream os = request.getOutputStream()) {
            final byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        final StringBuilder response = new StringBuilder();
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine.trim());
        }
        inputStream.close();

        final JSONObject json = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
        return json.getString("id");
    }

    public static String getSpeechToTextApi(final String wordForm) throws IOException {
        final String stringURL = TRANSCRIPT_URL + "/" + wordForm;
        final HttpURLConnection request = (HttpURLConnection) new URL(stringURL).openConnection();
        request.setRequestProperty("authorization", TOKEN);
        request.setRequestMethod("GET");

        final BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String inputLine;
        final StringBuilder response = new StringBuilder();
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close();

        final JSONObject text = new JSONObject(StringEscapeUtils.unescapeHtml4(response.toString()));
        if (text.get("status").equals("error")) {
            return "error";
        }

        return text.get("text").toString();
    }

    public static String getText(final String speechFile) throws IOException {
        final String audioID = postSpeechToTextApi(uploadFileSpeech(speechFile));
        String text;
        while (true) {
            text = getSpeechToTextApi(audioID);
            if (Objects.equals(text, "error") || Objects.equals(text, "null")) {
                continue;
            }
            break;
        }

        return text.replaceFirst("[^A-Za-z0-9_-]*$", "").toLowerCase();
    }

}
