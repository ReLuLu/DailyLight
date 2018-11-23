package de.relulu.DailyLight;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.relulu.DailyLight.commands.DailyConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteStreams;

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
	
	public FileConfiguration 		cfg = getConfig();
	private PluginDescriptionFile 	pdf = getDescription(); //damit nicht immer via getDescription was abgerufen wird
	
	/**
	 * Abstrakte Methode von JavaPlugin, wird beim Einschalten des Plugins ausgeführt.
	 */
	@Override
	public void onEnable() {
	    createConfig();
		if(cfg == null) {
	    	cfg = getConfig();
	    }

		DailyManager dman = new DailyManager(this);
		
        getServer().getPluginManager().registerEvents(new DailyListener(dman), this);
        this.getCommand("daily").setExecutor(new DailyConfig(dman));
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
		getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " disabled! :C");
	}
	
	/**
	 * Soll Zugriff auf die Konfiguration gewähren.
	 */
	public FileConfiguration passConfig() {
		return this.cfg;
	}
	
    /**
     * Erstellt die Standardkonfig config.yml im Plugin-Verzeichnis 
     * sofern diese noch nicht existiert. Nutzt dafür die integrierte Vorlage.
     */
    private void createConfig() {
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
    
    /**
     * Loads a resource with file name from JAR if it doesn't exist in your plugin's data folder.
     * https://www.spigotmc.org/threads/how-do-i-add-comments-to-my-config-file.38849/#post-446819
     * 
     * For YAML files, this preserves comments, as it copies the raw file. You can load it as a 
     * FileConfiguration with YamlConfiguration.loadConfiguration(). Basically, just replace 
     * saveDefaultConfig() with this, and override getConfig() to return this instead. 
     * Instead of using methods to create your default config, do it by hand.
     * 
     * @param plugin
     * @param resource
     * @return
     */
    public static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }
    
}
