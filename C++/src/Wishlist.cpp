#include "Wishlist.h"
#include <algorithm>
#include <iostream>
#include "Product.h"
#include "Utils.h"

Wishlist::Wishlist(int wishlistId) : wishlistId(wishlistId) {}

// Returns false if the product was null or already there.
bool Wishlist::addProduct(const ProductPtr& product) {
    if (!product || std::find(products.begin(), products.end(), product) != products.end())
        return false;
    products.push_back(product);
    return true;
}

// Returns true if the product was actually there.
bool Wishlist::removeProduct(const ProductPtr& product) {
    return util::removeOne(products, product);
}

void Wishlist::displayWishlist() const {
    std::cout << "\n WISHLIST \n";
    if (products.empty()) {
        std::cout << "Wishlist is empty.\n";
        return;
    }
    for (const auto& p : products) std::cout << p->getProductName() << "\n";
}
