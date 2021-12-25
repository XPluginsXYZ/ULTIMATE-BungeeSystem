package de.zappler.ultimatebungeesystem.bungeesystem.commands;


import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.bungeesystem.commands.lib.Messages;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.chat.ChatModule;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.maintenance.MaintenanceModule;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import java.util.ArrayList;


public class BungeeSystem extends Command implements TabExecutor {

    private String prefix = ULTIMATEBungeeSystem.getInstance().getPrefix();
    private Messages messages;

    public BungeeSystem(String name) {
        super(name);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        messages = new Messages(sender);
        MaintenanceModule maintenanceModule = (MaintenanceModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(MaintenanceModule.class);
        ChatModule chatModule = (ChatModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ChatModule.class);
        if(sender.hasPermission("bungeesystem.command")) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if(args.length == 0) {
                messages.sendmessage(sendhelp());
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    ULTIMATEBungeeSystem.getInstance().managerFromConfig();
                    messages.sendmessage(prefix +  "§7Die Configs wurden neugeladen.");
                } else if(args[0].equalsIgnoreCase("login")) {
                    if(!ULTIMATEBungeeSystem.getInstance().getIsloggedin().contains(proxiedPlayer)) {
                        ULTIMATEBungeeSystem.getInstance().getIsloggedin().add(proxiedPlayer);
                        messages.sendmessage(prefix + "§7Du wurdest in das System eingeloggt.");
                    } else {
                        ULTIMATEBungeeSystem.getInstance().getIsloggedin().remove(proxiedPlayer);
                        messages.sendmessage(prefix + "§7Du wurdest aus dem System ausgeloggt.");
                    }
                } else {
                    messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs reload §8(§erl§8)" +
                            "\n§8- §7/bs login");
                }
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("maintenance")) {
                    if(args[1].equalsIgnoreCase("true")) {
                        if(maintenanceModule.isMaintenance()) {
                            messages.sendmessage(prefix + "§7Das Netzwerk ist bereits in Wartungen.");
                            return;
                        }
                        maintenanceModule.setMaintenance(true);
                        for(ProxiedPlayer proxiedPlayers : ProxyServer.getInstance().getPlayers()) {
                            if(!proxiedPlayers.hasPermission("bungeesystem.maintenance.ignore")) {
                                proxiedPlayer.disconnect(new TextComponent(maintenanceModule.getMaintenancekickMessage()));
                            }
                        }
                        messages.sendmessage(prefix + "§7Das Netzwerk ist nun in Wartungen.");
                    } else if(args[1].equalsIgnoreCase("false")) {
                        if(!maintenanceModule.isMaintenance()) {
                            messages.sendmessage(prefix + "§7Das Netzwerk ist bereits nicht in Wartungen.");
                            return;
                        }
                        maintenanceModule.setMaintenance(true);
                        messages.sendmessage(prefix + "§7Das Netzwerk ist nun nicht mehr in Wartungen.");
                    } else {
                        messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs maintenance §8<§etrue | false>");
                    }
                } else if(args[0].equalsIgnoreCase("whitelist")) {
                    if(args[1].equalsIgnoreCase("list")) {
                        messages.sendmessage(prefix + "§7Hier ist die Whiteliste §8●");
                        StringBuilder builder = new StringBuilder();
                        int current = 0;
                        for(String players : maintenanceModule.getWhitelistedplayers()) {
                            builder.append("§7" + players + "§8" +(current >= maintenanceModule.getWhitelistedplayers().size() ? "!" : ", "));
                            current++;
                        }
                        messages.sendmessage(builder.toString());
                    } else {
                        messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs whitelist §8<§elist | add | remove§8> (§eSpieler§8)");
                    }
                } else if(args[0].equalsIgnoreCase("blockedwords")) {
                    if(args[1].equalsIgnoreCase("list")) {
                        messages.sendmessage(prefix + "§7Hier ist die BlockedWords Liste §8●");
                        StringBuilder builder = new StringBuilder();
                        int current = 0;
                        for(String players : maintenanceModule.getWhitelistedplayers()) {
                            builder.append("§7" + players + "§8" +(current >= maintenanceModule.getWhitelistedplayers().size() ? "!" : ", "));
                            current++;
                        }
                        messages.sendmessage(builder.toString());
                    } else {
                        messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs blockedwords §8<§elist | add | remove§8> (§eWort§8)");
                    }
                } else {
                    messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs blockedwords §8<§elist | add | remove§8> (§eWort§8)"
                                                                  + "\n§8- §7/bs whitelist §8<§elist | add | remove§8> (§eSpieler§8)");
                }
            } else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("whitelist")) {
                        if(args[1].equalsIgnoreCase("add")) {
                            if(!maintenanceModule.getWhitelistedplayers().contains(args[2])) {
                                maintenanceModule.addPlayerToWhiteList(args[2]);
                                messages.sendmessage(prefix + "§7Du hast den Spieler §a" + args[2] + "§7 hinzugefügt.");
                            } else {
                                messages.sendmessage(prefix + "§7Der Spieler §a" + args[2] + "§7 ist schon auf der Liste.");
                            }
                        } else if(args[1].equalsIgnoreCase("remove")) {
                            if(maintenanceModule.getWhitelistedplayers().contains(args[2])) {
                                maintenanceModule.removePlayerToWhiteList(args[2]);
                                messages.sendmessage(prefix + "§7Du hast den Spieler §a" + args[2] + "§7 entfernt.");
                            } else {
                                messages.sendmessage(prefix + "§7Der Spieler §a" + args[2] + "§7 ist nicht auf der Liste.");
                            }
                        } else {
                            messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs whitelist §8<§eadd | remove§8> (§eSpieler§8)");
                        }
                    } else if(args[0].equalsIgnoreCase("blockedwords")) {
                        if(args[1].equalsIgnoreCase("add")) {
                            if(!chatModule.getBlockedWords().contains(args[2])) {
                                chatModule.addWordToBlockedWordsList(args[2]);
                                messages.sendmessage(prefix + "§7Du das Wort §a" + args[2] + "§7 hinzugefügt.");
                            } else {
                                messages.sendmessage(prefix + "§7Das Wort §a" + args[2] + "§7 ist schon auf der Liste.");
                            }
                        } else if(args[1].equalsIgnoreCase("remove")) {
                            if(chatModule.getBlockedWords().contains(args[2])) {
                                chatModule.removeWordToBlockedWordsList(args[2]);
                                messages.sendmessage(prefix + "§7Du das Wort §a" + args[2] + "§7 entfernt.");
                            } else {
                                messages.sendmessage(prefix + "§7Das Wort §a" + args[2] + "§7 ist nicht auf der Liste.");
                            }
                        } else {
                            messages.sendmessage(messages.getWrongusage() + "\n§8- §7/bs blockedwords §8<§eadd | remove§8> (§eWort§8)");
                        }
                    } else {
                        messages.sendmessage(messages.getWrongusage()
                                                                     + "\n§8- §7/bs whitelist §8<§eadd | remove§8> (§eSpieler§8)"
                                                                     + "\n§8- §7/bs blockedwords §8<§eadd | remove§8> (§eWort§8)");
                    }
            } else {
                messages.sendmessage(sendhelp());
            }
        } else {
            messages.sendmessage(messages.getNoperms());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        ArrayList nothing = new ArrayList();
        MaintenanceModule maintenanceModule = (MaintenanceModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(MaintenanceModule.class);
        ChatModule chatModule = (ChatModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ChatModule.class);
        if(sender.hasPermission("bungeesystem.command")) {
            if(args.length == 1) {
                commands.add("reload");
                commands.add("login");
                commands.add("maintenance");
                commands.add("whitelist");
                commands.add("blockedwords");
                return commands;
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("maintenance")) {
                    commands.add("true");
                    commands.add("false");
                    return commands;
                } else if(args[0].equalsIgnoreCase("whitelist")) {
                    commands.add("list");
                    commands.add("add");
                    commands.add("remove");
                    return commands;
                } else if(args[0].equalsIgnoreCase("blockedwords")) {
                    commands.add("list");
                    commands.add("add");
                    commands.add("remove");
                    return commands;
                }
            } else if(args.length == 3) {
                if(args[0].equalsIgnoreCase("whitelist")) {
                    if(args[1].equalsIgnoreCase("add")) {
                        for(ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                            commands.add(players.getName());
                        }
                    } else if(args[1].equalsIgnoreCase("remove"))  {
                        for(String whitelistedplayers : maintenanceModule.getWhitelistedplayers()) {
                            commands.add(whitelistedplayers);
                        }
                    }
                    return commands;
                } else if(args[0].equalsIgnoreCase("blockedwords")) {
                    if(args[1].equalsIgnoreCase("add")) {
                            commands.add("wort");
                    } else if(args[1].equalsIgnoreCase("remove"))  {
                        for(String words : chatModule.getBlockedWords()) {
                            commands.add(words);
                        }
                    }
                    return commands;
                }
            } else {
                return nothing;
            }
        }
        return null;
    }

    private String sendhelp() {
        return messages.getWrongusage() + "\n§8- §7/bs reload §8(§erl§8)"
                                        + "\n§8- §7/bs login"
                                        + "\n§8- §7/bs maintenance §8<§etrue | false§8>"
                                        + "\n§8- §7/bs whitelist §8<§elist | add | remove§8> (§eSpieler§8)"
                                        + "\n§8- §7/bs blockedwords §8<§elist | add | remove§8> (§eSpieler§8)";
    }
}

