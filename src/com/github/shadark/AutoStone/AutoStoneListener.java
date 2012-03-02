package com.github.shadark.AutoStone;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.PlayerInventory;

public class AutoStoneListener implements Listener {
	
	private final AutoStone plugin;
	private Map<Player, Integer> coalCount = new HashMap<Player, Integer>(); //Counts the number of cobblestones placeable for each coal used
	
	public AutoStoneListener (AutoStone instance) {
		plugin = instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlaceCobblestone(final BlockPlaceEvent event) {
		final Block block = event.getBlock();
		Player player = event.getPlayer();
		Material mat = block.getType();
		if (mat.compareTo(Material.COBBLESTONE) == 0 && plugin.getPlayerEnabled(player) && !event.isCancelled()) {
			if (plugin.getTypePlugin(player) == 0) 										//Free AutoStone
				block.setType(Material.STONE);
			else {																		//AutoStone with coal
				if ((coalCount.get(player) == null) || (coalCount.get(player) == 0)) {	//Removing coal and granting uses
					PlayerInventory inv = player.getInventory();
					int coalUsed = plugin.getCoalUsed();
					int stoneGiven = plugin.getStoneGiven();
					int pos = inv.first(Material.COAL);
					int coal = 0;
					try {
						coal = inv.getItem(pos).getAmount();
					} catch (ArrayIndexOutOfBoundsException ex) {}
					if ((pos == -1) || (coal < coalUsed)) {//Not enough coal
						player.sendMessage("You don't have enough coal in your inventory! You need at least " + ChatColor.RED +  coalUsed + ChatColor.WHITE + " coal for " + ChatColor.RED + stoneGiven + ChatColor.WHITE + " uses.");
						event.setCancelled(true);
						return;
					} else {
						if (coal - coalUsed > 0)
							inv.getItem(pos).setAmount(coal - coalUsed);
						else
							inv.remove(Material.COAL);
						player.updateInventory();
						coalCount.put(player, stoneGiven);
					}
				}
				coalCount.put(player, (coalCount.get(player) - 1));
				block.setType(Material.STONE);
				player.sendMessage(ChatColor.RED + " stone use(s) remaining");
			}
		}
	}
	
}

//TODO