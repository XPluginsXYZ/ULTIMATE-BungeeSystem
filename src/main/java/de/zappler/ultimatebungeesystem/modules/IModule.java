package de.zappler.ultimatebungeesystem.modules;

import java.io.File;

public interface IModule {

    File getFile();

    String toString();

    IModule fromString(String serializedData);

    String getDefaultConfig();
}
