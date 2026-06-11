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
                INSERT INTO products(name, price, stock) VALUES (?, ?, ?)
                """;

        try (
                Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getPrice());
            pstmt.setInt(3, product.getStock());
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

    public boolean existsById(Long id) {
        String sql = """
                SELECT COUNT(*) FROM products WHERE id = ?
                """;

        try (
                Connection con = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
                ) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public int update(Long productId, ProductDto product) {
        String sql = """
                UPDATE products
                SET
                    name = ?,
                    price = ?,
                    stock = ?
                WHERE
                   id = ? 
                """;

        try (
                Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getPrice());
            pstmt.setInt(3, product.getStock());
            pstmt.setLong(4, productId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        String sql = """
                DELETE FROM products
                WHERE id = ?
                """;

        try(
                Connection con = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
                ) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
