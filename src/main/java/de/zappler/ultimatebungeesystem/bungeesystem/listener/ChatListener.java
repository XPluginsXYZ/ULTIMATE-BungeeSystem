package de.zappler.ultimatebungeesystem.bungeesystem.listener;

import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.chat.ChatModule;
import de.zappler.ultimatebungeesystem.modules.config.ConfigModule;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Locale;

public class ChatListener implements Listener {

    @EventHandler
      public void onChat(ChatEvent chatEvent) {
        if(chatEvent.getMessage().startsWith("/")) return;
      ChatModule chatModule = (ChatModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ChatModule.class);
      ConfigModule configModule = (ConfigModule) ULTIMATEBungeeSystem.getInstance().getModuleManager().getModuleByClazzIndex(ConfigModule.class);
        ProxiedPlayer senderproxiedPlayer = (ProxiedPlayer) chatEvent.getSender();
        if (configModule.isAllowblockedlist()) {
            for (String badWord : chatModule.getBlockedWords()) {
                if (chatEvent.getMessage().toLowerCase(Locale.ROOT).contains(badWord.toLowerCase(Locale.ROOT))) {
                    chatEvent.setCancelled(true);
                    // TODO Punishment
                    return;
                }
            }
        }
    }
}
