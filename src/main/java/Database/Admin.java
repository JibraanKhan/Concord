package Database;

import java.io.Serializable;

public class Admin extends Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		super.setLockChatLogPermission(true);
	}
	
	public boolean equals(Object obj)
	{
		return super.equals(this, obj);
	}
}
