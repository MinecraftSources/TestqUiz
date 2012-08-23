package me.number1_Master.TestqUiz.Methods;

import java.util.logging.Logger;

import me.number1_Master.TestqUiz.TestqUizMain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OtherMethods
{
	private static TestqUizMain plugin;
	public OtherMethods(TestqUizMain instance)
	{
		plugin = instance;
	}
	
	static Logger log = Logger.getLogger("Minecraft");
	static String prefix = ChatColor.YELLOW + "[TestqUiz] " + ChatColor.GOLD;
	public static String location;
	static Player cheater1;
	
	//--------------------------------------------
	public static boolean check(Player player, String playerName, Sign sign, String loc)
	{
		if(sign.getLine(0).equals("[TestqUiz]") && sign.getLine(2).equals("") && sign.getLine(3).equals("") && (sign.getLine(1).equalsIgnoreCase("Incorrect") || sign.getLine(1).equalsIgnoreCase("Correct") || sign.getLine(1).equalsIgnoreCase("Finish")))
		{
			if(plugin.takeTest.containsKey(playerName) && System.currentTimeMillis() - plugin.takeTest.get(playerName) >= plugin.getConfig().getInt("General.Start.Time") *1000)
			{	
				if(!(plugin.incorrectAntiSpam.containsKey(playerName) && plugin.correctAntiSpam.containsKey(playerName) && plugin.finishAntiSpam.containsKey(playerName)))
				{
					if(!(plugin.cheating.containsKey(loc)) && !(plugin.cheaters.contains(playerName)))
					{
						location = loc;
						cheater1 = player;
						plugin.cheating.put(loc, System.currentTimeMillis());
						plugin.cheaters.add(playerName);
						return true;
					}
					else if(plugin.cheating.containsKey(loc) && System.currentTimeMillis() - plugin.cheating.get(loc) <= 10 *1000 && !(plugin.cheaters.contains(playerName)))
					{
						if(plugin.getConfig().getBoolean("General.Cheating.Kick.Use") == true)
						{
							if(plugin.getConfig().getBoolean("General.Cheating.Player") == true)
							{
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("General.Cheating.Kick.Command").replaceAll("PLAYERNAME", cheater1.getName()));
							}
							else
							{
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("General.Cheating.Kick.Command").replaceAll("PLAYERNAME", playerName));
							}
						}
						if(plugin.getConfig().getBoolean("General.Cheating.Teleport to Spawn") == true)
						{
							if(plugin.getConfig().getBoolean("General.Cheating.Player") == true)
							{
								cheater1.teleport(player.getWorld().getSpawnLocation());
							}
							else
							{
								player.teleport(player.getWorld().getSpawnLocation());
							}
						}
					}
					else
					{
						return false;
					}
				}
				return false;
			}
			else
			{
				if(plugin.getConfig().getBoolean("General.Start.Teleport to Spawn") == true)
				{
					player.teleport(player.getWorld().getSpawnLocation());
				}
				if(plugin.getConfig().getBoolean("General.Start.Kick.Use") == true)
				{
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("General.Start.Kick.Command").replaceAll("PLAYERNAME", playerName));
				}
				return false;
			}
		}
		return false;
	}
	public static void notify(String permissionNode, String prefix, String msg, String playerName)
	{
		for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers())
		{
			if(onlinePlayer.hasPermission(permissionNode))
			{
				onlinePlayer.sendMessage(prefix + plugin.getConfig().getString("Messages.Notify." + msg).replace("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD));
			}
		}
	}

	public static void addMoney(String playerName, Player player, String prefix)
	{
		String oldAmount = plugin.economy.format(plugin.economy.getBalance(playerName));
		plugin.economy.depositPlayer(playerName, plugin.getConfig().getDouble("Finish.Permissions.Reward.Economy Amount"));
		String newAmount = plugin.economy.format(plugin.economy.getBalance(playerName));
		String amount;
		if(plugin.getConfig().getDouble("Finish.Permissions.Reward.Economy Amount") == 1)
		{
			amount = plugin.getConfig().getDouble("Finish.Permissions.Reward.Economy Amount") + " " + plugin.economy.currencyNameSingular();
		}
		else
		{
			amount = plugin.getConfig().getDouble("Finish.Permissions.Reward.Economy Amount") + " " + plugin.economy.currencyNamePlural();
		}
		
		String economyRewardMsg = plugin.getConfig().getString("Messages.To-Player.Economy Reward");
		economyRewardMsg = economyRewardMsg.replaceAll("PLAYERNAME", ChatColor.YELLOW + playerName + ChatColor.GOLD);
		economyRewardMsg = economyRewardMsg.replaceAll("AMOUNT", ChatColor.YELLOW + amount + ChatColor.GOLD);
		economyRewardMsg = economyRewardMsg.replaceAll("OLDAMOUNT", ChatColor.YELLOW + oldAmount + ChatColor.GOLD);
		economyRewardMsg = economyRewardMsg.replaceAll("NEWAMOUNT", ChatColor.YELLOW + newAmount + ChatColor.GOLD);	
		player.sendMessage(prefix + economyRewardMsg);
	}
	public static void giveItems(Player player, String prefix)
	{
		for(String itemTotal : plugin.itemRewards)
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
			catch(NumberFormatException e)
			{
				log.info(prefix + "Check Reward Items in the config.yml. Something is incorrect!");
			}
		}
	}
}
