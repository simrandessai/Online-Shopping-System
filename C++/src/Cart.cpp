#include "Cart.h"
#include <algorithm>
#include <iostream>
#include "Product.h"
#include "Utils.h"

Cart::Cart(int cartId) : cartId(cartId) {}

// Add product to cart. If the product already exists, increase its quantity.
bool Cart::addProduct(const ProductPtr& product, int quantity) {
    if (!product || quantity <= 0) return false;
    for (auto& item : items) {
        if (item.getProduct()->getProductId() == product->getProductId()) {
            if (item.getQuantity() + quantity > product->getStock()) {
                std::cout << "Cannot add more than the available stock.\n";
                return false;
            }
            item.setQuantity(item.getQuantity() + quantity);
            return true;
        }
    }
    if (quantity > product->getStock()) {
        std::cout << "Cannot add more than the available stock.\n";
        return false;
    }
    items.emplace_back(product, quantity);
    return true;
}

// Removes a product (matched by ID). Returns true if something was removed.
bool Cart::removeProduct(const ProductPtr& product) {
    if (!product) return false;
    auto it = std::remove_if(items.begin(), items.end(), [&](const CartItem& item) {
        return item.getProduct()->getProductId() == product->getProductId();
    });
    bool removed = (it != items.end());
    items.erase(it, items.end());
    return removed;
}

double Cart::calculateTotal() const {
    double total = 0;
    for (const auto& item : items) total += item.getTotalPrice();
    return total;
}

void Cart::displayCart() const {
    std::cout << "\n CART \n";
    if (items.empty()) {
        std::cout << "Cart is empty.\n";
    } else {
        for (const auto& item : items) std::cout << item.toString() << "\n";
        std::cout << "Grand Total : Rs " << util::num(calculateTotal()) << "\n";
    }
}

void Cart::clearCart() { items.clear(); }
