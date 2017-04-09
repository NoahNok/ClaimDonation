package noahnok.claimdonation.files.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import noahnok.claimdonation.files.main;

public class cdQuickClaim implements CommandExecutor {
	private final main plugin;

	public cdQuickClaim(main plugin) {
		this.plugin = plugin; 
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			Player p = (Player) sender;
			if (sender.hasPermission("cd.claim")){
				if (plugin.Cdu.getDonations(p) == null){
					p.sendMessage(plugin.CU.getMessage("PLAYER_NOITEM"));
						return true;
				}
				plugin.Cdu.giveDonation(p, plugin.Cdu.getDonations(p));
				plugin.CU.getMessage("DONATION_CLAIM");
				return true;
			}else{sender.sendMessage(plugin.CU.getMessage("NO_PERM")); return true;}
		}else{sender.sendMessage("This command can only be run by a player!"); return true;}
		
	}

}
