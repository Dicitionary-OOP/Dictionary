package dictionary.graphic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    private static SoundManager INSTANCE;
    private Map<String, MediaPlayer> mediaPlayers;

    public static SoundManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SoundManager();
        }

        return INSTANCE;
    }

    private SoundManager() {
        this.mediaPlayers = new HashMap<>();
    }

    public void addSound(final String soundName, final String soundFilePath) {
        final Media media = new Media(new File(soundFilePath).toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayers.put(soundName, mediaPlayer);
    }

    public void play(final String soundName) {
        final MediaPlayer mediaPlayer = mediaPlayers.get(soundName);
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pause(final String soundName) {
        final MediaPlayer mediaPlayer = mediaPlayers.get(soundName);
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop(String soundName) {
        final MediaPlayer mediaPlayer = mediaPlayers.get(soundName);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setVolume(String soundName, double volume) {
        final MediaPlayer mediaPlayer = mediaPlayers.get(soundName);
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }
}
