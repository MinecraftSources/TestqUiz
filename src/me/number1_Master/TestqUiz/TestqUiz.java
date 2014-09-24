package me.number1_Master.TestqUiz;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Messages;
import me.number1_Master.TestqUiz.Config.Users;
import me.number1_Master.TestqUiz.Listeners.BlockListener;
import me.number1_Master.TestqUiz.Listeners.CorrectListener;
import me.number1_Master.TestqUiz.Listeners.FinishListener;
import me.number1_Master.TestqUiz.Listeners.IncorrectListener;
import me.number1_Master.TestqUiz.Listeners.PlayerListener;
import me.number1_Master.TestqUiz.Listeners.PreprocessListener;
import me.number1_Master.TestqUiz.Utils.Log;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Location;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class TestqUiz extends JavaPlugin
{
	public static TestqUiz p;
	public static File dir;
	
	public static Permission permission = null;
	public static Economy economy = null;
	
	public ArrayList<String> incorrectBypass = new ArrayList<String>();
	public HashMap<String, Integer> incorrectAmount = new HashMap<String, Integer>();
	public HashMap<String,Long> antiSpam = new HashMap<String, Long>();
	public HashMap<String, Long> notPassed = new HashMap<String, Long>();
	public HashMap<String, Long> cheaters = new HashMap<String, Long>();
	public HashMap<Location, String> cheatLocs = new HashMap<Location, String>();
	public HashMap<String, Integer> clearLag = new HashMap<String, Integer>();
	
	@Override
	public void onEnable()
	{
		p = this;
		
		if(getServer().getPluginManager().getPlugin("Vault") != null)
		{
			RegisteredServiceProvider<Permission> permissionPlugin = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
			if(permissionPlugin != null)
			{
				permission = permissionPlugin.getProvider();
				
				RegisteredServiceProvider<Economy> economyPlugin = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
				if(economyPlugin != null) economy = economyPlugin.getProvider();
			}
		}
		if(permission != null && economy != null) Log.i("Vault hooked in for Permissions and Economy!");
		else if(permission != null && economy == null) Log.i("Vault hooked in for Permissions. Economy disabled!");

		dir = getDataFolder();
		Users.reload();
		Messages.reload();
		Config.reload();
		
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new PreprocessListener(), this);
		getServer().getPluginManager().registerEvents(new IncorrectListener(), this);
		getServer().getPluginManager().registerEvents(new CorrectListener(), this);
		getServer().getPluginManager().registerEvents(new FinishListener(), this);
		getCommand("TestqUiz").setExecutor(new TestqUizCommand());	
		Log.i("I am ready to test your players!");
	}
	
	@Override
	public void onDisable() {}
}
