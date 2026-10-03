#pragma once
// An item in an order: a product, quantity, and the price at the time of ordering.
#include <string>
#include "Fwd.h"

class OrderItem {
private:
    ProductPtr product;
    int quantity;
    double price;

public:
    OrderItem(ProductPtr product, int quantity);

    ProductPtr getProduct() const { return product; }
    int getQuantity() const { return quantity; }
    double getPrice() const { return price; }
    double getSubTotal() const { return price * quantity; }
    std::string toString() const;
};
