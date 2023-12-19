# Email Invaders

_______________________________________________________________________________________________________________________

## Beschreibung

Im Spiel Email Invaders geht es darum sein Postfach vor bösen Phising-Mails zu beschützen.
Das Spiel ist in seiner Funktion an das bekannte Spiel Space Invaders angelehnt.
_______________________________________________________________________________________________________________________

Das Menü:
![Bild von Menü](email_invaders_menu.png)
_______________________________________________________________________________________________________________________

Gameplay:
![Bild vom Gameplay](email_invaders_game.png)
_______________________________________________________________________________________________________________________

Gameover:
![Bild Gameover Screen](email_invaders_gameover.png)
_______________________________________________________________________________________________________________________

## Funktionsweise

Man steuert mit den Pfeiltasten das eigene Schiff und kann mit der Leertaste schießen. Nun geht es darum einen möglicht
hohen Highscore zu erreichen. Dieser wird durch das Abschießen von Mails erreicht. Diese kommen in verschiedenen
Varianten mit unterschiedlich vielen Punkten und anderen Attributen.
In regelmäßigen Intervalen wird eine Bonusmail erzeugt die sich am oberen Bildschirmrand bewegt. Bei einem Treffer gibt
es Bonuspunkte und der Spieler erhält Leben.
Falls alle Mails zerstört wurden, erscheinen neue Mails. Wenn eine Mail den unteren Bildschirmrand erreicht oder der
Spieler zu oft von den Mails getroffen wird, ist das Spiel vorbei.
Mit der abnehmender Anzahl werden die Mails schneller und sie schießen auch schneller.
Zusätzlich gibt es noch verschiedene Power-ups mit unterschiedlichen effekten, die auftreten, wenn sie mit dem Spieler
kollidieren.

## Power-ups

_______________________________________________________________________________________________________________________

+ 1000 Bonuspunkte
  ![Power-up 1](src/main/resources/com/lhebenbr/emailinvaders/assets/textures/point_bonus.png?raw=true)

_______________________________________________________________________________________________________________________

+ Man verliert Leben
+ Zerstört Mails auf dem Weg
  ![Power-up 2](src/main/resources/com/lhebenbr/emailinvaders/assets/textures/skull.png?raw=true)

_______________________________________________________________________________________________________________________

+ Man kann für 2 Sekunden schneller schießen
  ![Power-up 3](src/main/resources/com/lhebenbr/emailinvaders/assets/textures/powerup_bonus.png?raw=true)

_______________________________________________________________________________________________________________________

+ Man gewinnt Leben dazu, aber man kann nicht mehr als 3 Leben haben
  ![Power-up 4](src/main/resources/com/lhebenbr/emailinvaders/assets/textures/heart_bonus.png?raw=true)