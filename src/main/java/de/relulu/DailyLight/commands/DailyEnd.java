package de.relulu.DailyLight.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.util.MessageHandler;
import de.relulu.DailyLight.DailyManager;

/**
 * Diese Klasse handhabt den /dend Befehl
 * 
 * @author ReLuLu
 *
 */
public class DailyEnd implements CommandExecutor {

	private DailyManager dman;
	private MessageHandler mh;
	
	public DailyEnd(DailyManager dman) {
		this.dman = dman;
		this.mh = dman.getMessageHandler();
	}
	
	/**
	 * Handelt den dend Befehl ab
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
			
			// wenn der Befehl ohne Parameter daherkommt
			if(comparams.length < 1) {
				
				mh.tell(p, mh.getPrimaryColor() + "Parkour beendet in "
						+ mh.getSecondaryFormat() + dman.getPlayerDurationTime(p.getDisplayName()));
				
				dman.removePlayerStartTime(p.getDisplayName());
				dman.removePlayerCheck(p.getDisplayName());

				// bedingungslos immer verwundbar zurücksetzen, für den Fall dass während eines Parkous die Config geändert wird
				p.setInvulnerable(false);
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1);
				
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
						
						mh.tell(p, mh.getPrimaryColor() + "Parkour für Spieler: "
                                + mh.getSecondaryFormat() + targetplayer.getDisplayName()
								+ mh.getPrimaryColor() + " beendet.");
						
						mh.tell(targetplayer,mh.getPrimaryColor() + "Parkour beendet in "
								+ mh.getSecondaryFormat() + dman.getPlayerDurationTime(targetplayer.getDisplayName()));
						
						dman.removePlayerStartTime(targetplayer.getDisplayName());
						dman.removePlayerCheck(targetplayer.getDisplayName());

                        // bedingungslos immer verwundbar zurücksetzen, für den Fall dass während eines Parkous die Config geändert wird
						targetplayer.setInvulnerable(false);
						targetplayer.playSound(targetplayer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1);
						
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
					
					mh.tell(p, mh.getPrimaryColor() + "Nur ein Operator kann für andere Spieler den Endpunkt setzen.");
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
