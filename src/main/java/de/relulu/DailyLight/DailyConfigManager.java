package de.relulu.DailyLight;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Diese Klasse k�mmert sich um das Auslesen und Bereitstellen von Konfigurationsparametern
 * 
 * @author ReLuLu
 *
 */
public class DailyConfigManager {
	
	private FileConfiguration cfg;
	
	private String messageprefix = ""; // redundante Initialisierung
	private String primarycolor = "�e"; // redundante Initialisierung
	
	/**
	 * Konstruktor f�r f�r den Konfigurationsmanager
	 * 
	 * @param fcfg
	 */
	public DailyConfigManager(FileConfiguration fcfg) {
		this.cfg = fcfg;
		initMessageCustomization();
	}
	
	/**
	 * Liest Parameter, die Chatnachrichten betreffen, aus der config aus
	 */
	private void initMessageCustomization() {
		this.messageprefix = cfg.getString("message-prefix", ""); // leerer String als default
		this.messageprefix = messageprefix.replace("&", "�"); // wandelt den MC Farbcode in Spigot Farbcode um (& zu �)
		if(!(this.messageprefix.equals(""))) {
			this.messageprefix = messageprefix + " "; // damit zw. Prefix und normalem Text eine L�cke ist
		}
		this.primarycolor = cfg.getString("primary-color", "�e"); // �e als default
		this.primarycolor = primarycolor.replace("&", "�"); // wandelt den MC Farbcode in Spigot Farbcode um (& zu �)
	}
	
	/**
	 * Gibt den Prefix f�r Chatausgaben des Plugins zur�ck
	 * 
	 * @return
	 */
	public String getMessagePrefix() {
		return this.messageprefix;
	}
	
	/**
	 * Gibt die Akzentfarbe f�r Chatausgaben des Plugins zur�ck
	 * 
	 * @return
	 */
	public String getMessagePrimaryColor() {
		return this.primarycolor;
	}
	
	
}
