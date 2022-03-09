package Database;

public class UserCreatedRole extends Role
{
	public UserCreatedRole(Boolean[] permissions, String name) {
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
	}
	
	public UserCreatedRole(Boolean[] permissions) {
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
	}
}
