package repository;

import common.DBConnectionUtil;
import dto.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public void save(ProductDto product) throws SQLException {
        String sql = """
                INSERT INTO products(id, name, price, stock) VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setInt(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductDto> findAll() {
        String sql = """
                SELECT *
                FROM products
                """;
        List<ProductDto> products = new ArrayList<>();

        try (
                Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                ProductDto product = new ProductDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock")
                );
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
