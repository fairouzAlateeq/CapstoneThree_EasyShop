package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged in users should have access to these actions
@RestController
@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao){
        this.shoppingCartDao = shoppingCartDao;
    }

    // each method in this controller requires a Principal object as a parameter
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(user.getId());
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @GetMapping("{userId}")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCart getByUserId(int userId){
        return shoppingCartDao.getByUserId(userId);
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("products/{productId}")

    public ShoppingCart createShoppingCart(@RequestBody ShoppingCart shoppingCart){
        return shoppingCartDao.create(shoppingCart);
    }

//    @PostMapping("/products/{productId}")
//    public ShoppingCart addProductToCart(@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal) {
//        try {
//            String username = principal.getName();
//            User user = userDao.getByUserName(username);
//            ShoppingCart cart = shoppingCartDao.getByUserId(user.getId());
//            cart.add(item);
//            return shoppingCartDao.create(cart);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to add product to cart.");
//        }
//    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("/products/{productId}")

    public void putShoppingCart(@PathVariable int id, @RequestBody ShoppingCart shoppingCart){
        shoppingCartDao.update(id, shoppingCart);
    }

//    // add a DELETE method to clear all products from the current users cart
//    // https://localhost:8080/cart
    @DeleteMapping("{productId}")

    public void deleteShoppingCart(@PathVariable int id){
        shoppingCartDao.delete(id);
    }

}
