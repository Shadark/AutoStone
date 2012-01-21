package com.github.shadark.AutoStone;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoStone extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	public Map<Player, Boolean> pluginEnabled = new HashMap<Player, Boolean>();
	
	public void onEnable(){ 
		 log.info("Plugin AutoStone enabled.");
		 PluginManager pm = this.getServer().getPluginManager();
		 pm.registerEvents(new AutoStoneListener(this), this);
	}
	 
	public void onDisable(){ 
		 log.info("Plugin AutoStone disabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
	 
		if (cmd.getName().equalsIgnoreCase("autostone")) {
			if (pluginEnabled.containsKey(player)) {
				boolean enabled = pluginEnabled.get(player);
				if (enabled) {
					sender.sendMessage("AutoStone disabled.");
				} else {
					sender.sendMessage("AutoStone enabled.");
				}
				pluginEnabled.put(player, !enabled);
			} else {
				pluginEnabled.put(player, true);
				sender.sendMessage("AutoStone enabled.");
			}
			return true;
		}
		return false;
	}
	
}
