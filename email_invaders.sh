#!/bin/bash

# Stelle sicher, dass Java installiert ist
if ! type java > /dev/null; then
    echo "Java ist nicht installiert. Bitte installieren Sie Java, um das Spiel zu starten."
    exit 1
fi

# Pfad zur JAR-Datei
JAR_PATH="src/main/java/com/lhebenbr/emailinvaders/EmailInvaders-1.0-SNAPSHOT.jar"

# Überprüfe, ob die JAR-Datei existiert
if [ ! -f "$JAR_PATH" ]; then
    echo "Die JAR-Datei wurde nicht gefunden. Bitte stellen Sie sicher, dass $JAR_PATH im selben Verzeichnis wie dieses Skript liegt."
    exit 1
fi

# Führe die JAR-Datei aus
echo "Starte Email Invaders..."
java -jar "$JAR_PATH"
