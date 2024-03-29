package me.number1_Master.TestqUiz.Events;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class IncorrectAnswerEvent extends Event implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private boolean bypassing = false;
	private boolean kicking = false;
	private boolean banning = false;
	private boolean cancelled = false;
	
	public IncorrectAnswerEvent(Player player)
	{
		this.player = player;
		
		if(TestqUiz.p.incorrectBypass.contains(player.getName())) bypassing = true;
		
		if(Config.getBoolean("Incorrect Answer.Kicking.Use") || Config.getBoolean("Incorrect Answer.Banning.Use")) addWrongAnswer(1);
		
		if(TestqUiz.p.incorrectAmount.containsKey(player.getName()))
		{
			if(Config.getBoolean("Incorrect Answer.Kicking.Use") && TestqUiz.p.incorrectAmount.get(player.getName()) >= Config.getInt("Incorrect Answer.Kicking.Amount")) this.kicking = true;
			
			if(Config.getBoolean("Incorrect Answer.Banning.Use"))
			{
				int amount;
				if(!(Config.getBoolean("Incorrect Answer.Banning.Base Off"))) amount = Config.getInt("Incorrect Answer.Banning.Amount");
				else
				{
					if(Config.getBoolean("Incorrect Answer.Kicking.Reset")) amount = Config.getInt("Incorrect Answer.Banning.Amount") * Config.getInt("Incorrect Answer.Kicking.Amount");
					else amount = Config.getInt("Incorrect Answer.Banning.Amount") + Config.getInt("Incorrect Answer.Kicking.Amount") - 1;
				}
				if(TestqUiz.p.incorrectAmount.get(player.getName()) >= amount) this.banning = true;
			}
		}
	}
	
	public HandlerList getHandlers()
	{ return handlers; }
	
	public static HandlerList getHandlerList()
	{ return handlers; }
	
	public Player getPlayer()
	{ return player; }
	
	public void addWrongAnswer(int amount)
	{
		if(!(TestqUiz.p.incorrectAmount.containsKey(player.getName())))
		{
			TestqUiz.p.incorrectAmount.put(player.getName(), amount);
			return;
		}
		TestqUiz.p.incorrectAmount.put(player.getName(), TestqUiz.p.incorrectAmount.get(player.getName()) + amount);
	}
	
	public void subtractWrongAnswer(int amount)
	{
		if(!(TestqUiz.p.incorrectAmount.containsKey(player.getName()))) return;
		if(amount >= TestqUiz.p.incorrectAmount.get(player.getName()))
		{
			TestqUiz.p.incorrectAmount.remove(player.getName());
			return;
		}
		TestqUiz.p.incorrectAmount.put(player.getName(), TestqUiz.p.incorrectAmount.get(player.getName()) - amount);
	}
	
	public boolean isBypassing()
	{ return bypassing; }
	
	public void setBypassing(boolean bypassing)
	{ bypassing = this.bypassing; }
	
	public boolean isKicking()
	{ return kicking; }
	
	public void setKicking(boolean kicking)
	{ kicking = this.kicking; }
	
	public boolean isBanning()
	{ return banning; }
	
	public void setBanning(boolean banning)
	{ banning = this.banning; }
	
	public boolean isCancelled()
	{ return cancelled; }
	
	public void setCancelled(boolean cancelled)
	{ this.cancelled = cancelled; }
}