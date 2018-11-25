package de.relulu.DailyLight;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import de.relulu.DailyLight.commands.DailyAdmin;
import de.relulu.DailyLight.util.ConfigLists;
import de.relulu.DailyLight.util.ConfigManager;
import de.relulu.DailyLight.commands.DailyCheck;
import de.relulu.DailyLight.commands.DailyEnd;
import de.relulu.DailyLight.commands.DailyStart;

/**
 * Hauptklasse für das Plugin.
 * 
 * @author ReLuLu
 *
 */
public class DailyInit extends JavaPlugin {
	
	private FileConfiguration 		cfg;
	private PluginDescriptionFile 	pdf = getDescription(); //damit nicht immer via getDescription was abgerufen wird
	
	/**
	 * Abstrakte Methode von JavaPlugin, wird beim Einschalten des Plugins ausgeführt.
	 */
	@Override
	public void onEnable() {
	    createDefaultConfig();
		if(cfg == null) {
	    	cfg = getConfig();
	    }

	    // erst die Konfigurationsklassen schrittweise erzeugen
        ConfigManager confman = new ConfigManager(this, this.getConfig(), new ConfigLists(this));
		// dann den DailyManager erzeugen
		DailyManager dman = new DailyManager(this, confman);
		
        getServer().getPluginManager().registerEvents(new DailyListener(dman), this);
        this.getCommand("daily").setExecutor(new DailyAdmin(dman));
        this.getCommand("dcheck").setExecutor(new DailyCheck(dman));
        this.getCommand("dstart").setExecutor(new DailyStart(dman));
        this.getCommand("dend").setExecutor(new DailyEnd(dman));
    	getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " by " + pdf.getAuthors().get(0) + " enabled! :)");
	}
	
    /**
     * Abstrakte Methode von JavaPlugin, wird ausgeführt, wenn das Plugin deaktiviert wird.
     */
	@Override
	public void onDisable() {
        //getLogger().info("Saving configuration file...");
        //this.saveConfig();
		getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " disabled! :C");
	}

    /**
     * Erstellt die Standardkonfig config.yml im Plugin-Verzeichnis 
     * sofern diese noch nicht existiert. Nutzt dafür die integrierte Vorlage.
     */
    private void createDefaultConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("config.yml not found :( Creating one with default values...");
                saveDefaultConfig();
            } else {
                getLogger().info("config.yml found :) Loading...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
