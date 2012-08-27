package me.number1_Master.TestqUiz.Methods;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.number1_Master.TestqUiz.TestqUizMain;

public class FinishAnswerMethod
{
	private static TestqUizMain plugin;
	public FinishAnswerMethod(TestqUizMain instance)
	{
		plugin = instance;
	}
	
	static Logger log = Logger.getLogger("Minecraft");
	static String prefix = ChatColor.YELLOW + "[TestqUiz] " + ChatColor.GOLD;
	
	//---------------------------------------------------
	
	public static void finish(Player player, String playerName)
	{
		if(!(plugin.finishAntiSpam.containsKey(playerName)))
		{
			plugin.finishAntiSpam.put(playerName, System.currentTimeMillis() + 3000);
			if(plugin.incorrectAmount.containsKey(playerName))
			{
				plugin.incorrectAmount.remove(playerName);
			}
			
			player.sendMessage(prefix + plugin.getConfig().getString("Messages.To-Player.Finish").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
			
			if(plugin.getConfig().getBoolean("Finish.Announce") == true)
			{
				Bukkit.getServer().broadcastMessage(prefix + plugin.getConfig().getString("Messages.Announce.Finish").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
			}
			if(plugin.getConfig().getBoolean("Finish.Log") == true && plugin.getConfig().getBoolean("Finish.Announce") == false)
			{
				log.info(prefix + plugin.getConfig().getString("Messages.Log.Finish").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
			}
			if(plugin.getConfig().getBoolean("Finish.Notify") == true && plugin.getConfig().getBoolean("Finish.Announce") == false)
			{
				OtherMethods.notify("TestqUiz.finish.notify", prefix, "Finish", playerName);
			}
			
			if(plugin.usingVault == true && plugin.permission != null && plugin.getConfig().getBoolean("Finish.Permissions.Use") == true && plugin.permission.playerInGroup(player, plugin.getConfig().getString("Finish.Permissions.From Group")))
			{
				plugin.permission.playerAddGroup(Bukkit.getServer().getWorld(plugin.getConfig().getString("Finish.Permissions.World")), playerName, plugin.getConfig().getString("Finish.Permissions.To Group"));
				if(plugin.getConfig().getBoolean("Finish.Permissions.Add or Change") == false)
				{
					plugin.permission.playerRemoveGroup(Bukkit.getServer().getWorld(plugin.getConfig().getString("Finish.Permissions.World")), playerName, plugin.getConfig().getString("Finish.Permissions.From Group"));
				}		
				String groupChangeMsg = plugin.getConfig().getString("Messages.To-Player.Group Change");
				groupChangeMsg = groupChangeMsg.replaceAll("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD);
				groupChangeMsg = groupChangeMsg.replaceAll("OLDGROUP", ChatColor.YELLOW + plugin.getConfig().getString("Finish.Permissions.From Group") + ChatColor.GOLD);
				groupChangeMsg = groupChangeMsg.replaceAll("NEWGROUP", ChatColor.YELLOW + plugin.getConfig().getString("Finish.Permissions.To Group") + ChatColor.GOLD);
				player.sendMessage(prefix + groupChangeMsg);
				
				if(plugin.economy != null && plugin.getConfig().getBoolean("Finish.Permissions.Reward.Use Economy") == true)
				{
					if(plugin.economy.hasAccount(playerName) == true)
					{
						OtherMethods.addMoney(playerName, player, prefix);
					}
					else
					{
						plugin.economy.createPlayerAccount(playerName);
						OtherMethods.addMoney(playerName, player, prefix);
					}
				}
				if(plugin.getConfig().getBoolean("Finish.Permissions.Reward.Use Items") == true)
				{
					OtherMethods.giveItems(player, prefix);
					player.sendMessage(prefix + plugin.getConfig().getString("Messages.To-Player.Item Reward").replace("PLAYERNAME", ChatColor.YELLOW + player.getName() + ChatColor.GOLD));					
				}
			}
			return;
		}
		return;
	}
}
