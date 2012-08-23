package me.number1_Master.TestqUiz;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestqUizCommand implements CommandExecutor
{
	private TestqUizMain plugin;
	public TestqUizCommand(TestqUizMain instance)
	{
		plugin = instance;
	}
	String prefix = ChatColor.YELLOW + "[TestqUiz] " + ChatColor.GOLD;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("TestqUiz"))
		{
			// /TestqUiz
			if(args.length == 0 || args.length >= 2)
			{
				if(!(sender.hasPermission("TestqUiz.version")))
				{
					sender.sendMessage(prefix + "You don't have permission to use that!");
					return true;
				}
				plugin.version(sender, prefix);
				return true;
			}
			
			// /TestqUiz reload
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(!(sender.hasPermission("TestqUiz.reload")))
				{
					sender.sendMessage(prefix + "You don't have permission to use that!");
					return true;
				}
				plugin.reloadConfig();
				plugin.saveConfig();
				sender.sendMessage(prefix + "Config reloaded!");
				return true;
			}
			// /TestqUiz bypass
			else if(args[0].equalsIgnoreCase("bypass"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(prefix + "You must be a player for this command!");
					return true;
				}
				
				Player player = (Player) sender;
				if(!(player.hasPermission("TestqUiz.bypass")))
				{
					player.sendMessage(prefix + "You don't have permission to use that!");
					return true;
				}
				
				if(plugin.incorrectBypass.contains(player.getName()))
				{
					plugin.incorrectBypass.remove(player.getName());
					player.sendMessage(prefix + "You can no longer bypass Incorrect Answers!");
					return true;
				}
				else
				{
					plugin.incorrectBypass.add(player.getName());
					player.sendMessage(prefix + "You can now bypass Incorrect Answers!");
					return true;
				}
			}
			// /TestqUiz somethingelse
			else
			{
				sender.sendMessage(prefix + "Unkown " + ChatColor.YELLOW + "TestqUiz" + ChatColor.GOLD + " command!");
				return true;
			}
		}
		return false;
	}

}
