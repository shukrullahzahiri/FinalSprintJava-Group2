package main.java.com.ecommerce;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final String url = "jdbc:postgresql://localhost:5432/ecommerce";
    private final String user = "postgres";
    private final String password = "Kabul@123";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addProduct(Product product) throws SQLException {
        String SQL = "INSERT INTO products(name, price, quantity, seller_id) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setInt(4, product.getSellerId());
            pstmt.executeUpdate();
        }
    }

    public List<Product> getProductsBySeller(int sellerId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String SQL = "SELECT * FROM products WHERE seller_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, sellerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                products.add(product);
            }
        }
        return products;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String SQL = "SELECT * FROM products";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setSellerId(rs.getInt("seller_id"));
                products.add(product);
            }
        }
        return products;
    }

    public void deleteProduct(int productId) throws SQLException {
        String SQL = "DELETE FROM products WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        }
    }
}
