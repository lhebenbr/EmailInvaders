package manager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Die Klasse {@code EvDatabaseManager} dient zur Verwaltung der Datenbankoperationen
 * für das Spiel "Email Invaders". Sie kümmert sich um das Erstellen, Verbinden und
 * Interagieren mit der SQLite-Datenbank.
 */
public class EvDatabaseManager {

    String userHome = System.getProperty("user.home");
    String appFolder = ".EmailInvaders";
    String dbFileName = "highscore_ev.db";
    File dbFile = new File(userHome, appFolder + File.separator + dbFileName);

    private static EvDatabaseManager instance;

    /**
     * Gibt die einzige Instanz des {@code EvDatabaseManager} zurück. Erstellt die Instanz,
     * falls sie noch nicht existiert.
     *
     * @return Die einzige Instanz von {@code EvDatabaseManager}.
     */
    public static EvDatabaseManager getInstance() {
        if (instance == null) {
            instance = new EvDatabaseManager();
        }
        return instance;
    }

    /**
     * Konstruktor für {@code EvDatabaseManager}.
     */
    public EvDatabaseManager() {
    }

    /**
     * Initialisiert die Datenbank. Erstellt die Datenbankdatei und die erforderlichen Tabellen,
     * falls sie noch nicht existieren.
     */
    public void init() {
        try {
            if (!dbFile.exists()) {
                if (!dbFile.getParentFile().exists()) {
                    boolean dirCreated = dbFile.getParentFile().mkdirs();
                    if (!dirCreated) {
                        System.out.println("db kann nicht erstellt werden");
                    }
                }
                initializeDatabase(dbFile.getPath());
            }
        } catch (Exception e) {
            System.err.println("An error occurred while initializing the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Stellt eine Verbindung zur SQLite-Datenbank her.
     *
     * @return Eine {@code Connection}-Instanz zur Datenbank.
     */
    private Connection connect() {
        String url = "jdbc:sqlite:" + dbFile.getPath();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Fügt einen neuen Highscore in die Datenbank ein.
     *
     * @param score Der Highscore, der eingefügt werden soll.
     */
    public void insert(int score) {
        String sql = "insert into highscores (score) values (?);";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gibt den höchsten gespeicherten Highscore zurück.
     *
     * @return Der höchste Highscore als {@code int}.
     */
    public int getMaxScore() {
        String sql = "SELECT MAX(score) AS max_score FROM highscores";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            if (rs.next()) {
                return rs.getInt("max_score");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Initialisiert die Datenbankstruktur, indem eine Tabelle für Highscores erstellt wird,
     * falls sie noch nicht existiert.
     *
     * @param filePath Der Dateipfad zur SQLite-Datenbank.
     */
    private void initializeDatabase(String filePath) {
        String url = "jdbc:sqlite:" + filePath;

        String sql = "CREATE TABLE IF NOT EXISTS highscores (\n"
                + " id integer PRIMARY KEY,\n"
                + " score integer NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // Create a new table
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
