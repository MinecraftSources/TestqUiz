package me.number1_Master.TestqUiz.Events;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FinishAnswerEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private boolean cancelled = false;
	
	public FinishAnswerEvent(Player player)
	{
		this.player = player;
	}
	
	public HandlerList getHandlers()
	{ return handlers; }
	
	public static HandlerList getHandlerList()
	{ return handlers; }
	
	public Player getPlayer()
	{ return player; }
	
	public boolean isUsingPermissions()
	{
		if(Config.getBoolean("Finish.Permissions.Use"))
		{
			if(TestqUiz.permission != null) return true;
			return false;
		}
		return false;
	}
	
	public boolean isUsingEconomy()
	{
		if(Config.getBoolean("Finish.Permissions.Reward.Use Economy"))
		{
			if(TestqUiz.economy != null) return true;
			return false;
		}
		return false;
	}
	
	public boolean isCancelled()
	{ return cancelled; }
	
	public void setCancelled(boolean cancelled)
	{ this.cancelled = cancelled; }

}
