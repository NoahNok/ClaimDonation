package noahnok.claimdonation.files;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class cdGUIEvents implements Listener {
	private final main plugin;

	public cdGUIEvents(main plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void cancleInvClick(InventoryClickEvent e){
		
		String name = e.getInventory().getName();
		if (ChatColor.stripColor(name).contains("Donation Claim")){
			String[] n = name.split(" ");
			String invname = n[0] + " " + n[1];
			
			if (ChatColor.stripColor(invname).equals("Donation Claim")){
				e.setCancelled(true);
			}else{
				e.setCancelled(false);
			}
		}else{
			e.setCancelled(false);
		}
	}
    public static boolean hasClickedTop(InventoryClickEvent event) {
        return event.getRawSlot() == event.getSlot();
    }
	
	@EventHandler
	public void removeItem(InventoryClickEvent e){
	String name = e.getInventory().getName();
	String[] n = name.split(" ");
	
	
	if (ChatColor.stripColor(name).contains("Donation Claim")){
		String invname = n[0] + " " + n[1];
	if (ChatColor.stripColor(invname).equals("Donation Claim")){
		//returns true for top inv.
		if (hasClickedTop(e) == true){	
		Player target = Bukkit.getServer().getPlayer(ChatColor.stripColor(n[3]));
	
		Player p = (Player) e.getWhoClicked();
		if (p.hasPermission("cd.gui.remove")){
			if (e.getClick().equals(ClickType.RIGHT)){
				ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14); ItemMeta meta = glass.getItemMeta(); meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lREMOVED")); glass.setItemMeta(meta);
				if (!e.getCurrentItem().isSimilar(glass)){
					if (!(e.getCurrentItem().getType().equals(Material.AIR))){
					
		
						int slot = e.getSlot();
						ArrayList<String> commands = plugin.Cdu.getDonations(target);
						commands.remove(slot);
				
						e.getInventory().setItem(slot, glass);
						p.updateInventory();
					}
				}
			}
		}
	}
	}else{
		e.setCancelled(false);
	}
	}else{e.setCancelled(false);}
	
	}

}
