package com.j0ach1mmall3.kfa;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.j0ach1mmall3.kfa.api.Placeholders;
import com.j0ach1mmall3.kfa.commands.Commands;
import com.j0ach1mmall3.kfa.listeners.PlayerListener;

public class Main extends JavaPlugin{
	private static ServicesManager sm;
	public void onEnable(){
		sm = getServer().getServicesManager();
		getConfig().options().copyDefaults();
		saveConfig();
		new GameHandler(this);
		new PlayerListener(this);
		new CustomScoreboard(this);
		GameHandler.clearBans();
		for(Player p : Bukkit.getOnlinePlayers()){
			p.kickPlayer(Placeholders.parse(getConfig().getString("RestartMessage"), p));
		}
		getCommand("KFA").setExecutor(new Commands(this));
		CustomScoreboard.startScheduler(this);
	}
	
	public static Permission getPermission(){
		return sm.getRegistration(net.milkbowl.vault.permission.Permission.class).getProvider();
	}
	
	public static Chat getChat(){
		return sm.getRegistration(net.milkbowl.vault.chat.Chat.class).getProvider();
	}
	
	public static Economy getEconomy(){
		return sm.getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
	}
}
