package de.zappler.ultimatebungeesystem.bungeesystem.modules.chat;

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
public class ChatModule implements IModule {

    private ArrayList<String> blockedWords;

    public ChatModule(ArrayList blockedWords) {
        this.blockedWords = blockedWords;
    }

    @Override
    public File getFile() {
        return new File(ULTIMATEBungeeSystem.getInstance().getBungeesystemfile(), "chat_config.json");
    }

    @Override
    public IModule fromString(String serializedData) {
        return new Gson().fromJson(serializedData, ChatModule.class);
    }

    @Override
    public String getDefaultConfig() {
        return new ChatModule(new ArrayList()).toString();
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public void addWordToBlockedWordsList(String word)  {
        blockedWords.add(word);
        try {
            new ConfigManager(this).insert(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeWordToBlockedWordsList(String word)  {
        blockedWords.remove(word);
        try {
            new ConfigManager(this).insert(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
