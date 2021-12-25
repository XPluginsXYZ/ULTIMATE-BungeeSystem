package de.zappler.ultimatebungeesystem.bungeesystem.commands.lib;

import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class Messages {

    private CommandSender commandSender;

    private String noperms = ULTIMATEBungeeSystem.getInstance().getPrefix() + "§7Du hast keine Rechte um auf den Kommand zu zugreifen.";
    private String noproxiedplayer = ULTIMATEBungeeSystem.getInstance().getPrefix() + "§7Diesen Kommand kannst du nicht mit der Console machen.";
    private String wrongusage  = ULTIMATEBungeeSystem.getInstance().getPrefix() + "§7Du hast den Kommand falsch benutzt. Möglichkeiten §8●";
    public Messages(CommandSender sender) {
        this.commandSender = sender;
    }

    public  String getNoperms() {
        return noperms;
    }

    public  String getNoproxiedplayer() {
        return noproxiedplayer;
    }

    public String getWrongusage() {
        return wrongusage;
    }

    public void sendmessage(String message) {
        commandSender.sendMessage(new TextComponent(message));
    }
}
