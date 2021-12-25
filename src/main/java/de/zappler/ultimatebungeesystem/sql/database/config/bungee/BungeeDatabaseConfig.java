package de.zappler.ultimatebungeesystem.sql.database.config.bungee;

import de.zappler.ultimatebungeesystem.sql.database.config.IDatabaseConfig;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeDatabaseConfig implements IDatabaseConfig {

    private final Plugin plugin;
    private final File configFile;
    private Configuration cfg;

    public BungeeDatabaseConfig(Plugin bungeePlugin) {
        this.plugin = bungeePlugin;
        this.configFile = new File(plugin.getDataFolder(), "mysql_config.yml");
        this.initConfiguration();
        this.init();
    }

    public void initConfiguration() {
        try {
            if (!this.plugin.getDataFolder().exists()) {
                this.plugin.getDataFolder().mkdir();
            }
            if (!this.configFile.exists()) {
                this.configFile.createNewFile();
            }
            this.cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        this.addDefault("DATABASE", "system");
        this.addDefault("HOST", "localhost");
        this.addDefault("PORT", 3306);
        this.addDefault("USER", "root");
        this.addDefault("PASSWORD", "");
        this.addDefault("maxPoolSize", 10);
        this.addDefault("cachePrepStmts", true);
        this.addDefault("prepStmtCacheSize", 250);
        this.addDefault("prepStmtCacheSqlLimit", 2048);

        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.cfg, this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDefault(String path, Object value) {
        if (!this.cfg.contains(path)) {
            this.cfg.set(path, value);
        }
    }

    public String getHost() {
        return this.cfg.getString("HOST");
    }

    @Override
    public String getUser() {
        return this.cfg.getString("USER");
    }

    @Override
    public String getPassword() {
        return this.cfg.getString("PASSWORD");
    }

    @Override
    public int getPort() {
        return this.cfg.getInt("PORT");
    }

    @Override
    public String getDatabase() {
        return this.cfg.getString("DATABASE");
    }

    @Override
    public int getMaxPoolSize() {
        return this.cfg.getInt("maxPoolSize");
    }

    @Override
    public boolean isCachePreparedStatements() {
        return this.cfg.getBoolean("cachePrepStmts");
    }

    @Override
    public int getPreparedStatementCacheSize() {
        return this.cfg.getInt("prepStmtCacheSize");
    }

    @Override
    public int getPreparedStatementCacheSQLLimit() {
        return this.cfg.getInt("prepStmtCacheSqlLimit");
    }

    @Override
    public String getDirPath() {
        return null;
    }

    @Override
    public String getPoolName() {
        return this.plugin.getDescription().getName() + "-hikari";
    }
}
