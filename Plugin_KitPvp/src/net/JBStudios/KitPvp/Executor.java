package net.JBStudios.KitPvp;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.JBStudios.KitPvp.GameData.PropKeys;

public class Executor implements CommandExecutor {
	
	Manager manager;
	ArrayList<GameData> gameDataList;
	
	public Executor() {
		manager = Manager.getManager();
		gameDataList = manager.getGameDataList();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (args[0].equalsIgnoreCase("create")) {
				if (args.length == 2) {
					boolean successful = true;
					for (GameData gameData: gameDataList) {
						if (gameData.getProperty(PropKeys.Name).equalsIgnoreCase(args[1])) {
							successful = false;
						}
					}
					if (successful) {
						gameDataList.add(new GameData(args[1]));
						player.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+args[1]+ChatColor.RESET+": "+ChatColor.GREEN+"was added successfully!");
					}else {
						player.sendMessage(ChatColor.RED+"A game already exist with that name!");
					}
				}else {
					player.sendMessage(ChatColor.RED+"Invalid amount of args there should only be two: <create> [name]");
				}
			}else if (args[0].equalsIgnoreCase("list")) {
				if (args.length == 1) {
					for (GameData gameData: gameDataList) {
						player.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+gameData.getProperty(PropKeys.Name)+ChatColor.RESET+",");
					}
					String temp = "s";
					if (gameDataList.size() == 1) {
						temp = "";
					}
					player.sendMessage(ChatColor.GREEN+"There is a total of ["+ChatColor.YELLOW+""+gameDataList.size()+""+ChatColor.GREEN+"] game"+temp);
				}else {
					player.sendMessage(ChatColor.RED+"Invalid amount of args there should only be one: <list>");
				}
			}else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length == 2) {
					boolean successful = false;
					String name = "";
					for (int i=0;i<gameDataList.size();i++) {
						if (gameDataList.get(i).getProperty(PropKeys.Name).equalsIgnoreCase(args[1])) {
							name = gameDataList.get(i).getProperty(PropKeys.Name);
							gameDataList.remove(i);
							successful = true;
						}
					}
					if (successful) {
						player.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+name+ChatColor.RESET+": "+ChatColor.GREEN+"was removed successfully!");
					}else {
						player.sendMessage(ChatColor.RED+"Invalid name: "+args[1]);
					}
				}else {
					player.sendMessage(ChatColor.RED+"Invalid amount of args there should only be two: <remove> [name]");
				}
			}else if (args[0].equalsIgnoreCase("modify")) {
				
			}else {
				player.sendMessage(ChatColor.RED+"Unknown arg at space 1: "+args[0]);
			}
		}else {
			sender.sendMessage(ChatColor.RED+"Must be a player to run commands!");
		}
		return false;
	}

}