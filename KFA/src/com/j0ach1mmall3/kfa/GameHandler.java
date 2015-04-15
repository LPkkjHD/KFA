package com.j0ach1mmall3.kfa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.BanList.Type;
import org.bukkit.entity.Player;

import com.j0ach1mmall3.kfa.api.Placeholders;

public class GameHandler extends Main{
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
			String LoseBroadcast = Placeholders.parse(plugin.getConfig().getString("LoseBroadcast"), p);
	        joined.sendMessage(LoseBroadcast);
			joined.sendMessage("§d§l" + joinedPlayers.size() + " §5§lplayers remaining!");
		}
		String KickMessage = Placeholders.parse(plugin.getConfig().getString("KickMessage"), p);
		p.kickPlayer(KickMessage);
		Bukkit.getServer().getBanList(Type.NAME).addBan(p.getName(), KickMessage, new Date(Long.MAX_VALUE), p.getName()).save();
		if(joinedPlayers.size() == 1){
			handleWin(Bukkit.getPlayer(joinedPlayers.get(0)));
		}
	}

	private static void handleWin(final Player p) {
		String WinMessage = Placeholders.parse(plugin.getConfig().getString("WinMessage"), p);
        p.sendMessage(WinMessage);
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 10, 1);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				p.kickPlayer(Placeholders.parse(plugin.getConfig().getString("RestartMessage"), p));
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
