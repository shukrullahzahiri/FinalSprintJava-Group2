package com.ecommerce;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Scanner;
import java.sql.SQLException;

public class AuthenticationService {
    private UserDAO userDAO = new UserDAO();
    private Scanner scanner = new Scanner(System.in);

    public User login() throws SQLException {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        
        User user = userDAO.getUserByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }

    public void register() throws SQLException {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter role (buyer, seller, admin):");
        String role = scanner.nextLine().toUpperCase();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, email, role);

        userDAO.addUser(user);
        System.out.println("Registration successful.");
    }
}
