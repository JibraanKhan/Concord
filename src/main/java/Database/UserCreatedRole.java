package Database;

import java.io.Serializable;

public class UserCreatedRole extends Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserCreatedRole() {
		this(new boolean[9], "<Default RoleName>");
	}
	
	public UserCreatedRole(boolean[] permissions, String name) {
		/*
		 *[
			Can user delete any chat messages besides their own?
			Can user remove other users?
			Can user set the roomType?
			Can user give roles to other users & themselves?
			Can user invite others to the chat room?
			Can user create ChatLogs?
			Can user delete ChatLogs?
			Can user delete Rooms?
			Can user lock chatLogs?
		  ]
		*/
		super.setRoleName(name);
		super.setDeleteChatPermission(permissions[0]);
		super.setRemoveUserPermission(permissions[1]);
		super.setRoomTypePermission(permissions[2]);
		super.setGiveRolePermission(permissions[3]);
		super.setInvitePermission(permissions[4]);
		super.setCreateChatLogPermission(permissions[5]);
		super.setDeleteChatLogPermission(permissions[6]);
		super.setDeleteRoomPermission(permissions[7]);
		super.setLockChatLogPermission(permissions[8]);
	}
	
	public UserCreatedRole(boolean[] permissions) {
		/*
		 *[
			Can user delete any chat messages besides their own?
			Can user remove other users?
			Can user type in the chat logs?
			Can user give roles to other users & themselves?
			Can user invite others to the chat room?
			Can user create ChatLogs?
			Can user delete ChatLogs?
			Can user delete Rooms?
			Can user lock chatLogs?
		  ]
		*/
		super.setRoleName("<Untitled Role>");
		super.setDeleteChatPermission(permissions[0]);
		super.setRemoveUserPermission(permissions[1]);
		super.setRoomTypePermission(permissions[2]);
		super.setGiveRolePermission(permissions[3]);
		super.setInvitePermission(permissions[4]);
		super.setCreateChatLogPermission(permissions[5]);
		super.setDeleteChatLogPermission(permissions[6]);
		super.setDeleteRoomPermission(permissions[7]);
		super.setLockChatLogPermission(permissions[8]);
	}
	
	public boolean equals(Object obj)
	{
		return super.equals(this, obj);
	}
}
