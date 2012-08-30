package me.number1_Master.TestqUiz.Listeners;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

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
		Users.addToStringList("Passed", playerName);
		
		String mainMsg = prefix + Messages.getString("Messags.To-Player.Finish").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String announceMsg = prefix + Messages.getString("Messages.Announce.Finish").replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
		String logMsg = Messages.getString("Messages.Log.Finish").replaceAll("PLAYERNAME", playerName);
		
		player.sendMessage(mainMsg);
			
		if(Config.getBoolean("Finish.Announce")) Bukkit.getServer().broadcastMessage(announceMsg);
		else
		{
			if(Config.getBoolean("Finish.Log")) Log.i(logMsg);
			if(Config.getBoolean("Finish.Notify")) Utils.notify(playerName, "TestqUiz.finish.notify", "Finish");
		}
		if(e.isUsingPermissions())
		{
			Permission permission = TestqUiz.permission;
			
			World world = Bukkit.getServer().getWorld(Config.getString("Finish.Permissions.World"));
			String fromGroup = Config.getString("Finish.Permissions.From Group");
			String toGroup = Config.getString("Finish.Permissions.To Group");
			
			if(permission.playerInGroup(world, playerName, fromGroup))
			{
				permission.playerAddGroup(world, playerName, toGroup);
				if(!(Config.getBoolean("Finish.Permissions.Add or Change"))) permission.playerRemoveGroup(world, playerName, fromGroup);
	
				String changeMsg = prefix + Messages.getString("Messages.To-Player.Group Change");
				changeMsg = changeMsg.replaceAll("PLAYERNAME", Utils.y + playerName + Utils.o);
				changeMsg = changeMsg.replaceAll("OLDGROUP", Utils.y + fromGroup + Utils.o);
				changeMsg = changeMsg.replaceAll("NEWGROUP", Utils.y + toGroup + Utils.o);
				player.sendMessage(changeMsg);
					
				if(e.isUsingEconomy())
				{
					Economy economy = TestqUiz.economy;
					
					if(!(economy.hasAccount(playerName))) economy.createPlayerAccount(playerName);
					
					String oldAmount = economy.format(economy.getBalance(playerName));
					economy.depositPlayer(playerName, Config.getDouble("Finish.Permissions.Reward.Economy Amount"));
					String newAmount = economy.format(economy.getBalance(playerName));
					
					String amount;
					if(Config.getInt("Finish.Permissions.Reward.Economy Amount") == 1) amount = Config.getDouble("Finish.Permissions.Reward.Economy Amount") + " " + economy.currencyNameSingular();
					else amount = Config.getDouble("Finish.Permissions.Reward.Economy Amount") + " " + economy.currencyNamePlural();
					
					String economyRewardMsg = prefix + Messages.getString("Messages.To-Player.Economy Reward");
					economyRewardMsg = economyRewardMsg.replaceAll("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD);
					economyRewardMsg = economyRewardMsg.replaceAll("AMOUNT", ChatColor.YELLOW + amount + ChatColor.GOLD);
					economyRewardMsg = economyRewardMsg.replaceAll("OLDAMOUNT", ChatColor.YELLOW + oldAmount + ChatColor.GOLD);
					economyRewardMsg = economyRewardMsg.replaceAll("NEWAMOUNT", ChatColor.YELLOW + newAmount + ChatColor.GOLD);	
					player.sendMessage(economyRewardMsg);			
				}
				if(Config.getBoolean("Finish.Permissions.Reward.Use Items"))
				{
					for(String itemTotal : Config.getStringList("Finish.Permissions.Reward.Items"))
					{
						String[] split = itemTotal.split(" ");
						try
						{
							int itemInt = Integer.parseInt(split[0]); 
							int amount = Integer.parseInt(split[1]);
							ItemStack item = new ItemStack(Material.getMaterial(itemInt));
							item.setAmount(amount);
							player.getInventory().addItem(item);
						}
						catch(NumberFormatException err)
						{ 
							Log.i("Check Reward Items in the config.yml Something is incorrect!");
							Log.i(err.getMessage());
						}
					}
					player.sendMessage(prefix + Messages.getString("Messages.To-Player.Item Reward").replace("PLAYERNAME", ChatColor.YELLOW + player.getName() + ChatColor.GOLD));	
				}
			}
		}
	}
}
