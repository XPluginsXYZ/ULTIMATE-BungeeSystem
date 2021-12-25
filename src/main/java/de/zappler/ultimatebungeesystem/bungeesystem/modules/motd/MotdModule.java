package de.zappler.ultimatebungeesystem.bungeesystem.modules.motd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.modules.IModule;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Setter
@Getter
public class MotdModule implements IModule {

    private String Motd;
    private String PlayerInfo;

    public MotdModule(String motd, String playerInfo) {
        Motd = motd;
        PlayerInfo = playerInfo;
    }

    @Override
    public File getFile() {
        return new File(ULTIMATEBungeeSystem.getInstance().getBungeesystemfile(), "motd_config.json");
    }

    @Override
    public IModule fromString(String serializedData) {
        return new Gson().fromJson(serializedData, MotdModule.class);
    }

    @Override
    public String getDefaultConfig() {
        return new MotdModule(ULTIMATEBungeeSystem.getInstance().getPrefix() + "§cDas Netzwerk ist offen \n Plugin made by Zappler2k", "Netzwerk.de").toString();
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }


}
