package com.j0ach1mmall3.kfa.api;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

public class ActionBarAPI {
	private static Class<?> packetClass = null;
	private static Class<?> componentClass = null;
	private static Class<?> serializerClass = null;
	private static Constructor<?> packetConstructor = null;
	public static void sendActionBar(Player p, String msg) {
		msg = Placeholders.parse(msg, p);
		try {
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutChat");
			componentClass = ReflectionAPI.getNmsClass("IChatBaseComponent");
			serializerClass = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");
			packetConstructor = packetClass.getConstructor(componentClass, byte.class);
			Object BaseComponent = serializerClass.getMethod("a", String.class).invoke(null, "{\"text\": \"" + msg + "\"}");
			Object packet = packetConstructor.newInstance(BaseComponent, (byte)2);
			ReflectionAPI.sendPacket(p, packet);
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
