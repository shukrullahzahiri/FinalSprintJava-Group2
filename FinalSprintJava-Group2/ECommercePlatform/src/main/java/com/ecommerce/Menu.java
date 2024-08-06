package main.java.com.ecommerce;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private AuthenticationService authService = new AuthenticationService();
    private UserDAO userDAO = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();

    public void showMainMenu() {
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    authService.register();
                    break;
                case 2:
                    User user = authService.login();
                    if (user != null) {
                        showUserMenu(user);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showUserMenu(User user) {
        switch (user.getRole()) {
            case "buyer":
                showBuyerMenu();
                break;
            case "seller":
                showSellerMenu(user);
                break;
            case "admin":
                showAdminMenu();
                break;
            default:
                System.out.println("Invalid role.");
        }
    }

    private void showBuyerMenu() {
        while (true) {
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showSellerMenu(User user) {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View My Products");
            System.out.println("5. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addProduct(user);
                    break;
                case 2:
                    updateProduct(user);
                    break;
                case 3:
                    deleteProduct(user);
                    break;
                case 4:
                    viewMyProducts(user);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showAdminMenu() {
        while (true) {
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    viewAllProducts();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void viewProducts() {
        try {
            for (Product product : productDAO.getAllProducts()) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchProduct() {
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        try {
            for (Product product : productDAO.getAllProducts()) {
                if (product.getName().equalsIgnoreCase(name)) {
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addProduct(User user) {
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        System.out.println("Enter product price:");
        double price = scanner.nextDouble();
        System.out.println("Enter product quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setSellerId(user.getId());
        try {
            productDAO.addProduct(product);
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateProduct(User user) {
        System.out.println("Enter product ID to update:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            Product product = productDAO.getAllProducts().stream()
                    .filter(p -> p.getId() == productId && p.getSellerId() == user.getId())
                    .findFirst().orElse(null);

            if (product != null) {
                System.out.println("Enter new name:");
                String name = scanner.nextLine();
                System.out.println("Enter new price:");
                double price = scanner.nextDouble();
                System.out.println("Enter new quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline

                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);

                productDAO.addProduct(product);
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(User user) {
        System.out.println("Enter product ID to delete:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            Product product = productDAO.getAllProducts().stream()
                    .filter(p -> p.getId() == productId && p.getSellerId() == user.getId())
                    .findFirst().orElse(null);

            if (product != null) {
                productDAO.deleteProduct(productId);
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewMyProducts(User user) {
        try {
            for (Product product : productDAO.getProductsBySeller(user.getId())) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAllUsers() {
        try {
            for (User user : userDAO.getAllUsers()) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        System.out.println("Enter user ID to delete:");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            userDAO.deleteUser(userId);
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewAllProducts() {
        try {
            for (Product product : productDAO.getAllProducts()) {
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
