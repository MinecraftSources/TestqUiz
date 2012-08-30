package me.number1_Master.TestqUiz.Listeners;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Users;
import me.number1_Master.TestqUiz.Events.AnswerPreprocessEvent;
import me.number1_Master.TestqUiz.Events.CorrectAnswerEvent;
import me.number1_Master.TestqUiz.Events.FinishAnswerEvent;
import me.number1_Master.TestqUiz.Events.IncorrectAnswerEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		String playerName = player.getName();
		Block checkSign = player.getLocation().getBlock().getRelative(BlockFace.DOWN, 2);
		
		if(checkSign.getType() == Material.WALL_SIGN || checkSign.getType() == Material.SIGN_POST)
		{
			Sign sign = (Sign) checkSign.getState();
				
			if(sign.getLine(0).equals("[TestqUiz]") && sign.getLine(2).equals("") && sign.getLine(3).equals("")
					&& (sign.getLine(1).equalsIgnoreCase("Incorrect") || sign.getLine(1).equalsIgnoreCase("Correct") || sign.getLine(1).equalsIgnoreCase("Finish")))
			{	
				AnswerPreprocessEvent preprocessEvent = new AnswerPreprocessEvent(player, sign);
				Bukkit.getServer().getPluginManager().callEvent(preprocessEvent);
				if(preprocessEvent.isCancelled()) return;
				
				TestqUiz.p.antiSpam.put(playerName, System.currentTimeMillis() + 2000);
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
		
		if(TestqUiz.p.cheaters.containsKey(playerName) && TestqUiz.p.cheaters.get(player) <= System.currentTimeMillis())
		{
			TestqUiz.p.cheaters.remove(playerName);
			for(Object o : TestqUiz.p.cheatLocs.keySet())
			{
				if(TestqUiz.p.cheatLocs.get(o).equals(player))
				{
					TestqUiz.p.cheatLocs.remove(o);
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
		
		if(Users.getStringList("Passed").contains(playerName)) return;
		
		if(TestqUiz.p.clearLag.containsKey(e.getPlayer())
				&& Bukkit.getScheduler().isCurrentlyRunning(TestqUiz.p.clearLag.get(e.getPlayer().getName()))) Bukkit.getScheduler().cancelTask(TestqUiz.p.clearLag.get(e.getPlayer().getName()));
		
		if(!(TestqUiz.p.notPassed.containsKey(e.getPlayer().getName()))) TestqUiz.p.notPassed.put(e.getPlayer().getName(), System.currentTimeMillis() + (Config.getInt("General.Start.Time") * 1000));
	}	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{				
		final String playerName = e.getPlayer().getName();
		
		int task = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TestqUiz.p, new Runnable()
		{
			@Override
			public void run()
			{
				if(TestqUiz.p.incorrectBypass.contains(playerName)) TestqUiz.p.incorrectBypass.remove(playerName);
				
				if(TestqUiz.p.incorrectAmount.containsKey(playerName)) TestqUiz.p.incorrectAmount.remove(playerName);
				
				if(TestqUiz.p.antiSpam.containsKey(playerName)) TestqUiz.p.antiSpam.remove(playerName);

				if(TestqUiz.p.notPassed.containsKey(playerName)) TestqUiz.p.notPassed.remove(playerName);

				//if(TestqUiz.p.cheating.containsKey(PreprocessListener.location)) TestqUiz.p.cheating.remove(PreprocessListener.location);

				//if(TestqUiz.p.cheaters.contains(playerName)) TestqUiz.p.cheaters.remove(playerName);
			}
			
		}, Config.getInt("General.Logout.Clear") * 20);
		TestqUiz.p.clearLag.put(playerName, task);
	}
}
