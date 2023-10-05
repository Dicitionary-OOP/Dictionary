package dictionary.api;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeechOffline {
    static final String VOICENAME = "kevin16";

    public static void getTextToSpeech(final String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        final Voice voice = VoiceManager.getInstance().getVoice(VOICENAME);

        if (voice == null) {
            System.err.println("Cannot find a voice named " + VOICENAME + ". Please specify a valid voice.");
            System.exit(1);
        }

        voice.allocate();
        voice.speak(text);
    }
}
