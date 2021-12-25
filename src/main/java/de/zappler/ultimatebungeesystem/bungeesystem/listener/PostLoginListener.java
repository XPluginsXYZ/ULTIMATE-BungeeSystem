package de.zappler.ultimatebungeesystem.bungeesystem.listener;

import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.maintenance.MaintenanceModule;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PostLoginEvent postLoginEvent) {
        MaintenanceModule maintenanceModule = (MaintenanceModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(MaintenanceModule.class);
        ProxiedPlayer proxiedPlayer = postLoginEvent.getPlayer();
        if (maintenanceModule.isMaintenance()) {
            if (!maintenanceModule.getWhitelistedplayers().contains(proxiedPlayer.toString())) {
                proxiedPlayer.disconnect(new TextComponent(maintenanceModule.getMaintenancekickMessage()));
            }
        }

    }
}
