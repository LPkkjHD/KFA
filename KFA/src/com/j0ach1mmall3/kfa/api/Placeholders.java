package com.j0ach1mmall3.kfa.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.j0ach1mmall3.kfa.Main;

public class Placeholders {
	private Main plugin;
	public Placeholders(Main plugin){
		this.plugin = plugin;
	}
	public static String parse(String s, Player p){
		s = s.replace("%serverip%", Bukkit.getIp().toString())
				.replace("%motd%", Bukkit.getMotd())
				.replace("%servername%", Bukkit.getName())
				.replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
				.replace("%max%", String.valueOf(Bukkit.getMaxPlayers()));
		if(p != null){
			s = s.replace("%playername%", p.getName())
				.replace("%displayname%", p.getDisplayName())
				.replace("%health%", String.valueOf(p.getHealth()))
				.replace("%X%", String.valueOf(p.getLocation().getBlockX()))
				.replace("%Y%", String.valueOf(p.getLocation().getBlockY()))
				.replace("%Z%", String.valueOf(p.getLocation().getBlockZ()))
				.replace("%world%", p.getWorld().getName())
				.replace("%level%", String.valueOf(p.getLevel()))
				.replace("%exp%", String.valueOf(p.getExp()))
				.replace("%ip%", p.getAddress().toString())
				.replace("%balance%", String.valueOf(((net.milkbowl.vault.permission.Permission) Main.getPermission()).getPrimaryGroup(p)))
				.replace("%prefix%", ((net.milkbowl.vault.chat.Chat) Main.getChat()).getPlayerSuffix(p))
				.replace("%suffix%", ((net.milkbowl.vault.chat.Chat) Main.getChat()).getPlayerSuffix(p))
				.replace("%balance%", String.valueOf(((net.milkbowl.vault.economy.Economy) Main.getEconomy()).getBalance(p)));
		}
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
}
