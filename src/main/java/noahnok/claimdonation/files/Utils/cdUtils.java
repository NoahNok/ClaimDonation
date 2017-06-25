package noahnok.claimdonation.files.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import noahnok.claimdonation.files.main;

public class cdUtils {
	private final main plugin;

	public cdUtils(main plugin) {
		this.plugin = plugin; 
	}
	public HashMap<UUID, ArrayList<String>> donation = new HashMap<UUID, ArrayList<String>>();
	public ArrayList<String> getDonations(Player p){
		UUID uuid = p.getUniqueId();
		if (! donation.containsKey(uuid)){
			ArrayList<String> l = new ArrayList<String>();
			return l;
		}
		ArrayList<String> l = donation.get(uuid);
		return l;
	}	public ArrayList<String> getOfflineDonations(OfflinePlayer p){
		UUID uuid = p.getUniqueId();
		if (! donation.containsKey(uuid)){
			ArrayList<String> l = new ArrayList<String>();
			return l;
		}
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
	public void setOfflineDonation(OfflinePlayer p, String ditem){
		UUID uuid = p.getUniqueId();
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
			plugin.getDataConfig().set("players."+uuid.toString()+".commands", donation.get(uuid));
			plugin.saveDataConfig();
		}
	}
	public void loadDonation(){
		if (plugin.getDataConfig().getConfigurationSection("players") == null){
			return;
		}
		for (String key : plugin.getDataConfig().getConfigurationSection("players").getKeys(false)){
			ArrayList<String> list =  (ArrayList<String>) plugin.getDataConfig().getStringList("players."+key+".commands");
			donation.put(UUID.fromString(key), list);
		}
		plugin.getDataConfig().set("players", null);
		plugin.saveDataConfig();
	}
	public ItemStack retrieveCfgStack(String name){

		String iname = ChatColor.translateAlternateColorCodes('&', plugin.getItemsConfig().getString("GUIItems." + name + ".Name"));

		ItemStack item = new ItemStack(Material.getMaterial(plugin.getItemsConfig().getString("GUIItems." + name + ".Material")), 1, (short)plugin.getItemsConfig().getInt("GUIItems." + name + ".ColorID"));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(iname);
		item.setItemMeta(meta);
		return item;
		
	}
	public void loadColors(){
		int count = 1;
		for (String part : plugin.getConfig().getConfigurationSection("colors").getKeys(false)){
			plugin.CGUI.colors.put(count, plugin.getConfig().getString("colors." + part));
			count += 1;
		}
	}
	
}
