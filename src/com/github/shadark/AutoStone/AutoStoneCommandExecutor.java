package com.github.shadark.AutoStone;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoStoneCommandExecutor implements CommandExecutor{

	private AutoStone plugin;
	
	public AutoStoneCommandExecutor(AutoStone plugin) {
		this.plugin = plugin;
	}
	
	private boolean isNumeric (String str) {
		try {  
			Integer.parseInt(str);
		} catch(NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
			if (command.getName().equalsIgnoreCase("autostone")) {
				if (args.length == 0) {
					return false;
				} else {
					boolean enabled = plugin.getPlayerEnabled(player);
					String argument = args[0].toLowerCase();
					if (argument.compareTo("free") == 0) {
						if (player.hasPermission("autostone.place.free")) {
							if (!enabled) {
								plugin.setTypePlugin(player, 0);
								sender.sendMessage(ChatColor.GRAY + "Free AutoStone enabled.");
							} else
								sender.sendMessage(ChatColor.GRAY + "AutoStone disabled.");
							plugin.setPlayerEnabled(player, !enabled);
						} else
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					} else if (argument.compareTo("coal") == 0) {
						if (player.hasPermission("autostone.place.coal")) {
							if (!enabled) {
								plugin.setTypePlugin(player,1);
								sender.sendMessage(ChatColor.GRAY + "AutoStone with Coal enabled.");
							} else
								sender.sendMessage(ChatColor.GRAY + "AutoStone disabled.");
							plugin.setPlayerEnabled(player, !enabled);
						} else
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					} else if (argument.compareTo("rates") == 0) {
						if (player.hasPermission("autostone.rates"))
							sender.sendMessage(ChatColor.RED + "" + plugin.getCoalUsed() + ChatColor.WHITE + " unit(s) of coal will grant you " + ChatColor.RED + plugin.getStoneGiven() + ChatColor.WHITE + " unit(s) of stone.");
						else
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					} else if (argument.compareTo("reload") == 0) {
						if (player.hasPermission("autostone.rates")) {
							plugin.setCoalUsed(plugin.getConfig().getInt("CoalUsed"));
							plugin.setStoneGiven(plugin.getConfig().getInt("StoneGiven"));
							sender.sendMessage(ChatColor.GREEN + "AutoStone configuration successfully reloaded.");
						} else
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					} else if (argument.compareTo("set") == 0) {
						if (player.hasPermission("autostone.admin")) {
							if (args.length < 1 || args.length < 2)
								player.sendMessage(ChatColor.RED + "Not enough arguments!");
							else {
								if (args[1].toLowerCase().compareTo("coalused") == 0) {
									if (isNumeric(args[2])) {
										plugin.setCoalUsed(Integer.parseInt(args[2]));
										player.sendMessage(ChatColor.GREEN + "Coal used set to "+ ChatColor.RED + args[2]);
									} else
										player.sendMessage(ChatColor.RED + "You must type an integer as an argument!");
								} else if (args[1].toLowerCase().compareTo("stonegiven") == 0) {
									if (isNumeric(args[2])) {
										plugin.setStoneGiven(Integer.parseInt(args[2]));
										player.sendMessage(ChatColor.GREEN + "Stone given set to "+ ChatColor.RED + args[2]);
									} else
										player.sendMessage(ChatColor.RED + "You must type an integer as an argument!");
								} else 
									player.sendMessage("Unknown variable: " + args[1]);
							}
						} else
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					} else if (argument.compareTo("save") == 0) {
						if (player.hasPermission("autostone.admin")) {
							plugin.getConfig().set("CoalUsed", plugin.getCoalUsed());
							plugin.getConfig().set("StoneGiven", plugin.getStoneGiven());
							plugin.saveConfig();
							player.sendMessage(ChatColor.GREEN + "AutoStone configuration successfully saved.");
						} else
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					} else {
						sender.sendMessage("Unknown argument: " + args[0]);
					}
				}
			}
			return true;
		} else {
			sender.sendMessage("You must be a player!");
			return true;
		}
	}

}
