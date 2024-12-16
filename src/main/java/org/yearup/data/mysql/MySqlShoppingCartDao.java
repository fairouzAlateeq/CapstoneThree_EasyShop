package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;

@Component
public class MySqlShoppingCartDao implements ShoppingCartDao {
    private DataSource dataSource;

    @Autowired
    public MySqlShoppingCartDao(DataSource datasource) {
        this.dataSource = datasource;
    }


    @Override
    public ShoppingCart getByUserId(int userId) {
        return null ;
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingcart) {
        return null;
    }

    @Override
    public void update(int id, ShoppingCart shoppingCart) {

    }

    @Override
    public void delete(int id) {

    }
}
