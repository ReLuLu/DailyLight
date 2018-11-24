package de.relulu.DailyLight.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Diese Klasse kümmert sich um das Auslesen und Bereitstellen von Konfigurationsparametern
 * 
 * @author ReLuLu
 *
 */
public class ConfigManager {
	
	private FileConfiguration cfg;
	private ConfigLists conflist;

	/**
	 * Konstruktor für für den Konfigurationsmanager
	 * 
	 * @param fcfg die Konfigurationsdatei
     * @param conflist die ConfigLists Klasse
	 */
	public ConfigManager(FileConfiguration fcfg, ConfigLists conflist) {
		this.cfg = fcfg;
		this.conflist = conflist;
	}

    /**
     * Gibt die ConfigLists zurück, um Zugriff
     * auf listenbasierte Konfigurationsinhalte
     * zu ermöglichen
     *
     * @return conflist ConfigLists
     * @deprecated
     */
    public ConfigLists getConfigLists() {
        return this.conflist;
    }

	/**
	 * Setzt die nodamage-Variable
	 * @param b neuer Wert
	 */
	public void setNoDamage(boolean b) {
		cfg.set("player-nodamage", b);
	}

	/**
	 * Holt den Wert der nodamage-Variable
	 * @return Wert der Variable
	 */
	public boolean getNoDamage() {
		return cfg.getBoolean("player-nodamage");
	}

	/**
	 * Setzt die nohunger-Variable
	 * @param b neuer Wert
	 */
	public void setNoHunger(boolean b) {
		cfg.set("player-nohunger", b);
	}

	/**
	 * Holt den Wert der nohunger-Variable
	 * @return Wert der Variable
	 */
	public boolean getNoHunger() {
		return cfg.getBoolean("player-nohunger");
	}

	/**
	 * Setzt die antigrief-Variable
	 * @param b neuer Wert
	 */
	public void setAntiGrief(boolean b) {
		cfg.set("decoration-antigrief", b);
	}

	/**
	 * Holt den Wert der antigrief-Variable
	 * @return Wert der Variable
	 */
	public boolean getAntiGrief() {
		return cfg.getBoolean("decoration-antigrief");
	}

    /**
     * Holt die Liste der Checkpoint-Trigger (Holzknöpfe) aus der ConfigLists Klasse
     * @return die Materials als Liste
     */
    public List<Material> getCheckpointTriggerButtons() {
        return conflist.getCheckpointTriggerButtons();
    }

    /**
     * Holt die Liste der Checkpoint-Trigger (Holzdruckplatten) aus der ConfigLists Klasse
     * @return die Materials als Liste
     */
    public List<Material> getCheckpointTriggerPlates() {
        return conflist.getCheckpointTriggerPlates();
    }

    /**
     * Holt die Liste der antigrief-Materials (Blumentöpfe, ..) aus der ConfigLists Klasse
     * @return die Materials als Liste
     */
    public List<Material> getAntiGriefMaterials() {
        return conflist.getAntiGriefMaterials();
    }



}
