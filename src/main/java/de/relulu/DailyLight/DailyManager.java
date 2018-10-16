package de.relulu.DailyLight;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Location;

/**
 * Eine Klasse zur Verwaltung der Informationen und so.
 * 
 * @author ReLuLu
 *
 */
public class DailyManager {
	
	private DailyInit di;
	private DailyConfigManager dcoman;
	
	private Logger 				log;
	
	private HashMap<String, Location> playerchecks = new HashMap<String, Location>();
	private HashMap<String, Date> playertimes = new HashMap<String, Date>();
	
	public DailyManager(DailyInit di) {
		this.di = di;
		this.dcoman = new DailyConfigManager(di.getConfig());
	}
	
	/**
	 * Getter für's Log
	 * 
	 * @return
	 */
	public Logger getLogger() {
		return this.log;
	}
	
	/**
	 * Gibt den DailyConfigManager zurück, um Zugriff 
	 * auf Konfigurationsinhalte zu ermöglichen
	 * 
	 * @return
	 */
	public DailyConfigManager getConfigManager() {
		return dcoman;
	}
	
	/**
	 * Setzt einen Spieler Start-Zeitstempel Datensatz
	 * 
	 * @param pln
	 */
	public void setPlayerStartTime(String pln) {
		Date st = new Date();
		playertimes.put(pln, st);
	}
	
	/**
	 * Löscht die Spielerzeit
	 * 
	 * @param pln
	 * @return
	 */
	public boolean removePlayerStartTime(String pln) {
		
		Date st;
		if(playertimes.containsKey(pln)) {
			st = playertimes.get(pln);
			playertimes.remove(pln, st);
			return true;
		}
		return false;
	}
	
	/**
	 * Gibt zurück, ob ein Spieler in einem Daily ist.
	 * Fragt dafür ab, ob ein Start-Zeitstempel exitistiert, wenn
	 * nicht dann ist ein Spieler auch aktuell nicht im Daily.
	 * 
	 * @param pln
	 * @return
	 */
	public boolean isPlayerInDaily(String pln) {
		
		return playertimes.containsKey(pln);
	}
	
	/**
	 * Löscht den Spieler / Ort Datensatz
	 * 
	 * @param pln
	 * @return
	 */
	public boolean removePlayerCheck(String pln) {
		
		if(playerchecks.containsKey(pln)) {
			Location loc = playerchecks.get(pln);
			playerchecks.remove(pln, loc);
			return true;
		}
		return false;
	}
	
	/**
	 * Setzt einen aktuellen Spieler / Location Datensatz
	 * 
	 * @param pln
	 * @param loc
	 */
	public void setPlayerCheck(String pln, Location loc) {
		playerchecks.put(pln, loc);
	}
	
	/**
	 * Schaut nach, ob ein Spieler bereits einen Checkpoint ausgelöst hat
	 * 
	 * @param pln
	 * @return
	 */
	public boolean hasPlayerCheck(String pln) {
		if(playerchecks.containsKey(pln)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gibt zu einem Spieler die Location raus
	 * 
	 * @param pln
	 * @return
	 */
	public Location getPlayerCheck(String pln) {
		return playerchecks.get(pln);
	}
	
	/**
	 * Gibt die Dauer von Start zu Ende als String zurück
	 * 
	 * @param pln
	 * @return
	 */
	public String getPlayerDurationTime(String pln) {
		
		Date et = new Date();
		Date st;
		
		if(playertimes.containsKey(pln)) {
			st = playertimes.get(pln);
		} else {
			//st = new Date(); // neues, aktuelles Datum setzen
			return("0" + this.getConfigManager().getMessagePrimaryColor() + " Stunden, §r" 
				    + "0" + this.getConfigManager().getMessagePrimaryColor() + " Minuten, §r" 
				    + "0" + this.getConfigManager().getMessagePrimaryColor() + " Sekunden"); 
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
	    
	    return(elapsedHours + this.getConfigManager().getMessagePrimaryColor() + " Stunden, §r" 
	    + elapsedMinutes + this.getConfigManager().getMessagePrimaryColor() + " Minuten, §r" 
	    + elapsedSeconds + this.getConfigManager().getMessagePrimaryColor() + " Sekunden"); 

	}
	
	/**
	 * Eine Debug-Methode, um den aktuell gespeicherten Checkpoint zum Spieler ausgeben zu lassen
	 * 
	 * @param pln
	 * @return
	 */
	public String getPlayerCheckLocationInfo(String pln) {
		String playercheckinfo = "";
		
		if(playerchecks.containsKey(pln)) {
			Location loc = playerchecks.get(pln);
			String x = String.valueOf(loc.getX());
			String y = String.valueOf(loc.getY());
			String z = String.valueOf(loc.getZ());
		    String xyz = x + this.getConfigManager().getMessagePrimaryColor() 
		    		+ " / §r" + y 
		    		+ this.getConfigManager().getMessagePrimaryColor() 
		    		+ " / §r" + z + " §r";
		    
		    playercheckinfo = String.join(" ", this.getConfigManager().getMessagePrefix() 
		    		+ "    §r" + pln 
		    		+ this.getConfigManager().getMessagePrimaryColor() 
		    		+ " @ §r" + xyz);
		}
		
		return playercheckinfo;
	}
	
	/**
	 * Eine Debug-Methode, um Übersicht über aktuelle gespeicherte Checkpoints zu kriegen
	 * 
	 * @return
	 */
	public List<String> getPlayerCheckList() {
		List<String> playerchecklist = new ArrayList<>();
		
		for (Map.Entry<String, Location> entry : playerchecks.entrySet()) {
		    String key = entry.getKey();
		    Location value = entry.getValue();
			String x = String.valueOf(value.getX());
			String y = String.valueOf(value.getY());
			String z = String.valueOf(value.getZ());
		    String xyz = x + this.getConfigManager().getMessagePrimaryColor() 
		    		+ " / §r" + y 
		    		+ this.getConfigManager().getMessagePrimaryColor() 
		    		+ " / §r" + z + " §r";
		    
		    playerchecklist.add(String.join(" ", this.getConfigManager().getMessagePrefix() 
		    		+ "    §r" + key 
		    		+ this.getConfigManager().getMessagePrimaryColor() 
		    		+ " @ §r" + xyz));
		}	
		return playerchecklist;
	}

}
