package com.j0ach1mmall3.kfa.listeners;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.j0ach1mmall3.kfa.GameHandler;
import com.j0ach1mmall3.kfa.Main;

public class PlayerListener implements Listener{
	private Main plugin;
	private static int a = 5;
	private static int id = 0;
	public PlayerListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		e.setJoinMessage("");
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				p.sendMessage("§5§lWelcome to §2§lKFA§5§l, a brand new Minigame!");
				p.sendMessage("§5§lWhen the counter is at 0, you may not do anything anymore, including moving, chatting, performing commands etc. If you do so, you will lose the game. Good luck!");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 10, 1);
			}
		}, 20L);
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run(){
				if(a < 0){
					Bukkit.getScheduler().cancelTask(id);
					GameHandler.addPlayer(p);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 1);
					return;
				}
				p.sendMessage("§5§l" + a);
				p.playSound(p.getLocation(), Sound.CLICK, 10, 1);
				a--;
			}
		}, 40L, 20L);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(!GameHandler.getJoinedPlayers().contains(p)){
			return;
		}
		e.setCancelled(true);
		GameHandler.handleLoss(p);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(!GameHandler.getJoinedPlayers().contains(p)){
			return;
		}
		e.setCancelled(true);
		GameHandler.handleLoss(p);
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		if(!GameHandler.getJoinedPlayers().contains(p)){
			return;
		}
		e.setCancelled(true);
		GameHandler.handleLoss(p);
	}
}	
