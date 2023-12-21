#!/bin/bash

# Stelle sicher, dass Java installiert ist
if ! type java > /dev/null; then
    echo "Java ist nicht installiert. Bitte installieren Sie Java, um das Spiel zu starten."
    exit 1
fi

# Determine OS and set the corresponding JAR
if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    JAR_PATH="libs/EmailInvaders-linux.jar"
elif [[ "$OSTYPE" == "darwin"* ]]; then
    JAR_PATH="libs/EmailInvaders-mac.jar"
elif [[ "$OSTYPE" == "cygwin" ]] || [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "win32" ]]; then
    JAR_PATH="libs/EmailInvaders-windows.jar"
else
    echo "Unbekanntes Betriebssystem: $OSTYPE"
    exit 1
fi

# Führe die entsprechende JAR-Datei aus
java -jar "$JAR_PATH"


# Überprüfe, ob die JAR-Datei existiert
if [ ! -f "$JAR_PATH" ]; then
    echo "Die JAR-Datei wurde nicht gefunden. Bitte stellen Sie sicher, dass $JAR_PATH im selben Verzeichnis wie dieses Skript liegt."
    exit 1
fi

# Führe die JAR-Datei aus
echo "Starte Email Invaders..."
java -jar "$JAR_PATH"
