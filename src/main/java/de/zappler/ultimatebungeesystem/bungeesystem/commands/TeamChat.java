package de.zappler.ultimatebungeesystem.bungeesystem.commands;


import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.bungeesystem.commands.lib.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamChat extends Command {

    private String TCPrefix = "§cTC §7| §f";
    private Messages messages;

    public TeamChat(String name) {
        super(name);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        messages = new Messages(sender);
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if(proxiedPlayer.hasPermission("bungeesystem.teamchat.command")) {
                if(args.length == 0) {
                    messages.sendmessage(messages.getWrongusage() + "\n§8- §7/teamchat §8<§emessage§8>");
                } else {
                    if(ULTIMATEBungeeSystem.getInstance().getIsloggedin().contains(proxiedPlayer)) {
                        for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                            if(ULTIMATEBungeeSystem.getInstance().getIsloggedin().contains(players)) {
                                String message = null;
                                for(int i = 0; i<args.length; i++) {
                                    message = args[i];
                                }
                                players.sendMessage(new TextComponent(TCPrefix + "§e" + proxiedPlayer + "§8● §7" + message));
                            }
                        }
                    } else {
                        messages.sendmessage(ULTIMATEBungeeSystem.getInstance().getPrefix() + "§7Du must dich erst in das System einloggen.");
                    }
                }
            } else {
                messages.sendmessage(messages.getNoperms());
            }
        } else {
            messages.sendmessage(messages.getNoproxiedplayer());
        }
    }
}
