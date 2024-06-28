package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("orders")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class OrderController {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProfileDao profileDao;
    private final ShoppingCartDao shoppingCartDao;

    public OrderController(OrderDao orderDao, UserDao userDao, ProfileDao profileDao,ShoppingCartDao shoppingCartDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.shoppingCartDao = shoppingCartDao;
    }

    @PostMapping()
    public ShoppingCart checkout(Principal principal) {
        try
        {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();
            Profile profile = profileDao.getProfileById(userId);
            ShoppingCart shoppingCart = shoppingCartDao.getByUserId(userId);

            orderDao.create(userId, profile, shoppingCart);
            shoppingCartDao.deleteCart(userId);
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
