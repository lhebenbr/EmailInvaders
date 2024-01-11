package manager;

import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse {@code EvSoundManager} verwaltet die Audiowiedergabe für das Spiel.
 * Sie implementiert das Singleton-Entwurfsmuster, um eine einzelne Instanz der
 * Audiosteuerung zu gewährleisten und verwendet eine Map, um Audiodateien zu cachen.
 */
public class EvSoundManager {
    private static final EvSoundManager instance = new EvSoundManager();
    private Map<String, AudioClip> soundCache = new HashMap<>();

    /**
     * Privater Konstruktor für {@code EvSoundManager}.
     */
    private EvSoundManager() {
    }

    /**
     * Gibt die einzige Instanz des {@code EvSoundManager} zurück.
     *
     * @return Die einzige Instanz von {@code EvSoundManager}.
     */
    public static EvSoundManager getInstance() {
        return instance;
    }

    /**
     * Spielt den Sound, der sich am angegebenen Pfad befindet. Wenn der Parameter
     * {@code loop} auf {@code true} gesetzt ist, wird der Sound in einer Endlosschleife
     * wiedergegeben.
     *
     * @param path Der Pfad zur Audiodatei, die abgespielt werden soll.
     * @param loop {@code true}, wenn der Sound wiederholt werden soll; andernfalls {@code false}.
     */
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
