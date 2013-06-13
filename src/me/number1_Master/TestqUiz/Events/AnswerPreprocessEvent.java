package me.number1_Master.TestqUiz.Events;

import me.number1_Master.TestqUiz.TestqUiz;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AnswerPreprocessEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private Sign sign;
	private boolean cancelled = false;
	
	public AnswerPreprocessEvent(Player player, Sign sign)
	{
		this.player = player;
		sign = this.sign;
	}
	
	public HandlerList getHandlers()
	{ return handlers; }
	
	public static HandlerList getHandlerList()
	{ return handlers; }
	
	public Player getPlayer()
	{ return player; }
	
	public Sign getSign()
	{ return sign; }
	
	public boolean isStartingEarly()
	{
		if(TestqUiz.p.notPassed.containsKey(player.getName())) return true;
		else return false;
	}
	
	public boolean isSpam()
	{
		if(TestqUiz.p.antiSpam.containsKey(player.getName())) return true;
		else return false;
	}
	
	public boolean isCheating(Location location)
	{
		if(TestqUiz.p.cheatLocs.containsKey(location))
		{
			String player2 = TestqUiz.p.cheatLocs.get(location);
			
			if(!(TestqUiz.p.cheaters.containsKey(player2)))
			{
				TestqUiz.p.cheatLocs.remove(location);
				return false;
			}
			else
			{
				if(TestqUiz.p.cheaters.get(player2) <= System.currentTimeMillis())
				{
					TestqUiz.p.cheatLocs.remove(location);
					TestqUiz.p.cheaters.remove(player2);
					return false;
				}
				
				if(!(player2.equals(player.getName())))
				{ return true; }
				else return false;
			}
		}
		else return false;
	}

	public boolean isCancelled()
	{ return cancelled; }

	public void setCancelled(boolean cancelled)
	{ this.cancelled = cancelled; }

}
