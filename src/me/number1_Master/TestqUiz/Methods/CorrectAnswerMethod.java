package me.number1_Master.TestqUiz.Methods;

import java.util.logging.Logger;

import me.number1_Master.TestqUiz.TestqUizMain;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CorrectAnswerMethod
{
	private static TestqUizMain plugin;
	public CorrectAnswerMethod(TestqUizMain instance)
	{
		plugin = instance;
	}
	
	static Logger log = Logger.getLogger("Minecraft");
	static String prefix = ChatColor.YELLOW + "[TestqUiz] " + ChatColor.GOLD;
		
	//---------------------------------------------------
	
	public static void correct(Player player, String playerName)
	{
		if(!(plugin.correctAntiSpam.containsKey(playerName)))
		{
			plugin.correctAntiSpam.put(playerName, System.currentTimeMillis());
			
			player.sendMessage(prefix + plugin.getConfig().getString("Messages.To-Player.Correct").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
			
			if(plugin.getConfig().getBoolean("Correct Answer.Announce") == true)
			{
				player.getServer().broadcastMessage(prefix + plugin.getConfig().getString("Messages.Announce.Correct").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
				return;
			}
			if(plugin.getConfig().getBoolean("Correct Answer.Log") && plugin.getConfig().getBoolean("Correct Answer.Announce") == false)
			{
				log.info(prefix + plugin.getConfig().getString("Messages.Log.Correct").replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
			}
			if(plugin.getConfig().getBoolean("Correct Answer.Notify") == true && plugin.getConfig().getBoolean("Correct Answer.Announce") == false)
			{
				OtherMethods.notify("TestqUiz.correct.notify", prefix, "Correct", playerName);
			}
			return;
		}
		return;
	}
}
