package com.marquez.houseplugin.listener;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.marquez.houseplugin.HousePlugin;
import com.marquez.houseplugin.data.House;
import com.marquez.houseplugin.data.HouseManager;
import com.marquez.houseplugin.enums.MessageEnum;
import com.marquez.houseplugin.util.DateTime;

public class SignInteractListener implements Listener{
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				String name = HouseManager.getHouseName(e.getClickedBlock().getLocation());
				if(name == null) return;
				House house = HouseManager.getHouse(name);
				Player p = e.getPlayer();
				if(house.getExpireTime() == 0 || house.getOwner() == null) {
					if(HouseManager.getHouse(p) != null) {
						p.sendMessage(MessageEnum.House_Rental_Fail_HaveHouse.getMessage());
						return;
					}
					if(HousePlugin.econ.getBalance(p) < house.getPrice()) {
						p.sendMessage(MessageEnum.House_Rental_Fail_InsufficientCost.getMessage());
						return;
					}
					house.setOwner(p);
					house.setExpireTime(System.currentTimeMillis() + HousePlugin.DEFAULT_RENTAL_PERIOD.getMillis());
					HouseManager.addPlayer(house, p);
					HouseManager.refreshSign(house);
					p.sendMessage(MessageEnum.House_Rental_Success_Default.getMessage().replace("%Rental_Remaining%", HousePlugin.DEFAULT_RENTAL_PERIOD.toTimeString()));
				}else if(house.getOwner().getName().equals(p.getName())) {
					if((house.getExpireTime() - System.currentTimeMillis()) + HousePlugin.DEFAULT_RENTAL_PERIOD.getMillis() > HousePlugin.MAX_RENTAL_PERIOD.getMillis()) {
						p.sendMessage(MessageEnum.House_Rental_Fail_Maximum.getMessage().replace("%Rental_Remaining%", new DateTime(house.getExpireTime() - System.currentTimeMillis()).toTimeString()));
						return;
					}
					house.setExpireTime(house.getExpireTime() + HousePlugin.DEFAULT_RENTAL_PERIOD.getMillis());
					p.sendMessage(MessageEnum.House_Rental_Success_AppendTime.getMessage().replace("%Rental_Remaining%", new DateTime(house.getExpireTime() - System.currentTimeMillis()).toTimeString()));
				}
			}
		}
	}

}
