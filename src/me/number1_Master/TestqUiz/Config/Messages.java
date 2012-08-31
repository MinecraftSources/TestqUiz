package me.number1_Master.TestqUiz.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Utils.Log;


public class Messages
{	
	private static FileConfiguration messages;
	private static File messagesFile;
	
	public static void reload()
	{
		if(messagesFile == null) messagesFile = new File(TestqUiz.dir, "messages.yml");
		
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		
		messages.options().header("Check out http://bit.ly/TestqUizConfiguration for help!");
		
		messages.addDefault("Messages.To-Player.Incorrect", "That is INCORRECT!");
		messages.addDefault("Messages.To-Player.Correct", "That is CORRECT!");
		messages.addDefault("Messages.To-Player.Finish", "CONGRADULATIONS!!! You passed the test!");
		messages.addDefault("Messages.To-Player.Group Change", "Your group has now been changed to NEWGROUP !");
		messages.addDefault("Messages.To-Player.Economy Reward", "For passing the test, you have earned NEWAMOUNT !");
		messages.addDefault("Messages.To-Player.Item Reward", "For passing the test, you earned some items!!!");
		
		messages.addDefault("Messages.Announce.Incorrect", "PLAYERNAME got a wrong answer! HA!");
		messages.addDefault("Messages.Announce.Kick", "PLAYERNAME got kicked from the test!");
		messages.addDefault("Messages.Announce.Ban", "PLAYERNAME got banned from the test!");
		messages.addDefault("Messages.Announce.Correct", "PLAYERNAME got a correct answer! YAY!");
		messages.addDefault("Messages.Announce.Finish", "PLAYERNAME finished the test. CONGRADULATIONS!");
		
		messages.addDefault("Messages.Notify.Incorrect", "PLAYERNAME got a wrong answer!");
		messages.addDefault("Messages.Notify.Kick", "PLAYERNAME just got kicked from testing!");
		messages.addDefault("Messages.Notify.Ban", "PLAYERNAME just got banned for a bad test!");
		messages.addDefault("Messages.Notify.Correct", "PLAYERNAME got a correct answer!");
		messages.addDefault("Messages.Notify.Finish", "PLAYERNAME finished the test!");
		
		messages.addDefault("Messages.Log.Incorrect", "PLAYERNAME got a question incorrect!");
		messages.addDefault("Messages.Log.Kick", "PLAYERNAME got kicked!");
		messages.addDefault("Messages.Log.Ban", "PLAYERNAME got banned from the test!");
		messages.addDefault("Messages.Log.Correct", "PLAYERNAME got a question correct!");
		messages.addDefault("Messages.Log.Finish", "PLAYERNAME finished the test!");
		
		messages.options().copyDefaults(true);
		save();
	}
	private static void save()
	{
		if(messages == null || messagesFile == null) return;
		
		try
		{ messages.save(messagesFile); }
		catch(Exception err)
		{ Log.s("Could not save messages.yml!!!"); }
	}
	public static String getString(String path)
	{
		if(messages == null || messagesFile == null) reload();
		return messages.getString(path);
	}
	public static void set(String path, Object value)
	{
		if(messages == null || messagesFile == null) reload();
		messages.set(path, value);
		save();
	}
}