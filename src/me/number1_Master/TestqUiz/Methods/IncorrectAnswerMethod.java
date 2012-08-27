package me.number1_Master.TestqUiz.Methods;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.number1_Master.TestqUiz.TestqUizMain;

public class IncorrectAnswerMethod
{
	private static TestqUizMain plugin;
	public IncorrectAnswerMethod(TestqUizMain instance)
	{
		plugin = instance;
	}
	
	static Logger log = Logger.getLogger("Minecraft");
	static String prefix = ChatColor.YELLOW + "[TestqUiz] " + ChatColor.GOLD;
	static Integer amount;
	
	public static void incorrect(final Player player, final String playerName)
	{
		if(!(plugin.incorrectAntiSpam.containsKey(playerName)))
		{
			plugin.incorrectAntiSpam.put(playerName, System.currentTimeMillis() + 3000);
		}
		if(plugin.incorrectBypass.contains(playerName))
		{
			if(!(plugin.incorrectAntiSpam.containsKey(playerName)))
			{
				plugin.incorrectAntiSpam.put(playerName, System.currentTimeMillis() + 3000);
				player.sendMessage(prefix + "You walked over a Incorrect Answer");
				return;
			}
			return;
		}
		
		OtherMethods.teleport(player, plugin.getConfig().getString("General.Teleport Command"));
		player.sendMessage(prefix + plugin.getConfig().getString("Messages.To-Player.Incorrect").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
		if(plugin.getConfig().getBoolean("Incorrect Answer.Announce") == true)
		{
			Bukkit.getServer().broadcastMessage(prefix + plugin.getConfig().getString("Messages.Announce.Incorrect").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
		}
		if(plugin.getConfig().getBoolean("Incorrect Answer.Log") == true && plugin.getConfig().getBoolean("Incorrect Answer.Announce") == false)
		{
			log.info(prefix + 	plugin.getConfig().getString("Messages.Log.Incorrect").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
		}
		if(plugin.getConfig().getBoolean("Incorrect Answer.Notify") == true && plugin.getConfig().getBoolean("Incorrect Answer.Announce") == false)
		{
			OtherMethods.notify("TestqUiz.incorrect.notify", prefix, "Incorrect", playerName);
		}
		
		if(plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Use") == true || plugin.getConfig().getBoolean("Incorrect Answer.Banning.Use") == true)
		{
			if(!(plugin.incorrectAmount.containsKey(playerName  )))
			{
				plugin.incorrectAmount.put(playerName, 1);
			}
			else
			{
				plugin.incorrectAmount.put(playerName, plugin.incorrectAmount.get(playerName) +1);
			}
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
		{
			public void run()
			{
				// Kicking --------------------
				if (plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Use") == true)
				{
					if(plugin.incorrectAmount.get(playerName) >= plugin.getConfig().getInt("Incorrect Answer.Kicking.Amount"))
					{		
						if(plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Reset") == true)
						{
							plugin.incorrectAmount.remove(playerName);
						}
						if(plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Announce") == true)
						{
							Bukkit.getServer().broadcastMessage(prefix + plugin.getConfig().getString("Messages.Announce.Kick").replace("PlAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
						}
						if(plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Log") == true && plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Announce") == false)
						{
							log.info(prefix + plugin.getConfig().getString("Messages.Log.Kick").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
						}
						if(plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Notify") && plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Announce") == false)
						{
							OtherMethods.notify("TestqUiz.notify.kick", prefix, "Kick", playerName);
						}
							
						if(player.isOnline())
						{
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("Incorrect Answer.Kicking.Command").replace("PLAYERNAME",playerName));
						}			
					}
				}
				
				//Banning
				if(plugin.getConfig().getBoolean("Incorrect Answer.Banning.Use") == true)
				{
					if(amount == null)
					{
						if(plugin.getConfig().getBoolean("Incorrect Answer.Banning.Base Off") == false)
						{
							amount = plugin.getConfig().getInt("Incorrect Answer.Banning.Amount");
						}
						else
						{
							if(plugin.getConfig().getBoolean("Incorrect Answer.Kicking.Reset") == true)
							{
								amount = plugin.getConfig().getInt("Incorrect Answer.Banning.Amount") * plugin.getConfig().getInt("Incorrect Answer.Kicking.Amount");
							}
							else
							{
								amount = plugin.getConfig().getInt("Incorrect Answer.Banning.Amount") + plugin.getConfig().getInt("Incorrect Answer.Kicking.Amount") - 1;
							}
						}
					}
					if(plugin.incorrectAmount.get(playerName) >= amount)
					{
						if(player.isOnline())
						{
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("Incorrect Answer.Banning.Command").replace("PLAYERNAME", playerName));
						}
						if(plugin.getConfig().getBoolean("Incorrect Answer.Banning.Announce") == true)
						{
							Bukkit.getServer().broadcastMessage(prefix + plugin.getConfig().getString("Messages.Announce.Ban").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
						}
						if(plugin.getConfig().getBoolean("Incorrect Answer.Banning.Log") == true && plugin.getConfig().getBoolean("Incorrect Answer.Banning.Announce") == false)
						{
							log.info(prefix + plugin.getConfig().getString("Messages.Log.Ban").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
						}
						if(plugin.getConfig().getBoolean("Incorrect Answer.Banning.Notify") == true && plugin.getConfig().getBoolean("Incorrect Answer.Banning.Announce") == false)
						{
							OtherMethods.notify("TestqUiz.notify.ban", prefix, "Ban", playerName);
						}
						plugin.incorrectAmount.remove(playerName);
						return;
					}
					return;
				}
				return;
			}
		}, 5);
	}
}
