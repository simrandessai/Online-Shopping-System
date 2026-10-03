#include "Seller.h"
#include <iostream>
#include "Product.h"
#include "Utils.h"

Seller::Seller(int userId, const std::string& name, const std::string& email,
               const std::string& password, const std::string& phone,
               const std::string& address)
    : User(userId, name, email, password, phone, address, Role::SELLER) {}

void Seller::addProduct(ProductPtr product) {
    products.push_back(std::move(product));
}

void Seller::removeProduct(const ProductPtr& product) {
    util::removeOne(products, product);
}

void Seller::viewProducts() const {
    std::cout << "\nSeller Products\n";
    if (products.empty()) {
        std::cout << "No products available.\n";
        return;
    }
    for (const auto& p : products) std::cout << p->toString() << "\n";
}
