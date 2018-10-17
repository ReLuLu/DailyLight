DailyLight
==========
Ein simples Plugin zum Testen von gebauten Strukturen (Parkouren) zur Verwendung für das Daily-Format im BdHR-Daily Plugin.

Definition Parkour
==================
Ein Parkour kann alles sein, sogar die kleinste Strecke von Block A bis Block B, 
Start- und Endpunkte können vom Spieler dynamisch bestimmt werden. Ebenso können Checkpoints platziert werden.

Definition Checkpoint
=====================
Checkpoints können in Form eines Goldblocks verbaut werden, an dem sich mindestens ein Holzknopf
an einer beliebigen Seite befindet oder eine Druckplatte obendrauf. Um diesen auszulösen, 
muss nur ein Knopf oder die Druckplatte am Goldblock ausgelöst werden.

Befehle
=======

* **_/dstart_** Setzt dem Spieler dort wo er sich derzeit befindet einen Startpunkt, der auch als Checkpoint fungiert

* **_/dcheck_** Teleportiert den Spieler zum letzten Checkpoint (oder Startpunkt falls kein Checkpoint erreicht wurde)

* **_/dend_** Beendet den Parkour des Spielers und gibt dessen Dauer seit /dstart an