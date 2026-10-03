#include "Category.h"
#include <iostream>
#include "Product.h"
#include "Utils.h"

Category::Category(int categoryId, const std::string& categoryName)
    : categoryId(categoryId), categoryName(categoryName) {}

void Category::addProduct(ProductPtr product) {
    products.push_back(std::move(product));
}

void Category::addProduct(std::initializer_list<ProductPtr> productsToAdd) {
    for (const auto& product : productsToAdd) products.push_back(product);
}

void Category::removeProduct(const ProductPtr& product) {
    util::removeOne(products, product);
}

void Category::displayProducts() const {
    std::cout << "\nCategory : " << categoryName << "\n";
    for (const auto& p : products) std::cout << p->toString() << "\n";
}

std::string Category::toString() const {
    return "Category ID : " + std::to_string(categoryId) +
           "\nCategory : " + categoryName;
}
