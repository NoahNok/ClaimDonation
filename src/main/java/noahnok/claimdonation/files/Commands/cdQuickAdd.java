package noahnok.claimdonation.files.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import noahnok.claimdonation.files.main;

public class cdQuickAdd implements CommandExecutor {
	private final main plugin;

	public cdQuickAdd(main plugin) {
		this.plugin = plugin; 
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("cd.add")){
			if (args.length == 0){
				sender.sendMessage(plugin.CU.getMessage("QUICK_ADD_COMMAND"));
				return true;
			}
			Player target = plugin.Cdc.checkOnline(args[0]);
			if (target == null){
				sender.sendMessage(plugin.CU.getMessage("PLAYER_OFFLINE").replace("%player%", args[0]));
				return true;
			}
			else{
				if (args.length == 1){
					sender.sendMessage(plugin.CU.getMessage("ADD_COMMAND_NO_COMMAND"));
					return true;
				}
				String command = "";
				for (int i = 1; i < args.length; i++) {
                    command += args[i] + " ";
                }
				plugin.Cdu.setDonation(target, command);
				if (sender instanceof Player){
					sender.sendMessage(plugin.CU.getMessage("DONATION_ITEM_ADDED"));
				}
				return true;
			}
		}else{sender.sendMessage(plugin.CU.getMessage("NO_PERM")); return true;}
		
	}

}
