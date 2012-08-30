package me.number1_Master.TestqUiz.Events;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FinishAnswerEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private boolean cancelled = false;
	
	public FinishAnswerEvent(Player player)
	{
		player = this.player;
	}
	
	public HandlerList getHandlers()
	{ return handlers; }
	
	public HandlerList getHandlerList()
	{ return handlers; }
	
	public Player getPlayer()
	{ return player; }
	
	public boolean isUsingPermissions()
	{
		if(Config.getBoolean("Finish.Permissions.Use"))
		{
			if(TestqUiz.p.permission != null) return true;
			return false;
		}
		return false;
	}
	
	public Permission getPermissions()
	{ return TestqUiz.p.permission; }
	
	public boolean isUsingEconomy()
	{
		if(Config.getBoolean("Finish.Permissions.Reward.Use Economy"))
		{
			if(TestqUiz.p.economy != null) return true;
			return false;
		}
		return false;
	}
	
	public Economy getEconomy()
	{ return TestqUiz.p.economy; }
	
	public boolean isCancelled()
	{ return cancelled; }
	
	public void setCancelled(boolean cancelled)
	{ cancelled = this.cancelled; }

}
