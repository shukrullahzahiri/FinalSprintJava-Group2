package com.ecommerce;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void register(String username, String password, String email, String role) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, email, role);
        userDAO.addUser(user);
    }

    public User login(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
