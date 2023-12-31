package base.utils;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class RecordManager {
    private static TargetDataLine microphone;
    public static boolean isRecording = false;

    public static boolean isRecording() {
        return isRecording;
    }

    public static void startRecording(final String filePath) {
        try {
            isRecording = true;
            final AudioFormat format = new AudioFormat(8000, 8, 1, true, true);
            final DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = AudioSystem.getTargetDataLine(format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();
            final AudioInputStream ais = new AudioInputStream(microphone);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filePath));
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopRecording() {
        microphone.stop();
        microphone.close();
        isRecording = false;
    }
}
