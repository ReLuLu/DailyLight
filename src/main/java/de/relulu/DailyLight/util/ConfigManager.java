package de.relulu.DailyLight.util;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse kümmert sich um das Auslesen und Bereitstellen von Konfigurationsparametern
 * 
 * @author ReLuLu
 *
 */
public class ConfigManager {
	
	private FileConfiguration cfg;
	
	/**
	 * Konstruktor für für den Konfigurationsmanager
	 * 
	 * @param fcfg die Konfigurationsdatei
	 */
	public ConfigManager(FileConfiguration fcfg) {
		this.cfg = fcfg;
	}

	/**
	 * Setzt die immortal-Variable
	 * @param b neuer Wert
	 */
	public void setNoDamage(boolean b) {
		cfg.set("player-immortal", b);
	}

	/**
	 * Holt den Wert der immortal-Variable
	 * @return Wert der Variable
	 */
	public boolean getNoDamage() {
		return cfg.getBoolean("player-immortal");
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
     * Holt die Liste der antigrief-Materials (Blumentöpfe, ..)
     * @return die Materials als Liste
     */
	public List<Material> getAntiGriefMaterials() {
	    return getListFromConfig("decoration-antigrief-materials");
    }

    /**
     * Holt die Liste der Checkpoint-Trigger (Holzknöpfe, Holzdruckplatten)
     * @return die Materials als Liste
     */
    public List<Material> getCheckpointTriggerMaterials() {
	    return getListFromConfig("checkpoint-triggers");
    }

    /**
     * Liest eine Liste aus der Konfigurationsdatei aus
     * @param cfgpath der config-path der Liste
     * @return die Liste mit Materials
     */
	private List<Material> getListFromConfig(String cfgpath) {

        List<?> templist = cfg.getList(cfgpath);
        List<Material> list = new ArrayList<>();
        for(Object o : templist) {
            list.add(Material.matchMaterial((String)o));
        }
        return list;
    }



}
