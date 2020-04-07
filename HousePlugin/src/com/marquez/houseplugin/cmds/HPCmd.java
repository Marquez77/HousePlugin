package com.marquez.houseplugin.cmds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.marquez.houseplugin.HousePlugin;
import com.marquez.houseplugin.data.House;
import com.marquez.houseplugin.data.HouseManager;
import com.marquez.houseplugin.enums.MessageEnum;
import com.marquez.houseplugin.listener.AreaSelectListener;
import com.marquez.houseplugin.util.DateTime;

public class HPCmd implements CommandExecutor {

	private HousePlugin instance;

	public HPCmd(HousePlugin instance) {
		this.instance = instance;
	}
	
	public HashMap<OfflinePlayer, Integer> inviteTask = new HashMap<OfflinePlayer, Integer>();
	public HashMap<OfflinePlayer, OfflinePlayer> invitePlayer = new HashMap<OfflinePlayer, OfflinePlayer>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(MessageEnum.info_NoConsole.getMessage());
			return true;
		}
		if(args.length == 0 || args[0].equals("help")) {
			sender.sendMessage(MessageEnum.House_Help_Command.getMessages());
			return true;
		}else {
			Player p = (Player)sender;
			switch(args[0]) {
			case "invite": {
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				switch(args[1]) {
				case "accept": {
					if(!invitePlayer.containsKey(p)) {
						p.sendMessage(MessageEnum.Accept_FailNoReceive.getMessage());
						return true;
					}
					OfflinePlayer owner = invitePlayer.get(p);
					invitePlayer.remove(p);
					House house = HouseManager.getHouse(HouseManager.getHouse(owner));
					HouseManager.addPlayer(house, p);
					p.sendMessage(MessageEnum.Accept_Success_Receiver.getMessage().replace("%Playername%", owner.getName()));
					owner.getPlayer().sendMessage(MessageEnum.Accept_Success_Sender.getMessage().replace("%Playername%", p.getName()));
					String message = MessageEnum.Accept_Success_Broadcast.getMessage().replace("%Playername%", p.getName());
					for(OfflinePlayer member : house.getMember()) {
						if(!member.isOnline()) continue;
						member.getPlayer().sendMessage(message);
					}
					return true;
				}
				case "deny": {
					if(!invitePlayer.containsKey(p)) {
						p.sendMessage(MessageEnum.Deny_FailNoReceive.getMessage());
						return true;
					}
					OfflinePlayer owner = invitePlayer.get(p);
					invitePlayer.remove(p);
					p.sendMessage(MessageEnum.Deny_Success_Receiver.getMessage().replace("%Playername%", owner.getName()));
					owner.getPlayer().sendMessage(MessageEnum.Deny_Success_Sender.getMessage().replace("%Playername%", p.getName()));
					return true;
				}
				default:
					break;
				}
				String name = HouseManager.getHouse(p);
				if(name == null) {
					p.sendMessage(MessageEnum.Invite_Fail_YouDoNotHaveHouse.getMessage());
					return true;
				}
				House house = HouseManager.getHouse(name);
				if(!house.getOwner().getName().equals(p.getName())) {
					p.sendMessage(MessageEnum.Invite_Fail_NotOwner.getMessage());
					return true;
				}
				if(house.getMember().size() >= house.getMaximumPeople()) {
					p.sendMessage(MessageEnum.Invite_Fail_OverPeople.getMessage());
					return true;
				}
				if(inviteTask.containsKey(p)) {
					p.sendMessage(MessageEnum.Invite_Fail_AlreadyInvite.getMessage());
					return true;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					p.sendMessage(MessageEnum.Invite_Fail_InvalidUser.getMessage());
					return true;
				}
				if(HouseManager.getHouse(target) != null) {
					p.sendMessage(MessageEnum.Invite_Fail_AlreadyMember.getMessage());
					return true;
				}
				if(invitePlayer.containsKey(target)) {
					p.sendMessage(MessageEnum.Invite_Fail_AlreadyReceive.getMessage());
				}
				p.sendMessage(MessageEnum.Invite_Success_Sender.getMessage().replace("%Playername%", target.getName()));
				target.sendMessage(MessageEnum.Invite_Success_Receiver.getMessage().replace("%Playername%", p.getName()));
				invitePlayer.put(target, p);
				int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance,
						new Runnable() {
					public int i = 0;
					@Override
					public void run() {
						if(++i >= HousePlugin.INVITE_EXPIRE_TIME.getSeconds() || !invitePlayer.containsKey(target)) {
							if(i >= HousePlugin.INVITE_EXPIRE_TIME.getSeconds()) {
								p.sendMessage(MessageEnum.Invite_Expire.getMessage());
								target.sendMessage(MessageEnum.Invite_Expire.getMessage());
							}
							int task = inviteTask.get(p);
							inviteTask.remove(p);
							invitePlayer.remove(target);
							Bukkit.getScheduler().cancelTask(task);
							return;
						}
					}
				}, 20L, 20L);
				inviteTask.put(p, scheduler);
				break;
			}
			case "kick": {
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				String name = HouseManager.getHouse(p);
				House house;
				if(name == null || !(house = HouseManager.getHouse(name)).getOwner().getName().equals(p.getName())) {
					p.sendMessage(MessageEnum.Kick_Fail_NotOwner.getMessage());
					return true;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null || name != HouseManager.getHouse(target)) {
					p.sendMessage(MessageEnum.Kick_Fail_InvaildUser.getMessage());
					return true;
				}
				HouseManager.removePlayer(house, p);
				p.sendMessage(MessageEnum.Kick_Success.getMessage());
				break;
			}
			case "info": {
				String name = HouseManager.getHouse(p);
				if(name == null) {
					p.sendMessage(MessageEnum.House_Info_YouDoNotHaveHouse.getMessage());
					return true;
				}
				House house = HouseManager.getHouse(name);
				List<String> array = new ArrayList<String>();
				for(OfflinePlayer player : house.getMember()) {
					array.add(player.getName());
				}
				String[] members = array.toArray(new String[array.size()]);
				for(String s : MessageEnum.House_Info_Message.getMessages()) {
					s = s.replace("%House_Name%", house.getName()) 
					.replace("%House_Owner%", house.getOwner().getName())
					.replace("%Price%", house.getPrice()+"")
					.replace("%Rental_Remaining%", new DateTime(house.getExpireTime()-System.currentTimeMillis()).toTimeString())
					.replace("%Maximum_Member%", house.getMaximumPeople()+"")
					.replace("%Living_Member%", house.getMember().size()+"")
					.replace("%Members%", String.join(", ", members));
					p.sendMessage(s);
				}
				break;
			}
			case "teleport": {
				String name = HouseManager.getHouse(p);
				House house;
				if(name == null || (house = HouseManager.getHouse(name)).getBedLocation() == null) {
					p.sendMessage(MessageEnum.Teleport_FailNotExist.getMessage());
					return true;
				}
				p.teleport(house.getBedLocation());
				p.sendMessage(MessageEnum.Teleport_Success.getMessage());
				break;
			}
			case "doorautoclose": {
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				String name = HouseManager.getHouse(p);
				House house;
				if(name == null || !(house = HouseManager.getHouse(name)).getOwner().getName().equals(p.getName())) {
					p.sendMessage(MessageEnum.Doorautoclose_Fail_NotOwner.getMessage());
					return true;
				}
				try {
					int seconds = Integer.parseInt(args[1]);
					house.setDoorCloseTime(seconds);
					p.sendMessage(MessageEnum.Doorautoclose_Success.getMessage().replace("%Time%", seconds+""));
				} catch(NumberFormatException e) {
					p.sendMessage(MessageEnum.Doorautoclose_Fail_NotNumber.getMessage());
				}
				break;
			}
			case "leave": {
				String name = HouseManager.getHouse(p);
				if(name == null) {
					p.sendMessage(MessageEnum.Leave_FailYouDoNotHaveHouse.getMessage());
					return true;
				}
				House house = HouseManager.getHouse(name);
				if(house.getOwner().getName().equals(p.getName())) {
					house.setOwner(null);
					house.setExpireTime(0);
					for(OfflinePlayer member : new ArrayList<OfflinePlayer>(house.getMember())) {
						HouseManager.removePlayer(house, member);
						if(member.getName().equals(p.getName())) continue;
						if(member.isOnline()) {
							member.getPlayer().sendMessage(MessageEnum.Leave_Success_OwnerBroadcast.getMessage());
						}
					}
					p.sendMessage(MessageEnum.Leave_Success_Owner.getMessage());
				}else {
					HouseManager.removePlayer(house, p);
					String message = MessageEnum.Leave_Success_UserBroadcast.getMessage().replace("%Playername%", p.getName());
					for(OfflinePlayer member : house.getMember()) {
						member.getPlayer().sendMessage(message);
					}
					p.sendMessage(MessageEnum.Leave_Success_User.getMessage());
				}
				break;
			}
			case "create":
				if(!p.hasPermission("house.admin")) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				Location[] locs = AreaSelectListener.getSelected(p);
				if(locs == null) {
					p.sendMessage(MessageEnum.House_Create_Fail_ThereIsNoRegion.getMessage());
					return true;
				}
				if(HouseManager.isExists(args[1])) {
					p.sendMessage(MessageEnum.House_Create_Fail_AlreadyExists.getMessage());
					return true;
				}
				HouseManager.createHouse(args[1], locs[0], locs[1]);
				p.sendMessage(MessageEnum.House_Create_Success.getMessage().replace("%House_Name%", args[1]));
				break;
			case "delete":
				if(!p.hasPermission("house.admin")) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				if(!HouseManager.isExists(args[1])) {
					p.sendMessage(MessageEnum.House_Delete_FailInvaildHouse.getMessage());
					return true;
				}
				HouseManager.deleteHouse(args[1]);
				p.sendMessage(MessageEnum.House_Delete_Success.getMessage().replace("%House_Name%", args[1]));
				break;
			case "buysign": {
				if(!p.hasPermission("house.admin")) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				if(!HouseManager.isExists(args[1])) {
					p.sendMessage(MessageEnum.BuySign_Set_Fail_InvalidHouse.getMessage());
					return true;
				}
				Block block = p.getTargetBlock(null, 100);
				if(block == null || !block.getType().toString().contains("SIGN")) {
					p.sendMessage(MessageEnum.BuySign_Set_Fail_NoSign.getMessage());
					return true;
				}
				if(HouseManager.setSignLocation(args[1], block.getLocation())) {
					p.sendMessage(MessageEnum.BuySign_Set_Success.getMessage());
				}else {
					p.sendMessage(MessageEnum.BuySign_Set_Fail_TheSignIsAlreadySet.getMessage());
				}
				break;
			}
			case "door": {
				if(!p.hasPermission("house.admin")) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				if(!HouseManager.isExists(args[1])) {
					p.sendMessage(MessageEnum.Door_Set_Fail_InvalidHouse.getMessage());
					return true;
				}
				Block block = p.getTargetBlock(null, 100);
				if(block == null || !block.getType().toString().contains("DOOR")) {
					p.sendMessage(MessageEnum.Door_Set_Fail_NoDoor.getMessage());
					return true;
				}
				if(HouseManager.setDoorLocation(args[1], block.getLocation())) {
					p.sendMessage(MessageEnum.Door_Set_Success.getMessage());
				}else {
					p.sendMessage(MessageEnum.Door_Set_Fail_TheSignIsAlreadySet.getMessage());
				}
				break;
			}
			case "bed":
				if(!p.hasPermission("house.admin")) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				if(args.length < 2) {
					p.sendMessage(MessageEnum.info_InvalidUsage.getMessage());
					return true;
				}
				if(!HouseManager.isExists(args[1])) {
					p.sendMessage(MessageEnum.Bed_Set_Fail_InvalidHouse.getMessage());
					return true;
				}
				Block block = p.getTargetBlock(null, 100);
				if(block == null || (block.getType() != Material.BED && block.getType() != Material.BED_BLOCK)) {
					p.sendMessage(MessageEnum.Bed_Set_Fail_NoBed.getMessage());
					return true;
				}
				if(HouseManager.setBedLocation(args[1], block.getLocation())) {
					p.sendMessage(MessageEnum.Bed_Set_Success.getMessage());
				}else {
					p.sendMessage(MessageEnum.Bed_Set_Fail_TheSignIsAlreadySet.getMessage());
				}
				break;
			case "reload":
				if(!p.hasPermission("house.admin")) {
					p.sendMessage(MessageEnum.info_NoPermission.getMessage());
					return true;
				}
				instance.loadConfig();
				p.sendMessage(MessageEnum.House_Reload_Success.getMessage());
				break;
			}
		}
		return true;
	}


}
