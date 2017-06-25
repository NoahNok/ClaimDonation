package noahnok.claimdonation.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class cdGUI {
	private final main plugin;

	public cdGUI(main plugin) {
		this.plugin = plugin; 
	}
	
	public HashMap<Integer, String> colors = new HashMap<Integer, String>();
	
	public void loadPlayerGUI(UUID uuid){
		Player p = Bukkit.getServer().getPlayer(uuid);
		Inventory pInv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', colors.get(1) + "Donation " + colors.get(2) + "Claim " + colors.get(3) + "- " +  colors.get(4) + p.getName()));
		
		if (!plugin.Cdu.donation.containsKey(uuid)){
			
			p.openInventory(pInv);
			return;
		}
		if (plugin.Cdu.donation.get(uuid).isEmpty()){
			p.openInventory(pInv);
		}else{
			int slot = 0;
			for (String cmd : plugin.Cdu.donation.get(uuid)){
			pInv.setItem(slot, createItemStack(cmd));
			slot ++;
		}
		p.openInventory(pInv);
		}
	}
	public void loadOtherPlayerGUI(UUID target, Player sender){
		Player p = Bukkit.getPlayer(target);
		String pname = "";
		if (p == null){
			pname = Bukkit.getOfflinePlayer(target).getName();
		}else{
			pname = p.getName();
		}
		Inventory pInv = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Donation Claim - " + ChatColor.translateAlternateColorCodes('&', "&f&l" + pname));
		
		if (!plugin.Cdu.donation.containsKey(target)){
			
			sender.openInventory(pInv);
			return;
		}
		if (plugin.Cdu.donation.get(target).isEmpty()){
			sender.openInventory(pInv);
		}else{
            int slot = 0;
			for (String cmd : plugin.Cdu.donation.get(target)){
                pInv.setItem(slot, createItemStack(cmd));
                slot ++;
		}
		sender.openInventory(pInv);
		}
	}
	
	public ItemStack createItemStack(String dcommand){
		ItemStack item;
		String[] split = dcommand.split(" ");
		if (split[0].equals("give")){
			String[] bi = dcommand.split(" ");
			int amount = Integer.parseInt(bi[3]);
			String samount = amount + "";
			amount = 1;
			
			item = new ItemStack(Material.getMaterial(bi[2].toUpperCase()), amount);
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b" + dcommand));
			ItemMeta m = item.getItemMeta();
			m.setLore(lore);
			m.setDisplayName(ChatColor.GOLD + (bi[2].replace("_", " ") + " x"+samount));
			item.setItemMeta(m);
			return item;
		}else{
			item = new ItemStack(Material.PAPER, 1);
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(ChatColor.BOLD + "Command");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&b" + dcommand));
			m.setLore(lore);
			item.setItemMeta(m);
		return item;
		}
	}

}
