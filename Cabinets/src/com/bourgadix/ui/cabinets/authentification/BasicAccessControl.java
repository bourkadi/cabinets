package com.bourgadix.ui.cabinets.authentification;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Role;
import com.bourgadix.dao.User;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicAccessControl implements AccessControl, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7465050742840857545L;
	private DaoService dao = new Dao();

	@Override
	public boolean signIn(String username, String password) {
		if (checkUser(username, password)) {
			CurrentUser.set(username);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isUserSignedIn() {
		return !CurrentUser.get().isEmpty();
	}

	@Override
	public boolean isUserInRole(String role) {
		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) dao
				.getByProperty(User.class, "username", getPrincipalName())
				.get(0).getRoles();
		int i = 0;
		for (Role r : roles) {
			if (r.getRole().equals(role)) {
				i++;
			}
		}
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getPrincipalName() {
		return CurrentUser.get();
	}

	public Boolean checkUser(String username, String password) {
		User user = null;
		if (username == null || username.isEmpty()) {
			return false;
		}
		try {
			user = (User) dao.getByProperty(User.class, "username", username)
					.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		if (!user.getEnabled()) {
			return false;
		}
		if (BCrypt.checkpw(password, user.getPassword())) {

			return true;
		} else {
			return false;
		}
	}

}
