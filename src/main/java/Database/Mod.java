package Database;

public class Mod extends Role
{
	public Mod() {
		super.setRoleName("Mod");
		super.setCreateChatLogPermission(false);
		super.setDeleteChatLogPermission(false);
		super.setDeleteChatPermission(true); //Mods can delete other users' messages
		super.setDeleteRoomPermission(false);
		super.setGiveRolePermission(false);
		super.setInvitePermission(true); //Mods can add users
		super.setRemoveUserPermission(true); //Mods can remove users
		super.setRoomTypePermission(true); 
		super.setLockChatLogPermission(false); //Mods can't lock chatLogs
	}
	
	public boolean equals(Object obj)
	{
		return super.equals(this, obj);
	}
}
