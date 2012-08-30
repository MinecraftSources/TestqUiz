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
	private Player player;
	private Sign sign;
	private boolean cancelled = false;
	
	public AnswerPreprocessEvent(Player player, Sign sign)
	{
		player = this.player;
		sign = this.sign;
	}
	
	public HandlerList getHandlers()
	{ return handlers; }
	
	public HandlerList getHandlerList()
	{ return handlers; }
	
	public Player getPlayer()
	{ return player; }
	
	public Sign getSign()
	{ return sign; }
	
	public boolean isStartingEarly()
	{
		if(TestqUiz.p.notPassed.containsKey(player.getName())) return false;
		return true;
	}
	
	public boolean isSpam()
	{
		if(TestqUiz.p.antiSpam.containsKey(player.getName())) return true;
		return false;
	}
	
	public boolean isCheating(Location location)
	{
		if(TestqUiz.p.cheatLocs.containsKey(location)
				&& !(TestqUiz.p.cheatLocs.get(location).equals(player))
				&& TestqUiz.p.cheaters.get(TestqUiz.p.cheatLocs.get(location).getName()) >= System.currentTimeMillis()) return true;
		return false;
	}

	public boolean isCancelled()
	{ return cancelled; }

	public void setCancelled(boolean cancelled)
	{ cancelled = this.cancelled; }

}
