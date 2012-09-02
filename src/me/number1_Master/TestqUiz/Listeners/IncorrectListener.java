package me.number1_Master.TestqUiz.Listeners;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Messages;
import me.number1_Master.TestqUiz.Events.IncorrectAnswerEvent;
import me.number1_Master.TestqUiz.Utils.Log;
import me.number1_Master.TestqUiz.Utils.Utils;

public class IncorrectListener implements Listener
{
	private String prefix = Utils.getPrefix(true);
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onIncorrect(IncorrectAnswerEvent e)
	{
		if(e.isCancelled()) return;
		
		Player player = e.getPlayer();
		String playerName = e.getPlayer().getName();
		
		if(e.isBypassing())
		{
			player.sendMessage(prefix + "You bypassed an incorrect answer!");
			return;
		}
		
		String mainMsg = prefix + Messages.getString("Messages.To-Player.Incorrect").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String announceMsg = prefix + Messages.getString("Messages.Announce.Incorrect").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String logMsg = Messages.getString("Messages.Log.Incorrect").replaceAll("PLAYERNAME", playerName);
		
		Utils.teleport(player);		
		player.sendMessage(mainMsg);
		
		if(Config.getBoolean("Incorrect Answer.Announce")) Bukkit.getServer().broadcastMessage(announceMsg);
		else
		{
			if(Config.getBoolean("Incorrect Answer.Log")) Log.i(logMsg);
			if(Config.getBoolean("Incorrect.Notify")) Utils.notify(playerName, "incorrect");
		}
		if(Config.getBoolean("Incorrect Answer.Kicking.Use") || Config.getBoolean("Incorrect Answer.Banning.Use")) e.addWrongAnswer(1);
		
		if(e.isBanning())
		{
			String banCmd = Config.getString("Incorrect Answer.Banning.Command").replace("PLAYERNAME", playerName);
			String banAnnounceMsg = prefix + Messages.getString("Messages.Announce.Ban").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
			String banLogMsg = Messages.getString("Messages.Log.Ban").replaceAll("PLAYERNAME", playerName);
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), banCmd);
			TestqUiz.p.incorrectAmount.remove(playerName);
				
			if(TestqUiz.p.getConfig().getBoolean("Incorrect Answer.Banning.Announce")) Bukkit.getServer().broadcastMessage(banAnnounceMsg);
			else
			{
				if(TestqUiz.p.getConfig().getBoolean("Incorrect Answer.Banning.Log")) Log.i(banLogMsg);
				if(TestqUiz.p.getConfig().getBoolean("Incorrect Answer.Banning.Notify")) Utils.notify(playerName, "ban");
			}
			return;
		}
		if(e.isKicking())
		{
			String kickCmd = Config.getString("Incorrect Answer.Kicking.Command").replace("PLAYERNAME", playerName);
			String kickAnnounceMsg = prefix + Messages.getString("Messages.Announce.Kick").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
			String kickLogMsg = Messages.getString("Messages.Log.Kick").replaceAll("PLAYERNAME", playerName);
				
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), kickCmd);		
				
			if(Config.getBoolean("Incorrect Answer.Kicking.Reset")) e.subtractWrongAnswer(100);		
					
			if(Config.getBoolean("Incorrect Answer.Kicking.Announce")) Bukkit.getServer().broadcastMessage(kickAnnounceMsg);
			else
			{
				if(Config.getBoolean("Incorrect Answer.Kicking.Log")) Log.i(kickLogMsg);
				if(Config.getBoolean("Incorrect Answer.Kicking.Notify")) Utils.notify(playerName, "kick");	
			}
		}
	}
}
