package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    void addItemToCart(int userId, int product_id);
    int updateItemInCart(int userId, int product_id);
    int deleteCart(int userId);
}
