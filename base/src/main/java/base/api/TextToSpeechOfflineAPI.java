package base.api;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeechOfflineAPI {
    static final String VOICE_NAME = "kevin16";

    public static void getTextToSpeech(final String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        final Voice voice = VoiceManager.getInstance().getVoice(VOICE_NAME);

        if (voice == null) {
            System.err.println("Cannot find a voice named " + VOICE_NAME + ". Please specify a valid voice.");
            System.exit(1);
        }

        voice.allocate();
        voice.speak(text);
    }
}
