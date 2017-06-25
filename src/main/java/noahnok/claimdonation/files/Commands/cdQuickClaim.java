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
			Player p = (Player )sender;
			if (plugin.toggles.get("INVENTORY") == true){
				p.performCommand("cd inv");
				return true;
			}else {
				p.performCommand("cd claim " + p.getName());
				return true;
			}
		}else{sender.sendMessage("This command can only be run by a player!"); return true;}
		
	}

}
