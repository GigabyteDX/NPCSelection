package com.gigabytedx.npcselection;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gigabytedx.npcselection.events.Interact;
import com.gigabytedx.npcselection.events.InventoryInteract;

public class Main extends JavaPlugin {

	private File npcMenusFile = new File(getDataFolder() + "/Data/npcMenusFile.yml");
	private FileConfiguration npcMenusConfig = YamlConfiguration.loadConfiguration(npcMenusFile);
	Logger logger;

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		logger = getLogger();
		registerCommands();
		loadFiles(npcMenusFile, npcMenusConfig);
		registerEvents();
		logger.info(pdfFile.getName() + " has been enabled (V." + pdfFile.getVersion() + ")");
	}

	private void registerCommands() {
		// getCommand("command").setExecutor(new commandclass(this));
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Interact(this, npcMenusConfig), this);
		pm.registerEvents(new InventoryInteract(this, npcMenusConfig), this);
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = getLogger();

		logger.info(pdfFile.getName() + " has been disabled (V." + pdfFile.getVersion() + ")");
	}

	public void saveCustomConfig(File file, FileConfiguration fileConfig) {
		try {
			fileConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFiles(File file, FileConfiguration fileConfig) {
		if (file.exists()) {
			try {
				fileConfig.load(file);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fileConfig.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void logError(String msg) {
			logger.severe(msg);
	}
	
	public void logDebug(String msg) {
		if (npcMenusConfig.getBoolean("enableDebug")) {
			logger.info("[Debug] " + msg);
		}
	}
}
