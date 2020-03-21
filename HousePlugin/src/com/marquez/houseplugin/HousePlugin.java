package com.marquez.houseplugin;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.marquez.houseplugin.enums.MessageEnum;
import com.marquez.houseplugin.util.DateTime;


public class HousePlugin extends JavaPlugin{
	
	public static DateTime DEFAULT_RENTAL_PERIOD;
	public static DateTime MAX_RENTAL_PERIOD;
	public static DateTime INVITE_EXPIRE_TIME;
	public static int DEFAULT_MAX_PEOPLE;
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public Object getValue(FileConfiguration config, String key, Object default_value) {
		if(!config.isSet(key)) {
			config.set(key, default_value);
		}
		return config.get(key);
	}
	
	public List<?> getValueList(FileConfiguration config, String key, List<?> default_value) {
		if(!config.isSet(key)) {
			config.set(key, default_value);
		}
		return config.getList(key);
	}
	
	public void loadConfig() {
		this.reloadConfig();
		FileConfiguration config = this.getConfig();
		DEFAULT_RENTAL_PERIOD = new DateTime((String)getValue(config, "config.Default_Rental_Period", "24h"));
		MAX_RENTAL_PERIOD = new DateTime((String)getValue(config, "config.Max_Rental_Period", "48h"));
		INVITE_EXPIRE_TIME = new DateTime((String)getValue(config, "config.Invite_Expire_Time", "30s"));
		DEFAULT_MAX_PEOPLE = (int)getValue(config, "config.Default_Maximum_People", 10);
		for(MessageEnum msgEnum : MessageEnum.values()) {
			String key = "message." + msgEnum.name().replace("_", ".");
			if(!config.isSet(key)) {
				if(msgEnum.isList()) config.set(key, msgEnum.getMessages());
				else config.set(key, msgEnum.getMessage());
			}else {
				if(config.isList(key)) msgEnum.setMessage(config.getStringList(key).toArray(new String[0]));
				else msgEnum.setMessage(config.getString(key));
			}
		}
		this.saveConfig();
	}
	

}
