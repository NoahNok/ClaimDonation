package noahnok.claimdonation.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
	boolean loggerMsg = true;
	int time;
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
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
	
	    if (!getVersion().equals(this.getDescription().getVersion())){
	    	this.getLogger().warning("AN UPDATE IS AVAILABLE: " + getVersion() + " YOUR VERSION: " + this.getDescription().getVersion());
	    	update = true;
	    }
	    
	    
	    createFiles();
	    
		saveDefaultConfig();
		
		this.getCommand("claimdonation").setExecutor(new cdCommands(this));
		this.getCommand("claim").setExecutor(new cdQuickClaim(this));
		this.getCommand("add").setExecutor(new cdQuickAdd(this));

		time = getConfig().getInt("autoSaveTimeInSeconds");

		getServer().getPluginManager().registerEvents(new cdGUIEvents(this), this);
		getServer().getPluginManager().registerEvents(new onJoin(this), this);

		if (getConfig().getBoolean("firstRun") == true) {
            reloadConfig();
            getConfig().set("firstRun", false);
            saveConfig();
        }
        Cdu.loadDonation();
        CU.loadMessages();
        loadToggles();
        Cdu.loadColors();


    	Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
              Cdu.saveDonation();
              if (loggerMsg == true) {
				  getLogger().info("Saved Donations to file!");
			  }
             
            }
        }, 0L, 20L * time);
        
        
		
		
	}
	public void onDisable(){
		Cdu.saveDonation();
	}
	public cdUtils Cdu = new cdUtils(this);
	public cdCommands Cdc = new cdCommands(this);
	public chatUtils CU = new chatUtils(this);
	public cdGUI CGUI = new cdGUI(this);
	public cdGUIEvents CGUIe = new cdGUIEvents(this);
	
	private String versionLink;
	
	public void loadToggles(){
        time = getConfig().getInt("autoSaveTimeInSeconds");
		for (String key : getConfig().getConfigurationSection("toggles").getKeys(false)){
			if (getConfig().getBoolean(key) == false || getConfig().getBoolean(key) == true){
				toggles.put(key, getConfig().getBoolean("toggles." + key));
			}else{
				toggles.put(key, false);
			}
		}
		loggerMsg = getConfig().getBoolean("toggles.LOGGER_SAVE_MSG");

	}
	public boolean isToggleEnabled(String toggle){
		return toggles.get(toggle);
	}
	
    private File dataf, itemsf;
    private FileConfiguration data, items;



    public FileConfiguration getItemsConfig() {
        return this.items;
    }    
    public FileConfiguration getDataConfig() {
        return this.data;
    }
    public void reloadItemsConfig() {
        try {
            try {
				items.load(itemsf);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
        }catch (IOException e) {
            e.printStackTrace();
        }
    }    
    public void reloadDataConfig() {
        try {
            try {
				data.load(dataf);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
        }catch (IOException e) {
            e.printStackTrace();
        }
    }  
    public void saveItemsConfig(){
    	try {
			items.save(itemsf);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }    
    public void saveDataConfig(){
    	try {
			data.save(dataf);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    private void createFiles() {

        dataf = new File(getDataFolder(), "data.yml");
        itemsf = new File(getDataFolder(), "items.yml");

        if (!dataf.exists()) {
            dataf.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        if (!itemsf.exists()) {
            itemsf.getParentFile().mkdirs();
            saveResource("items.yml", false);
         }

        data = new YamlConfiguration();
        items = new YamlConfiguration();
        try {
            try {
				data.load(dataf);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
            try {
				items.load(itemsf);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	

	
}

	
