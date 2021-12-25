package de.zappler.ultimatebungeesystem.bungeesystem.modules.tablist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler.ultimatebungeesystem.ULTIMATEBungeeSystem;
import de.zappler.ultimatebungeesystem.modules.IModule;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class TablistModule implements IModule {

    private String header;
    private String footer;

    public TablistModule(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }

    @Override
    public File getFile() {
        return new File(ULTIMATEBungeeSystem.getInstance().getBungeesystemfile(), "tablist_config.json");
    }

    @Override
    public IModule fromString(String serializedData) {
        return new Gson().fromJson(serializedData, TablistModule.class);
    }

    @Override
    public String getDefaultConfig() {
        return new TablistModule(ULTIMATEBungeeSystem.getInstance().getPrefix() + " - > §7Header", ULTIMATEBungeeSystem.getInstance().getPrefix() + " - > §7Footer").toString();
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

}
