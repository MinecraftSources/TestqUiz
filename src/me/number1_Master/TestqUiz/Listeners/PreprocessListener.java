package me.number1_Master.TestqUiz.Listeners;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Events.AnswerPreprocessEvent;
import me.number1_Master.TestqUiz.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PreprocessListener implements Listener
{
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPreprocess(AnswerPreprocessEvent e)
	{
		if(e.isCancelled()) return;		

		Player player = e.getPlayer();
		String playerName = player.getName();
		
		if(!(e.isStartingEarly()))
		{	
			if(!(e.isSpam()))
			{	
				Location l = player.getLocation();
				Location location = new Location(l.getWorld(), Math.round(l.getX()), Math.round(l.getY()), Math.round(l.getZ()));

				if(e.isCheating(location))
				{
					Player cheater1 = Bukkit.getServer().getPlayerExact(TestqUiz.p.cheatLocs.get(location));
					
					if(Config.getBoolean("General.Cheating.Teleport to Spawn"))
					{
						if(Config.getBoolean("General.Cheating.Player")) Utils.teleport(cheater1);
						else Utils.teleport(player);
					}
					if(Config.getBoolean("General.Cheating.Kick.Use"))
					{
						if(Config.getBoolean("General.Cheating.Player")) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.getString("General.Cheating.Kick.Command").replaceAll("PLAYERNAME", cheater1.getName()));
						else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.getString("General.Cheating.Kick.Command").replaceAll("PLAYERNAME", playerName));
					}
					e.setCancelled(true);
					return;
				}
				else
				{
					if(!(TestqUiz.p.cheatLocs.containsKey(location)))
					{
						TestqUiz.p.cheatLocs.put(location, playerName);
						TestqUiz.p.cheaters.put(playerName, System.currentTimeMillis() + 7000);
						return;
					}
				}
			}
			e.setCancelled(true);
			return;
		}
		else
		{
			if(Config.getBoolean("General.Start.Teleport to Spawn")) Utils.teleport(player);

			if(Config.getBoolean("General.Start.Kick.Use")) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.getString("General.Start.Kick.Command").replaceAll("PLAYERNAME", playerName));
				
			e.setCancelled(true);
			return;
		}
	}
}
