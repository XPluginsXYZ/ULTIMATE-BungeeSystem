package de.zappler.ultimatebungeesystem.bungeesystem.modules.maintenance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.modules.ConfigManager;
import de.zappler.ultimatebungeesystem.modules.IModule;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter
@Setter
public class MaintenanceModule implements IModule {

    private boolean maintenance;
    private String maintenanceMotd;
    private String maintenancePlayerInfo;
    private String maintenanceVersionInfo;
    private String maintenancekickMessage;
    private ArrayList<String> whitelistedplayers;

    public MaintenanceModule(Boolean maintenance, String maintenanceMotd, String maintenancePlayerInfo, String maintenanceVersionInfo, String maintenancekickMessage, ArrayList whitelistedplayers) {
        this.maintenance = maintenance;
        this.maintenanceMotd = maintenanceMotd;
        this.maintenancePlayerInfo = maintenancePlayerInfo;
        this.maintenanceVersionInfo = maintenanceVersionInfo;
        this.maintenancekickMessage = maintenancekickMessage;
        this.whitelistedplayers = whitelistedplayers;
    }

    @Override
    public File getFile() {
        return new File(ULTIMATEBungeeSystem.getInstance().getBungeesystemfile(), "maintenance_config.json");
    }

    @Override
    public IModule fromString(String serializedData) {
        return new Gson().fromJson(serializedData, MaintenanceModule.class);
    }

    @Override
    public String getDefaultConfig() {
        return new MaintenanceModule(true, ULTIMATEBungeeSystem.getInstance().getPrefix() + "§cDas Netzwerk ist in wartungen \n Plugin made by Zappler2k", "§cMaintenance", "§cMaintenance", ULTIMATEBungeeSystem.getInstance().getPrefix() + "§cDer Server ist in Wartungen.", new ArrayList()).toString();
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public void addPlayerToWhiteList(String player) {
        whitelistedplayers.add(player);
        try {
            new ConfigManager(this).insert(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlayerToWhiteList(String player) {
        whitelistedplayers.remove(player);
        try {
            new ConfigManager(this).insert(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
        try {
            new ConfigManager(this).insert(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
