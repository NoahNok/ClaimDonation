package noahnok.claimdonation.files.Utils;

import java.util.HashMap;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;
import noahnok.claimdonation.files.main;

public class chatUtils {
	private final main pl;

	public chatUtils(main pl) {
		this.pl = pl; 
	}
	public HashMap<String, String> messages = new HashMap<String, String>();
	public void loadMessages(){
		Bukkit.getServer().getLogger().info("[ClaimDonation] Loading Messages...");
		int count = 0;
		for (String key : pl.getConfig().getConfigurationSection("messages").getKeys(false)){
		if (getConfigMsg(key).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("prefix"))) || getConfigMsg(key) == null ||  getConfigMsg(key) == ""){
			Bukkit.getServer().getLogger().warning("[ClaimDonation] ERROR ON MESSAGE: "+key+ " - Message has no content!");
		}else{
			messages.put(key, getConfigMsg(key));
			count += 1;
			}
		}
		Bukkit.getServer().getLogger().info("[ClaimDonation] Loading Messages... COMPLETE - ["+count+"/20]");
	}
	public String getMessage(String msg){
		if (messages.containsKey(msg)){
			String message = messages.get(msg);
			return message;
		}else{
			return (ChatColor.RED + "ERROR NO MESSAGE FOUND!");
		}
	}
		
	public String getConfigMsg(String msg){
		if (pl.getConfig().getString("messages." + msg) == null){
			return null;
		}
		if (pl.getConfig().getString("messages." + msg) == ""){
			return "";
		}
		msg = ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("prefix") + pl.getConfig().getString("messages." + msg));
		return msg;
	}
	

}
