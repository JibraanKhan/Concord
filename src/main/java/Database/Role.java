package Database;

public abstract class Role
{
	private String roleName;
	private boolean deleteChatPermission;
	private boolean removeUserPermission;
	private boolean roomTypePermission;
	private boolean giveRolePermission;
	private boolean invitePermission;
	private boolean deleteRoomPermission;
	private boolean createChatLogPermission;
	private boolean deleteChatLogPermission;
	
	public String getRoleName()
	{
		return roleName;
	}
	
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	
	public boolean isDeleteChatPermission()
	{
		return deleteChatPermission;
	}
	
	public void setDeleteChatPermission(boolean deleteChatPermission)
	{
		this.deleteChatPermission = deleteChatPermission;
	}
	
	public boolean isRemoveUserPermission()
	{
		return removeUserPermission;
	}
	
	public void setRemoveUserPermission(boolean removeUserPermission)
	{
		this.removeUserPermission = removeUserPermission;
	}
	
	public boolean isRoomTypePermission()
	{
		return roomTypePermission;
	}
	
	public void setRoomTypePermission(boolean roomTypePermission)
	{
		this.roomTypePermission = roomTypePermission;
	}
	
	public boolean isGiveRolePermission()
	{
		return giveRolePermission;
	}
	
	public void setGiveRolePermission(boolean giveRolePermission)
	{
		this.giveRolePermission = giveRolePermission;
	}
	
	public boolean isInvitePermission()
	{
		return invitePermission;
	}
	
	public void setInvitePermission(boolean invitePermission)
	{
		this.invitePermission = invitePermission;
	}
	
	public boolean isDeleteRoomPermission()
	{
		return deleteRoomPermission;
	}
	
	public void setDeleteRoomPermission(boolean deleteRoomPermission)
	{
		this.deleteRoomPermission = deleteRoomPermission;
	}
	
	public boolean isCreateChatLogPermission()
	{
		return createChatLogPermission;
	}
	
	public void setCreateChatLogPermission(boolean createChatLogPermission)
	{
		this.createChatLogPermission = createChatLogPermission;
	}
	
	public boolean isDeleteChatLogPermission()
	{
		return deleteChatLogPermission;
	}
	
	public void setDeleteChatLogPermission(boolean deleteChatLogPermission)
	{
		this.deleteChatLogPermission = deleteChatLogPermission;
	}
	
}
