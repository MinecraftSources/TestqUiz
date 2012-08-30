package me.number1_Master.TestqUiz.Utils;

import me.number1_Master.TestqUiz.Config.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Messages;

public class Utils
{
	public static ChatColor o = ChatColor.GOLD;
	public static ChatColor y = ChatColor.YELLOW;
	
	private static String prefix = "[TestqUiz] ";
	public static String getPrefix(boolean colorize)
	{
		if(colorize) return y + prefix + o;
		else return prefix;
	}
	
	public static void notify(String playerName, String permissionNode, String type)
	{
		for(Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if(player.hasPermission(permissionNode)) player.sendMessage(getPrefix(true) + Messages.getString("Messages.Notify." + type).replaceAll("PLAYERNAME", y + playerName + o));
		}
	}
	public static void teleport(Player player)
	{
		String command =  Config.getString("General.Teleport Command").replaceAll("PLAYERNAME", player.getName());
		if(command.startsWith("P: "))
		{
			command =  command.replaceFirst("P: ", "");
			Bukkit.dispatchCommand(player, command);
		}
		else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
}
