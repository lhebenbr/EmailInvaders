package manager;

import event.EmailInvadersExit;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Die Klasse {@code EvGameManager} verwaltet den Spielstand und bietet eine zentrale
 * Steuerung für das Spiel. Sie verwendet das Singleton-Entwurfsmuster, um eine einzige
 * Instanz der Klasse zu gewährleisten.
 */
public class EvGameManager {
    private int score;
    private static EvGameManager instance;

    /**
     * Gibt die einzige Instanz des {@code EvGameManager} zurück. Erstellt die Instanz,
     * falls sie noch nicht existiert.
     *
     * @return Die einzige Instanz von {@code EvGameManager}.
     */
    public static EvGameManager getInstance() {
        if (instance == null) {
            instance = new EvGameManager();
        }
        return instance;
    }

    /**
     * Privater Konstruktor für {@code EvGameManager}.
     */
    private EvGameManager() {
    }

    /**
     * Gibt den aktuellen Spielstand zurück.
     *
     * @return Der aktuelle Spielstand als {@code int}.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setzt den Spielstand auf den angegebenen Wert.
     *
     * @param score Der neue Spielstand.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Erhöht den Spielstand um den angegebenen Wert.
     *
     * @param score Die Punktzahl, die zum aktuellen Spielstand addiert wird.
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Beendet das Spiel und schließt das Spielfenster.
     *
     * @param stage Das Fenster (Stage), das geschlossen werden soll.
     */
    public static void exitGame(Stage stage) {
        EmailInvadersExit exitEvent = new EmailInvadersExit();
        stage.fireEvent(exitEvent);
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
