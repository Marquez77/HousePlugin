package com.marquez.houseplugin.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DataFile {

	private static JavaPlugin plugin;
	private String filename;
	private File configFile;
	private FileConfiguration config;

	public static void init(JavaPlugin plugin) {
		DataFile.plugin = plugin;
	}
	
	public static JavaPlugin getPlugin() {
		return DataFile.plugin;
	}

	public DataFile(String filename) {
		this.filename = filename;
		this.configFile = new File(plugin.getDataFolder(), this.filename);
	}

	public void reloadConfig() {
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
	}

	public void saveConfig() {
		if(this.config == null || this.configFile == null) {
			return;
		}else {
			try {
				this.config.save(this.configFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public FileConfiguration getFileConfiguration() {
		return this.config;
	}
	
	public void delete() {
		this.configFile.delete();
	}

}
