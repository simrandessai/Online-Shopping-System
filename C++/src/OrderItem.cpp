#include "OrderItem.h"
#include "Product.h"
#include "Utils.h"

OrderItem::OrderItem(ProductPtr product, int quantity)
    : product(std::move(product)), quantity(quantity), price(this->product->getPrice()) {}

std::string OrderItem::toString() const {
    return product->getProductName() + " | Qty : " + std::to_string(quantity) +
           " | Price : Rs " + util::num(price) +
           " | Total : Rs " + util::num(getSubTotal());
}
