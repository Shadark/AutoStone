package com.github.shadark.AutoStone;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoStone extends JavaPlugin {
	
	private Map<Player, Boolean> pluginEnabled = new HashMap<Player, Boolean>(); //True if Enabled, False if Disabled
	private Map<Player, Integer> typePlugin = new HashMap<Player, Integer>(); //0 if Free, 1 if Coal
	private AutoStoneListener listener;
	private int coalUsed;
	private int stoneGiven;
	
	public void onEnable() { 
		Logger log = this.getServer().getLogger();
		this.saveConfig();
		if (!this.getConfig().contains("CoalUsed")) {
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		}
		coalUsed = this.getConfig().getInt("CoalUsed");
		stoneGiven = this.getConfig().getInt("StoneGiven");
		PluginManager pm = this.getServer().getPluginManager();
		AutoStoneCommandExecutor executor = new AutoStoneCommandExecutor(this);
		getCommand("autostone").setExecutor(executor);
		listener = new AutoStoneListener(this);
		log.info("Plugin AutoStone enabled.");
		pm.registerEvents(listener, this);
	}
	 
	public void onDisable() { 
		Logger log = this.getServer().getLogger();
		log.info("Plugin AutoStone disabled.");
	}
	
	public boolean getPlayerEnabled(Player player) {
		if (pluginEnabled.containsKey(player))
			return pluginEnabled.get(player);
		return false;
	}
	
	public void setPlayerEnabled(Player player, boolean enabled) {
		pluginEnabled.put(player, enabled);
	}
	
	public int getTypePlugin(Player player) {
		return typePlugin.get(player);
	}
	
	public void setTypePlugin(Player player, int type) {
		typePlugin.put(player, type);
	}
	
	public int getCoalUsed() {
		return coalUsed;
	}
	
	public void setCoalUsed(int coal) {
		coalUsed = coal;
	}
	
	public int getStoneGiven() {
		return stoneGiven;
	}
	
	public void setStoneGiven(int stone) {
		stoneGiven = stone;
	}
	
	public AutoStoneListener getListener() {
		return listener;
	}
}