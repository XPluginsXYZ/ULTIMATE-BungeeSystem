package de.zappler.ultimatebungeesystem.bungeesystem.listener;

import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.api.FormatUtil;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.maintenance.MaintenanceModule;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.motd.MotdModule;
import de.zappler.ultimatebungeesystem.modules.config.ConfigModule;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class ProxyPingListener implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent proxyPingEvent) {
        ServerPing ping = proxyPingEvent.getResponse();
        ConfigModule configModule = (ConfigModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ConfigModule.class);
        MaintenanceModule maintenanceModule = (MaintenanceModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(MaintenanceModule.class);
        MotdModule motdModule = (MotdModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(MotdModule.class);
        if (configModule.isAllowmotd()) {
            if (maintenanceModule.isMaintenance()) {
                ping.setDescriptionComponent(new TextComponent(FormatUtil.format(maintenanceModule.getMaintenanceMotd())));
                ping.setVersion(new ServerPing.Protocol(FormatUtil.format(maintenanceModule.getMaintenanceVersionInfo()), 0));
                ping.getPlayers().setSample(transform(FormatUtil.format(maintenanceModule.getMaintenancePlayerInfo())));
            } else {
                ping.setDescriptionComponent(new TextComponent(FormatUtil.format(motdModule.getMotd())));
                ping.getPlayers().setSample(transform(FormatUtil.format(motdModule.getPlayerInfo())));
            }
            proxyPingEvent.setResponse(ping);
        }

    }

    private List<String> getLines(String string) {
        final List<String> lines = new ArrayList<>();
        if (!string.contains("\n")) {
            lines.add(string);
            return lines;
        }
        for (String s : string.split("\n")) lines.add(s);
        return lines;
    }

    private ServerPing.PlayerInfo[] transform(String info) {
        List<String> infoL = getLines(info);
        ServerPing.PlayerInfo[] array = new ServerPing.PlayerInfo[infoL.size()];
        int i = 0;
        for (String s : infoL) {
            array[i] = new ServerPing.PlayerInfo(s, s);
            i++;
        }
        return array;
    }
}