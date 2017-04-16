package noahnok.claimdonation.files;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import noahnok.claimdonation.files.Commands.cdCommands;
import noahnok.claimdonation.files.Commands.cdQuickAdd;
import noahnok.claimdonation.files.Commands.cdQuickClaim;
import noahnok.claimdonation.files.Utils.cdUtils;
import noahnok.claimdonation.files.Utils.chatUtils;
import noahnok.claimdonation.files.Utils.onJoin;

public class main extends JavaPlugin{
	
	public String sneakyprefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
	public HashMap<String, Boolean> toggles = new HashMap<String, Boolean>();
	public boolean update = false;
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
	
	    if (!getVersion().equals(this.getDescription().getVersion())){
	    	this.getLogger().warning("AN UPDATE IS AVAILABLE: " + getVersion() + " YOUR VERSION: " + this.getDescription().getVersion());
	    	update = true;
	    }
	    
	    
	    
	    
		saveDefaultConfig();
		
		this.getCommand("claimdonation").setExecutor(new cdCommands(this));
		this.getCommand("claim").setExecutor(new cdQuickClaim(this));
		this.getCommand("add").setExecutor(new cdQuickAdd(this));
		
		getServer().getPluginManager().registerEvents(new cdGUIEvents(this), this);
		getServer().getPluginManager().registerEvents(new onJoin(this), this);

        		
        Cdu.loadDonation();
        CU.loadMessages();
        loadToggles();
        getCheckDownloadURL();

		
		
	}
	public void onDisable(){
		Cdu.saveDonation();
	}
	public cdUtils Cdu = new cdUtils(this);
	public cdCommands Cdc = new cdCommands(this);
	public chatUtils CU = new chatUtils(this);
	public cdGUI CGUI = new cdGUI(this);
	private String versionLink;
	
	public void loadToggles(){
		for (String key : getConfig().getConfigurationSection("toggles").getKeys(false)){
			if (getConfig().getBoolean(key) == false || getConfig().getBoolean(key) == true){
				toggles.put(key, getConfig().getBoolean("toggles." + key));
			}else{
				toggles.put(key, false);
			}
		}
		System.out.println(toggles.toString());
	}
	public boolean isToggleEnabled(String toggle){
		return toggles.get(toggle);
	}
	
	private Boolean getCheckDownloadURL() {
        try {
            URL url = new URL("https://www.spigotmc.org/resources/claimdonation.36531/");
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/1.0");
            conn.setConnectTimeout(5000);
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            Boolean getLinkNow = false;
            while ((inputLine = in.readLine()) != null) {
                if (getLinkNow) {
                    String re1 = ".*?"; // Non-greedy match on filler
                    String re2 = "(\".*?\")"; // Double Quote String 1

                    Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(inputLine);
                    if (m.find()) {
                        String downloadUrl = "http://www.spigotmc.org/" + m.group(1).toString().replace("\"", "");
                        this.versionLink = downloadUrl;
                    }

                    getLinkNow = false;
                }

                if (inputLine.contains("<label class=\"downloadButton \">")) {
                    getLinkNow = true;
                }
            }
            in.close();
            return true;
        } catch (Exception e) {
            getServer().getLogger().warning("Something went wrong while downloading an update.");
            getServer().getLogger().info("Please check the plugin's page to see if there are any updates available.");
            getServer().getLogger().fine(e.getMessage());
            
            return false;
        }
    }
	
}

	
