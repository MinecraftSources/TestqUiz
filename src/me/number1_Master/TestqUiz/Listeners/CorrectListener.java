package me.number1_Master.TestqUiz.Listeners;

import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Events.CorrectAnswerEvent;
import me.number1_Master.TestqUiz.Utils.Log;
import me.number1_Master.TestqUiz.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.mysql.jdbc.Messages;

public class CorrectListener implements Listener
{
	private String prefix = Utils.getPrefix(true);
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onCorrect(CorrectAnswerEvent e)
	{
		if(e.isCancelled()) return;
		
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		String mainMsg = prefix + Messages.getString("Messages.To-Player.Correct").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String announceMsg = prefix + Messages.getString("Messages.Announce.Correct").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String logMsg = Messages.getString("Messages.Log.Correct").replaceAll("PLAYERNAME", playerName);
		
		player.sendMessage(mainMsg);
			
		if(Config.getBoolean("Correct Answer.Announce")) Bukkit.getServer().broadcastMessage(announceMsg);
		else
		{
			if(Config.getBoolean("Correct Answer.Log")) Log.i(logMsg);
			if(Config.getBoolean("Correct.Notify")) Utils.notify(playerName, "TestqUiz.correct.notify", "Correct");
		}
	}
}
