package me.number1_Master.TestqUiz.Config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Utils.Log;
import me.number1_Master.TestqUiz.Utils.Update;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config 
{
	private static FileConfiguration config;
	private static File configFile;
	
	public static String[] itemRewards = {"272 1" , "273 1", "274 1", "275 1", "5 16"};
	
	public static void reload()
	{	
		if(configFile == null) configFile = new File(TestqUiz.dir, "config.yml");
		
		config = YamlConfiguration.loadConfiguration(configFile);
		
		Update.config(configFile);

		config.options().header("PLEASE look at http://bit.ly/TestqUizConfiguration for help!");
		
		config.addDefault("General.Version", "2.0.8");
		config.addDefault("General.Start.Time", 60);
		config.addDefault("General.Start.Kick.Use", true);
		config.addDefault("General.Start.Kick.Command", "kick PLAYERNAME Turn around and go read the rules!");
		config.addDefault("General.Start.Teleport to Spawn", true);
		
		config.addDefault("General.Cheating.Player", true);
		config.addDefault("General.Cheating.Kick.Use", true);
		config.addDefault("General.Cheating.Kick.Command", "kick PLAYERNAME Stop cheating!");
		config.addDefault("General.Cheating.Teleport to Spawn", true);
		
		config.addDefault("General.Teleport Command", "P: spawn");
		
		
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
				
		
		config.addDefault("Correct Answer.Announce", false);
		config.addDefault("Correct Answer.Log", true);
		config.addDefault("Correct Answer.Notify", false);
		
		
		config.addDefault("Finish.Announce", true);
		config.addDefault("Finish.Log", false);
		config.addDefault("Finish.Notify", false);
		config.addDefault("Finish.Permissions.Use", false);
		config.addDefault("Finish.Permissions.Add or Change", true);
		config.addDefault("Finish.Permissions.World", "world");
		config.addDefault("Finish.Permissions.From Group", "default");
		config.addDefault("Finish.Permissions.To Group", "user");
		config.addDefault("Finish.Permissions.Reward.Use Economy", false);
		config.addDefault("Finish.Permissions.Reward.Economy Amount", 20);
		config.addDefault("Finish.Permissions.Reward.Use Items", false);
		config.addDefault("Finish.Permissions.Reward.Items", Arrays.asList(itemRewards));
		
		config.options().copyHeader(true);
		config.options().copyDefaults(true);
		save();
	}
	private static void save()
	{
		if(config == null || configFile == null) return;
		
		try
		{ config.save(configFile); }
		catch(Exception err)
		{ Log.s("Could not save config.yml!"); }
	}
	private static void check()
	{ if(config == null || configFile == null) reload(); }
	public static ConfigurationSection getConfigSection(String path)
	{
		return config.getConfigurationSection(path);
	}
	public static Object get(String path)
	{
		check();
		return config.get(path);
	}
	public static boolean getBoolean(String path)
	{
		check();
		return config.getBoolean(path);
	}
	public static double getDouble(String path)
	{
		check();
		return config.getDouble(path);
	}
	public static int getInt(String path)
	{
		check();
		return config.getInt(path);
	}
	public static String getString(String path)
	{
		check();
		return config.getString(path);
	}
	public static List<String> getStringList(String path)
	{
		check();
		return config.getStringList(path);
	}
	public static void set(String path, Object value)
	{
		check();
		config.set(path, value);
		save();
	}
}
