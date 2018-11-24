package de.relulu.DailyLight.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.DailyLight.util.ConfigManager;
import de.relulu.DailyLight.DailyManager;
import de.relulu.DailyLight.util.MessageHandler;

/**
 * Diese Klasse handhabt die Befehle hinter /daily
 *
 * @author ReLuLu
 *
 */
public class DailyAdmin implements CommandExecutor {

        private DailyManager dman;
        private MessageHandler mh;
        private ConfigManager dconf;

        public DailyAdmin(DailyManager dman) {
            this.dman = dman;
            this.mh = dman.getMessageHandler();
            this.dconf = dman.getConfigManager();
        }

        /**
         * Handelt den daily Befehl sowie Unterbefehle ab
         */
        @Override
        public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {

            if(sender instanceof Player) {

                Player p = (Player)sender;

                if(p.isOp()) {

                    // wenn der Befehl ohne Parameter (=Unterbefehl) daherkommt
                    if(comparams.length < 1) {
                        // dann gib die Hilfe aus
                        helpMessage(p);
                    }

                    // wenn der Befehl mit mind. 1 Parameter (=Unterbefehl + Parameter) daherkommt
                    else {

                        // Unterbefehl auslesen und zwischenspeichern
                        String subcommand = comparams[0];

                        // den Unterbefehl switchen
                        switch (subcommand) {

                            case "start":

                                if(comparams.length == 1) {
                                    p.performCommand("/dstart");
                                } else if(comparams.length == 2) {
                                    p.performCommand("/dstart " + comparams[1]);
                                }

                                break;

                            case "end":

                                if(comparams.length == 1) {
                                    p.performCommand("/dend");
                                } else if(comparams.length == 2) {
                                    p.performCommand("/dend " + comparams[1]);
                                }

                                break;

                            case "nodamage":

                                // wenn der Befehl ohne Parameter zum Unterbefehl daherkommt
                                if (comparams.length == 1) {
                                    mh.tell(p, mh.getPrimaryColor() + "Spieler sind gegen Schäden immun: "
                                            + mh.getSecondaryFormat() + dconf.getNoDamage());
                                }

                                // wenn der Befehl genau 2 Parameter hat (=Unterbefehl + Wert)
                                else if (comparams.length == 2) {
                                    dconf.setNoDamage(Boolean.valueOf(comparams[1]));
                                    mh.tell(p, mh.getPrimaryColor() + "Spieler sind gegen Schäden immun: "
                                            + mh.getSecondaryFormat() + dconf.getNoDamage());
                                }

                                break;

                            case "nohunger":

                                // wenn der Befehl ohne Parameter zum Unterbefehl daherkommt
                                if (comparams.length == 1) {
                                    mh.tell(p, mh.getPrimaryColor() + "Spieler bekommen keinen Hunger: "
                                            + mh.getSecondaryFormat() + dconf.getNoHunger());
                                }

                                // wenn der Befehl genau 2 Parameter hat (=Unterbefehl + Wert)
                                else if (comparams.length == 2) {
                                    dconf.setNoHunger(Boolean.valueOf(comparams[1]));
                                    mh.tell(p, mh.getPrimaryColor() + "Spieler bekommen keinen Hunger: "
                                            + mh.getSecondaryFormat() + dconf.getNoHunger());
                                }

                                break;

                            case "antigrief":

                                // wenn der Befehl ohne Parameter zum Unterbefehl daherkommt
                                if (comparams.length == 1) {
                                    mh.tell(p, mh.getPrimaryColor() + "Antigrief: "
                                            + mh.getSecondaryFormat() + dconf.getAntiGrief());
                                }

                                // wenn der Befehl genau 2 Parameter hat (=Unterbefehl + Wert)
                                else if (comparams.length == 2) {
                                    dconf.setAntiGrief(Boolean.valueOf(comparams[1]));
                                    mh.tell(p, mh.getPrimaryColor() + "Antigrief: "
                                            + mh.getSecondaryFormat() + dconf.getAntiGrief());
                                }

                                break;

                            // ausgeben, welche Buttons als Checkpoint erkannt werden
                            case "checkbuttons":

                                mh.tell(p, mh.getPrimaryColor() + "Buttons:");
                                for(Material m : dconf.getCheckpointTriggerButtons()) {
                                    mh.tell(p, mh.getSecondaryFormat() + m);
                                }
                                break;

                            // ausgeben, welche Druckplatten als Checkpoint erkannt werden
                            case "checkplates":

                                mh.tell(p, mh.getPrimaryColor() + "Druckplatten:");
                                for(Material m : dconf.getCheckpointTriggerPlates()) {
                                    mh.tell(p, mh.getSecondaryFormat() + m);
                                }
                                break;

                            // ausgeben, welche Objekte, Blöcke, Entities durch Antigrief geschützt sind
                            case "antigriefobjects":

                                mh.tell(p, mh.getPrimaryColor() + "Antigrief:");
                                for(Material m : dconf.getAntiGriefMaterials()) {
                                    mh.tell(p, mh.getSecondaryFormat() + m);
                                }
                                break;

                            // wenn kein Unterbefehl zutrifft, dann gib die Befehlsliste aus
                            default:
                                helpMessage(p);
                                break;

                        }

                    }


                }
            }

            return true;
        }

    /**
     * Gibt eine Auflistung administrativer Befehle des Plugins aus.
     * @param cs der, der den Befehl ausgeführt hat
     */
    private void helpMessage(CommandSender cs) {
        mh.tell(cs, mh.getPrimaryColor() + "Administrative Daily-Befehle");
        mh.tell(cs, mh.getPrimaryColor() + "/daily start <Spielername>");
        mh.tell(cs, mh.getPrimaryColor() + "/daily end <Spielername>");
        mh.tell(cs, mh.getPrimaryColor() + "/daily nodamage <true|false>");
        mh.tell(cs, mh.getPrimaryColor() + "/daily nohunger <true|false>");
        mh.tell(cs, mh.getPrimaryColor() + "/daily antigrief <true|false>");
        mh.tell(cs, mh.getPrimaryColor() + "/daily checkbuttons");
        mh.tell(cs, mh.getPrimaryColor() + "/daily checkplates");
        mh.tell(cs, mh.getPrimaryColor() + "/daily antigriefobjects");
    }

}
