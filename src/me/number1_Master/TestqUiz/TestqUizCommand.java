package me.number1_Master.TestqUiz;

import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Messages;
import me.number1_Master.TestqUiz.Config.Users;
import me.number1_Master.TestqUiz.Utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestqUizCommand implements CommandExecutor
{
	private String prefix = Utils.getPrefix(true);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("TestqUiz"))
		{
			if(args.length == 0 || args.length >= 2)
			{
				if(!(sender.hasPermission("TestqUiz.version")))
				{
					sender.sendMessage(prefix + "You don't have permission to use that!");
					return true;
				}
				sender.sendMessage(prefix + "You are running version " + TestqUiz.p.getDescription().getVersion());
				return true;
			}
			
			else if(args[0].equalsIgnoreCase("reload"))
			{
				if(!(sender.hasPermission("TestqUiz.reload")))
				{
					sender.sendMessage(prefix + "You don't have permission to use that!");
					return true;
				}
				Config.reload();
				Messages.reload();
				Users.reload();
				sender.sendMessage(prefix + "Configutaions reloaded!");
				return true;
			}
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
				
				if(TestqUiz.p.incorrectBypass.contains(player.getName()))
				{
					TestqUiz.p.incorrectBypass.remove(player.getName());
					player.sendMessage(prefix + "You can no longer bypass Incorrect Answers!");
					return true;
				}
				else
				{
					TestqUiz.p.incorrectBypass.add(player.getName());
					player.sendMessage(prefix + "You can now bypass Incorrect Answers!");
					return true;
				}
			}
			else
			{
				sender.sendMessage(prefix + "Unkown " + Utils.y + "TestqUiz" + Utils.o + " command!");
				return true;
			}
		}
		return false;
	}

}
