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
	private Location doorLocation;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Locations getArea() {
		return area;
	}

	public void setArea(Locations area) {
		this.area = area;
	}

	public Location getSignLocation() {
		return signLocation;
	}

	public void setSignLocation(Location signLocation) {
		this.signLocation = signLocation;
	}

	public Location getDoorLocation() {
		return doorLocation;
	}

	public void setDoorLocation(Location doorLocation) {
		this.doorLocation = doorLocation;
	}

	public Location getBedLocation() {
		return bedLocation;
	}

	public void setBedLocation(Location bedLocation) {
		this.bedLocation = bedLocation;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMaximumPeople() {
		return maximumPeople;
	}

	public void setMaximumPeople(int maximumPeople) {
		this.maximumPeople = maximumPeople;
	}

	public int getDoorCloseTime() {
		return doorCloseTime;
	}

	public void setDoorCloseTime(int doorCloseTime) {
		this.doorCloseTime = doorCloseTime;
	}

	public OfflinePlayer getOwner() {
		return owner;
	}

	public void setOwner(OfflinePlayer owner) {
		this.owner = owner;
	}

	public List<OfflinePlayer> getMember() {
		return member;
	}

	public void setMember(List<OfflinePlayer> member) {
		this.member = member;
	}	
}
