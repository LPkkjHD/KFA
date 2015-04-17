package com.j0ach1mmall3.kfa;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.j0ach1mmall3.kfa.api.ActionBarAPI;

public class CustomScoreboard {
	private Main plugin;
	private static Scoreboard sb;
	private static Objective obj;
	private static int id = 0;
	public CustomScoreboard(Main plugin){
		this.plugin = plugin;
		sb = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = sb.registerNewObjective("§d§lAFK Time", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public static void addPlayer(Player p){
		p.setScoreboard(sb);
		obj.getScore(p.getName()).setScore(1);
	}
	
	public static void removePlayer(Player p){
		sb.resetScores(p.getName());
	}
	
	public static void startScheduler(Main plugin){
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Player p : GameHandler.getJoinedPlayers()){
					obj.getScore(p.getName()).setScore(obj.getScore(p.getName()).getScore()+1);
					ActionBarAPI.sendActionBar(p, "§d§lPlayers in-game: §5§l" + GameHandler.getJoinedPlayers().size());
				}  
			}
		}, 0, 20L);
	}
	
	public static void stopScheduler(){
		Bukkit.getScheduler().cancelTask(id);
	}
	
	public static void restartScheduler(Main plugin){
		if(id != 0){
			stopScheduler();
		}
		startScheduler(plugin);
	}
}
