package de.relulu.DailyLight.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import de.relulu.DailyLight.DailyManager;
import de.relulu.DailyLight.util.ConfigManager;
import de.relulu.DailyLight.util.MessageHandler;
import org.bukkit.util.StringUtil;

/**
 * Diese Klasse handhabt die Befehle hinter /daily
 *
 * @author ReLuLu
 *
 */
public class DailyAdmin implements CommandExecutor, TabCompleter {

    private MessageHandler mh;
    private ConfigManager confman;
    private PluginDescriptionFile pdf;

    private final List<String> subcommands = new ArrayList<>(Arrays.asList(
            "start",
            "check",
            "end",
            "nodamage",
            "nohunger",
            "antigrief",
            "checkbuttons",
            "checkplates",
            "antigriefobjects",
            "version"
    ));

    public DailyAdmin(DailyManager dman, PluginDescriptionFile pdf) {
        this.mh = dman.getMessageHandler();
        this.confman = dman.getConfigManager();
        this.pdf = pdf;
    }

    /**
     * Handelt den /daily-Befehl sowie Unterbefehle ab
     * @param sender wer hat den Befehl abgesetzt
     * @param command Befehl als Objekt
     * @param comname Name des Befehls (/daily)
     * @param comparams zugehörige Parameter (/daily nodamage status)
     * @return true
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
                                p.performCommand("dstart");
                            } else if(comparams.length == 2) {
                                p.performCommand("dstart " + comparams[1]);
                            }

                            break;

                        case "check":

                            if(comparams.length == 1) {
                                p.performCommand("dcheck");
                            } else if(comparams.length == 2) {
                                p.performCommand("dcheck " + comparams[1]);
                            }

                            break;

                        case "end":

                            if(comparams.length == 1) {
                                p.performCommand("dend");
                            } else if(comparams.length == 2) {
                                p.performCommand("dend " + comparams[1]);
                            }

                            break;

                        case "nodamage":

                            // wenn der Befehl ohne Parameter zum Unterbefehl daherkommt
                            if (comparams.length == 1) {
                                mh.tell(p, mh.getPrimaryColor() + "Spieler sind gegen Schäden immun: "
                                        + mh.getSecondaryFormat() + confman.getNoDamage());
                            }

                            // wenn der Befehl genau 2 Parameter hat (=Unterbefehl + Wert)
                            else if (comparams.length == 2) {
                                // nur wenn es nicht status ist, weil status keine Änderung machen soll
                                if(!comparams[1].equalsIgnoreCase("status")) {
                                    confman.setNoDamage(Boolean.valueOf(comparams[1]));
                                }
                                mh.tell(p, mh.getPrimaryColor() + "Spieler sind gegen Schäden immun: "
                                        + mh.getSecondaryFormat() + confman.getNoDamage());
                                mh.tell(p, mh.getPrimaryColor() + "Änderungen erst bei erneutem Parkours-Start wirksam.");
                            }

                            break;

                        case "nohunger":

                            // wenn der Befehl ohne Parameter zum Unterbefehl daherkommt
                            if (comparams.length == 1) {
                                mh.tell(p, mh.getPrimaryColor() + "Spieler bekommen keinen Hunger: "
                                        + mh.getSecondaryFormat() + confman.getNoHunger());
                            }

                            // wenn der Befehl genau 2 Parameter hat (=Unterbefehl + Wert)
                            else if (comparams.length == 2) {
                                // nur wenn es nicht status ist, weil status keine Änderung machen soll
                                if(!comparams[1].equalsIgnoreCase("status")) {
                                    confman.setNoHunger(Boolean.valueOf(comparams[1]));
                                }
                                mh.tell(p, mh.getPrimaryColor() + "Spieler bekommen keinen Hunger: "
                                        + mh.getSecondaryFormat() + confman.getNoHunger());
                            }

                            break;

                        case "antigrief":

                            // wenn der Befehl ohne Parameter zum Unterbefehl daherkommt
                            if (comparams.length == 1) {
                                mh.tell(p, mh.getPrimaryColor() + "Antigrief: "
                                        + mh.getSecondaryFormat() + confman.getAntiGrief());
                            }

                            // wenn der Befehl genau 2 Parameter hat (=Unterbefehl + Wert)
                            else if (comparams.length == 2) {
                                // nur wenn es nicht status ist, weil status keine Änderung machen soll
                                if(!comparams[1].equalsIgnoreCase("status")) {
                                    confman.setAntiGrief(Boolean.valueOf(comparams[1]));
                                }
                                mh.tell(p, mh.getPrimaryColor() + "Antigrief: "
                                        + mh.getSecondaryFormat() + confman.getAntiGrief());
                            }

                            break;

                        // ausgeben, welche Buttons als Checkpoint erkannt werden
                        case "checkbuttons":

                            mh.tell(p, mh.getPrimaryColor() + "Buttons:");
                            for(Material m : confman.getCheckpointTriggerButtons()) {
                                mh.tell(p, mh.getSecondaryFormat() + m);
                            }
                            break;

                        // ausgeben, welche Druckplatten als Checkpoint erkannt werden
                        case "checkplates":

                            mh.tell(p, mh.getPrimaryColor() + "Druckplatten:");
                            for(Material m : confman.getCheckpointTriggerPlates()) {
                                mh.tell(p, mh.getSecondaryFormat() + m);
                            }
                            break;

                        // ausgeben, welche Objekte, Blöcke, Entities durch Antigrief geschützt sind
                        case "antigriefobjects":

                            mh.tell(p, mh.getPrimaryColor() + "Antigrief:");
                            for(Material m : confman.getAntiGriefMaterials()) {
                                mh.tell(p, mh.getSecondaryFormat() + m);
                            }
                            break;

                        case "version":

                            StringBuilder authors = new StringBuilder();
                            for(String s : pdf.getAuthors()) {
                                authors.append(s);
                                authors.append("  ");
                            }

                            mh.tell(p, "DailyLight Version "
                                    + mh.getSecondaryFormat() + pdf.getVersion()
                                    + mh.getPrimaryColor() + " von "
                                    + mh.getSecondaryFormat() + authors.toString());
                            mh.tell(p,  mh.getPrimaryColor() + "DailyLight auf GitHub: "
                                    + mh.getSecondaryFormat() + "https://github.com/ReLuLu/DailyLight");
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
     * Handhabt die Tab-Vervollständigung des daily-Befehls, um mögliche Unterbefehle sowie Parameter vorschlagen zu lassen
     * @param sender wer hat den Befehl abgesetzt
     * @param command Befehl als Objekt
     * @param comname Name des Befehls (/daily)
     * @param comparams zugehörige Parameter (/daily nodamage status)
     * @return tabsuggestions Liste mit möglichen Befehls-/Parametervorschlägen
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String comname, String[] comparams) {

        List<String> tabsuggestions = new ArrayList<>();

        // /daily ohne irgendwas, alle Möglichkeiten offen
        if(comparams.length == 0) {
            return tabsuggestions;
        }

        // /daily mit mindestens angefangenem Parameter
        else if(comparams.length == 1) {
            StringUtil.copyPartialMatches(comparams[0], subcommands, tabsuggestions);
            Collections.sort(tabsuggestions);
        }

        // /daily xy az mit mindestens angefangenem zweitem Parameter
        else if(comparams.length == 2) {

            // unterscheiden, welcher subcommand es nun ist

            // zunächst start, check und end, da diese die gleiche Vervollständigung geben (online-players)
            if(comparams[0].equalsIgnoreCase("start")
                    || comparams[0].equalsIgnoreCase("check")
                    || comparams[0].equalsIgnoreCase("end")) {

                List<String> players = new ArrayList<>();
                Bukkit.getOnlinePlayers().forEach(p -> players.add(p.getName()));
                StringUtil.copyPartialMatches(comparams[1], players, tabsuggestions);

            }

            // dann alle mit booleschen Parametern (nohunger, nodamage, antigrief)
            else if(comparams[0].equalsIgnoreCase("nohunger")
                    || comparams[0].equalsIgnoreCase("nodamage")
                    || comparams[0].equalsIgnoreCase("antigrief")) {

                List<String> getset = new ArrayList<>(Arrays.asList("status", "true", "false"));
                StringUtil.copyPartialMatches(comparams[1], getset, tabsuggestions);

            }

        }

        return tabsuggestions;
    }

    /**
     * Gibt eine Auflistung administrativer Befehle des Plugins aus.
     * @param cs der, der den Befehl ausgeführt hat
     */
    private void helpMessage(CommandSender cs) {
        mh.tell(cs, "Administrative Daily-Befehle");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "start " + "<" + mh.getSecondaryFormat() + "Spielername" + mh.getPrimaryColor() + ">");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "check " + "<" + mh.getSecondaryFormat() + "Spielername" + mh.getPrimaryColor() + ">");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "end <" + mh.getSecondaryFormat() + "Spielername" + mh.getPrimaryColor() + ">");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "nodamage <" + mh.getSecondaryFormat() + "true" + mh.getPrimaryColor() + "|" + mh.getSecondaryFormat() + "false" + mh.getPrimaryColor() + ">");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "nohunger <" + mh.getSecondaryFormat() + "true" + mh.getPrimaryColor() + "|" + mh.getSecondaryFormat() + "false" + mh.getPrimaryColor() + ">");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "antigrief <" + mh.getSecondaryFormat() + "true" + mh.getPrimaryColor() + "|" + mh.getSecondaryFormat() + "false" + mh.getPrimaryColor() + ">");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "checkbuttons");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "checkplates");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "antigriefobjects");
        mh.tell(cs, mh.getPrimaryColor() + "/" + mh.getSecondaryFormat() + "daily " + mh.getPrimaryColor() + "version");
    }

}
