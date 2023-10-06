package dictionary.cli;

import java.io.IOException;

import dictionary.api.GoogleTranslateAPI;
import dictionary.api.TextToSpeechOffline;

public class App {
    public static void main(final String[] args) {
        TextToSpeechOffline.getTextToSpeech("Welcome to Dictionary!");
        try {
            System.out.println(GoogleTranslateAPI.translate("", "vi", "i love you"));
        } catch (IOException ex) {
        }
        new DictionaryCommandline().dictionaryAdvanced();
    }
}
