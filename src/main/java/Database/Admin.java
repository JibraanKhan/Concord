package Database;

public class Admin extends Role
{
	public Admin() {
		super.setRoleName("Admin");
		//Admins can do everything, they're the owner of the Room.
		super.setCreateChatLogPermission(true);
		super.setDeleteChatLogPermission(true);
		super.setDeleteChatPermission(true);
		super.setDeleteRoomPermission(true);
		super.setGiveRolePermission(true);
		super.setInvitePermission(true);
		super.setRemoveUserPermission(true);
		super.setRoomTypePermission(true); 
	}
}
