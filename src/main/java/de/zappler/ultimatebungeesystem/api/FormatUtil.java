package de.zappler.ultimatebungeesystem.api;

import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class FormatUtil {

    public static String format(ProxiedPlayer player, String bevore) {
        return bevore.replaceAll("%get_current_server%", player.getServer().getInfo().getName())
                .replaceAll("%prefix%", ULTIMATEBungeeSystem.getInstance().getPrefix())
                .replaceAll("%online_players%", String.valueOf(ProxyServer.getInstance().getOnlineCount()))
                .replaceAll("%get_current_server%", ProxyServer.getInstance().getName())
                .replaceAll("&", "§")
                .replaceAll("%max_players%", String.valueOf(ProxyServer.getInstance().getConfig().getPlayerLimit()));
    }

    public static String format(ProxiedPlayer player, String bevore, int onlinePlayers) {
        return bevore.replaceAll("%get_current_server%", player.getServer().getInfo().getName())
                .replaceAll("%prefix%", ULTIMATEBungeeSystem.getInstance().getPrefix())
                .replaceAll("%online_players%", String.valueOf(onlinePlayers))
                .replaceAll("%get_current_server%", ProxyServer.getInstance().getName())
                .replaceAll("&", "§")
                .replaceAll("%max_players%", String.valueOf(ProxyServer.getInstance().getConfig().getPlayerLimit()));
    }

    public static String format(String bevore) {
        return bevore.replaceAll("%online_players%", String.valueOf(ProxyServer.getInstance().getOnlineCount()))
                .replaceAll("%prefix%", ULTIMATEBungeeSystem.getInstance().getPrefix())
                .replaceAll("%get_current_server%", ProxyServer.getInstance().getName())
                .replaceAll("&", "§")
                .replaceAll("%max_players%", String.valueOf(ProxyServer.getInstance().getConfig().getPlayerLimit()));
    }
}
