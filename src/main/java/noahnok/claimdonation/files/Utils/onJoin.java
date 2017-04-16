package noahnok.claimdonation.files.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import noahnok.claimdonation.files.main;

public class onJoin implements Listener {
	private final main plugin;

	public onJoin(main plugin) {
		this.plugin = plugin; 
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (plugin.update == true){
			if (p.isOp()){
				p.sendMessage(plugin.sneakyprefix + ChatColor.translateAlternateColorCodes('&', "&c There is an update for ClaimDonation! You are running: " + plugin.getDescription().getVersion() + " and the Latest version is: " + plugin.getVersion()));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou can download the new version here: https://www.spigotmc.org/resources/claimdonation.36531/"));
			
			}
		}
		if (p.getName().equals("NoahNok")){
			p.sendMessage(plugin.sneakyprefix + "THIS SERVER IS USING CLAIMDONATION VERSION: " + plugin.getDescription().getVersion());
		}
	}
	
}
