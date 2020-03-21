package com.marquez.houseplugin.cmds;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.marquez.houseplugin.data.HouseManager;
import com.marquez.houseplugin.enums.MessageEnum;
import com.marquez.houseplugin.listener.AreaSelectListener;

public class HPCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(MessageEnum.info_NoConsole.getMessage());
			return true;
		}
		if(args.length == 0 || args[0].equals("help")) {
			sender.sendMessage(MessageEnum.House_Info_Command.getMessage());
			return true;
		}else {
			Player p = (Player)sender;
			switch(args[0]) {
			case "invite":
				
				break;
			case "kick":
				break;
			case "info":
				break;
			case "teleport":
				break;
			case "doorautoclose":
				break;
			case "leave":
				break;
			case "create":
				if(!p.isOp()) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_Invalid_Usage.getMessage());
					return true;
				}
				Location[] locs = AreaSelectListener.getSelected(p);
				if(locs == null) {
					p.sendMessage(MessageEnum.House_Create_Fail_ThereIsNoRegion.getMessage());
					return true;
				}
				HouseManager.createHouse(args[1], locs[0], locs[1]);
				p.sendMessage(MessageEnum.House_Create_Success.getMessage());
				break;
			case "delete":
				if(!p.isOp()) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_Invalid_Usage.getMessage());
					return true;
				}
				HouseManager.deleteHouse(args[1]);
				break;
			case "buysign":
				if(!p.isOp()) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_Invalid_Usage.getMessage());
					return true;
				}
				break;
			case "door":
				if(!p.isOp()) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_Invalid_Usage.getMessage());
					return true;
				}
				break;
			case "bed":
				if(!p.isOp()) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_Invalid_Usage.getMessage());
					return true;
				}
				break;
			case "reload":
				if(!p.isOp()) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				break;
			}
		}
		return true;
	}
	

}
