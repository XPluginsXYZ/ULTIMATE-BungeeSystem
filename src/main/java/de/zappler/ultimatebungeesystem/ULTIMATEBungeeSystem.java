package de.zappler.ultimatebungeesystem;

import de.zappler.ultimatebungeesystem.api.FormatUtil;
import de.zappler.ultimatebungeesystem.bungeesystem.commands.BungeeSystem;
import de.zappler.ultimatebungeesystem.bungeesystem.commands.JoinMe;
import de.zappler.ultimatebungeesystem.bungeesystem.commands.TeamChat;
import de.zappler.ultimatebungeesystem.bungeesystem.listener.*;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.chat.ChatModule;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.maintenance.MaintenanceModule;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.motd.MotdModule;
import de.zappler.ultimatebungeesystem.bungeesystem.modules.tablist.TablistModule;
import de.zappler.ultimatebungeesystem.modules.ModuleManager;
import de.zappler.ultimatebungeesystem.modules.config.ConfigModule;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class ULTIMATEBungeeSystem extends Plugin {

    private static ULTIMATEBungeeSystem instance;
    private static ModuleManager moduleManager;
    private static FormatUtil formatUtil;
    private String prefix = "Loading ....";
    private File bungeesystemfile;
    private ArrayList<ProxiedPlayer> isloggedin;

    public static ULTIMATEBungeeSystem getInstance() {
        return instance;
    }

    public static FormatUtil getFormatUtil() {
        return formatUtil;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public File getBungeesystemfile() {
        return bungeesystemfile;
    }

    public ArrayList<ProxiedPlayer> getIsloggedin() {
        return isloggedin;
    }



    @Override
    public void onEnable() {
        instance = this;
        isloggedin = new ArrayList<>();
        bungeesystemfile = new File(ULTIMATEBungeeSystem.getInstance().getDataFolder() + "//BungeeSystem");
        if (!this.bungeesystemfile.exists()) this.bungeesystemfile.mkdir();
        managerFromConfig();
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(prefix + "§7Das ULTIMATEBungeeSystem wurde erfolgreich geladen!"));
        registerALL();
    }

    public void managerFromConfig()  {
        moduleManager = new ModuleManager();

        try {
            //    ConfigModule    //
            moduleManager.register(new ConfigModule(null, null, null, true, true, true));
            this.prefix = ((ConfigModule) moduleManager.getModuleByClazzIndex(ConfigModule.class)).getPrefix();
            //    ConfigModule    //

            //    TablistModule    //
            moduleManager.register(new TablistModule(null, null));
            //    TablistModule    //

            //    MotdModule    //
            moduleManager.register(new MotdModule(null, null));
            //    MotdModule    //

            //    Maintenance   //
            moduleManager.register(new MaintenanceModule(true, null, null, null, null, null));
            //    Maintenance   //

            //    ChatModule   //
            moduleManager.register(new ChatModule(null));
            //    ChatModule   //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerALL() {
        //    Listeners   //
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PostLoginListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxyPingListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitchListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ChatListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener());
        //    Listeners   //
        //    Commands   //
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new JoinMe("joinme"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TeamChat("teamchat"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TeamChat("tc"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeeSystem("bs"));
        //    Commands   //

    }


    @Override
    public void onDisable() {
    }
}
