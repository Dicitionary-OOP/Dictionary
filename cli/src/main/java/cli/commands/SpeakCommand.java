package cli.commands;

import base.api.TextToSpeechOfflineAPI;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "speak", description = "Text to speak")
public class SpeakCommand implements Runnable {
    @Parameters(index = "0", description = "Text to speak")
    private String text;

    @Override
    public void run() {
        try {
            TextToSpeechOfflineAPI.getTextToSpeech(text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
