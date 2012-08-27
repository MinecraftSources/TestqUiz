package me.number1_Master.TestqUiz.Managers;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class YAMLManager
{	
	public static void loadConfig()
	{
		
	}
	
	public static void loadMessages(String directory)
	{
		File messagesFile = new File(directory, "messages.yml");
		YamlConfiguration messages = new YamlConfiguration();
		try
		{ messages.load(messagesFile); } 
		catch(Exception err) 
		{ err.printStackTrace(); }
		
		messages.options().copyDefaults(true);
		
		try
		{ messages.save(messagesFile); } 
		catch(Exception err) 
		{ err.printStackTrace(); }
	}
}