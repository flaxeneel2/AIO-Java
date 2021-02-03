package io.github.flaxeneel2.AIO;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings({"unused"})
public class SettingsManager {
    static SettingsManager instance = new SettingsManager();
    Plugin p;
    FileConfiguration config, data;
    File cfile, dfile;

    public static SettingsManager getInstance() {
        return instance;
    }

    public void checkngenfiles(Plugin p) {
        if(!p.getDataFolder().exists()) {
            boolean success = p.getDataFolder().mkdir();
            Main.sendToConsole("&6&lStatus: " + success);
        }
        if(!(new File(p.getDataFolder(), "config.yml").exists())) copy("/config.yml", "/config.yml", p);
        if(!(new File(p.getDataFolder(), "data.yml").exists())) copy("/data.yml", "/data.yml", p);
        if(!(new File(p.getDataFolder(), "data/data.yml").exists())) copy("/data.yml", "/data/data.yml", p);
        if(!(new File(p.getDataFolder(), "data/data/data/data.yml").exists())) copy("/data.yml", "/data/data/data/data.yml", p);
        this.cfile = new File(p.getDataFolder(), "config.yml");
        this.dfile = new File(p.getDataFolder(), "data.yml");
        this.config = YamlConfiguration.loadConfiguration(this.cfile);
        this.data = YamlConfiguration.loadConfiguration(this.dfile);

    }
    private void copy(String filename, String reldest, Plugin p) {
        InputStream file = SettingsManager.class.getResourceAsStream(filename);
        checkSubFolders(reldest, p);
        Path path = Paths.get((p.getDataFolder().getPath() + reldest));
        Main.sendToConsole(path.toString());
        try {
            Files.copy(file, path);
        } catch (IOException e) {
            Main.sendToConsole("&c&lOops, error!");
            e.printStackTrace();
        }
    }
    private void checkSubFolders(String path, Plugin p) {
        String[] args = path.split("/");
        StringBuilder dirs = new StringBuilder(p.getDataFolder().getPath() + "/");
        for(String item: args) {
            if(!item.equalsIgnoreCase(args[args.length-1])) dirs.append(item).append("/");
        }
        File tmp = new File(dirs.toString());
        if(!tmp.exists()) tmp.mkdirs();
    }

    public void reloadConfig() { this.config = YamlConfiguration.loadConfiguration(cfile); }
    public void reloadData() { this.data = YamlConfiguration.loadConfiguration(dfile); }
    public FileConfiguration getConfig() { return this.config; }
}
