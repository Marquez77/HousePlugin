package com.marquez.houseplugin.listener;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.marquez.houseplugin.enums.MessageEnum;
import com.marquez.houseplugin.util.Locations;

public class AreaSelectListener implements Listener{
	
	private static HashMap<Player, Location> pos1 = new HashMap<Player, Location>();
	private static HashMap<Player, Location> pos2 = new HashMap<Player, Location>();
	
	public static Location[] getSelected(Player p) {
		if(!pos1.containsKey(p) || !pos2.containsKey(p)) return null;
		return new Location[] { pos1.get(p), pos2.get(p) };
	}
	
	@EventHandler
	public void onAreaSelect(PlayerInteractEvent e) {
		if(e.getItem() != null && e.getItem().getType() == Material.IRON_HOE) {
			Player p = e.getPlayer();
			if(!p.hasPermission("house.admin")) return;
			if(e.getClickedBlock() == null) return;
			Location loc = e.getClickedBlock().getLocation();
			Action action = e.getAction();
			if(action == Action.LEFT_CLICK_BLOCK) {
				pos1.put(p, loc);
				p.sendMessage(MessageEnum.Select_Position_1.getMessage().replace("%Location%", Locations.getLocation2String(loc)));
			}else if(action == Action.RIGHT_CLICK_BLOCK) {
				pos2.put(p, loc);
				p.sendMessage(MessageEnum.Select_Position_2.getMessage().replace("%Location%", Locations.getLocation2String(loc)));
			}
			e.setCancelled(true);
		}
	}

}
