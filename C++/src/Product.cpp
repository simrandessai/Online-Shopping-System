#include "Product.h"
#include <iostream>
#include "Category.h"
#include "Review.h"
#include "Seller.h"
#include "Utils.h"

Product::Product(int productId, const std::string& productName,
                 const std::string& description, double price, int stock,
                 CategoryPtr category, SellerPtr seller)
    : productId(productId), productName(productName), description(description),
      price(price), stock(stock), category(std::move(category)),
      seller(std::move(seller)) {}

// Adds (or, with a negative number, removes) stock. Never goes below zero.
void Product::updateStock(int quantity) {
    if (stock + quantity < 0) {
        std::cout << "Stock cannot go below zero.\n";
        return;
    }
    stock += quantity;
}

// Reduces stock for a sale. Returns true if the stock was reduced.
bool Product::reduceStock(int quantity) {
    if (quantity <= 0) {
        std::cout << "Quantity must be greater than zero.\n";
        return false;
    }
    if (stock < quantity) {
        std::cout << "Insufficient Stock.\n";
        return false;
    }
    stock -= quantity;
    return true;
}

void Product::addReview(ReviewPtr review) {
    if (review) reviews.push_back(std::move(review));
}

void Product::removeReview(const ReviewPtr& review) {
    util::removeOne(reviews, review);
}

void Product::displayReviews() const {
    std::cout << "\nREVIEWS\n";
    if (reviews.empty()) {
        std::cout << "No Reviews Available.\n";
        return;
    }
    for (const auto& review : reviews) std::cout << review->toString() << "\n";
}

double Product::getAverageRating() const {
    if (reviews.empty()) return 0;
    double total = 0;
    for (const auto& review : reviews) total += review->getRating();
    return total / reviews.size();
}

void Product::setPrice(double p) {
    if (p < 0) {
        std::cout << "Price cannot be negative.\n";
        return;
    }
    price = p;
}

void Product::setStock(int s) {
    if (s < 0) {
        std::cout << "Stock cannot be negative.\n";
        return;
    }
    stock = s;
}

std::string Product::toString() const {
    return "\nPRODUCT"
           "\nProduct ID : " + std::to_string(productId) +
           "\nName       : " + productName +
           "\nDescription: " + description +
           "\nPrice      : Rs " + util::num(price) +
           "\nStock      : " + std::to_string(stock) +
           "\nCategory   : " + (category ? category->getCategoryName() : "N/A") +
           "\nSeller     : " + (seller ? seller->getName() : "N/A") +
           "\nRating     : " + util::fixed1(getAverageRating()) + "/5";
}
