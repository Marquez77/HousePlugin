package com.marquez.houseplugin.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Locations{
	public World world;
	public Location pos1;
	public Location pos2;

	public Locations(Location pos1, Location pos2) {
		int[] x = swap((int) pos1.getX(), (int) pos2.getX());
		int[] y = swap((int) pos1.getY(), (int) pos2.getY());
		int[] z = swap((int) pos1.getZ(), (int) pos2.getZ());
		pos1.setX(x[0]);
		pos2.setX(x[1]);
		pos1.setY(y[0]);
		pos2.setY(y[1]);
		pos1.setZ(z[0]);
		pos2.setZ(z[1]);
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.world = pos1.getWorld();
	}
	public int[] swap(int i1, int i2) {
		int[] a = new int[] { i1, i2 };
		if (i1 >= i2) {
			a = new int[] { i2, i1 };
		}
		return a;
	}
	
	public Location[] getPositions() {
		Location[] locations = new Location[4];
		locations[0] = pos1.clone();
		locations[1] = pos1.clone();
		locations[1].setX(pos2.getX());
		locations[2] = pos1.clone();
		locations[2].setZ(pos2.getZ());
		locations[3] = pos2.clone();
		return locations;
	}

	public boolean isInPosition(Location loc) {
		if (world.getName().equals(loc.getWorld().getName())) {
			if (pos1.getBlockX() <= loc.getBlockX() && pos2.getBlockX() >= loc.getBlockX()) {
				if (pos1.getBlockZ() <= loc.getBlockZ() && pos2.getBlockZ() >= loc.getBlockZ()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isInPosition(Player p) {
		return isInPosition(p.getLocation());
	}
	
	public static String getLocation2String(Location loc) {
		StringBuilder sb = new StringBuilder();
		sb.append(loc.getBlockX()).append(",").append(loc.getBlockY()).append(",").append(loc.getBlockZ());
		return sb.toString();
	}
	
	public CharSequence toStrings() {
		StringBuilder sb = new StringBuilder();
		sb.append(world.getName()).append("\n")
		.append(pos1.getBlockX()).append(",").append(pos1.getBlockY()).append(",").append(pos1.getBlockZ()).append("\n")
		.append(pos2.getBlockX()).append(",").append(pos2.getBlockY()).append(",").append(pos2.getBlockZ());
		return sb;
	}
	
	public static Location getString2Location(World world, String s) {
		String[] splited = s.split(",");
		Location loc = new Location(world, Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
		return loc;
	}
}
