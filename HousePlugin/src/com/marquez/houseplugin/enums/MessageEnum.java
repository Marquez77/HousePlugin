package com.marquez.houseplugin.enums;

import org.bukkit.ChatColor;

public enum MessageEnum {
	
	info_Prefix("[HousePlugin] "),
	info_NoPermission("&cYou don't have permission."),
	info_NoConsole("&cThis command can not use on console.");
	
	
	private String[] message;
	
	private MessageEnum(String... message) {
		this.message = message;
	}
	
	public boolean isList() {
		return message.length > 1;
	}
	
	public String getMessage() {
		return this.message[0];
	}
	
	public String[] getMessages() {
		return this.message;
	}
	
	public void setMessage(String... message) {
		for(int i = 0; i < message.length; i++) {
			message[i] = ChatColor.translateAlternateColorCodes('&', message[i]);
		}
		this.message = message;
	}

}

