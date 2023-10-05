package dictionary;

import dictionary.api.TextToSpeechOffline;

public class App {
    public static void main(final String[] args) {
        TextToSpeechOffline.getTextToSpeech("Welcome to Dictionary!");
        new DictionaryCommandline().dictionaryAdvanced();
    }
}
