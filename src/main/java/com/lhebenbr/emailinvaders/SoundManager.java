package com.lhebenbr.emailinvaders;


import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final SoundManager instance = new SoundManager();
    private Map<String, AudioClip> soundCache = new HashMap<>();

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        return instance;
    }

    public void playSound(String path, boolean loop) {
        AudioClip sound = soundCache.computeIfAbsent(path, p -> new AudioClip(new File(p).toURI().toString()));
        sound.stop();
        if (loop) {
            sound.setCycleCount(AudioClip.INDEFINITE);
        }
        sound.setVolume(0.5);
        sound.play();
    }

}
