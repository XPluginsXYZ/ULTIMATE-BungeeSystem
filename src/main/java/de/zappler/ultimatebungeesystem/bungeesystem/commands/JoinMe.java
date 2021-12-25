package de.zappler.ultimatebungeesystem.bungeesystem.commands;


import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.api.FormatUtil;
import de.zappler.ultimatebungeesystem.bungeesystem.commands.lib.Messages;
import de.zappler.ultimatebungeesystem.modules.config.ConfigModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class JoinMe extends Command implements Listener {

    private List<JoinMeData> joinMeDataList;
    private HashMap<ProxiedPlayer, Long> lastJoinMe;
    private String JoinMePrefix = "§aJoinMe §7| ";
    private Messages messages;

    public JoinMe(String name) {
        super(name);
        this.joinMeDataList = new CopyOnWriteArrayList<>();
        this.lastJoinMe = new HashMap<>();
        ProxyServer.getInstance().getPluginManager().registerListener(ULTIMATEBungeeSystem.getInstance(), this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        messages = new Messages(sender);
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
            if (args.length == 2 && args[0].equals("join")) {
                AtomicBoolean atomicBoolean = new AtomicBoolean();
                joinMeDataList.forEach(joinMeData -> {
                    if (joinMeData.id == Integer.parseInt(args[1])) {
                        proxiedPlayer.connect(joinMeData.serverInfo);
                        proxiedPlayer.sendMessage(new TextComponent(JoinMePrefix + "§7Du wurdest zum Server geschickt."));
                        atomicBoolean.set(true);
                    }
                });
                if (!atomicBoolean.get()) {
                    proxiedPlayer.sendMessage(new TextComponent(JoinMePrefix + "§cDieses JoinMe ist bereits abgelaufen."));
                }
                return;
            } else if (args.length != 0) {
                proxiedPlayer.sendMessage(new TextComponent(messages.getWrongusage() + "\n§8- §7/joinme"));
                return;
            }

            if (sender.hasPermission("bungeesystem.joinme.command")) {
                if (lastJoinMe.containsKey(proxiedPlayer)) {
                    if (System.currentTimeMillis() - lastJoinMe.get(proxiedPlayer) > 1000 * 60 * 5) {
                        executeJoinMe(proxiedPlayer, aBoolean -> {
                            if (!aBoolean) {
                                proxiedPlayer.sendMessage(new TextComponent(JoinMePrefix + "§cEs ist ein Fehler im laufe des JoinMe Prozesses aufgetreten. Dein JoinMe wurde nun gesperrt."));
                                lastJoinMe.remove(proxiedPlayer);
                            }
                        });
                        lastJoinMe.replace(proxiedPlayer, System.currentTimeMillis());
                    } else {
                        proxiedPlayer.sendMessage(new TextComponent(JoinMePrefix + "§cDu musst §e5 Minuten §cwarten bis du ein neues JoinMe erstellen kannst."));
                    }
                } else {
                    executeJoinMe(proxiedPlayer, aBoolean -> {
                        if (!aBoolean) {
                            proxiedPlayer.sendMessage(new TextComponent(JoinMePrefix + "§cEs ist ein Fehler im laufe des JoinMe Prozesses aufgetreten. Dein JoinMe wurde nun gesperrt."));
                            lastJoinMe.remove(proxiedPlayer);
                        }
                    });
                    lastJoinMe.put(proxiedPlayer, System.currentTimeMillis());
                }
            } else {
                ConfigModule configModule = (ConfigModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ConfigModule.class);
                sender.sendMessage(new TextComponent(FormatUtil.format(configModule.getNopermissonsmessage())));
            }
        }
    }

    public void executeJoinMe(ProxiedPlayer player, Consumer<Boolean> isSent) {
        JoinMeData data = new JoinMeData(new Random().nextInt(), player.getServer().getInfo());
        ProxyServer.getInstance().getPlayers().forEach(players -> {
            TextComponent component = new TextComponent("§8» §eKLICKE, §7um zu ihm zu springen");
            if(players.getServer().getInfo().getName() != player.getServer().getInfo().getName()) {
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinme join " + data.id));
            } else {

            }
            players.sendMessage(new TextComponent(""));
            players.sendMessage(new TextComponent("§8-----------| §aJoinMe§8 |-----------"));
            players.sendMessage(new TextComponent(""));
            players.sendMessage(new TextComponent("§8» §6" + player.getName() + "§7 spielt auf §e" + player.getServer().getInfo().getName()));
            players.sendMessage(component);
            players.sendMessage(new TextComponent(""));
            players.sendMessage(new TextComponent("§8-----------| §aJoinMe§8 |-----------"));
            players.sendMessage(new TextComponent(""));
        });
        joinMeDataList.add(data);
        isSent.accept(true);
        CompletableFuture.runAsync(() -> {
            try {
                wait(1000 * 60);
                joinMeDataList.remove(data);
            } catch (InterruptedException e) {
                joinMeDataList.remove(data);
                isSent.accept(false);
            }
        });
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class JoinMeData {
        int id;
        ServerInfo serverInfo;
    }
}
