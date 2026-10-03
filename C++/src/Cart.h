#pragma once
/**
 * A shopping cart, holding items before they're checked out into an order.
 * Buyers have their own cart; visitors who haven't signed in use a temporary
 * guest cart that is merged into their cart when they sign in.
 */
#include <vector>
#include "CartItem.h"
#include "Fwd.h"

class Cart {
private:
    int cartId;
    std::vector<CartItem> items;

public:
    explicit Cart(int cartId);

    bool addProduct(const ProductPtr& product, int quantity);
    bool removeProduct(const ProductPtr& product);
    double calculateTotal() const;
    void displayCart() const;
    void clearCart();
    std::vector<CartItem>& getItems() { return items; }
};
