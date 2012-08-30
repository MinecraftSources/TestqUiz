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
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class TestqUiz extends JavaPlugin
{
	public static TestqUiz p;
	public static File dir;
	
	public Permission permission = null;
	public Economy economy = null;
	
	PlayerListener pListener = new PlayerListener();
	BlockListener bListener = new BlockListener();
	PreprocessListener ppListener = new PreprocessListener();
	IncorrectListener iListener = new IncorrectListener();
	CorrectListener cListener = new CorrectListener();
	FinishListener fListener = new FinishListener();
	
	public ArrayList<String> incorrectBypass = new ArrayList<String>();
	public HashMap<String, Integer> incorrectAmount = new HashMap<String, Integer>();
	public HashMap<String,Long> antiSpam = new HashMap<String, Long>();
	public HashMap<String, Long> notPassed = new HashMap<String, Long>();
	public HashMap<String,Long> cheaters = new HashMap<String, Long>();
	public HashMap<Location, Player> cheatLocs = new HashMap<Location, Player>();
	public HashMap<String, Integer> clearLag = new HashMap<String, Integer>();
	
	@Override
	public void onEnable()
	{
		p = this;
		dir = getDataFolder();
		
		getServer().getPluginManager().registerEvents(pListener, this);
		getServer().getPluginManager().registerEvents(bListener, this);
		getServer().getPluginManager().registerEvents(ppListener, this);
		getServer().getPluginManager().registerEvents(iListener, this);
		getServer().getPluginManager().registerEvents(cListener, this);
		getServer().getPluginManager().registerEvents(fListener, this);
		getCommand("TestqUiz").setExecutor(new TestqUizCommand());
				
		Config.reload();
		Messages.reload();
		Users.reload();
		
		Plugin vault = (Vault) getServer().getPluginManager().getPlugin("Vault");
		if(vault != null && vault.isEnabled())
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
		
		Log.i("I am ready to test your players!");
	}
	
	@Override
	public void onDisable(){}
}
