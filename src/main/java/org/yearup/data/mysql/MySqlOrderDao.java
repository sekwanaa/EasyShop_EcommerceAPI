package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {
    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(int userId, Profile profile, ShoppingCart shoppingCart) {
        String order_insertSql = "INSERT INTO orders (user_id, date, address, city, state, zip, shipping_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String order_item_line_insertSql = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) VALUES (?, ?, ?, ?, ?)";
        Date date = Date.valueOf(LocalDate.now());
        try (Connection connection = getConnection()) {
            PreparedStatement order_ps = connection.prepareStatement(order_insertSql, Statement.RETURN_GENERATED_KEYS);
            order_ps.setInt(1, userId);
            order_ps.setDate(2, date);
            order_ps.setString(3, profile.getAddress());
            order_ps.setString(4, profile.getCity());
            order_ps.setString(5, profile.getState());
            order_ps.setString(6, profile.getZip());
            order_ps.setDouble(7, 5.00);

            order_ps.execute();

            ResultSet order_keys = order_ps.getGeneratedKeys();
            int order_id = 0;

            if (order_keys.next()) {
                order_id = order_keys.getInt(1);
            }

            for (Map.Entry<Integer, ShoppingCartItem> item : shoppingCart.getItems().entrySet()) {
                PreparedStatement order_item_line_ps = connection.prepareStatement(order_item_line_insertSql);
                order_item_line_ps.setLong(1, order_id);
                order_item_line_ps.setInt(2, item.getKey());
                order_item_line_ps.setBigDecimal(3, item.getValue().getLineTotal());
                order_item_line_ps.setInt(4, item.getValue().getQuantity());
                order_item_line_ps.setBigDecimal(5, item.getValue().getDiscountPercent());

                order_item_line_ps.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
