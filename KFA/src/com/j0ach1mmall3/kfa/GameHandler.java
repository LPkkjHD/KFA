package com.j0ach1mmall3.kfa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.BanList.Type;
import org.bukkit.entity.Player;

public class GameHandler {
	private static List<String> joinedPlayers = new ArrayList<String>();
	private static Main plugin;
	public GameHandler(Main plugin){
		this.plugin = plugin;
	}
	
	public static List<Player> getJoinedPlayers(){
		List<Player> players = new ArrayList<Player>();
		for(String player : joinedPlayers){
			players.add(Bukkit.getPlayer(player));
		}
		return players;
	}
	
	public static void addPlayer(Player p){
		joinedPlayers.add(p.getName());
	}
	
	public static void handleLoss(Player p){
		joinedPlayers.remove(p.getName());
		for(Player joined : getJoinedPlayers()){
			joined.sendMessage("§4§l" + p.getName() + " §c§lhas lost the game!");
			joined.sendMessage("§d§l" + joinedPlayers.size() + " §5§lplayers remaining!");
		}
		p.kickPlayer("You lost the game!");
		Bukkit.getServer().getBanList(Type.NAME).addBan(p.getName(), "§cYou lost the game!", new Date(Long.MAX_VALUE), p.getName()).save();
		if(joinedPlayers.size() == 1){
			handleWin(Bukkit.getPlayer(joinedPlayers.get(0)));
		}
	}

	private static void handleWin(final Player p) {
		p.sendMessage("§a§lYou won the game!");
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 10, 1);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				p.kickPlayer("§cRestarting the game!");
				clearBans();
			}
		}, 20L);
		
	}
	
	public static void clearBans(){
		plugin.getLogger().info("Clearing banned Players list!");
		for(BanEntry entry : Bukkit.getServer().getBanList(Type.NAME).getBanEntries()){
			Bukkit.getServer().getBanList(Type.NAME).pardon(entry.getTarget());
		}
	}
}
