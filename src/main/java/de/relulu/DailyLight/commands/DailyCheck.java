package de.relulu.DailyLight.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.util.MessageHandler;
import de.relulu.DailyLight.DailyManager;

/**
 * Diese Klasse handhabt den /dcheck Befehl
 * 
 * @author ReLuLu
 *
 */
public class DailyCheck implements CommandExecutor {

	private DailyManager dman;
	private MessageHandler mh;
	
	public DailyCheck(DailyManager dman) {
		this.dman = dman;
		this.mh = dman.getMessageHandler();
	}

	/**
	 * Handelt den dcheck Befehl ab
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            // wenn der Befehl ohne Parameter daherkommt
            if (comparams.length < 1) {

                // wenn der Spieler einen Checkpoint erreicht hat (sich also in der CP-Liste befindet)
                if (dman.hasPlayerCheck(p.getDisplayName())) {

                    p.teleport(dman.getPlayerCheck(p.getDisplayName()));
                    mh.tell(p, mh.getPrimaryColor() + "Zurück zum Checkpoint :)");

                // wenn er sich nicht in der CP Liste befindet...
                } else {

                    mh.tell(p, mh.getPrimaryColor() + "Noch keinen Checkpoint erreicht...");
                }

                // wenn der Spieler Op ist, dann gib den Checkpoint-Debug aus
                if(p.isOp()) {
                    printCheckpointDebug(p);
                }

            // wenn der Befehl mit Parameter daherkommt
            } else if (comparams.length == 1) {

                // wenn der Spieler dann noch Op ist...
                if (p.isOp()) {

                    String targetplayername;
                    targetplayername = comparams[0];
                    Player targetplayer = Bukkit.getPlayerExact(targetplayername);

                    // nur wenn auch wirklich ein valider targetplayer vorhanden ist
                    if (targetplayer != null) {

                        // wenn der Spieler einen Checkpoint erreicht hat (sich also in der CP-Liste befindet)
                        if (dman.hasPlayerCheck(targetplayer.getDisplayName())) {

                            // Spieler zum Checkpoint teleportieren
                            targetplayer.teleport(dman.getPlayerCheck(targetplayer.getDisplayName()));
                            mh.tell(p, mh.getPrimaryColor() + "Spieler "
                                    + mh.getSecondaryFormat() + targetplayer.getDisplayName()
                                    + mh.getPrimaryColor() + " zurück zum Checkpoint teleportiert!");

                            mh.tell(targetplayer, mh.getPrimaryColor() + "Du wurdest von "
                                    + mh.getSecondaryFormat() + p.getDisplayName()
                                    + mh.getPrimaryColor() + " zum letzten Checkpoint teleportiert :)");

                        // wenn der Spieler noch keinen Checkpoint erreicht hat
                        } else {
                            mh.tell(p, mh.getSecondaryFormat() + targetplayer.getDisplayName()
                                    + mh.getPrimaryColor() + " hat noch keinen Checkpoint erreicht...");
                        }

                        // wenn der Spieler Op ist, dann gib den Checkpoint-Debug aus
                        if(p.isOp()) {
                            printCheckpointDebug(p);
                        }

                    // wenn der gewünschte targetplayer nicht gefunden werden konnte
                    } else {

                        mh.tell(p, mh.getPrimaryColor() + "Spieler "
                                + mh.getSecondaryFormat() + targetplayername
                                + mh.getPrimaryColor() + " konnte nicht gefunden werden.");

                        return true;
                    }

                }

            }

            // wenn der Sender kein Spieler ist...
            } else {
                mh.tell(sender, mh.getPrimaryColor() + "Nur ein Spieler kann diesen Befehl nutzen!");
            }

            return true;
        }

    /**
     * Gibt die Liste der Spieler-/Checkpointmappings aus
     * @param p Spieler, der die Ausgabe erhält
     */
    private void printCheckpointDebug(Player p){
        List<String> playcheckli = dman.getPlayerCheckList();
        if (!playcheckli.isEmpty()) {

            mh.tell(p, mh.getPrimaryColor() + "Checkpointliste: " + mh.getSecondaryFormat());
            for (String s : playcheckli) {
                mh.tell(p, mh.getPrimaryColor() + s);
            }
        } else mh.tell(p, mh.getPrimaryColor() + "Checkpointliste: " + mh.getSecondaryFormat() + "leer");
    }

}
