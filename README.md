<div align="center">

# EasyShop Ecommerce API 
  
</div>

<p>
  Welcome to the EasyShop Ecommerce API repository! This project is a comprehensive Java-based application designed to manage an ecommerce website's functionality.

  ### Table of Contents
* [Features](https://github.com/sekwanaa/EasyShop_EcommerceAPI?tab=readme-ov-file#features)
* [A look into the application](https://github.com/sekwanaa/EasyShop_EcommerceAPI?tab=readme-ov-file#a-look-into-the-application)
* [Check out the website!](https://github.com/sekwanaa/EasyShop_EcommerceAPI?tab=readme-ov-file#checkout-the-website)
* [Interesting piece of code](https://github.com/sekwanaa/EasyShop_EcommerceAPI?tab=readme-ov-file#interesting-piece-of-code)
* [Error handling](https://github.com/sekwanaa/EasyShop_EcommerceAPI?tab=readme-ov-file#error-handling)
  
## Features
### Login / Register
* Using SpringBoot JWT security, we are able to store users in the database with a hashed password.
### Filtering Products
* Using modules created from the front end, the API interacts with the database to retrieve relevant products.
  * Filter by Category
  * Filter by Min Price
  * Filter by Max Price
  * Filter by Color
### Adding to cart
* Logged in users are able to add items to their shopping cart.
  * The user cart is linked to their login, so it will be persistent even if they log out.
### Order Processing
* Create Orders: Generate new order based on the items in the user's shopping cart...

## A Look Into the Application

### File Structure / EasyShopApplication.java
![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/49284580-17f5-4162-af84-34aeb5648868)

### SpringBoot AppConfig
![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/b1ce74a9-39a6-420d-ae49-01688fc2265b)

<details>

<summary>
  
  ## Checkout the website!
  
</summary>

### Home page not logged in
> This is the homepage when a user is not logged, in. They can view the products, but cannot add them to a cart.

![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/11f69233-0c41-4a8c-84a2-df35d645c994)

### Home page logged in
> After logging in, a user is able to add items to their cart.

![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/75c7e2b9-7612-4053-9acd-e35765211cd0)

### Cart
> The user's current cart.

![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/bdef7443-e25d-42ae-aa8f-ecb73862a3a6)

### Checkout
> Creating an order for the user based on items in the cart.

![image](https://github.com/sekwanaa/EasyShop_EcommerceAPI/assets/112197395/32bdca17-6fcb-4b49-b576-0db81b347209)

### Clear
> Clearing the cart if a user does not want to checkout.

![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/c6575ac7-53cc-4575-a816-3b0cc337b98b)

### File Structure / index.html
![image](https://github.com/sekwanaa/Capstone3_EcommerceAPI/assets/112197395/467d77d9-0e6d-4315-a339-029ecd752797)

</details>

## Interesting Piece of Code
> I chose this piece of code because this post mapping checks the shopping cart to see whether an item is present, and if so it will update the quantity rather than a duplicate item.
``` java
@PostMapping("products/{product_id}")
    public ShoppingCart addItemToCart(Principal principal, @PathVariable int product_id) {
        try
        {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            ShoppingCart shoppingCart = getCart(principal);

            for (Map.Entry<Integer, ShoppingCartItem> item : shoppingCart.getItems().entrySet()) {
                if (item.getValue().getProductId() == product_id) {
                    shoppingCartDao.updateItemInCart(userId, product_id);
                    return getCart(principal);
                }
            }

            shoppingCartDao.addItemToCart(userId, product_id);

        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return getCart(principal);
    }
```

<details>

<summary>

## Error Handling
  
</summary>

### Failure to load the cart
> If there was an error trying to load the cart

``` javascript
.catch(error => {
	const data = {
		error: 'Load cart failed.',
	}

	templateBuilder.append('error', data, 'errors')
})
```


### Error clearing the cart
> If there was an error trying to clear the cart

``` javascript
.catch(error => {
	const data = {
		error: 'Empty cart failed.',
	}

	templateBuilder.append('error', data, 'errors')
})
```

### Error checking out shopping cart
> If there was an error checking out the user's shopping cart

``` javascript
.catch(error => {
	const data = {
		error: 'Empty cart failed.',
	}

	templateBuilder.append('error', data, 'errors')
})
```
</details>


