package com.marquez.houseplugin.data;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PlayerManager {
	
	private static HashMap<Player, String> playerHouses = new HashMap<Player, String>();
	
	public void addPlayer(Player p, String name) {
		playerHouses.put(p, name);
	}
	
	public boolean hasHouse(Player p) {
		return playerHouses.containsKey(p);
	}
	
	public void removePlayer(Player p) {
		playerHouses.remove(p);
	}
	
	public String getHouse(Player p) {
		return playerHouses.get(p);
	}

}
