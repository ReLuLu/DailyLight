package de.relulu.DailyLight.commands;

import java.util.List;

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
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;

			// wenn der Spieler einen Checkpoint erreicht hat (sich also in der CP-Liste befindet)
			if(dman.hasPlayerCheck(p.getDisplayName())) {
				
				p.teleport(dman.getPlayerCheck(p.getDisplayName()));
				mh.tell(p, mh.getPrimaryColor() + "Zurück zum Checkpoint :)");

			// wenn er sich nicht in der CP Liste befindet...
			} else {

                mh.tell(p, mh.getPrimaryColor() + "Noch keinen Checkpoint erreicht...");

            }

            // wenn der Spieler dann noch Op ist...
            if(p.isOp()) {
                List<String> playcheckli = dman.getPlayerCheckList();
                if(!playcheckli.isEmpty()) {
						
                    mh.tell(p, mh.getPrimaryColor() + "Checkpointliste: " + mh.getSecondaryFormat());
                    for(String s : playcheckli) {
                        mh.tell(p, mh.getPrimaryColor() + s);
                    }
                } else mh.tell(p,mh.getPrimaryColor() + "Checkpointliste: " + mh.getSecondaryFormat() + "leer");
            }

        // wenn der Sender kein Spieler ist...
		} else {
			mh.tell(sender, mh.getPrimaryColor() + "Nur ein Spieler kann diesen Befehl nutzen!");
		}
		
		return true;
	}

}
