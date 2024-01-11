package manager;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EvSoundManager {
    private static final EvSoundManager instance = new EvSoundManager();
    private Map<String, AudioClip> soundCache = new HashMap<>();

    private EvSoundManager() {
    }

    public static EvSoundManager getInstance() {
        return instance;
    }

    public void playSound(String path, boolean loop) {
       AudioClip sound = soundCache.computeIfAbsent(path, p -> new AudioClip(getClass().getResource(p).toExternalForm()));
        sound.stop();
        if (loop) {
            sound.setCycleCount(AudioClip.INDEFINITE);
        }
        sound.setVolume(0.5);
        sound.play();
    }

}
