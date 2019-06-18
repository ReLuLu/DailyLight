package de.relulu.DailyLight;

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

import de.relulu.DailyLight.util.ConfigManager;
import de.relulu.DailyLight.util.MessageHandler;

/**
 * Diese Klasse stellt den Listener für aufkommende Events, die von diesem
 * Plugin verarbeitet werden sollen.
 * 
 * @author ReLuLu
 *
 */
public class DailyListener implements Listener {
	
	private DailyManager dman;
	private MessageHandler mh;
	private ConfigManager confman;

    /**
     * Konstruktor für den Listener
     * @param dman DailyManager Objekt
     */
    public DailyListener(DailyManager dman) {
        this.dman = dman;
        this.mh = dman.getMessageHandler();
        this.confman = dman.getConfigManager();
	}

    /**
	 * Player interagiert bzw. löst etwas aus
	 * 
	 * @param pie das PlayerInteractEvent
	 */
	@EventHandler
	public void onCheck(PlayerInteractEvent pie) { 
		
		// Rechtsklick Action
		if(pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			Player p = pie.getPlayer();
			Block bl = pie.getClickedBlock();
			Material m = pie.getClickedBlock().getType();

			// wenn der Block in der Knopfliste ist
			if(confman.getCheckpointTriggerButtons().contains(m)) {
				
				// Den geklickten Block auf Switch casten
				Switch sw = (Switch)bl.getState().getBlockData();
				
				// Knopf obendrauf aber nicht an der Decke
				if((sw.getFace() == Face.FLOOR) && (confman.getCheckpointTriggerBlocks().contains(bl.getRelative(BlockFace.DOWN).getType()))) {
					
					p.sendMessage(mh.getPrefix()
							+ mh.getPrimaryColor()
							+ "Checkpoint Knopf obendrauf");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
				
				// Knopf drumrum, schaut nach dem Block an dem face wo Knopf dranklebt
				else if(bl.getRelative(confman.getCheckpointTriggerBlocks().contains(sw.getFacing().getOppositeFace()).getType())) {
					
					p.sendMessage(mh.getPrefix()
                            + mh.getPrimaryColor()
                            + "Checkpoint Knopf drumrum");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
				
				// Knopf an der Decke aber nur wenn dort ein Checkpointblock ist
				else if((sw.getFace() == Face.CEILING) && (confman.getCheckpointTriggerBlocks().contains(bl.getRelative(BlockFace.UP).getType()))) {
					
					p.sendMessage(mh.getPrefix()
                            + mh.getPrimaryColor()
							+ "Checkpoint Knopf an der Decke");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
			}

			// wenn der Block in der Topfpflanzenliste ist
			else if(confman.getAntiGriefMaterials().contains(m)) {

                // nur wenn es in der Konfiguration aktiviert ist
				if(confman.getAntiGrief()) {

                    // wenn der Spieler sich im Parkour befindet
                    if(dman.isPlayerInDaily(p.getDisplayName())) {
                        pie.setCancelled(true);
                    }
                }
			}
		}
		
		// Physical Action durch Betätigen einer Druckplatte
		else if(pie.getAction().equals(Action.PHYSICAL)) {
			
			Player p = pie.getPlayer();
			Block bl = pie.getClickedBlock();
			Material m = pie.getClickedBlock().getType();
			
			if(confman.getCheckpointTriggerPlates().contains(m)) {
				
				// Druckplatten sind ja immer auf einem Untergrund, daher DOWN
				if(confman.getCheckpointTriggerBlocks().contains(bl.getRelative(BlockFace.DOWN).getType())) {
					
					p.sendMessage(mh.getPrefix()
                            + mh.getPrimaryColor()
							+ "Checkpoint Platte obendrauf");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());

				}
			}
			
		}

	}
	
	/**
	 * Das Foodlevel vom Spieler ändert sich
	 * 
	 * @param flce das FoodLevelChangeEvent
	 */
	@EventHandler
	public void onHunger(FoodLevelChangeEvent flce) {

	    // nur wenn es in der Konfiguration aktiviert ist
        if(confman.getNoHunger()) {

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

}
