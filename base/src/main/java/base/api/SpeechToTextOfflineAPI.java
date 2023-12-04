package base.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class SpeechToTextOfflineAPI {
    public static String getText(final String speechFile) throws IOException {
        final Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        final StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        final InputStream stream = new FileInputStream(new File(speechFile));

        recognizer.startRecognition(stream);
        String text = "";
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            text += result.getHypothesis();
        }
        recognizer.stopRecognition();
        return text;
    }
}
