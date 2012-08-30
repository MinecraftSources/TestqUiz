package me.number1_Master.TestqUiz.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CorrectAnswerEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean cancelled = false;
	
	public CorrectAnswerEvent(Player player)
	{
		player = this.player;
	}
	
	public HandlerList getHandlers()
	{ return handlers; }
	
	public HandlerList getHandlerList()
	{ return handlers; }
	
	public Player getPlayer()
	{ return player; }
	
	public boolean isCancelled()
	{ return cancelled; }
	
	public void setCancelled(boolean cancelled)
	{ cancelled = this.cancelled; }

}
