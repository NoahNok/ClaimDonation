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
						int glassslot = e.getSlot();
						ArrayList<String> commands;
						if (online == true){
							commands = plugin.Cdu.getDonations(target);
						}else{
							commands = plugin.Cdu.getOfflineDonations(op);
						}
						if (slot >= commands.size()){
						    slot = 0;
                        }
						commands.remove(slot);
						
						e.getInventory().setItem(glassslot, plugin.Cdu.retrieveCfgStack("RedGlass"));
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
		if (item == null){
			return true;
		}
		if (item.isSimilar(plugin.Cdu.retrieveCfgStack("RedGlass")) || item.isSimilar(plugin.Cdu.retrieveCfgStack("GreenGlass"))){
			return true;
		}else{
			return false;
		}
	}
	
	@EventHandler
	public void claimItem(InventoryClickEvent e){
		if (plugin.isToggleEnabled("INV_CLAIM") == true){

		if (isDonationInv(e.getInventory())){
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
								int glassslot = e.getSlot();
								ArrayList<String> commands = plugin.Cdu.getDonations(target);
								if (slot >= commands.size()){
									slot = 0;
								}
								String command = commands.get(slot);
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
								commands.remove(slot);
								e.getInventory().setItem(glassslot, plugin.Cdu.retrieveCfgStack("GreenGlass"));
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
