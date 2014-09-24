package me.number1_Master.TestqUiz.Listeners;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Messages;
import me.number1_Master.TestqUiz.Config.Users;
import me.number1_Master.TestqUiz.Events.FinishAnswerEvent;
import me.number1_Master.TestqUiz.Utils.Log;
import me.number1_Master.TestqUiz.Utils.Utils;

public class FinishListener implements Listener
{
	private String prefix = Utils.getPrefix(true);
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onFinish(FinishAnswerEvent e)
	{
		if(e.isCancelled()) return;
		
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		if(TestqUiz.p.incorrectAmount.containsKey(playerName)) TestqUiz.p.incorrectAmount.remove(playerName);
		
		String mainMsg = prefix + Messages.getString("Messages.To-Player.Finish").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String announceMsg = prefix + Messages.getString("Messages.Announce.Finish").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String logMsg = Messages.getString("Messages.Log.Finish").replaceAll("PLAYERNAME", playerName);
		
		player.sendMessage(mainMsg);
			
		if(Config.getBoolean("Finish.Announce")) Bukkit.getServer().broadcastMessage(announceMsg);
		else
		{
			if(Config.getBoolean("Finish.Log")) Log.i(logMsg);
			if(Config.getBoolean("Finish.Notify")) Utils.notify(playerName, "finish");
		}
		
		if(Users.getStringList("Passed").contains(playerName)) return;
		else Users.addToStringList("Passed", playerName);
		
		if(e.isUsingPermissions())
		{
			Permission permission = TestqUiz.permission;
			
			//World world = Bukkit.getServer().getWorld(Config.getString("Finish.Permissions.World"));
			String fromGroup = Config.getString("Finish.Permissions.From Group");
			String toGroup = Config.getString("Finish.Permissions.To Group");
			
			if(permission.playerInGroup(player, fromGroup))
			{
				permission.playerAddGroup(player, toGroup);
				if(!(Config.getBoolean("Finish.Permissions.Add or Change"))) permission.playerRemoveGroup(player, fromGroup);
	
				String changeMsg = prefix + Messages.getString("Messages.To-Player.Group Change");
				changeMsg = changeMsg.replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
				changeMsg = changeMsg.replaceAll("OLDGROUP", Utils.y + fromGroup + Utils.o);
				changeMsg = changeMsg.replaceAll("NEWGROUP", Utils.y + toGroup + Utils.o);
				player.sendMessage(changeMsg);

			}
		}
	}
}
