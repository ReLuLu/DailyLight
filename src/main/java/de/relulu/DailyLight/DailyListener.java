package de.relulu.DailyLight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.relulu.DailyLight.util.ConfigManager;
import de.relulu.DailyLight.util.MessageHandler;
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
	private MessageHandler mh;
	private ConfigManager dconf;

    /**
     * Konstruktor für den Listener
     * @param dman DailyManager Objekt
     */
    public DailyListener(DailyManager dman) {
        this.dman = dman;
        this.mh = dman.getMessageHandler();
        this.dconf = dman.getConfigManager();
        try {
            // probieren die Listen aus der Config auszulesen...
            this.triggerbuttons = dconf.getCheckpointTriggerButtons();
            this.triggerplates = dconf.getCheckpointTriggerPlates();
            this.antigriefmaterials = dconf.getAntiGriefMaterials();
        } catch(Exception e) {
            // wenns fehlschlägt dann implizit füllen
            // Liste aller gültigen Knöpfe für Checkpoints
            this.triggerbuttons = new ArrayList<>(Arrays.asList(
                    Material.OAK_BUTTON, Material.BIRCH_BUTTON, Material.SPRUCE_BUTTON,
                    Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.ACACIA_BUTTON
            ));

            // Liste aller gültigen Druckplatten für Checkpoints
            this.triggerplates = new ArrayList<>(Arrays.asList(
                    Material.OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE,
                    Material.SPRUCE_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
                    Material.JUNGLE_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE
            ));

            // Liste aller Topfpflanzen für den gm2 Deko-Schutz
            this.antigriefmaterials = new ArrayList<>(Arrays.asList(
                    Material.POTTED_ACACIA_SAPLING,
                    Material.POTTED_ALLIUM,
                    Material.POTTED_AZURE_BLUET,
                    Material.POTTED_BIRCH_SAPLING,
                    Material.POTTED_BLUE_ORCHID,
                    Material.POTTED_BROWN_MUSHROOM,
                    Material.POTTED_CACTUS,
                    Material.POTTED_DANDELION,
                    Material.POTTED_DARK_OAK_SAPLING,
                    Material.POTTED_DEAD_BUSH,
                    Material.POTTED_FERN,
                    Material.POTTED_JUNGLE_SAPLING,
                    Material.POTTED_OAK_SAPLING,
                    Material.POTTED_ORANGE_TULIP,
                    Material.POTTED_OXEYE_DAISY,
                    Material.POTTED_PINK_TULIP,
                    Material.POTTED_POPPY,
                    Material.POTTED_RED_MUSHROOM,
                    Material.POTTED_RED_TULIP,
                    Material.POTTED_SPRUCE_SAPLING,
                    Material.POTTED_WHITE_TULIP,
                    Material.FLOWER_POT
            ));
        }
	}

    private List<Material> triggerbuttons;
    private List<Material> triggerplates;
    private List<Material> antigriefmaterials;

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
			if(triggerbuttons.contains(m)) {
				
				// Den geklickten Block auf Switch casten
				Switch sw = (Switch)bl.getState().getBlockData();
				
				// Knopf obendrauf aber nicht an der Decke
				if((sw.getFace() == Face.FLOOR) && (bl.getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK)) {
					
					p.sendMessage(mh.getPrefix()
							+ mh.getPrimaryColor()
							+ "Checkpoint Knopf obendrauf");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
				
				// Knopf drumrum, schaut nach dem Block an dem face wo Knopf dranklebt
				else if(bl.getRelative(sw.getFacing().getOppositeFace()).getType() == Material.GOLD_BLOCK) {
					
					p.sendMessage(mh.getPrefix()
                            + mh.getPrimaryColor()
                            + "Checkpoint Knopf drumrum");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
				
				// Knopf an der Decke aber nur wenn dort ein Goldblock ist
				else if((sw.getFace() == Face.CEILING) && (bl.getRelative(BlockFace.UP).getType() == Material.GOLD_BLOCK)) {
					
					p.sendMessage(mh.getPrefix()
                            + mh.getPrimaryColor()
							+ "Checkpoint Knopf an der Decke");
					dman.setPlayerCheck(p.getDisplayName(), p.getLocation());
					
				}
			}

			// wenn der Block in der Topfpflanzenliste ist
			else if(antigriefmaterials.contains(m)) {

				// wenn der Spieler sich im Parkour befindet
				if(dman.isPlayerInDaily(p.getDisplayName())) {
					pie.setCancelled(true);
				}
			}
		}
		
		// Physical Action durch Betätigen einer Druckplatte
		else if(pie.getAction().equals(Action.PHYSICAL)) {
			
			Player p = pie.getPlayer();
			Block bl = pie.getClickedBlock();
			Material m = pie.getClickedBlock().getType();
			
			if(triggerplates.contains(m)) {
				
				// Druckplatten sind ja immer auf einem Untergrund, daher DOWN
				if(bl.getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
					
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
        if(dconf.getNoHunger()) {

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
