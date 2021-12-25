package de.zappler.ultimatebungeesystem.bungeesystem.listener;

import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.api.FormatUtil;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.tablist.TablistModule;
import de.zappler.ultimatebungeesystem.modules.config.ConfigModule;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent serverSwitchEvent) {
        TablistModule tabModule = (TablistModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(TablistModule.class);
        ConfigModule configModule = (ConfigModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ConfigModule.class);
        if (configModule.isAllowtablist()) {
            ProxyServer.getInstance().getPlayers().forEach(player -> {
                player.setTabHeader(new TextComponent(FormatUtil.format(player, tabModule.getHeader())), new TextComponent(FormatUtil.format(player, tabModule.getFooter())));
            });
        }
    }
}
