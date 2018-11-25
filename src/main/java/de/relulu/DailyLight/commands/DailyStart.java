package de.relulu.DailyLight.commands;

import de.relulu.DailyLight.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.util.MessageHandler;
import de.relulu.DailyLight.DailyManager;

/**
 * Diese Klasse handhabt den /dstart Befehl
 * 
 * @author ReLuLu
 *
 */
public class DailyStart implements CommandExecutor {
	
	private DailyManager dman;
	private MessageHandler mh;
	private ConfigManager dconf;
	
	public DailyStart(DailyManager dman) {
		this.dman = dman;
		this.mh = dman.getMessageHandler();
		this.dconf = dman.getConfigManager();
	}

	/**
	 * Handelt den dstart Befehl ab
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
			
			// wenn der Befehl ohne Parameter daherkommt
			if(comparams.length < 1) {
				
				mh.tell(p,mh.getPrimaryColor() + "Startpunkt gesetzt");
				
				dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
				dman.setPlayerStartTime(p.getDisplayName());

				// unverwundbar setzen wenn aktiv
				if(dconf.getNoDamage()) {
                    p.setInvulnerable(true);
                }

                // Hunger auf 10 Keulen auffüllen wenn aktiv
                if(dconf.getNoHunger()) {
                    p.setFoodLevel(20);
                }

				return true;
				
			// wenn der Befehl mit Parameter daherkommt
			} else if(comparams.length == 1) {
				
				// nur wenn der Spieler auch Operator ist darf er das
				if(p.isOp()) {
					
					String targetplayername;
					targetplayername = comparams[0];
					Player targetplayer = Bukkit.getPlayerExact(targetplayername);

                    // nur wenn auch wirklich ein valider targetplayer vorhanden ist
					if(targetplayer != null) {
						
						mh.tell(p,mh.getPrimaryColor() + "Startpunkt für Spieler: "
                                + mh.getSecondaryFormat() + targetplayer.getDisplayName()
								+ mh.getPrimaryColor() + " gesetzt.");
						
						mh.tell(targetplayer, mh.getPrimaryColor() + "Dein Startpunkt wurde von "
                                + mh.getSecondaryFormat() + p.getDisplayName()
								+ mh.getPrimaryColor() + " gesetzt.");
						
						// Spieler zum Start-Initiator teleportieren
						targetplayer.teleport(p.getLocation());
						
						// Location vom targetplayer
						dman.setPlayerCheck(targetplayer.getDisplayName(), targetplayer.getLocation());
						
						dman.setPlayerStartTime(targetplayer.getDisplayName());

                        // unverwundbar setzen wenn aktiv
						if(dconf.getNoDamage()) {
                            targetplayer.setInvulnerable(true);
                        }

                        // Hunger auf 10 Keulen auffüllen wenn aktiv
                        if(dconf.getNoHunger()) {
                            targetplayer.setFoodLevel(20);
                        }
						
						return true;

                    // wenn der gewünschte targetplayer nicht gefunden werden konnte
					} else {

                        mh.tell(p,mh.getPrimaryColor() + "Spieler "
                                + mh.getSecondaryFormat() + targetplayername
                                + mh.getPrimaryColor() + " konnte nicht gefunden werden.");
						
						return true;
					}
				
				// sonst bekommt er eine Meldung, dass ihm die Rechte fehlen
				} else {

                    mh.tell(p, mh.getPrimaryColor() + "Nur ein Operator kann für andere Spieler den Startpunkt setzen.");
                    return true;
				}
				
			}

		// Konsole
		} else {
            mh.tell(sender, mh.getPrimaryColor() + "Nur ein Spieler kann diesen Befehl nutzen!");
            return true;
		}
		
		return false;
	}

}
