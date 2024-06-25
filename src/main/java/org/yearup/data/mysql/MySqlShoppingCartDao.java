package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String query = "SELECT * FROM shopping_cart AS s JOIN products AS p ON p.product_id = s.product_id WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(MySqlProductDao.mapRow(resultSet));
                item.setQuantity(resultSet.getInt("quantity"));
                shoppingCart.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shoppingCart;
    }

    @Override
    public void addItemToCart(int userId, int product_id) {
        ShoppingCart shoppingCart = getByUserId(userId);
        String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(1, userId);
            ps.setInt(2, product_id);
            ps.setInt(3, 1);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateItemInCart(int userId, int product_id) {
        String updateSql = """
                UPDATE shopping_cart
                SET
                quantity = quantity + 1
                WHERE user_id = ? AND product_id = ?;
                """;

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(updateSql);
            ps.setInt(1, userId);
            ps.setInt(2, product_id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteCart(int userId) {
        String deleteSql = """
                DELETE FROM shopping_cart
                WHERE user_id = ?;
                """;

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(deleteSql);
            ps.setInt(1, userId);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
