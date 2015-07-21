package com.bourgadix.services;

import com.bourgadix.dao.Role;

public interface UsersService {

	public Message addUser(int us,String username, String password, Boolean enabled,
			String email);

	public Message updateUserInfo(int us,int u,String username, String password, Boolean enabled,
			String email);

	public String cryptPassword(String p);

	public void addRoleToUser(Role role,int u);

	public void removeRoleToUser(Role role,int u);
}
