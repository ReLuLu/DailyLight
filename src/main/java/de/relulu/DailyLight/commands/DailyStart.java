package de.relulu.DailyLight.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.DailyManager;

/**
 * Diese Klasse handhabt den /dstart Befehl
 * 
 * @author ReLuLu
 *
 */
public class DailyStart implements CommandExecutor {
	
	private DailyManager dman;
	
	public DailyStart(DailyManager dman) {
		this.dman = dman;
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
				
				p.sendMessage(dman.getConfigManager().getMessagePrefix() 
						+ dman.getConfigManager().getMessagePrimaryColor() 
						+ "Startpunkt gesetzt");
				
				dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
				dman.setPlayerStartTime(p.getDisplayName());
				p.setInvulnerable(true);
				p.setFoodLevel(20); // Hunger auf standardmäßig 10 Keulen auffüllen
				
				return true;
				
			// wenn der Befehl mit Parameter daherkommt
			} else if(comparams.length == 1) {
				
				// nur wenn der Spieler auch Operator ist darf er das
				if(p.isOp()) {
					
					String targetplayername = "";
					
					/*
					for(String s: comparams) {
						targetplayername = s;
					}*/
					
					targetplayername = comparams[0];

					Player targetplayer = Bukkit.getPlayerExact(targetplayername);
					if(targetplayer != null) {
						
						p.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Startpunkt für Spieler:§r " + targetplayer.getDisplayName() 
								+ dman.getConfigManager().getMessagePrimaryColor() + " gesetzt.");
						
						targetplayer.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Dein Startpunkt wurde von§r " + p.getDisplayName() 
								+ dman.getConfigManager().getMessagePrimaryColor() + " gesetzt.");
						
						// Spieler zum Start-Initiator teleportieren
						targetplayer.teleport(p.getLocation());
						
						// Location vom targetplayer
						dman.setPlayerCheck(targetplayer.getDisplayName(), targetplayer.getLocation());
						
						// Location von dem, der den Befehl tippt
						//dman.setPlayerCheck(targetplayer.getDisplayName(), p.getLocation());
						
						dman.setPlayerStartTime(targetplayer.getDisplayName());
						
						// Spieler unverwundbar setzen
						targetplayer.setInvulnerable(true);
						
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
