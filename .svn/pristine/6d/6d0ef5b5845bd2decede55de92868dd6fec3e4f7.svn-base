package com.bourgadix.services;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Role;
import com.bourgadix.dao.User;

public class AccessManagement implements AccessService {
	private DaoService dao = new Dao();

	@Override
	public Boolean canUpdateClient(int u) {
		// TODO Auto-generated method stub
		User user = dao.get(User.class, u);
		// Role role = dao.get(Role.class, 3);// Collaborator role
		int i = 0;
		for (Role role : user.getRoles()) {
			if (role.getUserRolesId() == 3) {
				i++;
			}
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean canUpdateMedicament(int u) {
		// TODO Auto-generated method stub
		User user = dao.get(User.class, u);
		Role role = dao.get(Role.class, 2);// User role

		if (user.getRoles().contains(role)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean canUpdateRdv(int u) { // A RDV is a visit too
		User user = dao.get(User.class, u);
		// Role role = dao.get(Role.class, 3);// Collaborator role
		int i = 0;
		for (Role role : user.getRoles()) {
			if (role.getUserRolesId() == 3) {
				i++;
			}
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean canUpdateUsers(int u) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		User user = dao.get(User.class, u);
		Role role = dao.get(Role.class, 1);// Manager role

		if (user.getRoles().contains(role)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		AccessManagement accessManagement = new AccessManagement();
		Dao dao = new Dao();

		System.out.println("Amine can update Client?: =>"
				+ accessManagement.canUpdateClient(4));
	}

}
