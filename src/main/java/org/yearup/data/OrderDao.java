package org.yearup.data;

import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

public interface OrderDao {
    void create(int userId, Profile profile, ShoppingCart shoppingCart);
}
