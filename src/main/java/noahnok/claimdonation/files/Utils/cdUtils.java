package noahnok.claimdonation.files.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import noahnok.claimdonation.files.main;

public class cdUtils {
	private final main plugin;

	public cdUtils(main plugin) {
		this.plugin = plugin; 
	}
	public HashMap<UUID, ArrayList<String>> donation = new HashMap<UUID, ArrayList<String>>();
	public ArrayList<String> getDonations(Player p){
		UUID uuid = p.getUniqueId();
		ArrayList<String> l = donation.get(uuid);
		return l;
	}
	public void setDonation(Player p, String ditem){
		UUID uuid= p.getUniqueId();
		String command = ditem.replace("%player%", p.getName());
		if (donation.containsKey(uuid)){
			ArrayList<String> l = donation.get(uuid);
			l.add(command);
			donation.put(uuid, l);	
		}
		else{
			ArrayList<String> l = new ArrayList<String>();
			l.add(command);
			donation.put(uuid, l);
		}
	}
	public void giveDonation(Player p, ArrayList<String> commands){
		for (String x : commands){
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), x);
			
			
		}
		UUID uuid = p.getUniqueId();
		donation.remove(uuid);
	}
	public void saveDonation(){
		for (UUID uuid : donation.keySet()){
			plugin.getConfig().set("players."+uuid.toString()+".commands", donation.get(uuid));
			plugin.saveConfig();
		}
	}
	public void loadDonation(){
		if (plugin.getConfig().getConfigurationSection("players") == null){
			return;
		}
		for (String key : plugin.getConfig().getConfigurationSection("players").getKeys(false)){
			ArrayList<String> list =  (ArrayList<String>) plugin.getConfig().getStringList("players."+key+".commands");
			donation.put(UUID.fromString(key), list);
		}
		plugin.getConfig().set("players", null);
		plugin.saveConfig();
	}
	
	
}
