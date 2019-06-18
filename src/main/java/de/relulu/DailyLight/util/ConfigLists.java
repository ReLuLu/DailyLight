package de.relulu.DailyLight.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import de.relulu.DailyLight.DailyInit;

/**
 * Eine Klasse, die die Bereitstellung der Listen in der Konfiguration handhabt
 */
public class ConfigLists {

    private FileConfiguration cfg;
    private Logger log;

    private List<Material> triggerbuttons;
    private List<Material> triggerplates;
    private List<Material> triggerblockmaterials;
    private List<Material> antigriefmaterials;

    /**
     * ConfigLists Konstruktor
     * @param di DailyInit Instanz
     */
    public ConfigLists(DailyInit di) {
        this.cfg = di.getConfig();
        this.log = di.getLogger();
        this.createLists();
    }

    /**
     * Liest erstmalig die Listen aus der config.yml aus und speichert diese separat ab.
     */
    private void createLists() {

        try {

            // probieren die Listen aus der Config auszulesen...
            this.triggerbuttons = this.readCheckpointTriggerButtons();
            this.triggerplates = this.readCheckpointTriggerPlates();
            this.triggerblocks = this.readCheckpointTriggerMaterials();
            this.antigriefmaterials = this.readAntiGriefMaterials();
            log.info("Material list readout from config.yml successful!");

        } catch (Exception e) {

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
            
            // Liste aller gültigen Blocktypen für einen Checkpoint
            this.triggerblocks = new ArrayList<>(Arrays.asList(
                    Material.GOLD_BLOCK
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

    /**
     * Liest die Liste der antigrief-Materials (Blumentöpfe, ..) aus der config.yml aus
     * @return die Materials als Liste
     */
    private List<Material> readAntiGriefMaterials() {
        return getListFromConfig("decoration-antigrief-materials");
    }

    /**
     * Liest die Liste der Checkpoint-Trigger (Holzknöpfe) aus der config.yml aus
     * @return die Materials als Liste
     */
    private List<Material> readCheckpointTriggerButtons() {
        return getListFromConfig("checkpoint-trigger-buttons");
    }

    /**
     * Liest die Liste der Checkpoint-Trigger (Holzdruckplatten) aus der config.yml aus
     * @return die Materials als Liste
     */
    private List<Material> readCheckpointTriggerPlates() {
        return getListFromConfig("checkpoint-trigger-plates");
    }
    
    /**
     * Liest die Liste der Checkpoint-Blöcke (default Goldblock) aus der config.yml aus
     * @return die Materials als Liste
     */
    private List<Material> readCheckpointTriggerBlocks() {
        return getListFromConfig("checkpoint-trigger-blockmaterials");
    }

    /**
     * Liest eine Liste aus der Konfigurationsdatei aus
     * @param cfgpath der config-path der Liste
     * @return die Liste mit Materials
     */
    private List<Material> getListFromConfig(String cfgpath) {
        List<Material> list = new ArrayList<>();
        for(String s : cfg.getStringList(cfgpath)) {
            list.add(Material.matchMaterial(s));
        }
        return list;
    }

    /**
     * Holt die Liste der antigrief-Materials (Blumentöpfe, ..)
     * @return die Materials als Liste
     */
    List<Material> getAntiGriefMaterials() {
        return this.antigriefmaterials;
    }

    /**
     * Holt die Liste der Checkpoint-Trigger (Holzknöpfe)
     * @return die Materials als Liste
     */
    List<Material> getCheckpointTriggerButtons() {
        return this.triggerbuttons;
    }

    /**
     * Holt die Liste der Checkpoint-Trigger (Holzdruckplatten)
     * @return die Materials als Liste
     */
    List<Material> getCheckpointTriggerPlates() {
        return this.triggerplates;
    }
    
    /**
     * Holt die Liste der Checkpoint-Blöcke (default Goldblock)
     * @return die Materials als Liste
     */
    List<Material> getCheckpointTriggerBlocks() {
        return this.triggerblocks;
    }

}
