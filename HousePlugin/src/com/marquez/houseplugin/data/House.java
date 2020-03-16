package com.marquez.houseplugin.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import com.marquez.houseplugin.util.Locations;

public class House {
	private String name;
	private Locations area;
	private Location signLocation;
	private Location bedLocation;
	private int level;
	private int price;
	private int maximumPeople;
	private int doorCloseTime;
	private OfflinePlayer owner;
	private List<OfflinePlayer> member;
	
	public House(String name, Location pos1, Location pos2) {
		this.name = name;
		this.area = new Locations(pos1, pos2);
		this.level = 0;
		this.price = 1;
		this.maximumPeople = 1;
		this.doorCloseTime = 1;
		this.member = new ArrayList<OfflinePlayer>();
	}

}
