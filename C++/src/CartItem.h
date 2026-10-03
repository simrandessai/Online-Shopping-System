#pragma once
// An individual item in the shopping cart: a product with its quantity.
#include <string>
#include "Fwd.h"

class CartItem {
private:
    ProductPtr product;
    int quantity;

public:
    CartItem(ProductPtr product, int quantity);

    ProductPtr getProduct() const { return product; }
    int getQuantity() const { return quantity; }
    void setQuantity(int q) { quantity = q; }
    double getTotalPrice() const;
    std::string toString() const;
};
