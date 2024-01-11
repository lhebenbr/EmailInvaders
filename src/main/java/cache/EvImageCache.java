package cache;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse {@code EvImageCache} wird verwendet, um Bilder im Speicher zu halten,
 * damit sie nicht jedes Mal vom Datenträger geladen werden müssen, wenn sie benötigt werden.
 * Sie nutzt eine {@link HashMap}, um Bilder mit ihren Dateipfaden zu speichern.
 */
public class EvImageCache {
    /**
     * Die Cache-Map, die den String-Pfad eines Bildes mit dem {@link Image}-Objekt verknüpft.
     */
    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Ruft ein Bild aus dem Cache basierend auf dem gegebenen Pfad ab.
     * Wenn das Bild nicht im Cache vorhanden ist, wird es geladen, im Cache gespeichert
     * und anschließend zurückgegeben.
     *
     * @param path Der Pfad zur Bildressource.
     * @return Das {@link Image}-Objekt, das mit dem angegebenen Pfad verbunden ist.
     */
    public static Image getImage(String path) {
        if (!cache.containsKey(path)) {
            cache.put(path, new Image(EvImageCache.class.getResourceAsStream(path)));
        }
        return cache.get(path);
    }
}
