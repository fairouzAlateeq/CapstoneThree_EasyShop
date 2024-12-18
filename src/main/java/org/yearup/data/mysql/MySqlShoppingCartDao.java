package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

@Component
public class MySqlShoppingCartDao implements ShoppingCartDao {
    private DataSource dataSource;

    @Autowired
    public MySqlShoppingCartDao(DataSource datasource) {
        this.dataSource = datasource;
    }


    @Override
    public ShoppingCart getByUserId(int userId) {
        String query = "SELECT * FROM shopping_cart WHERE user_id = ?;";
        ShoppingCart shoppingCart = new ShoppingCart();
        try (
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.getProductId();
                    item.setQuantity(quantity);
                    shoppingCart.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingCart;
    }
    @Override
    public ShoppingCart create(ShoppingCart shoppingcart) {
        String query = "INSERT INTO shopping_cart(`product_id`, `quantity`) VALUES(?,?);";
        try (
                Connection connection = this.dataSource.getConnection();
              //  PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            for (ShoppingCartItem item : shoppingcart.getItems().values()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, item.getProductId());
                    preparedStatement.setInt(2, item.getQuantity());
                    preparedStatement.executeUpdate();
                }
            }
            //int rows = preparedStatement.executeUpdate();
//
//            if (rows > 0) {
//                try (
//                        ResultSet resultSet = preparedStatement.getGeneratedKeys()
//                ) {
//                    if (resultSet.next()) {
//                        shoppingcart.setUserId(resultSet.getInt(1));
//                        return shoppingcart;
//                    }
//                }
//            }
            return shoppingcart;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int id, ShoppingCart shoppingCart) {
        String query = "UPDATE shopping_cart SET product_id =?,  quantity=? WHERE user_id=?;";
        try (
                Connection connection = this.dataSource.getConnection();
                //  PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            for (ShoppingCartItem item : shoppingCart.getItems().values()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, item.getProductId());
                    preparedStatement.setInt(2, item.getQuantity());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int userId) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ?;";

        try (
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, userId);
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted for user_id " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
