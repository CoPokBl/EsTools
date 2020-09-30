package me.CoPokBl.EsTools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {}
	
	@Override
	public void onDisable() {}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("estools")) {
			sender.sendMessage(ChatColor.BLUE + "EsTools v1.7 By CoPokBl");
			return true;
		}
		
		// tphere
		if (label.equalsIgnoreCase("tphere")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			if (checkPerm(sender, "estools.tp"))
				return false;
			
			if (args.length == 1) {
				// get player
				Player target = getPlayer(sender, args[0]);
				
				if (target == null)
					return false;
				
				Player p = (Player) sender;
				Location ploc = p.getLocation();
				target.teleport(ploc);
				return true;
			}
			// wrong amount of args
			sender.sendMessage(ChatColor.RED + "Usage: /tphere <player>");
			return true;
		}
		
		else if (label.equalsIgnoreCase("changename")) {
			if (!(args.length == 2)) {
				sender.sendMessage(ChatColor.RED + "Usage: /changename <player> [new name]");
				return true;
			}
			
			Player target = getPlayer(sender, args[0]);
			
			if (target == null)
				return false;
			
			target.setDisplayName(args[1]);
			sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s name to " + args[1]);
			return true;
		}
		
		else if (label.equalsIgnoreCase("tpall")) {
			//perm check
			if (checkPerm(sender, "estools.tp"))
				return false;

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
				
				Player targettp = getPlayer(sender, args[0]);
				
				if (targettp == null)
					return false;
				
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
		
		
		else if (label.equalsIgnoreCase("suicide")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			p.setHealth(0.0);
			return true;
		}
		
		else if (label.equalsIgnoreCase("sudo")) {
			if (checkPerm(sender, "estools.sudo"))
				return false;

			Player target = getPlayer(sender, args[0]);
			
			if (target == null)
				return false;
			
			String commandtorun = "";
			for (int i = 1;i < args.length;i++) {
			    commandtorun += args[i] + " ";
			}
			Bukkit.dispatchCommand(target, commandtorun);
			sender.sendMessage(ChatColor.GREEN + "Forced " + target.getName() + " to run the command: " + commandtorun);
			return true;
		}
		
		else if (label.equalsIgnoreCase("smite")) {
			if (checkPerm(sender, "estools.smite"))
				return false;
			
			if (!(args.length == 1)) {
				sender.sendMessage(ChatColor.RED + "Usage: /smite (Player)");
				return true;
			}
			
			Player target = getPlayer(sender, args[0]);
			
			if (target == null)
				return false;
			
			target.getWorld().strikeLightning(target.getLocation());
			sender.sendMessage(ChatColor.GREEN + target.getName() + " has been struck with lightning!");
			return true;
		}
		
		else if (label.equalsIgnoreCase("invsee")) {
			if (checkPerm(sender, "estools.invsee"))
				return false;
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			if (args.length != 1) {
	            sender.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
	            return false;
	        }
			Player target = getPlayer(sender, args[0]);
			
			if (target == null)
				return false;
			
	        ((Player) sender).openInventory(target.getInventory());
	        sender.sendMessage("Opened " + target.getName() + "'s Inventory");
	        return true;
		}
		
		else if (label.equalsIgnoreCase("fly")) {
			if (checkPerm(sender, "estools.fly"))
				return false;
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			if (p.getAllowFlight()) {
				p.sendMessage(ChatColor.GREEN + "Disabled Flying!");
				p.setAllowFlight(false);
				return true;
			}
			p.sendMessage(ChatColor.GREEN + "Enabled Flying!");
			p.setAllowFlight(true);
			return true;
		}
		
		else if (label.equalsIgnoreCase("sethealth")) {
			if (checkPerm(sender, "estools.health"))
				return false;
			
			if (args.length == 2) {
				Player target = getPlayer(sender, args[0]);
				
				if (target == null)
					return false;
				
				double healthtoset;
                try {
                    healthtoset = Double.parseDouble(args[1]);
                } catch(Exception e) {
                	sender.sendMessage("Usage: /sethealth <player> <health>");
    				return true;
                }
                target.setHealth(healthtoset);
                sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s health to " + args[1]);
                return true;
			}
			sender.sendMessage("Usage: /sethealth <player> <health>");
			return true;
		}
		
		else if (label.equalsIgnoreCase("setmaxhealth")) {
			if (checkPerm(sender, "estools.health"))
				return false;
			if (args.length == 2) {
				Player target = getPlayer(sender, args[0]);
				
				if (target == null)
					return false;
				
                double healthtoset;
                try {
                    healthtoset = Double.parseDouble(args[1]);
                } catch(Exception e) {
                	sender.sendMessage("Usage: /setmaxhealth <player> <health>");
    				return true;
                }
                
                // set max health
                AttributeInstance attribute = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                attribute.setBaseValue(healthtoset);
                
                sender.sendMessage(ChatColor.GREEN + "Set " + target.getName() + "'s max health to " + args[1]);
                return true;
			}
			sender.sendMessage("Usage: /setmaxhealth <player> <health>");
			return true;
		}
		
		else if (label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("creative")) {
			if (checkPerm(sender, "estools.gm"))
				return false;
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Creative!");
			return true;
		}
		
		else if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("survival")) {
			if (checkPerm(sender, "estools.gm"))
				return false;
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			p.setGameMode(GameMode.SURVIVAL);
			p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Survival!");
			return true;
		}
		
		else if (label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("adventure")) {
			if (checkPerm(sender, "estools.gm"))
				return false;
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Adventure!");
			return true;
		}
		
		else if (label.equalsIgnoreCase("spec") || label.equalsIgnoreCase("sp")) {
			if (checkPerm(sender, "estools.gm"))
				return false;
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
				return true;
			}
			Player p = (Player) sender;
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(ChatColor.GREEN + "Your Gamemode Has Been Set To Spectator!");
			return true;
		}
		else if (label.equalsIgnoreCase("heal")) {
			if (checkPerm(sender, "estools.heal"))
				return false;
			
			Player p;
			
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Usage: /heal <player>");
					return false;
				}
				
				p = (Player) sender;
			} else {
				p = getPlayer(sender, args[0]);
				sender.sendMessage("You Have Healed " + p.getName() + "!");
			}

			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			p.sendMessage(ChatColor.GREEN + "You Have Been Healed!");
			return true;
		}
		else if (label.equalsIgnoreCase("feed")) {
			if (checkPerm(sender, "estools.feed"))
				return false;
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "Usage: /feed <player>");
					return false;
				}
				Player p = (Player) sender;
				p.setFoodLevel(20);
				p.sendMessage(ChatColor.GREEN + "You Have Been Fed!");
				return true;
			}
			Player p = getPlayer(sender, args[0]);
			p.setFoodLevel(20);
			p.setSaturation(15);
			p.sendMessage(ChatColor.GREEN + "You Have Been Fed!");
			sender.sendMessage("You Have Fed " + p.getName() + "!");
			return true;
		}
		else if (label.equalsIgnoreCase("i")) {
			if (checkPerm(sender, "estools.give"))
				return false;
			
			if (!(sender instanceof Player)) {
				s(sender, "&4You must be a player to run this command!");
			}
			
			if (args.length == 0) {
				s(sender, "&4Usage: /i <Item> [Amount]");
			}
			
			Player p = (Player) sender;
			
			int amount = 1;
			
			if (args.length > 1) {
				amount = Integer.valueOf(args[1]);
			}
			
			Material mat = Material.getMaterial(args[0].toUpperCase());
			
			if (mat != null) {
				p.getInventory().addItem(new ItemStack(mat, amount));
			} else {
				s(sender, "&4That item doesn't exist!");
			}
			return true;
		}
		else if (label.equalsIgnoreCase("h")) {
			if (checkPerm(sender, "estools.give"))
				return false;
			
			if (!(sender instanceof Player)) {
				s(sender, "&4You must be a player to run this command!");
			}
			
			if (args.length == 0) {
				s(sender, "&4Usage: /h <Item> [Amount]");
			}
			
			Player p = (Player) sender;
			
			int amount = 1;
			
			if (args.length > 1) {
				amount = Integer.valueOf(args[1]);
			}
			
			Material mat = Material.getMaterial(args[0].toUpperCase());
			
			if (mat != null) {
				p.getInventory().setItemInMainHand(new ItemStack(mat, amount));
			} else {
				s(sender, "&4That item doesn't exist!");
			}
			return true;
		}
		return true;
	}
	
	public Player getPlayer(CommandSender sender, String playerName) {
		try {
			return Bukkit.getPlayer(playerName);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Player " + playerName + " does not exist!");
			return null;
		}
	}
	
	// returns true and says if it doesnt have permission
	// returns true to avoid 50 million not gates
	public Boolean checkPerm(CommandSender sender, String perm) {
		if (sender.hasPermission(perm)) {
			return false;
		}
		else {
			sender.sendMessage(ChatColor.RED + "You Do Not Have Permission To Do That!");
			return true;
		}
	}
	
	public void s(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

}
