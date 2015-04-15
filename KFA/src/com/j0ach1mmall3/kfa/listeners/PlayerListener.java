package com.j0ach1mmall3.kfa.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.j0ach1mmall3.kfa.GameHandler;
import com.j0ach1mmall3.kfa.Main;
import com.j0ach1mmall3.kfa.api.Placeholders;

public class PlayerListener implements Listener{
	private Main plugin;
	private static int a = 0;
	private static int id = 0;
	public PlayerListener(Main plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		a = plugin.getConfig().getInt("JoinCountdown");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		e.setJoinMessage("");
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				String IntroductionMessage = Placeholders.parse(plugin.getConfig().getString("IntroductionMessage"), p);
				p.sendMessage(IntroductionMessage);
				String IntroductionMessage2 = Placeholders.parse(plugin.getConfig().getString("IntroductionMessage2"), p);
				p.sendMessage(IntroductionMessage2);
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
		Location f = e.getFrom();
	    Location t = e.getTo();
	    if(f.getX() == t.getX() && f.getY() == t.getY() && f.getZ() == t.getZ()){
	    	if(p.hasPermission("kfa.bypass.look")){
		    	return;
	    	}
	    }
	    if(f.getX() != t.getX() || f.getY() != t.getY() || f.getZ() != t.getZ()){
	    	if(p.hasPermission("kfa.bypass.move")){
		    	return;
	    	}
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
		if(p.hasPermission("kfa.bypass.chat")){
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
		if(p.hasPermission("kfa.bypass.command")){
			return;
		}
		e.setCancelled(true);
		GameHandler.handleLoss(p);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(!GameHandler.getJoinedPlayers().contains(p)){
			return;
		}
		GameHandler.handleLoss(p);
	}
}	
