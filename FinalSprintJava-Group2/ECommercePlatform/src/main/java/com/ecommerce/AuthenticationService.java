package main.java.com.ecommerce;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthenticationService {
    private UserDAO userDAO = new UserDAO();
    private Scanner scanner = new Scanner(System.in);

    public User login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                System.out.println("Invalid credentials.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter role (buyer, seller, admin):");
        String role = scanner.nextLine();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        try {
            userDAO.addUser(user);
            System.out.println("Registration successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
