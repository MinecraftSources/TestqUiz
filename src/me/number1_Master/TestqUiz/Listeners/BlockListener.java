package me.number1_Master.TestqUiz.Listeners;

import me.number1_Master.TestqUiz.Utils.Utils;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockListener implements Listener
{
	private static String prefix = Utils.getPrefix(true);
	
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		Player player = e.getPlayer();
		
		if (e.getLine(0).equals("[TestqUiz]"))
		{
			if (e.getLine(2).equals("") && e.getLine(3).equals(""))
			{
				if (e.getLine(1).equalsIgnoreCase("Correct"))
				{
					if (player.hasPermission("TestqUiz.correct.create")) 
					{
						player.sendMessage(prefix + "Your answer has been made!");
						return;
					}
					else
					{
						e.setCancelled(true);
						player.sendMessage(prefix + "You don't have permission to create that type of sign!");
						return;
					}
				}
				else if (e.getLine(1).equalsIgnoreCase("Incorrect"))
				{
					if (player.hasPermission("TestqUiz.incorrect.create")) 
					{
						player.sendMessage(prefix + "Your answer has been made!");
						return;
					}
					else
					{
						e.setCancelled(true);
						player.sendMessage(prefix + "You don't have permission to create that type of sign!");
						return;
					}
				}
				else if (e.getLine(1).equalsIgnoreCase("Finish"))
				{
					if (player.hasPermission("TestqUiz.finish.create")) 
					{
						player.sendMessage(prefix + "Your answer has been made!");
						return;
					}
					else
					{
						e.setCancelled(true);
						player.sendMessage(prefix + "You don't have permission to create that type of sign!");
						return;
					}
				}
				else 
				{
					player.sendMessage(prefix + "Line " + Utils.y + "two" + Utils.o + " is not a valid !");
					return;
				}
			}
			else 
			{
				player.sendMessage(prefix + "Lines " + Utils.y + "three and four"  + Utils.o + " must be blank!");
				return;
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();

		if (e.getBlock().getType() == Material.WALL_SIGN || e.getBlock().getType() == Material.SIGN_POST)
		{
			Sign sign = (Sign) e.getBlock().getState();
			if(sign.getLine(0).equals("[TestqUiz]") && sign.getLine(2).equalsIgnoreCase("") && sign.getLine(3).equalsIgnoreCase(""))
			{
				if(sign.getLine(1).equalsIgnoreCase("Correct"))
				{
					if (player.hasPermission("TestqUiz.correct.break")) 
					{
						player.sendMessage(prefix + "Your answer has been made!");
						return;
					}
					else
					{
						e.setCancelled(true);
						player.sendMessage(prefix + "You don't have permission to destroy that type of sign!");
						return;
					}
				}
				if(sign.getLine(1).equalsIgnoreCase("Incorrect"))
				{
					if (player.hasPermission("TestqUiz.incorrect.break")) 
					{
						player.sendMessage(prefix + "Your answer has been made!");
						return;
					}
					else
					{
						e.setCancelled(true);
						player.sendMessage(prefix + "You don't have permission to destroy that type of sign!");
						return;
					}
				}
				if(sign.getLine(1).equalsIgnoreCase("Finish"))
				{
					if (player.hasPermission("TestqUiz.finish.break")) 
					{
						player.sendMessage(prefix + "Your answer has been made!");
						return;
					}
					else
					{
						e.setCancelled(true);
						player.sendMessage(prefix + "You don't have permission to destroy that type of sign!");
						return;
					}
				}
			}
		}
	}
}
