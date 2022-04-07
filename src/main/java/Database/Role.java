package Database;

import java.io.Serializable;
import java.util.Objects;

public abstract class Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleName;
	private boolean deleteChatPermission;
	private boolean removeUserPermission;
	private boolean roomTypePermission;
	private boolean giveRolePermission;
	private boolean invitePermission;
	private boolean deleteRoomPermission;
	private boolean createChatLogPermission;
	private boolean deleteChatLogPermission;
	private boolean lockChatLogPermission;
	
	public Role() {
		roleName = "<Default Name>";
		deleteChatPermission = false;
		removeUserPermission = false;
		roomTypePermission = false;
		giveRolePermission = false;
		invitePermission = false;
		deleteRoomPermission = false;
		createChatLogPermission = false;
		deleteChatLogPermission = false;
		lockChatLogPermission = false;
	}
	
	public boolean isLockChatLogPermission()
	{
		return lockChatLogPermission;
	}

	public void setLockChatLogPermission(boolean lockChatLogPermission)
	{
		this.lockChatLogPermission = lockChatLogPermission;
	}

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

	@Override
	public int hashCode()
	{
		return Objects.hash(createChatLogPermission, deleteChatLogPermission, deleteChatPermission,
				deleteRoomPermission, giveRolePermission, invitePermission, lockChatLogPermission, removeUserPermission,
				roleName, roomTypePermission);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return createChatLogPermission == other.isCreateChatLogPermission()
				&& deleteChatLogPermission == other.isDeleteChatLogPermission()
				&& deleteChatPermission == other.isDeleteChatPermission()
				&& deleteRoomPermission == other.isDeleteRoomPermission() 
				&& giveRolePermission == other.isGiveRolePermission()
				&& invitePermission == other.isInvitePermission() 
				&& lockChatLogPermission == other.isLockChatLogPermission()
				&& removeUserPermission == other.isRemoveUserPermission() 
				&& Objects.equals(roleName, other.getRoleName())
				&& roomTypePermission == other.isRoomTypePermission();
	}
	
	public boolean equals(Object obj1, Object obj2)
	{
		if (obj1 == obj2)
			return true;
		if (obj2 == null || obj1 == null)
			return false;
		if (obj1.getClass() != obj2.getClass())
			return false;
		
		Role other = (Role) obj2;
		Role beingComparedTo = (Role) obj1;
		return beingComparedTo.isCreateChatLogPermission() == other.isCreateChatLogPermission()
				&& beingComparedTo.isDeleteChatLogPermission() == other.isDeleteChatLogPermission()
				&& beingComparedTo.isDeleteChatPermission() == other.isDeleteChatPermission()
				&& beingComparedTo.isDeleteRoomPermission() == other.isDeleteRoomPermission() 
				&& beingComparedTo.isGiveRolePermission() == other.isGiveRolePermission()
				&& beingComparedTo.isInvitePermission() == other.isInvitePermission()
				&& beingComparedTo.isLockChatLogPermission() == other.isLockChatLogPermission()
				&& beingComparedTo.isRemoveUserPermission() == other.isRemoveUserPermission() 
				&& Objects.equals(beingComparedTo.getRoleName(), other.getRoleName())
				&& beingComparedTo.isRoomTypePermission() == other.isRemoveUserPermission();
	}
	
}
