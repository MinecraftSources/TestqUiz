package me.number1_Master.TestqUiz;

import me.number1_Master.TestqUiz.Methods.CorrectAnswerMethod;
import me.number1_Master.TestqUiz.Methods.FinishAnswerMethod;
import me.number1_Master.TestqUiz.Methods.IncorrectAnswerMethod;
import me.number1_Master.TestqUiz.Methods.OtherMethods;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TestqUizListener implements Listener
{
	private TestqUizMain plugin;
	public TestqUizListener(TestqUizMain instance)
	{
		plugin = instance;
	}
	
	String prefix = ChatColor.YELLOW + "[TestqUiz] " + ChatColor.GOLD;

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		String playerName = player.getName();
		Block checkSign = player.getLocation().getBlock().getRelative(BlockFace.DOWN, 2);
		
		if(checkSign.getType() == Material.WALL_SIGN || checkSign.getType() == Material.SIGN_POST)
		{
			Sign sign = (Sign) checkSign.getState();
			
			Location tempLoc = player.getLocation();
			World world = tempLoc.getWorld();
			Long x = Math.round(tempLoc.getX());
			Long y = Math.round(tempLoc.getY());
			Long z = Math.round(tempLoc.getZ());
			String loc = world + " " + x + " " + y + " " + z;
	
			if(OtherMethods.check(player, playerName, sign, loc) == true)
			{	
				//	Incorrect
				if (sign.getLine(1).equalsIgnoreCase("Incorrect"))
				{
					IncorrectAnswerMethod.incorrect(player, playerName);
					return;
				} 
				
				//	Correct
				else if(sign.getLine(1).equalsIgnoreCase("Correct"))
				{
					CorrectAnswerMethod.correct(player, playerName);
					return;
				}
				
				//	Finish
				else if(sign.getLine(1).equalsIgnoreCase("Finish"))
				{
					FinishAnswerMethod.finish(player, playerName);
					return;
				}
			}
			return;
		}
		//	AntiSpam Removal
		if(plugin.incorrectAntiSpam.containsKey(playerName) && System.currentTimeMillis() - plugin.incorrectAntiSpam.get(playerName) >= 3000)
		{
			plugin.incorrectAntiSpam.remove(playerName);
		}
		if(plugin.correctAntiSpam.containsKey(playerName) && System.currentTimeMillis() - plugin.correctAntiSpam.get(playerName) >= 3000)
		{
			plugin.correctAntiSpam.remove(playerName);
		}
		if(plugin.finishAntiSpam.containsKey(playerName) && System.currentTimeMillis() - plugin.finishAntiSpam.get(playerName) >= 3000)
		{
			plugin.finishAntiSpam.remove(playerName);
		}
		if(plugin.cheating.containsKey(OtherMethods.location) && System.currentTimeMillis() - plugin.cheating.get(OtherMethods.location) >= 10000)
		{
			plugin.cheating.remove(OtherMethods.location);
			if(plugin.cheaters.contains(playerName))
			{
				plugin.cheaters.remove(playerName);
			}
		}
		return;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e)
	{
		Player player = e.getPlayer();
		
		if (e.getLine(0).equals("[TestqUiz]"))
		{
			if (e.getLine(2).equals("") && e.getLine(3).equals(""))
			{
				//	Correct
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
				//	Incorrect
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
				//	Finish
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
					player.sendMessage(prefix + "Line " + ChatColor.YELLOW + "two" + ChatColor.GOLD + " is not a valid !");
					return;
				}
			}
			else 
			{
				player.sendMessage(prefix + "Lines " + ChatColor.YELLOW + "three and four"  + ChatColor.GOLD + " must be blank!");
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
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		if(!(plugin.takeTest.containsKey(e.getPlayer().getName())))
		{
			plugin.takeTest.put(e.getPlayer().getName(), System.currentTimeMillis());
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{				
		final String playerName = e.getPlayer().getName();
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				if(plugin.incorrectBypass.contains(playerName))
				{
					plugin.incorrectBypass.remove(playerName);
				}
				if(plugin.incorrectAmount.containsKey(playerName))
				{
					plugin.incorrectAmount.remove(playerName);
				}
				if(plugin.incorrectAntiSpam.containsKey(playerName))
				{
					plugin.incorrectAntiSpam.remove(playerName);
				}
				if(plugin.correctAntiSpam.containsKey(playerName))
				{
					plugin.correctAntiSpam.remove(playerName);
				}
				if(plugin.finishAntiSpam.containsKey(playerName))
				{
					plugin.finishAntiSpam.remove(playerName);
				}
				if(plugin.takeTest.containsKey(playerName))
				{
					plugin.takeTest.remove(playerName);
				}
				if(plugin.cheating.containsKey(OtherMethods.location))
				{
					plugin.cheating.remove(OtherMethods.location);
				}
				if(plugin.cheaters.contains(playerName))
				{
					plugin.cheaters.remove(playerName);
				}
			}
			
		}, plugin.getConfig().getInt("General.Logout.Clear") *20);
	}
}
