#include "CartItem.h"
#include "Product.h"
#include "Utils.h"

CartItem::CartItem(ProductPtr product, int quantity)
    : product(std::move(product)), quantity(quantity) {}

double CartItem::getTotalPrice() const {
    return product->getPrice() * quantity;
}

std::string CartItem::toString() const {
    return "ID: " + std::to_string(product->getProductId()) +
           " | " + product->getProductName() +
           " | Qty : " + std::to_string(quantity) +
           " | Total : Rs " + util::num(getTotalPrice());
}
