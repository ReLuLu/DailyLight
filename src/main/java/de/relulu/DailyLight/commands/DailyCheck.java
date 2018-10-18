package de.relulu.DailyLight.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.DailyManager;

/**
 * Diese Klasse handhabt den /dcheck Befehl
 * 
 * @author ReLuLu
 *
 */
public class DailyCheck implements CommandExecutor {

	private DailyManager dman;
	
	public DailyCheck(DailyManager dman) {
		this.dman = dman;
	}

	/**
	 * Handelt den dcheck Befehl ab
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
			if(dman.hasPlayerCheck(p.getDisplayName())) {
				
				p.teleport(dman.getPlayerCheck(p.getDisplayName()));
				if(p.isOp()) {
					List<String> playcheckli = dman.getPlayerCheckList();
					if(!playcheckli.isEmpty()) {
						
						p.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Checkpointliste:§r ");
						
						for(String s : playcheckli) {
							p.sendMessage(s);
						}
					} else p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Checkpointliste:§r leer");
				}
				p.sendMessage(dman.getConfigManager().getMessagePrefix() 
						+ dman.getConfigManager().getMessagePrimaryColor() 
						+ "Zurück zum Checkpoint :)");
				
				return true;
				
			} else {
				
				p.sendMessage(dman.getConfigManager().getMessagePrefix() 
						+ dman.getConfigManager().getMessagePrimaryColor() 
						+ "Noch keinen Checkpoint erreicht...");
				
				if(p.isOp()) {
					List<String> playcheckli = dman.getPlayerCheckList();
					if(!playcheckli.isEmpty()) {
						
						p.sendMessage(dman.getConfigManager().getMessagePrefix() 
								+ dman.getConfigManager().getMessagePrimaryColor() 
								+ "Checkpointliste:§r ");
						
						for(String s : playcheckli) {
							p.sendMessage(s);
						}
					} else p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Checkpointliste:§r leer");
					
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
