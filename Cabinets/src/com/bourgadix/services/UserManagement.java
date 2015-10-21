package com.bourgadix.services;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Role;
import com.bourgadix.dao.User;

public class UserManagement implements UsersService {

	private DaoService dao = new Dao();
	private static Logger logger = LogManager.getLogger(UserManagement.class);
	private static Level VERBOSE = Level.forName("VERBOSE", 190);
	private AccessService access = new AccessManagement();

	@Override
	public Message addUser(int us, String username, String password,
			Boolean enabled, String email) {
		// TODO Auto-generated method stub
		Message message = new Message();
		if (!access.canUpdateUsers(us)) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		} else {
			User user = new User();
			user.setEmail(email);
			user.setEnabled(enabled);
			user.setUsername(username);
			user.setPassword(cryptPassword(password));
			try {
				dao.save(user);
				StringBuilder builder = new StringBuilder();
				builder.append("Username:=>" + username);
				builder.append("\n");
				builder.append("email=>" + email);
				logger.log(VERBOSE, builder);
				message.setMessage("User added with success");
				message.setValue(true);
				return message;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.log(VERBOSE, "Unable to add user, already exist");
				message.setMessage("Unable to add user, already exist");
				message.setValue(false);
				return message;
			}
		}
	}

	public Boolean isExisted(String username, String email) {
		if (dao.getByProperty(User.class, "username", username).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Message updateUserInfo(int us, int u, String username,
			String password, Boolean enabled, String email) {
		// TODO Auto-generated method stub
		Message message = new Message();
		if (!access.canUpdateUsers(us)) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		} else {
			try {
				User user = dao.get(User.class, u);
				if (username != null) {
					user.setUsername(username);
					logger.log(VERBOSE, "User " + user.getUsername()
							+ " has changed its username to " + username);
				}
				if (email != null) {
					user.setEmail(email);
					logger.log(VERBOSE, "User " + user.getUsername()
							+ " has changed its email to " + email);
				}
				if (enabled != null) {
					user.setEnabled(enabled);
					logger.log(VERBOSE, "User " + user.getUsername()
							+ " has changed its status to " + enabled);
				}
				if (password != null) {
					user.setPassword(cryptPassword(password));
					logger.log(VERBOSE, "User " + user.getUsername()
							+ " has changed its password");
				}
				dao.update(user);
				message.setMessage("User has been changed");
				message.setValue(true);
				return message;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.log(VERBOSE, "User Id not found");
				message.setMessage("An error has occured while updating the user");
				message.setValue(false);
				return message;
			}
		}
	}

	public String cryptPassword(String p) {
		return getHash(p, "SHA-512");
	}

	public static String getHash(String txt, String hashType) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance(hashType);
			byte[] array = md.digest(txt.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			// error action
		}
		return null;
	}

	@Override
	public void addRoleToUser(Role role, int u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRoleToUser(Role role, int u) {
		// TODO Auto-generated method stub

	}

}
