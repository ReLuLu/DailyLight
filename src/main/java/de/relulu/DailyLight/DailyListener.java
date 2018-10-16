package de.relulu.DailyLight;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Switch.Face;

/**
 * Diese Klasse stellt den Listener für aufkommende Events, die von diesem
 * Plugin verarbeitet werden sollen.
 * 
 * @author ReLuLu
 *
 */
public class DailyListener implements Listener {
	
	private DailyManager dman;

	public DailyListener(DailyManager dman) {
		this.dman = dman;
	}	
	
	private final List<Material> validbuttons = Arrays.asList(
			Material.OAK_BUTTON, Material.BIRCH_BUTTON, Material.SPRUCE_BUTTON,
			Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON
			);
	
	private final List<Material> validplates = Arrays.asList(
			Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, 
			Material.SPRUCE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, 
			Material.JUNGLE_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE
		);
	
	/**
	 * Player interagiert bzw. löst etwas aus
	 * 
	 * @param pie
	 */
	@EventHandler
	public void onCheck(PlayerInteractEvent pie) { 
		
		// Rechtsklick Action
		if(pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			Player p = (Player)pie.getPlayer();
			Block bl = pie.getClickedBlock();
			Material m = pie.getClickedBlock().getType();

			if(validbuttons.contains(m)) { // wenn der Block in der Knopfliste ist
				
				// Den geklickten Block auf Switch casten
				Switch sw = (Switch)bl.getState().getBlockData();
				
				// Knopf obendrauf aber nicht an der Decke
				if((sw.getFace() == Face.FLOOR) && (bl.getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK)) {
					
					p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Checkpoint Knopf obendrauf");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
				
				// Knopf drumrum, schaut nach dem Block an dem face wo Knopf dranklebt
				else if(bl.getRelative(sw.getFacing().getOppositeFace()).getType() == Material.GOLD_BLOCK) {
					
					p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Checkpoint Knopf drumrum");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
				
				// Knopf an der Decke aber nur wenn dort ein Goldblock ist
				else if((sw.getFace() == Face.CEILING) && (bl.getRelative(BlockFace.UP).getType() == Material.GOLD_BLOCK)) {
					
					p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Checkpoint Knopf an der Decke");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}

			}
		}
		
		// Physical Action durch Betätigen einer Druckplatte
		else if(pie.getAction().equals(Action.PHYSICAL)) {
			
			Player p = (Player)pie.getPlayer();
			Block bl = pie.getClickedBlock();
			Material m = pie.getClickedBlock().getType();
			
			if(validplates.contains(m)) {
				
				// Druckplatten sind ja immer auf einem Untergrund, daher DOWN
				if(bl.getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
					
					p.sendMessage(dman.getConfigManager().getMessagePrefix() 
							+ dman.getConfigManager().getMessagePrimaryColor() 
							+ "Checkpoint Platte obendrauf");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());

				}
			}
			
		}

	}
	
	/**
	 * Das Foodlevel vom Spieler ändert sich
	 * 
	 * @param flce
	 */
	@EventHandler
	public void onHunger(FoodLevelChangeEvent flce) { 
		
		// welche Entity löst das Event aus
		HumanEntity he = flce.getEntity();
		
		// ist die Entity auch ein Spieler?
		if(he instanceof Player) {
			Player p = (Player)he;
			// ist der Spieler auch aktuell im Daily?
			if(dman.isPlayerInDaily(p.getName())) {
				flce.setCancelled(true);
			}
		}
		
	}

}
