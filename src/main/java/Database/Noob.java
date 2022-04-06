package Database;

public class Noob extends Role
{
	public Noob() {
		super.setRoleName("Admin");
		super.setCreateChatLogPermission(false);
		super.setDeleteChatLogPermission(false);
		super.setDeleteChatPermission(false);
		super.setDeleteRoomPermission(false);
		super.setGiveRolePermission(false);
		super.setInvitePermission(false);
		super.setRemoveUserPermission(false);
		super.setRoomTypePermission(false); 
		super.setLockChatLogPermission(false);
	}
	
	public boolean equals(Object obj)
	{
		return super.equals(this, obj);
	}
}
