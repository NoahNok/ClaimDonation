package noahnok.claimdonation.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import noahnok.claimdonation.files.Commands.cdCommands;
import noahnok.claimdonation.files.Commands.cdQuickAdd;
import noahnok.claimdonation.files.Commands.cdQuickClaim;
import noahnok.claimdonation.files.Utils.Metrics;
import noahnok.claimdonation.files.Utils.cdUtils;
import noahnok.claimdonation.files.Utils.chatUtils;
import noahnok.claimdonation.files.Utils.onJoin;

public class main extends JavaPlugin{
	
	public String sneakyprefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
	public String getVersion(){
		try {
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + "36531")
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(
                    con.getInputStream())).readLine();
            if (version.length() <= 7) {
            	
                return version;
            }
        } catch (Exception ex) {
            this.getLogger().info("Failed to check for a update on spigot.");
        }
		return null;
		
	}
	
	public void onEnable(){
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }
	    if (!getVersion().equals(this.getDescription().getVersion())){
	    	this.getLogger().warning("AN UPDATE IS AVAILABLE: " + getVersion() + " YOUR VERSION: " + this.getDescription().getVersion());
	    }
	    
	    
	    
	    
		saveDefaultConfig();
		Cdu.loadDonation();
		this.getCommand("claimdonation").setExecutor(new cdCommands(this));
		this.getCommand("claim").setExecutor(new cdQuickClaim(this));
		this.getCommand("add").setExecutor(new cdQuickAdd(this));
		CU.loadMessages();
		getServer().getPluginManager().registerEvents(new cdGUIEvents(this), this);
		getServer().getPluginManager().registerEvents(new onJoin(this), this);
		
		
		
	}
	public void onDisable(){
		Cdu.saveDonation();
	}
	public cdUtils Cdu = new cdUtils(this);
	public cdCommands Cdc = new cdCommands(this);
	public chatUtils CU = new chatUtils(this);
	public cdGUI CGUI = new cdGUI(this);
	
	
	
	
}

	
