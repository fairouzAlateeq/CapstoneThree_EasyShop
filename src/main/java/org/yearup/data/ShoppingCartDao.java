package org.yearup.data;

import org.springframework.web.bind.annotation.PathVariable;
import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    ShoppingCart create(ShoppingCart shoppingcart);
    void update(int id, ShoppingCart shoppingCart);
    void delete(@PathVariable int id);
}
