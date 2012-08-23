package me.number1_Master.TestqUiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import me.number1_Master.TestqUiz.Methods.CorrectAnswerMethod;
import me.number1_Master.TestqUiz.Methods.FinishAnswerMethod;
import me.number1_Master.TestqUiz.Methods.IncorrectAnswerMethod;
import me.number1_Master.TestqUiz.Methods.OtherMethods;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class TestqUizMain extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	public String[] itemRewards = {"272 1" , "273 1", "274 1", "275 1", "5 16"};
	private FileConfiguration config;
	
	public ArrayList<String> incorrectBypass = new ArrayList<String>();
	public HashMap<String, Integer> incorrectAmount = new HashMap<String, Integer>();
	public HashMap<String,Long> incorrectAntiSpam = new HashMap<String, Long>();
	public HashMap<String, Long> correctAntiSpam = new HashMap<String, Long>();
	public HashMap<String, Long> finishAntiSpam = new HashMap<String, Long>();
	public HashMap<String, Long> takeTest = new HashMap<String, Long>();
	public HashMap<String, Long> cheating = new HashMap<String, Long>();
	public ArrayList<String> cheaters = new ArrayList<String>();
	
	TestqUizMain plugin;
	TestqUizCommand command = new TestqUizCommand(this);
	TestqUizListener listener = new TestqUizListener(this);	
	CorrectAnswerMethod correct = new CorrectAnswerMethod(this);
	IncorrectAnswerMethod incorrect = new IncorrectAnswerMethod(this);
	FinishAnswerMethod finish = new FinishAnswerMethod(this);
	OtherMethods methods = new OtherMethods(this);
	
	public boolean usingVault;
	public Permission permission = null;
	public Economy economy = null;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		PluginDescriptionFile pdf = getDescription();
		
		getServer().getPluginManager().registerEvents(listener, this);
		getCommand("TestqUiz").setExecutor(command);
				
		config = getConfig();
		getConfig().options().header("Check out http://bit.ly/MIJ4uv for help!");
		generalConfigurations(config);
		incorrectConfigurations(config);
		correctConfigurations(config);
		finishConfigurations(config);
		messageConfigurations(config);
		getConfig().options().copyDefaults(true);
		saveConfig();
		log.info("[" + pdf.getName() + "] The config.yml has loaded!");
			
		vaultCheck(pdf);
		log.info("[" + pdf.getName() + "] Vault check complete!");
		
		log.info("[" + pdf.getName() + "] I am ready to test your players!");
	}
	
	@Override
	public void onDisable(){}
	
	//onEnable Methods
	// - Configurations
	public void generalConfigurations(FileConfiguration config)
	{
		config.addDefault("General.Start.Time", 60);
		config.addDefault("General.Start.Kick.Use", true);
		config.addDefault("General.Start.Kick.Command", "kick PLAYERNAME Turn around and go read the rules!");
		config.addDefault("General.Start.Teleport to Spawn", true);
		
		config.addDefault("General.Cheating.Player", true);
		config.addDefault("General.Cheating.Kick.Use", true);
		config.addDefault("General.Cheating.Kick.Command", "kick PLAYERNAME Stop cheating!");
		config.addDefault("General.Cheating.Teleport to Spawn", true);
		
		config.addDefault("General.Logout.Clear", 120);
	}
	public void incorrectConfigurations(FileConfiguration config)
	{		
		config.addDefault("Incorrect Answer.Announce", false);
		config.addDefault("Incorrect Answer.Log", true);
		config.addDefault("Incorrect Answer.Notify", true);
		
		config.addDefault("Incorrect Answer.Kicking.Use", true);
		config.addDefault("Incorrect Answer.Kicking.Amount", 3);
		config.addDefault("Incorrect Answer.Kicking.Reset", false);
		config.addDefault("Incorrect Answer.Kicking.Announce", false);
		config.addDefault("Incorrect Answer.Kicking.Log", true);
		config.addDefault("Incorrect Answer.Kicking.Notify", true);
		config.addDefault("Incorrect Answer.Kicking.Command", "kick PLAYERNAME You got too many questions wrong!");
		
		config.addDefault("Incorrect Answer.Banning.Use", false);
		config.addDefault("Incorrect Answer.Banning.Base Off", true);
		config.addDefault("Incorrect Answer.Banning.Amount", 3);
		config.addDefault("Incorrect Answer.Banning.Announce", false);
		config.addDefault("Incorrect Answer.Banning.Notify", true);
		config.addDefault("Incorrect Answer.Banning.Log", true);
		config.addDefault("Incorrect Answer.Banning.Command", "ban PLAYERNAME You got banned for failing to read the rules!");
	}
	public void correctConfigurations(FileConfiguration config)
	{
		config.addDefault("Correct Answer.Announce", false);
		config.addDefault("Correct Answer.Log", true);
		config.addDefault("Correct Answer.Notify", false);
	}
	public void finishConfigurations(FileConfiguration config)
	{
		config.addDefault("Finish.Announce", true);
		config.addDefault("Finish.Log", false);
		config.addDefault("Finish.Notify", false);
		config.addDefault("Finish.Permissions.Use", false);
		config.addDefault("Finish.Permissions.Add or Change", true);
		config.addDefault("Finish.Permissions.From Group", "default");
		config.addDefault("Finish.Permissions.To Group", "user");
		config.addDefault("Finish.Permissions.Reward.Use Economy", false);
		config.addDefault("Finish.Permissions.Reward.Economy Amount", 20);
		config.addDefault("Finish.Permissions.Reward.Use Items", false);
		config.addDefault("Finish.Permissions.Reward.Items", Arrays.asList(itemRewards));
	}
	public void messageConfigurations(FileConfiguration config)
	{
		config.addDefault("Messages.To-Player.Incorrect", "That is INCORRECT!");
		config.addDefault("Messages.To-Player.Correct", "That is CORRECT!");
		config.addDefault("Messages.To-Player.Finish", "CONGRADULATIONS!!! You passed the test!");
		config.addDefault("Messages.To-Player.Group Change", "Your group has now been changed to NEWGROUP !");
		config.addDefault("Messages.To-Player.Economy Reward", "For passing the test, you have earned NEWAMOUNT !");
		config.addDefault("Messages.To-Player.Item Reward", "For passing the test, you earned some items!!!");
		
		config.addDefault("Messages.Announce.Incorrect", "PLAYERNAME got a wrong answer! HA!");
		config.addDefault("Messages.Announce.Kick", "PLAYERNAME got kicked from the test!");
		config.addDefault("Messages.Announce.Ban", "PLAYERNAME got banned from the test!");
		config.addDefault("Messages.Announce.Correct", "PLAYERNAME got a correct answer! YAY!");
		config.addDefault("Messages.Announce.Finish", "PLAYERNAME finished the test. CONGRADULATIONS!");
		
		config.addDefault("Messages.Notify.Incorrect", "PLAYERNAME got a wrong answer!");
		config.addDefault("Messages.Notify.Kick", "PLAYERNAME just got kicked from testing!");
		config.addDefault("Messages.Notify.Ban", "PLAYERNAME just got banned for a bad test!");
		config.addDefault("Messages.Notify.Correct", "PLAYERNAME got a correct answer!");
		config.addDefault("Messages.Notify.Finish", "PLAYERNAME finished the test!");
		
		config.addDefault("Messages.Log.Incorrect", "PLAYERNAME got a question incorrect!");
		config.addDefault("Messages.Log.Kick", "PLAYERNAME got kicked!");
		config.addDefault("Messages.Log.Ban", "PLAYERNAME got banned from the test!");
		config.addDefault("Messages.Log.Correct", "PLAYERNAME got a question correct!");
		config.addDefault("Messages.Log.Finish", "PLAYERNAME finished the test!");
	}
	// - Vault
	public void vaultCheck(PluginDescriptionFile pdf)
	{
		if(getServer().getPluginManager().getPlugin("Vault") != null)
		{
			log.info("[" + pdf.getName() + "] Vault detected!");
			
			RegisteredServiceProvider<Permission> permissionPlugin = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
			if(permissionPlugin != null)
			{
				permission = permissionPlugin.getProvider();
				log.info("[" + pdf.getName() + "] Vault Permissions settings enabled!");
				
				RegisteredServiceProvider<Economy> economyPlugin = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
				if(economyPlugin != null)
				{
					economy = economyPlugin.getProvider();
					log.info("[" + pdf.getName() + "] Vault Economy settings enabled!");
				}
				else
				{
					log.info("[" + pdf.getName() + "] Vault Economy settings disabled!");	
				}
			}
			else
			{
				log.info("[" + pdf.getName() + "] Vault Permissions and Economy settings disabled!");	
			}
			
			usingVault = true;
		}
		else
		{
			usingVault = false;
			log.info("[" + pdf.getName() + "] All Vault settings are disabled!!!");		
		}
	}
	
	//Commands
	public void version(CommandSender sender, String prefix)
	{
		PluginDescriptionFile pdf = getDescription();
		sender.sendMessage(prefix + "You are running version " + ChatColor.YELLOW + pdf.getVersion() + ChatColor.GOLD + "!");
	}
}
