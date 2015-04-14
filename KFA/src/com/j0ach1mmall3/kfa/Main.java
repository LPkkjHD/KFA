package com.j0ach1mmall3.kfa;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.j0ach1mmall3.kfa.listeners.PlayerListener;

public class Main extends JavaPlugin{
	public void onEnable(){
		getConfig().options().copyDefaults();
		saveConfig();
		new GameHandler(this);
		new PlayerListener(this);
		GameHandler.clearBans();
		for(Player p : Bukkit.getOnlinePlayers()){
			p.kickPlayer("Restarting the game...");
		}
	}
}
