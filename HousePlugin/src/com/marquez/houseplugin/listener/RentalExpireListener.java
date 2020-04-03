package com.marquez.houseplugin.listener;

import java.util.ArrayList;

import org.bukkit.OfflinePlayer;

import com.marquez.houseplugin.data.House;
import com.marquez.houseplugin.data.HouseManager;
import com.marquez.houseplugin.enums.MessageEnum;

public class RentalExpireListener extends Thread{
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				for(String name : HouseManager.getHouses()) {
					House house = HouseManager.getHouse(name);
					if(house.getExpireTime() == 0 || house.getOwner() == null) { 
						if(house.getExpireTime() <= System.currentTimeMillis()) {
							for(OfflinePlayer member : new ArrayList<OfflinePlayer>(house.getMember())) {
								HouseManager.removePlayer(house, member);
								if(member.isOnline()) {
									member.getPlayer().sendMessage(MessageEnum.House_Rental_Expire.getMessage());
								}
							}
							house.setOwner(null);
							house.setExpireTime(0);
						}
					}
					HouseManager.refreshSign(house);
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			
		}
	}

}
