package de.relulu.DailyLight.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.DailyManager;

/**
 * Diese Klasse handhabt den /dend Befehl
 * 
 * @author ReLuLu
 *
 */
public class DailyEnd implements CommandExecutor {

	private DailyManager dman;
	
	public DailyEnd(DailyManager dman) {
		this.dman = dman;
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
				
				p.sendMessage(dman.getConfigManager().getMessagePrefix() 
						+ dman.getConfigManager().getMessagePrimaryColor() 
						+ "Parkour beendet in§r " + dman.getPlayerDurationTime(p.getDisplayName()));
				
				dman.removePlayerStartTime(p.getDisplayName());
				dman.removePlayerCheck(p.getDisplayName());
				p.setInvulnerable(false);
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1);
				
				return true;
				
			// wenn der Befehl mit Parameter daherkommt
			} else if(comparams.length == 1) {
				
				// nur wenn der Spieler auch Operator ist darf er das
				if(p.isOp()) {
					
					String targetplayername = "";
					targetplayername = comparams[0];
					Player targetplayer = Bukkit.getPlayerExact(targetplayername);
					
					if(targetplayer != null) {
						
						p.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Parkour für Spieler:§r " + targetplayer.getDisplayName() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ " beendet.");
						
						targetplayer.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Parkour beendet in§r " 
								+ dman.getPlayerDurationTime(targetplayer.getDisplayName()));
						
						dman.removePlayerStartTime(targetplayer.getDisplayName());
						dman.removePlayerCheck(targetplayer.getDisplayName());
						targetplayer.setInvulnerable(false);
						targetplayer.playSound(targetplayer.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1);
						
						return true;
						
					} else {
						
						p.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Spieler§r " + targetplayername 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ " konnte nicht gefunden werden.");
						
						return true;
					}
				
				// sonst bekommt er eine Meldung, dass ihm die Rechte fehlen
				} else {
					
					p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Nur ein Operator kann für andere Spieler den Startpunkt setzen.");
					
					return true;
				}
				
			}
		
		} else {
			sender.sendMessage("Nur ein Spieler kann diesen Befehl nutzen!");
			return true;
		}
		
		return false;
	}

}
