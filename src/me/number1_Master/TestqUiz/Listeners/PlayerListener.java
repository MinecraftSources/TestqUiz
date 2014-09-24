package me.number1_Master.TestqUiz.Listeners;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Users;
import me.number1_Master.TestqUiz.Events.AnswerPreprocessEvent;
import me.number1_Master.TestqUiz.Events.CorrectAnswerEvent;
import me.number1_Master.TestqUiz.Events.FinishAnswerEvent;
import me.number1_Master.TestqUiz.Events.IncorrectAnswerEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		String playerName = player.getName();
		Block checkSign = e.getTo().getBlock().getRelative(BlockFace.DOWN, 2);
		
		if(checkSign.getType() == Material.WALL_SIGN || checkSign.getType() == Material.SIGN_POST)
		{
			Sign sign = (Sign) checkSign.getState();
				
			if(sign.getLine(0).equals("[TestqUiz]") && sign.getLine(2).equals("") && sign.getLine(3).equals("")
					&& (sign.getLine(1).equalsIgnoreCase("Incorrect") || sign.getLine(1).equalsIgnoreCase("Correct") || sign.getLine(1).equalsIgnoreCase("Finish")))
			{	
				AnswerPreprocessEvent preprocessEvent = new AnswerPreprocessEvent(player, sign);
				Bukkit.getServer().getPluginManager().callEvent(preprocessEvent);
				TestqUiz.p.antiSpam.put(playerName, System.currentTimeMillis() + 1000);
				if(preprocessEvent.isCancelled()) return;
				
				if (sign.getLine(1).equalsIgnoreCase("Incorrect"))
				{
					IncorrectAnswerEvent incorrectEvent = new IncorrectAnswerEvent(player);
					Bukkit.getServer().getPluginManager().callEvent(incorrectEvent);
					return;
				} 
				else if(sign.getLine(1).equalsIgnoreCase("Correct"))
				{
					CorrectAnswerEvent correctEvent = new CorrectAnswerEvent(player);
					Bukkit.getServer().getPluginManager().callEvent(correctEvent);
					return;
				}				
				else if(sign.getLine(1).equalsIgnoreCase("Finish"))
				{
					FinishAnswerEvent finishEvent = new FinishAnswerEvent(player);
					Bukkit.getServer().getPluginManager().callEvent(finishEvent);
					return;
				}
				return;
			}
		}
		if(TestqUiz.p.antiSpam.containsKey(playerName) && TestqUiz.p.antiSpam.get(playerName) <= System.currentTimeMillis()) TestqUiz.p.antiSpam.remove(playerName);

		if(TestqUiz.p.notPassed.containsKey(playerName) && TestqUiz.p.notPassed.get(playerName) <= System.currentTimeMillis()) TestqUiz.p.notPassed.remove(playerName);
		
		if(TestqUiz.p.cheaters.containsKey(playerName) && TestqUiz.p.cheaters.get(playerName) <= System.currentTimeMillis())
		{
			TestqUiz.p.cheaters.remove(playerName);
			for(Location loc : TestqUiz.p.cheatLocs.keySet())
			{
				if(TestqUiz.p.cheatLocs.get(loc).equals(playerName))
				{
					TestqUiz.p.cheatLocs.remove(loc);
					break;
				}
			}
		}
		return;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		if(TestqUiz.p.clearLag.containsKey(playerName) && Bukkit.getServer().getScheduler().isQueued(TestqUiz.p.clearLag.get(playerName)))
		{ Bukkit.getServer().getScheduler().cancelTask(TestqUiz.p.clearLag.get(playerName)); }
		
		if(Users.getStringList("Passed").contains(playerName)) return;
		
		if(!(TestqUiz.p.notPassed.containsKey(e.getPlayer().getName()))) TestqUiz.p.notPassed.put(e.getPlayer().getName(), System.currentTimeMillis() + (Config.getInt("General.Start.Time") * 1000));
	}	
	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent e)
	{				
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		int task = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TestqUiz.p, new Runnable()
		{
			public void run()
			{
				String playerName = e.getPlayer().getName();
				
				if(TestqUiz.p.incorrectBypass.contains(playerName)) TestqUiz.p.incorrectBypass.remove(playerName);
				
				if(TestqUiz.p.incorrectAmount.containsKey(playerName)) TestqUiz.p.incorrectAmount.remove(playerName);
				
				if(TestqUiz.p.antiSpam.containsKey(playerName)) TestqUiz.p.antiSpam.remove(playerName);

				if(TestqUiz.p.notPassed.containsKey(playerName)) TestqUiz.p.notPassed.remove(playerName);

				if(TestqUiz.p.cheaters.containsKey(playerName))
				{
					TestqUiz.p.cheaters.remove(playerName);
					for(Location loc : TestqUiz.p.cheatLocs.keySet())
					{
						if(TestqUiz.p.cheatLocs.get(loc).equals(playerName))
						{
							TestqUiz.p.cheatLocs.remove(loc);
							break;
						}
					}
				}
			}
			
		}, 150*20);
		TestqUiz.p.clearLag.put(playerName, task);
	}
}
