package com.marquez.houseplugin.enums;

import org.bukkit.ChatColor;

public enum MessageEnum {

	info_Prefix("[HousePlugin] "),
	info_NoPermission("&cYou don't have permission."),
	info_NoConsole("&cThis command can not use on console."),
	info_InvalidUsage("잘못된 명령어 입력입니다."),
	House_Help_Command("&6/house help &7- &f집 명령어를 확인합니다.", "&6/house invite <playername> &7- &f해당 유저를 자신의 집 멤버로 초대합니다.", "&6/house invite accept &7- &f멤버 초대를 수락합니다.", "&6/house kick <playername> &7- &f해당 유저를 자신의 집 멤버 목록에서 제거합니다.", "&6/house info &7- &f집 정보를 확인합니다.", "&6/house teleport &7- &f집에 있는 침대로 텔레포트 합니다.", "&6/house doorautoclose <number> &7- &f문이 자동으로 닫히는 시간을 조절합니다. 단위는 초 단위 입니다.", "&6/house leave &7- &f집 멤버에서 탈퇴합니다. 소유주가 사용 할 시 모든 멤버가 탈퇴되며 집이 판매 상태로 변경됩니다."),
	House_Info_Message("&m----------------&r &e%House_Name% 집 정보 &m----------------", "&6집 주인(%House_Owner%", "&6렌탈 가격(24h)(%Price%", "&6남은 렌탈 기간(%Rental_Remaining%", "&6최대 거주 인원수(%Maximum_Member%", "&6현재 거주 인원수(%Living_Member%", "", "&6맴버 목록:", "&6%Members%"),
	House_Info_YouDoNotHaveHouse("[House] 당신은 집이 없습니다."),
	Sign_Buy("%House_Name%", "%Price%", "", ""),
	Sign_Rental("%House_Name%", "%Rental_Remaining%", "%House_Owner%", "%Living_Member% / %Maximum_Member%"),
	Select_Position_1("pos1 %Location%"),
	Select_Position_2("pos2 %Location%"),
	Invite_Success_Sender("[House] %Playername%님을 집 멤버로 초대하였습니다."),
	Invite_Success_Receiver("[House] %Playername%님이 집 멤버로 초대하였습니다."),
	Invite_Expire("[House] 초대가 만료되었습니다."),
	Invite_Fail_AlreadyInvite("[House] 당신은 이미 초대중인 사람이 존재합니다."),
	Invite_Fail_AlreadyReceive("[House] 해당 유저는 이미 초대중입니다."),
	Invite_Fail_InvalidUser("[House] 해당 유저는 존재하지 않습니다."),
	Invite_Fail_YouDoNotHaveHouse("[House] 당신은 집이 없습니다!"),
	Invite_Fail_AlreadyMember("[House] 해당 유저는 이미 집이 존재합니다!"),
	Invite_Fail_OverPeople("[House] 더 이상 집에 유저를 초대할 수 없습니다."),
	Invite_Fail_NotOwner("[House] 당신은 집 주인이 아닙니다."),
	Accept_FailNoReceive("[House] 당신은 받은 초대가 없습니다."),
	Accept_Success_Sender("[House] %Playername%님이 초대를 수락하였습니다."),
	Accept_Success_Receiver("[House] %Playername%님의 초대를 수락하였습니다."),
	Accept_Success_Broadcast("[House] %Playername%님이 집에 가입하였습니다."),
	Deny_FailNoReceive("[House] 당신은 받은 초대가 없습니다."),
	Deny_Success_Sender("[House] %Playername%님이 초대를 거절하였습니다."),
	Deny_Success_Receiver("[House] %Playername%님의 초대를 거절하였습니다."),
	Kick_Success("[House] 해당 유저를 집에서 추방하였습니다."),
	Kick_Fail_InvaildUser("[House] 해당 유저는 당신의 집에 존재하지 않습니다!"),
	Kick_Fail_NotOwner("[House] 당신은 집 주인이 아닙니다."),
	Teleport_Success("[House] 집으로 텔레포트하였습니다!"),
	Teleport_FailNotExist("[House] 집이 없거나, 텔레포트 지점이 존재하지 않습니다!"),
	Doorautoclose_Fail_NotOwner("[House] 당신은 집이 없거나 주인이 아닙니다."),
	Doorautoclose_Fail_NotNumber("[House] 입력 값이 잘못되었습니다."),
	Doorautoclose_Success("[House] 문 자동 잠금 시간을 %Time% (으)로 변경하였습니다."),
	Leave_Success_User("[House] %Playername%"),
	Leave_Success_UserBroadcast("[House] %Playername%님이 집 멤버에서 탈퇴하였습니다."),
	Leave_Success_Owner("[House] 집을 탈퇴하여 집이 삭제되었습니다."),
	Leave_Success_OwnerBroadcast("[House] 소유자가 집을 제거하였습니다. 모든 멤버가 탈퇴됩니다."),
	Leave_FailYouDoNotHaveHouse("[House] 당신은 집이 없습니다."),
	House_Create_Success("[House] %House_Name% 집을 생성하였습니다!"),
	House_Create_Fail_AlreadyExists("[House] 이미 존재하는 이름입니다."),
	House_Create_Fail_ThereIsNoRegion("[House] 집 범위가 설정되어 있지 않습니다!"),
	House_Delete_Success("[House] %House_Name% 집을 제거하였습니다!"),
	House_Delete_FailInvaildHouse("[House] 존재하지 않는 집입니다."),
	BuySign_Set_Success("[House] Buy Sign을 설정하였습니다."),
	BuySign_Set_Fail_InvalidHouse("[House] 존재하지 않는 집입니다."),
	BuySign_Set_Fail_TheSignIsAlreadySet("[House] 이미 설정한 Buy Sign입니다."),
	BuySign_Set_Fail_NoSign("[House] 해당 블럭은 Sign이 아닙니다."),
	Door_Set_Success("[House] Door을 설정하였습니다."),
	Door_Set_Fail_InvalidHouse("[House] 존재하지 않는 집입니다."),
	Door_Set_Fail_TheSignIsAlreadySet("[House] 이미 설정한 Door입니다."),
	Door_Set_Fail_NoDoor("[House] 해당 블럭은 Door이 아닙니다."),
	Bed_Set_Success("[House] Bed을 설정하였습니다."),
	Bed_Set_Fail_InvalidHouse("[House] 존재하지 않는 집입니다."),
	Bed_Set_Fail_TheSignIsAlreadySet("[House] 이미 설정한 Bed입니다."),
	Bed_Set_Fail_NoBed("[House] 해당 블럭은 Bed가 아닙니다."),
	House_Reload_Success("[House] Config 리로드에 성공하였습니다."),
	House_Rental_Expire("[House] 집 렌탈 기간이 종료되었습니다!"),
	House_Rental_Success_Default("[House] 집 렌탈에 성공하였습니다. 남은 시간 - [%Rental_Remaining%]"),
	House_Rental_Success_AppendTime("[House] 추가 렌탈에 성공하였습니다. 남은 시간 - [%Rental_Remaining%]"),
	House_Rental_Fail_InsufficientCost("[House] 비용이 부족하여 집 렌탈에 실패하였습니다."),
	House_Rental_Fail_Maximum("[House] 더 이상 집을 렌탈할 수 없습니다."),
	House_Rental_Fail_HaveHouse("[House] 당신은 집이 이미 존재합니다.");

	private String[] message;

	private MessageEnum(String... message) {
		this.message = message;
	}

	public boolean isList() {
		return message.length > 1;
	}

	public String getMessage() {
		return this.message[0];
	}

	public String[] getMessages() {
		return this.message;
	}

	public void setMessage(String... message) {
		for(int i = 0; i < message.length; i++) {
			message[i] = ChatColor.translateAlternateColorCodes('&', message[i]);
		}
		this.message = message;
	}

}

