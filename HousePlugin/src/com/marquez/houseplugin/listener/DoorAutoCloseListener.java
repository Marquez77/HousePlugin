package com.marquez.houseplugin.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;

import com.marquez.houseplugin.HousePlugin;
import com.marquez.houseplugin.data.House;
import com.marquez.houseplugin.data.HouseManager;

public class DoorAutoCloseListener implements Listener{

	private HashMap<Location, Long> closeTime = new HashMap<Location, Long>();

	@EventHandler
	public void onDoorOpen(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if(block.getType().toString().contains("DOOR")) {
				Location loc = block.getLocation();
				Door door = (Door)block.getState().getData();
				if(door.isOpen()) return;
				if(door.isTopHalf()) loc.subtract(0, 1, 0);
				Player p = e.getPlayer();
				String name = HouseManager.getHouse(p);
				if(name == null) return;
				House house = HouseManager.getHouse(name);
				if(house.getDoorLocation().distance(loc) < 1) {
					closeTime.put(loc, System.currentTimeMillis()+house.getDoorCloseTime()*1000);
				}
			}
		}
	}

	public int run(HousePlugin instance) {
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, 
				new Runnable() {

			@Override
			public void run() {
				for(Location loc : closeTime.keySet()) {
					if(closeTime.get(loc) <= System.currentTimeMillis()) {
						closeTime.remove(loc);
						BlockState state = loc.getBlock().getState();
						Door door = (Door)state.getData();
						door.setOpen(false);
						state.setData(door);
						state.update();
					}
				}
			}
		}, 20L, 20L);
	}

}
