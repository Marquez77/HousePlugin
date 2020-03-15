package com.marquez.houseplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class HousePlugin extends JavaPlugin{
	
	@Override
	public void onEnable() {
		getCommand("house").setExecutor(null);
		return;
	}
	
	@Override
	public void onDisable() {
		System.out.println("TEST");
		return;
	}
	

}
