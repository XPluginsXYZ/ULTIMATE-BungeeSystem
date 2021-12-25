package de.zappler.ultimatebungeesystem.modules;

import java.io.IOException;
import java.util.ArrayList;

public class ModuleManager {

    private ArrayList<IModule> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
    }

    public void register(IModule module) throws IOException {
        modules.add(module.fromString(new ConfigManager(module).getContent()));
    }

    public IModule getModuleByClazzIndex(Class<? extends IModule> clazz) {
        for (IModule module : modules) {
            if (module.getClass().getName().equalsIgnoreCase(clazz.getName())) {
                return module;
            }
        }
        return null;
    }

    public ArrayList<IModule> getModules() {
        return modules;
    }
}
