package com.marquez.houseplugin.data;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import com.marquez.houseplugin.util.DataFile;
import com.marquez.houseplugin.util.Locations;

public class HouseManager {
	
	private static HashMap<String, House> houses = new HashMap<String, House>();
	private static HashMap<Location, String> signs = new HashMap<Location, String>();
	private static HashMap<Location, String> doors = new HashMap<Location, String>();
	private static HashMap<Location, String> beds = new HashMap<Location, String>();
	
	private static void addHouseValues(House house) {
		String name = house.getName();
		houses.put(name, house);
		if(house.getSignLocation() != null) signs.put(house.getSignLocation(), name);
		if(house.getDoorLocation() != null) doors.put(house.getDoorLocation(), name);
		if(house.getBedLocation() != null) beds.put(house.getBedLocation(), name);
	}
	
	private static void removeHouseValues(String name) {
		if(!houses.containsKey(name)) return;
		House house = houses.get(name);
		if(house.getSignLocation() != null) signs.remove(house.getSignLocation());
		if(house.getDoorLocation() != null) doors.remove(house.getDoorLocation());
		if(house.getBedLocation() != null) beds.remove(house.getBedLocation());
		houses.remove(name);
	}
	
	public static boolean setSignLocation(String name, Location loc) {
		if(signs.containsKey(loc)) return false;
		houses.get(name).setSignLocation(loc);
		signs.put(loc, name);
		return true;
	}
	
	public static boolean setDoorLocation(String name, Location loc) {
		if(doors.containsKey(loc)) return false;
		houses.get(name).setDoorLocation(loc);
		doors.put(loc, name);
		return true;
	}
	
	public static boolean setBedLocation(String name, Location loc) {
		if(beds.containsKey(loc)) return false;
		houses.get(name).setBedLocation(loc);
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
		addHouseValues(house);
	}
	
	public static void loadAllDatas() {
		File folder = new File(DataFile.getPlugin().getDataFolder(), "house");
		for(String filename : folder.list()) {
			StringBuilder sb = new StringBuilder();
			sb.append("house/").append(filename);
			loadData(sb.toString());
		}
	}

}
