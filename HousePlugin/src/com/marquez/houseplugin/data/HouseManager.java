package com.marquez.houseplugin.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.material.Door;

import com.marquez.houseplugin.enums.MessageEnum;
import com.marquez.houseplugin.util.DataFile;
import com.marquez.houseplugin.util.DateTime;
import com.marquez.houseplugin.util.Locations;

public class HouseManager {
	
	private static HashMap<String, House> houses = new HashMap<String, House>();
	private static HashMap<Location, String> signs = new HashMap<Location, String>();
	private static HashMap<Location, String> doors = new HashMap<Location, String>();
	private static HashMap<Location, String> beds = new HashMap<Location, String>();
	private static HashMap<OfflinePlayer, String> players = new HashMap<OfflinePlayer, String>();
	
	public static List<String> getHouses() {
		return new ArrayList<String>(houses.keySet());
	}
	
	public static boolean addPlayer(House house, OfflinePlayer p) {
		if(house.getMember().size() == house.getMaximumPeople()) return false;
		house.getMember().add(p);
		players.put(p, house.getName());
		return true;
	}
	
	public static void removePlayer(House house, OfflinePlayer p) {
		house.getMember().remove(p);
		players.remove(p);
	}
	
	public static String getHouse(OfflinePlayer p) {
		return players.containsKey(p) ? players.get(p) : null;
	}
	
	private static void addHouseValues(House house) {
		String name = house.getName();
		houses.put(name, house);
		if(house.getSignLocation() != null) signs.put(house.getSignLocation(), name);
		if(house.getDoorLocation() != null) doors.put(house.getDoorLocation(), name);
		if(house.getBedLocation() != null) beds.put(house.getBedLocation(), name);
		for(OfflinePlayer member : house.getMember()) {
			players.put(member, house.getName());
		}
	}
	
	private static void removeHouseValues(String name) {
		if(!houses.containsKey(name)) return;
		House house = houses.get(name);
		if(house.getSignLocation() != null) signs.remove(house.getSignLocation());
		if(house.getDoorLocation() != null) doors.remove(house.getDoorLocation());
		if(house.getBedLocation() != null) beds.remove(house.getBedLocation());
		for(OfflinePlayer member : house.getMember()) {
			players.remove(member);
		}
		houses.remove(name);
	}
	
	public static String getHouseName(Location loc) {
		if(signs.containsKey(loc)) return signs.get(loc);
		if(doors.containsKey(loc)) return doors.get(loc);
		if(beds.containsKey(loc)) return beds.get(loc);
		return null;
	}
	
	public static boolean setSignLocation(String name, Location loc) {
		if(signs.containsKey(loc)) return false;
		House house = houses.get(name);
		if(house.getSignLocation() != null) signs.remove(house.getSignLocation());
		house.setSignLocation(loc);
		signs.put(loc, name);
		refreshSign(house);
		return true;
	}
	
	public static void refreshSign(House house) {
		Sign sign = (Sign)house.getSignLocation().getBlock().getState();
		String[] lines;
		if(house.getOwner() == null) {
			lines = MessageEnum.Sign_Buy.getMessages();
		}else {
			lines = MessageEnum.Sign_Rental.getMessages();
		}
		for(int i = 0; i < lines.length && i < 4; i++) {
			String line = lines[i].replace("%House_Name%", house.getName()).replace("%Price%", house.getPrice()+"");
			if(house.getExpireTime() > 0) {
				DateTime time = new DateTime(house.getExpireTime()-System.currentTimeMillis());
				line = line.replace("%Rental_Remaining%", time.toTimeString()).replace("%House_Owner%", house.getOwner().getName()).replace("%Living_Member%", house.getMember().size()+"").replace("%Maximum_Member%", house.getMaximumPeople()+"");
			}
			sign.setLine(i, line);
		}
		sign.update();
	}
	
	public static boolean setDoorLocation(String name, Location loc) {
		if(((Door)loc.getBlock().getState()).isTopHalf()) loc.subtract(0, 1, 0);
		if(doors.containsKey(loc)) return false;
		House house = houses.get(name);
		if(house.getDoorLocation() != null) doors.remove(house.getDoorLocation());
		house.setDoorLocation(loc);
		doors.put(loc, name);
		return true;
	}
	
	public static boolean setBedLocation(String name, Location loc) {
		if(beds.containsKey(loc)) return false;
		House house = houses.get(name);
		if(house.getBedLocation() != null) beds.remove(house.getBedLocation());
		house.setBedLocation(loc);
		beds.put(loc, name);
		return true;
	}
	
	public static void createHouse(String name, Location pos1, Location pos2) {
		House house = new House(name, pos1, pos2);
		addHouseValues(house);
		saveData(house);
	}
	
	public static boolean isExists(String name) {
		return houses.containsKey(name);
	}
	
	public static void deleteHouse(String name) {
		removeHouseValues(name);
		StringBuilder sb = new StringBuilder();
		sb.append("house/").append(name).append(".yml");
		File file = new File(DataFile.getPlugin().getDataFolder(), sb.toString());
		file.delete();
	}
	
	public static House getHouse(String name) {
		return houses.get(name);
	}
	
	public static void saveData(House house) {
		String name = house.getName();
		StringBuilder sb = new StringBuilder();
		sb.append("house/").append(name).append(".yml");
		DataFile houseData = new DataFile(sb.toString());
		houseData.reloadConfig();
		FileConfiguration houseDatac = houseData.getFileConfiguration();
		Locations area = house.getArea();
		houseDatac.set("name", name);
		houseDatac.set("pos1", area.pos1);
		houseDatac.set("pos2", area.pos2);
		houseDatac.set("sign", house.getSignLocation());
		houseDatac.set("door", house.getDoorLocation());
		houseDatac.set("bed", house.getBedLocation());
		houseDatac.set("level", house.getLevel());
		houseDatac.set("price", house.getPrice());
		houseDatac.set("maximum_people", house.getMaximumPeople());
		houseDatac.set("door_close_time", house.getDoorCloseTime());
		houseDatac.set("owner", house.getOwner());
		houseDatac.set("member", house.getMember());
		houseDatac.set("expire", house.getExpireTime());
		houseData.saveConfig();
	}
	
	public static void saveAllData() {
		for(House house : houses.values()) {
			saveData(house);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void loadData(String filename) {
		DataFile houseData = new DataFile(filename);
		houseData.reloadConfig();
		FileConfiguration houseDatac = houseData.getFileConfiguration();
		String name = houseDatac.getString("name");
		Location pos1 = (Location)houseDatac.get("pos1");
		Location pos2 = (Location)houseDatac.get("pos2");
		House house = new House(name, pos1, pos2);
		house.setSignLocation((Location)houseDatac.get("sign"));
		house.setDoorLocation((Location)houseDatac.get("door"));
		house.setBedLocation((Location)houseDatac.get("bed"));
		house.setLevel(houseDatac.getInt("level"));
		house.setPrice(houseDatac.getInt("price"));
		house.setMaximumPeople(houseDatac.getInt("maximum_people"));
		house.setDoorCloseTime(houseDatac.getInt("door_close_time"));
		Object owner = houseDatac.get("owner");
		if(owner != null) house.setOwner((OfflinePlayer)owner);
		List<?> member = houseDatac.getList("member");
		if(member != null) house.setMember((List<OfflinePlayer>)member);
		house.setExpireTime(houseDatac.getLong("expire"));
		addHouseValues(house);
	}
	
	public static void loadAllDatas() {
		File folder = new File(DataFile.getPlugin().getDataFolder(), "house");
		if(!folder.exists()) folder.mkdir();
		for(String filename : folder.list()) {
			StringBuilder sb = new StringBuilder();
			sb.append("house/").append(filename);
			loadData(sb.toString());
		}
	}

}
