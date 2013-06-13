package me.number1_Master.TestqUiz.Utils;

import java.util.logging.Logger;

import me.number1_Master.TestqUiz.TestqUiz;

import org.bukkit.Bukkit;

public class Log
{
	private static Logger log = Bukkit.getServer().getLogger();
	private static String prefix = Utils.getPrefix(false);
	
	public static void i(String message)
	{
		log.info(prefix + message);
	}
	public static void w(String message) 
	{
		log.warning(prefix + message);
	}
	public static void s(String message)
	{
		log.severe(prefix + message);
	}
	public static void d(String message)
	{
		if(!(TestqUiz.p.getConfig().getBoolean("General.Debug"))) return;
		i(message);
	}
}
