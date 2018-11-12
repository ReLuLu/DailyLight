package de.relulu.DailyLight.util;

import org.bukkit.command.CommandSender;

/**
 * Handles chat messages to spare some code elsewhere and make formatting of messages easier to configure
 *
 * @author ReLuLu
 *
 */
public class MessageHandler {

    private String prefix = ""; // redundant init
    private String primarycolor = "§e"; // redundant init
    private String secondaryformat = "§r"; // redundant init

    /**
     * Constructor for the MessageHandler object with everything it needs to know
     *
     * @param messageprefix the message prefix if any, can be an empty string
     * @param primarycolor the message primary color
     * @param secondaryformat the message secondary format
     */
    public MessageHandler(String messageprefix, String primarycolor, String secondaryformat) {
        this.prefix = setPrefix(messageprefix);
        this.primarycolor = setPrimaryColor(primarycolor);
        this.secondaryformat = setSecondaryFormat(secondaryformat);
    }

    /**
     * Alternate to sendMessage that deals with prefix and color
     * console usage
     *
     * @param cs sender, who receives the message
     * @param text text token
     */
    public void tell(CommandSender cs, String text) {
        cs.sendMessage(this.prefix + text);
    }

    /**
     * Checks the provided prefix variable for emptyness and replaces
     * the chat color characters with the bukkit ones if any.
     * @param prefix the raw message prefix from config
     * @return prefix with converted chat color characters
     */
    private String setPrefix(String prefix) {
        return !prefix.trim().isEmpty() ? (prefix.replace("&", "§") + " ") : prefix.trim();
    }

    /**
     * Checks the provided color variable for emptyness and replaces
     * the chat color characters with the bukkit ones if any.
     * @param color the raw color variable from config
     * @return color with converted chat color characters
     */
    private String setPrimaryColor(String color) {
        return !color.trim().isEmpty() ? color.replace("&", "§") : color.trim();
    }

    /**
     * Checks the provided format variable for emptyness and replaces
     * the chat color characters with the bukkit ones if any.
     * @param format the raw color variable from config
     * @return format with converted chat color characters
     */
    private String setSecondaryFormat(String format) {
        return !format.trim().isEmpty() ? format.replace("&", "§") : format.trim();
    }

    /**
     * Returns the message prefix
     * @return prefix message prefix
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * Returns the primary color
     * @return primarycolor primary chat color
     */
    public String getPrimaryColor() {
        return this.primarycolor;
    }

    /**
     * Returns the secondary format
     * @return secondaryformat secondary format
     */
    public String getSecondaryFormat() {
        return this.secondaryformat;
    }

}
