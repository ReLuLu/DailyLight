package de.relulu.DailyLight;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.relulu.DailyLight.util.ConfigManager;
import de.relulu.DailyLight.util.MessageHandler;
import org.bukkit.Location;

/**
 * Eine Klasse zur Verwaltung der Informationen und so.
 * 
 * @author ReLuLu
 *
 */
public class DailyManager {
	
	private DailyInit di;
	private ConfigManager dcoman;

	private MessageHandler mh;
	
	private Logger log;
	
	private HashMap<String, Location> playerchecks = new HashMap<String, Location>();
	private HashMap<String, Date> playertimes = new HashMap<String, Date>();
	
	public DailyManager(DailyInit di) {
		this.di = di;
		this.dcoman = new ConfigManager(di.getConfig());
		this.mh = new MessageHandler(
		        di.getConfig().getString("message-prefix", ""),
                di.getConfig().getString("primary-color", "§e"),
                di.getConfig().getString("secondary-color", "§r")
        );
	}
	
	/**
	 * Getter für's Log
	 * 
	 * @return log Logger des Plugins
	 */
	public Logger getLogger() {
		return this.log;
	}

    /**
     * Gibt den MessageHandler zurück, um schöne Textausgaben
     * über eine zentrale Klasse zu schicken
     *
     * @return mh MessageHandler
     */
	public MessageHandler getMessageHandler() {
	    return this.mh;
    }

	/**
	 * Gibt den ConfigManager zurück, um Zugriff
	 * auf Konfigurationsinhalte zu ermöglichen
	 * 
	 * @return dcoman ConfigManager
	 */
	public ConfigManager getConfigManager() {
		return this.dcoman;
	}
	
	/**
	 * Setzt einen Spieler Start-Zeitstempel Datensatz
	 * 
	 * @param pln Spielername als String
	 */
	public void setPlayerStartTime(String pln) {
		Date st = new Date();
		playertimes.put(pln, st);
	}
	
	/**
	 * Löscht die Spielerzeit
	 * 
	 * @param pln Spielername als String
	 * @return true wenn der Spielername mit dem Zeitmapping aus der internen Zeitmessungs-HashMap gelöscht werden konne, false wenn nicht
	 */
	public boolean removePlayerStartTime(String pln) {
		
		Date st;
		if(playertimes.containsKey(pln)) {
			st = playertimes.get(pln);
			return playertimes.remove(pln, st);
		}
		return false;
	}
	
	/**
	 * Gibt zurück, ob ein Spieler in einem Daily ist.
	 * Fragt dafür ab, ob ein Start-Zeitstempel exitistiert, wenn
	 * nicht dann ist ein Spieler auch aktuell nicht im Daily.
	 * 
	 * @param pln Spielername als String
	 * @return true wenn der Spielername in der internen Zeitmessungs-HashMap vorhanden ist, false wenn nicht
	 */
	public boolean isPlayerInDaily(String pln) {
		
		return playertimes.containsKey(pln);
	}
	
	/**
	 * Löscht den Spieler / Ort Datensatz
	 * 
	 * @param pln Spielername als String
	 * @return true wenn das Mapping von Spieler und Location erfolgreich gelöscht wurde, false wenn nicht
	 */
	public boolean removePlayerCheck(String pln) {
		
		if(playerchecks.containsKey(pln)) {
			Location loc = playerchecks.get(pln);
			return playerchecks.remove(pln, loc);
		}
		return false;
	}
	
	/**
	 * Setzt einen aktuellen Spieler / Location Datensatz
	 * 
	 * @param pln Spielername als String
	 * @param loc Ort als Location, wo der Spieler den Checkpoint ausgelöst hat
	 */
	public void setPlayerCheck(String pln, Location loc) {
		playerchecks.put(pln, loc);
	}
	
	/**
	 * Schaut nach, ob ein Spieler bereits einen Checkpoint ausgelöst hat
	 * 
	 * @param pln Spielername als String
	 * @return true wenn der Spielername in der Checkpoint-HashMap vorhanden ist, false wenn nicht
	 */
	public boolean hasPlayerCheck(String pln) {
		return playerchecks.containsKey(pln);
	}
	
	/**
	 * Gibt zu einem Spieler die Location raus
	 * 
	 * @param pln Spielername als String
	 * @return Location zum Spieler, wie sie in der Checkpoint-HashMap gemappt ist
	 */
	public Location getPlayerCheck(String pln) {
		return playerchecks.get(pln);
	}
	
	/**
	 * Gibt die Dauer von Start zu Ende als String zurück
	 * 
	 * @param pln Spielername als String
	 * @return formatierter String mit Angabe zur verstrichenen Zeit
	 */
	public String getPlayerDurationTime(String pln) {
		
		Date et = new Date();
		Date st;
		
		if(playertimes.containsKey(pln)) {
			st = playertimes.get(pln);
		} else {
			//st = new Date(); // neues, aktuelles Datum setzen
			return(mh.getPrimaryColor() + "Keine Startzeit!");
		}
		
		long duration = et.getTime() - st.getTime();
		
		// ---
		// Calculate the difference between two dates in hours:minutes:seconds?
		// https://stackoverflow.com/a/43893009
		
	    long secondsInMilli = 1000;
	    long minutesInMilli = secondsInMilli * 60;
	    long hoursInMilli = minutesInMilli * 60;
		
	    long elapsedHours = duration / hoursInMilli;
	    duration = duration % hoursInMilli;

	    long elapsedMinutes = duration / minutesInMilli;
	    duration = duration % minutesInMilli;

	    long elapsedSeconds = duration / secondsInMilli;
	    
	    return(mh.getSecondaryFormat() + elapsedHours + mh.getPrimaryColor() + " Stunden, " +
                mh.getSecondaryFormat() + elapsedMinutes + mh.getPrimaryColor() + " Minuten, " +
                mh.getSecondaryFormat() + elapsedSeconds + mh.getPrimaryColor() + " Sekunden");

	}
	
	/**
	 * Eine Debug-Methode, um Übersicht über aktuelle gespeicherte Checkpoints zu kriegen
	 * 
	 * @return playerchecklist die Checkpoint-HashMap (Spieler + Location Mappings) als Stringliste
	 */
	public List<String> getPlayerCheckList() {
		List<String> playerchecklist = new ArrayList<>();
		
		for (Map.Entry<String, Location> entry : playerchecks.entrySet()) {
		    String key = entry.getKey();
		    Location value = entry.getValue();
			String x = String.valueOf(value.getX());
			String y = String.valueOf(value.getY());
			String z = String.valueOf(value.getZ());
		    String xyz = mh.getSecondaryFormat() + x
                    + mh.getPrimaryColor() + " / "
                    + mh.getSecondaryFormat() + y
		    		+ mh.getPrimaryColor() + " / "
                    + mh.getSecondaryFormat() + z;
		    
		    playerchecklist.add(String.join(" ", "    " // Einzug damit es nicht so Standard im Chat ausschaut
                    + mh.getSecondaryFormat() + key
		    		+ mh.getPrimaryColor() + " @ " + xyz));
		}	
		return playerchecklist;
	}

}
