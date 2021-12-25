package de.zappler.ultimatebungeesystem.modules;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigManager {

    private IModule module;

    public ConfigManager(IModule module) throws IOException {
        this.module = module;

        if (!module.getFile().getParentFile().exists()) {
            module.getFile().getParentFile().mkdir();
        }

        if (!module.getFile().exists()) {
            module.getFile().createNewFile();
            FileWriter writer = new FileWriter(module.getFile());
            writer.write(module.getDefaultConfig());
            writer.flush();
        }
    }

    public String getContent() throws IOException {
        return new String(Files.readAllBytes(module.getFile().toPath()));
    }

    public void insert(String data) throws IOException {
        FileWriter writer = new FileWriter(module.getFile());
        writer.write(data);
        writer.flush();
    }
}
