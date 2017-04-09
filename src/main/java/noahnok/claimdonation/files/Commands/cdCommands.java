package noahnok.claimdonation.files.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import noahnok.claimdonation.files.main;

public class cdCommands implements CommandExecutor {
	private final main plugin;

	public cdCommands(main plugin) {
		this.plugin = plugin; 
	}
	
	public String Message(String msg){
		return plugin.CU.getMessage(msg);
	}
	
	
	//COMMANDS
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length <= 0){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + "&7ClaimDonation Version 1.2.2 - Use /cd claim <name> to claim anything you buy on our store; in-game!"));
			sender.sendMessage(Message("BASE_COMMAND"));
			return true;
			
		}
		if (args[0].equalsIgnoreCase("inv")){
			Player p = (Player) sender;
			if (args.length == 1){
				
				plugin.CGUI.loadPlayerGUI(p.getUniqueId());
				return true;
			}
			OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
			if (!plugin.Cdu.donation.containsKey(target.getUniqueId())){
				sender.sendMessage("NO PLAYER FOUND");
				return true;
			}
				plugin.CGUI.loadOtherPlayerGUI(target.getUniqueId(), p);
				return true;
			
			
			
		}
		//CLAIM COMMAND
		if (args[0].equalsIgnoreCase("claim")){
			if (sender.hasPermission("cd.claim")){
			if (args.length == 1){
				sender.sendMessage(Message("CLAIM_COMMAND"));
				return true;
			}
			Player target = checkOnline(args[1]);
			if (target == null){
				sender.sendMessage(Message("PLAYER_OFFLINE").replace("%player%", args[1]));
				return true;
			}
			else{
				if (plugin.Cdu.getDonations(target) == null){
					if (sender instanceof Player){
						target.sendMessage(Message("PLAYER_NOITEM"));
						return true;
					}
					else {
						sender.sendMessage("Player: " + args[1] + "does not have any donation items to claim!");
						return true;
					}
				}
				plugin.Cdu.giveDonation(target, plugin.Cdu.getDonations(target));
				sender.sendMessage(Message("DONATION_CLAIM"));
				return true;
			}
			}else{sender.sendMessage(Message("NO_PERM")); return true;}
		}
		
		//ADD COMMAND
		else if (args[0].equalsIgnoreCase("add")){
			if (sender.hasPermission("cd.add")){
			if (args.length == 1){
				sender.sendMessage(Message("ADD_COMMAND"));
				return true;
			}
			Player target = checkOnline(args[1]);
			if (target == null){
				sender.sendMessage(Message("PLAYER_OFFLINE").replace("%player%", args[1]));
				return true;
			}
			else{
				if (args.length == 2){
					sender.sendMessage(Message("ADD_COMMAND_NO_COMMAND"));
					return true;
				}
				String command = "";
				for (int i = 2; i < args.length; i++) {
                    command += args[i] + " ";
                }
				plugin.Cdu.setDonation(target, command);
				if (sender instanceof Player){
					sender.sendMessage(Message("DONATION_ITEM_ADDED"));
				}
				return true;
			}
			}else{sender.sendMessage(Message("NO_PERM")); return true;}
		}

		//LIST COMMAND
		else if(args[0].equalsIgnoreCase("list")){
			if (sender.hasPermission("cd.list")){
				if (args.length == 1){
					sender.sendMessage(Message("LIST_COMMAND"));
					return true;
				}
				Player target = checkOnline(args[1]);
				if (target == null){
					sender.sendMessage(Message("PLAYER_OFFLINE").replace("%player%", args[1]));
					return true;
				}
				ArrayList<String> commands = plugin.Cdu.getDonations(target);
				if (commands.size() == 0){
					sender.sendMessage(Message("PLAYER_COMMAND_LIST_NOCMDS").replace("%player%", target.getName()));
					return true;
				}
				int count = 0;
				sender.sendMessage(Message("PLAYER_COMMAND_LIST_HEADER").replace("%player%", target.getName()));
				for (String command : commands){
					count += 1;
					
					sender.sendMessage(Message("PLAYER_COMMAND_LIST").replace("%cmdnumber%", count + "").replace("%command%", command));
					
				}
				return true;
			}else{sender.sendMessage(Message("NO_PERM")); return true;}
		}
		//REMOVE COMMAND
		else if(args[0].equalsIgnoreCase("remove")){
			if (sender.hasPermission("cd.remove")){
				if (args.length == 1){
					sender.sendMessage(Message("REMOVE_COMMAND"));
					return true;
				}
				Player target = checkOnline(args[1]);
				if (target == null){
					sender.sendMessage(Message("PLAYER_OFFLINE").replace("%player%", args[1]));
					return true;
				}
				if (args.length == 2){
					sender.sendMessage(Message("REMOVE_COMMAND_NONUMBER").replace("%player%", target.getName()));
					return true;
				}
				int number;
			    try {
			    	number = Integer.parseInt(args[2]) - 1;
			    } catch (NumberFormatException e) {
			        sender.sendMessage(Message("NUMBERS_ONLY")); return true;
			    }
				ArrayList<String> commands = plugin.Cdu.getDonations(target);
				if (commands.size() == 0){
					sender.sendMessage(Message("PLAYER_COMMAND_LIST_NOCMDS").replace("%player%", target.getName()));
					return true;
				}
				String command = commands.get(number);
				commands.remove(number);
				sender.sendMessage(Message("COMMAND_REMOVED").replace("%command%", command).replace("%player%", target.getName()));
				return true;
				
				
			}
		}
		//RELOAD COMMAND
		else if (args[0].equalsIgnoreCase("reload")){
			if (sender.hasPermission("cd.reload")){
				plugin.reloadConfig();
				plugin.CU.loadMessages();
				sender.sendMessage(Message("CONFIG_RELOAD"));
				return true;
			}else{sender.sendMessage(Message("NO_PERM")); return true;}
		}
		else {
			sender.sendMessage(Message("SUBCMD_INCORRECT").replace("%command%", args[0]));
			return true;
		}
		return false;

	}

	public Player checkOnline(String splayer) {
		if (Bukkit.getServer().getPlayer(splayer) != null){
			Player target = Bukkit.getServer().getPlayer(splayer);
			return target;
		}
		else{
			return null;
		}
		
	}

}
