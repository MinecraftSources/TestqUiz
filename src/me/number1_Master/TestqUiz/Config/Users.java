package me.number1_Master.TestqUiz.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Utils.Log;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Users
{
	private static FileConfiguration users;
	private static File usersFile;
	
	public static void reload()
	{
		if(usersFile == null) usersFile = new File(TestqUiz.dir, "users.yml");
		
		users = YamlConfiguration.loadConfiguration(usersFile);
		
		users.options().header("Check out http://bit.ly/TestqUizConfiguration for help!\n#DO NOT TOUCH THIS UNLESS YOU KNOW WHAT YOU ARE DOING!");
		
		users.addDefault("Passed", new ArrayList<String>());
		
		users.options().copyDefaults(true);
		save();
	}
	private static void save()
	{
		if(users == null || usersFile == null) return;
		
		try
		{ users.save(usersFile); }
		catch(Exception err)
		{ Log.s("Could not save users.yml!"); }
	}
	private static void check()
	{ if(users == null || usersFile == null) reload(); }
	public static List<String> getStringList(String path)
	{
		check();
		return users.getStringList(path);
	}
	public static void addToStringList(String path, String value)
	{
		check();	
		ArrayList<String> passed = (ArrayList<String>) getStringList(path);
		if(passed.contains(value)) return;
		passed.add(value);
		users.set(path, passed);
		save();
	}
}
