package xBank;

import java.util.logging.Logger;

import xBank.commands.BankAdmin;
import xBank.commands.bankCommand;
import xBank.commands.oldHelp;
import xBank.listeners.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.iConomy.iConomy;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Core extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft");
	private PermissionManager pex = null;
	public iConomy iConomy = null;
	public WorldGuardPlugin wg = null;
	

	
	@Override
	public void onEnable(){
		this.wg  = getWorldGuard();
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			pex = PermissionsEx.getPermissionManager();
		}
		
		new server(this);
		this.getCommand("bankcp").setExecutor(new BankAdmin(this));
		this.getCommand("hbank").setExecutor(new oldHelp(this));
		this.getCommand("bank").setExecutor(new bankCommand(this));

		log.info("[Bank] enabled!");
	}
	
	@Override
	public void onDisable(){
				

	}
	
	public boolean checkPerms(Player player, String permission){
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			pex = PermissionsEx.getPermissionManager();
		if(pex.has(player, permission)){
			return true;
		}
		}
		return false;
		
	}
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
	
	public String getBankAccount(Player player){
		if(hasBankAccount(player)){
			return getConfig().getString("Players." + player.getName() + ".Account");
		}
		return null;
	}

	public boolean hasBankAccount(Player player) {
		if(getConfig().contains("Players." + player.getName())){
			if(!(getConfig().getString("Players." + player.getName() + ".Bank") == null)){
				return true;
			}
		}
		return false;
	}

}
