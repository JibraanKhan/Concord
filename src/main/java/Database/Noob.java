package Database;

import java.io.Serializable;

public class Noob extends Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
