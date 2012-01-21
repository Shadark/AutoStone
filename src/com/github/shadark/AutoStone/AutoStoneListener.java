package com.github.shadark.AutoStone;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class AutoStoneListener implements Listener {
	
	private final AutoStone plugin;
	
	public AutoStoneListener (AutoStone instance) {
		plugin = instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlaceCobblestone(final BlockPlaceEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		Material mat = block.getType();
		if (plugin.pluginEnabled.containsKey(player))
			if (mat.compareTo(Material.COBBLESTONE) == 0 && plugin.pluginEnabled.get(player))
				block.setType(Material.STONE);
	}
	
}