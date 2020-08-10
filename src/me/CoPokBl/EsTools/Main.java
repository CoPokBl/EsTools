package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	// hi adam
	
	@Override
	public void onEnable() {
		// On startup code
	}
	
	@Override
	public void onDisable() {
		// when plugin stops
	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("estools")) {
			sender.sendMessage(ChatColor.BLUE + "EsTools v1.3 By CoPokBl");
			return true;
		}
		
		// tphere
		if (label.equalsIgnoreCase("tphere")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			if (!(sender.hasPermission("estools.tp"))) {
				sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
				return true;
			}
			if (args.length == 1) {
				// see if player exist
				try {
					Player target = Bukkit.getPlayer(args[0]);
				} catch(Exception e) {
					sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist");
					return true;
				}
				// no error
				Player target = Bukkit.getPlayer(args[0]);
				Player p = (Player) sender;
				Location ploc = p.getLocation();
				target.teleport(ploc);
				return true;
			}
			// wrong amount of args
			sender.sendMessage(ChatColor.RED + "Usage: /tphere <player>");
			return true;
		}
		
		if (label.equalsIgnoreCase("changename")) {
			if (!(args.length == 2)) {
				sender.sendMessage(ChatColor.RED + "Usage: /changename <player> [new name]");
				return true;
			}
			// see if player exist
			try {
				Player target = Bukkit.getPlayer(args[0]);
			} catch(Exception e) {
				sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist");
				return true;
			}
			// no error
			Player target = Bukkit.getPlayer(args[0]);
			target.setDisplayName(args[1]);
			sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s name to " + args[1]);
			return true;
		}
		
		if (label.equalsIgnoreCase("tpall")) {
			//perm check
			if (!(sender.hasPermission("estools.tp"))) {
				sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
				return true;
			}
			if (args.length == 0) {
				if (sender instanceof Player) {
					// Player
					Player senderp = (Player) sender;
					Location locp = senderp.getLocation();
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.teleport(locp);
					}
					return true;
				}
				// console
				sender.sendMessage(ChatColor.RED + "If you run this command from the console you must specify a player");
				return true;
			}
			
			// 1 arg
			if (args.length == 1) {
				try {
					Player p = Bukkit.getPlayer(args[0]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist");
				}
				//no error
				Player targettp = Bukkit.getPlayer(args[0]);
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.teleport(targettp);
				}
				return true;
				//end of for loop
			}
			//args not equal to 1 or 0
			sender.sendMessage(ChatColor.RED + "Usage: /tpall <player> or /tpall");
			return true;
		}
		
		
		if (label.equalsIgnoreCase("suicide")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			p.setHealth(0.0);
			return true;
		}
		
		if (label.equalsIgnoreCase("sudo")) {
			if (!(sender.hasPermission("estools.sudo"))) {
				sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
				return true;
			}
			try {
				Player target = Bukkit.getPlayer(args[0]);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist!");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			String commandtorun = "";
			for (int i = 1;i < args.length;i++) {
			    commandtorun += args[i] + " ";
			}
			Bukkit.dispatchCommand(target, commandtorun);
			sender.sendMessage(ChatColor.GREEN + "Forced " + target.getName() + " to run the command: " + commandtorun);
			return true;
		}
		
		if (label.equalsIgnoreCase("smite")) {
			if (!(sender.hasPermission("estools.smite"))) {
				sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
				return true;
			}
			if (!(args.length == 1)) {
				sender.sendMessage(ChatColor.RED + "Usage: /smite (Player)");
				return true;
			}
			try {
				Player target = Bukkit.getPlayer(args[0]);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist!");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute positioned as " + target.getName() + " run summon minecraft:lightning_bolt");
			sender.sendMessage(ChatColor.GREEN + target.getName() + " has been struck with lightning!");
			return true;
		}
		
		if (label.equalsIgnoreCase("invsee")) {
			if (!(sender.hasPermission("estools.invsee"))) {
				sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
				return true;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			if (args.length != 1) {
	            sender.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
	            return false;
	        }
			try {
				Player target = Bukkit.getPlayer(args[0]);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist!");
				return true;
			}
	        Player target = Bukkit.getPlayer(args[0]);
	        ((Player) sender).openInventory(target.getInventory());
	        sender.sendMessage("Opened " + target.getName() + "'s Inventory");
	        return true;
		}
		
		if (label.equalsIgnoreCase("fly")) {
			if (sender.hasPermission("estools.fly")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
					return true;
				}
				Player p = (Player) sender;
				if (p.getAllowFlight() == true) {
					p.sendMessage(ChatColor.GREEN + "Disabled Flying!");
					p.setAllowFlight(false);
					return true;
				}
				p.sendMessage(ChatColor.GREEN + "Enabled Flying!");
				p.setAllowFlight(true);
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("sethealth")) {
			if (sender.hasPermission("estools.health")) {
				if (args.length == 2) {
					try {
						Player target = Bukkit.getPlayer(args[0]);
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist!");
						return true;
					}
					Player target = Bukkit.getPlayer(args[0]);
	                int healthtoset;
	                try {
	                    healthtoset = Integer.parseInt(args[1]);
	                } catch(Exception e) {
	                	sender.sendMessage("Usage: /sethealth PLAYER HEALTHTOSET");
	    				return true;
	                }
	                target.setHealth(healthtoset);
	                sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s health to " + args[1]);
	                return true;
				}
				sender.sendMessage("Usage: /sethealth PLAYER HEALTHTOSET");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("setmaxhealth")) {
			if (sender.hasPermission("estools.health")) {
				if (args.length == 2) {
					try {
						Player target = Bukkit.getPlayer(args[0]);
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "Player " + args[0] + " does not exist!");
						return true;
					}
					Player target = Bukkit.getPlayer(args[0]);
	                int healthtoset;
	                try {
	                    healthtoset = Integer.parseInt(args[1]);
	                } catch(Exception e) {
	                	sender.sendMessage("Usage: /setmaxhealth PLAYER HEALTHTOSET");
	    				return true;
	                }
	                target.setMaxHealth(healthtoset);
	                sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s max health to " + args[1]);
	                return true;
				}
				sender.sendMessage("Usage: /setmaxhealth PLAYER HEALTHTOSET");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("creative")) {
			if (sender.hasPermission("estools.gm")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
					return true;
				}
				Player p = (Player) sender;
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Creative!");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("survival")) {
			if (sender.hasPermission("estools.gm")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
					return true;
				}
				Player p = (Player) sender;
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Survival!");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("adventure")) {
			if (sender.hasPermission("estools.gm")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
					return true;
				}
				Player p = (Player) sender;
				p.setGameMode(GameMode.ADVENTURE);
				p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Adventure!");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		if (label.equalsIgnoreCase("spec") || label.equalsIgnoreCase("sp")) {
			if (sender.hasPermission("estools.gm")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
					return true;
				}
				Player p = (Player) sender;
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Spectator!");
				return true;
			}
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
		
		
		
		
		return true;
	}

}
