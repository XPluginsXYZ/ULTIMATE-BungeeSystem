package de.zappler.ultimatebungeesystem.modules.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.modules.IModule;
import lombok.Getter;
import lombok.Setter;

import java.io.File;


@Getter
@Setter
public class ConfigModule implements IModule {

    private String prefix;
    private String bansystemprefix;
    private String nopermissonsmessage;
    private boolean allowmotd;
    private boolean allowtablist;
    private boolean allowblockedlist;


    public ConfigModule(String prefix, String bansystemprefix, String nopermissonsmessage, boolean allowmotd, boolean allowtablist, boolean allowblockedlist) {
        this.prefix = prefix;
        this.bansystemprefix = bansystemprefix;
        this.nopermissonsmessage = nopermissonsmessage;
        this.allowmotd = allowmotd;
        this.allowtablist = allowtablist;
        this.allowblockedlist = allowblockedlist;
    }

    @Override
    public File getFile() {
        return new File(ULTIMATEBungeeSystem.getInstance().getDataFolder(), "config.json");
    }

    @Override
    public IModule fromString(String serializedData) {
        return new Gson().fromJson(serializedData, ConfigModule.class);
    }

    @Override
    public String getDefaultConfig() {
        return new ConfigModule("§cBungeeSystem §7 | ", "§cBanSystem §7 | ", "%prefix% §7Du hast keine Berechtigungen zu diesem Befehl.", true, true, true).toString();
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
