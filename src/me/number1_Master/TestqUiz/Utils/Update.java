package me.number1_Master.TestqUiz.Utils;

import java.io.File;
import java.util.HashMap;

import me.number1_Master.TestqUiz.TestqUiz;
import me.number1_Master.TestqUiz.Config.Config;
import me.number1_Master.TestqUiz.Config.Messages;

public class Update
{
	public static void config(File configFile)
	{
		if(Config.getString("General.version") != null && Config.getString("General.version").equals("2.3"))
		{
			File oldFile = new File(TestqUiz.dir, "config_old.yml");
			if(oldFile.exists())
			{
				try
				{ oldFile.delete(); }
				catch(Exception err)
				{ Log.w("Could NOT delete old "); }
			}
			configFile.renameTo(oldFile);
			
			Config.set("General.version", null);
			Config.set("General.Logout", null);
			if(Config.get("Messages") != null)
			{
				move("Messages.To-Player");
				move("Messages.Announce");
				move("Messages.Log");
				move("Messages.Notify");
				Config.set("Messages", null);
				Log.i("Moving Messages in config.yml to Messages in messages.yml ...");
			}
		}
	}
	private static void move(String startPath)
	{
		HashMap<String, String> moveNodes = new HashMap<String, String>();

		for(String s : Config.getConfigSection(startPath).getKeys(true))
		{
			String path = startPath + "." + s;
			String value = Config.getString(path);
			if(value == null) continue;
			moveNodes.put(path, value);
		}
		for(String s : moveNodes.keySet()) Messages.set(s, moveNodes.get(s));
	}
}