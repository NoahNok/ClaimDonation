package noahnok.claimdonation.files;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class cdGUIEvents implements Listener {
	private final main plugin;

	public cdGUIEvents(main plugin) {
		this.plugin = plugin; 
	}

	private final boolean isDonationInv(Inventory inv){
		String wholeName = inv.getName();
		if (ChatColor.stripColor(wholeName).contains("Donation Claim")){
			String[] n = wholeName.split(" ");
			String invname = n[0] + " " + n[1];
			if (ChatColor.stripColor(invname).equals("Donation Claim")){
				return true;
			}else{
			return false;
			}
		}else{
			return false;
		}
	}
	@EventHandler
	public void cancleInvClick(InventoryClickEvent e){
		if (isDonationInv(e.getInventory())){
			System.out.println("CD DEBUG 1");
			e.setCancelled(true);
		}
		
	
	}
    public static boolean hasClickedTop(InventoryClickEvent event) {
        return event.getRawSlot() == event.getSlot();
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void removeItem(InventoryClickEvent e){
	
		if (plugin.isToggleEnabled("INV_REMOVE") == true){
		if (isDonationInv(e.getInventory())){
			System.out.println("CD DEBUG 2");
		//returns true for top inv.
		if (hasClickedTop(e) == true){
		String[] splt = e.getInventory().getName().split(" ");
		Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(splt[3]));
		OfflinePlayer op;
		
		boolean online = true;
		if (target == null){
			op = Bukkit.getOfflinePlayer(ChatColor.stripColor(splt[3]));	
			online = false;
		}else{
			op = null;
		}
	
		Player p = (Player) e.getWhoClicked();
		if (p.hasPermission("cd.gui.remove")){
			if (e.getClick().equals(ClickType.RIGHT)){

				if (isGlass(e.getCurrentItem()) == false){
					if (!(e.getCurrentItem().getType().equals(Material.AIR))){
					
		
						int slot = e.getSlot();
						ArrayList<String> commands;
						if (online == true){
							commands = plugin.Cdu.getDonations(target);
						}else{
							commands = plugin.Cdu.getOfflineDonations(op);
						}
						commands.remove(slot);
						ItemStack rglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14); ItemMeta rmeta = rglass.getItemMeta(); rmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lREMOVED")); rglass.setItemMeta(rmeta);
						e.getInventory().setItem(slot, rglass);
						p.updateInventory();
					}
				}
			}
		}
	}
	
	}
	
	}
	}
	
	private boolean isGlass(ItemStack item){
		ItemStack gglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5); ItemMeta meta = gglass.getItemMeta(); meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lCLAIMED")); gglass.setItemMeta(meta);
		ItemStack rglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14); ItemMeta rmeta = rglass.getItemMeta(); rmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lREMOVED")); rglass.setItemMeta(rmeta);
		if (item == null){
			return true;
		}
		if (item.isSimilar(gglass) || item.isSimilar(rglass)){
			return true;
		}else{
			return false;
		}
	}
	
	@EventHandler
	public void claimItem(InventoryClickEvent e){
		if (plugin.isToggleEnabled("INV_CLAIM") == true){

		if (isDonationInv(e.getInventory())){
			System.out.println("CD DEBUG 3");
			if (hasClickedTop(e) == true){
			Player p = (Player) e.getWhoClicked();
			String[] splt = e.getInventory().getName().split(" ");
			Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(splt[3]));

			if (p == target){
				if (p.hasPermission("cd.claim")){
					if (e.getClick().equals(ClickType.LEFT)){
						if (isGlass(e.getCurrentItem()) == false){
							if (!(e.getCurrentItem().getType().equals(Material.AIR))){
								int slot = e.getSlot();
								ArrayList<String> commands = plugin.Cdu.getDonations(target);
								String command = commands.get(slot);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
								commands.remove(slot);
								ItemStack gglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5); ItemMeta meta = gglass.getItemMeta(); meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lCLAIMED")); gglass.setItemMeta(meta);
								e.getInventory().setItem(slot, gglass);
								p.updateInventory();
							}else{e.setCancelled(true);}
						}
					}
				}
			}
		}
	}
		
	}
	}

}
