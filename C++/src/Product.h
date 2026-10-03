#pragma once
/**
 * A product in the online shopping system: ID, name, description, price,
 * stock, category, seller and reviews.
 */
#include <string>
#include <vector>
#include "Fwd.h"

class Product {
private:
    int productId;
    std::string productName;
    std::string description;
    double price;
    int stock;

    // Relationships
    CategoryPtr category;
    SellerPtr seller;

    // One Product can have many Reviews
    std::vector<ReviewPtr> reviews;

public:
    Product(int productId, const std::string& productName,
            const std::string& description, double price, int stock,
            CategoryPtr category, SellerPtr seller);

    // Stock methods
    void updateStock(int quantity);
    bool reduceStock(int quantity);

    // Review methods
    void addReview(ReviewPtr review);
    void removeReview(const ReviewPtr& review);
    std::vector<ReviewPtr>& getReviews() { return reviews; }
    void displayReviews() const;
    double getAverageRating() const;

    // Getters & Setters
    int getProductId() const { return productId; }
    void setProductId(int id) { productId = id; }
    const std::string& getProductName() const { return productName; }
    void setProductName(const std::string& n) { productName = n; }
    const std::string& getDescription() const { return description; }
    void setDescription(const std::string& d) { description = d; }
    double getPrice() const { return price; }
    void setPrice(double p);
    int getStock() const { return stock; }
    void setStock(int s);
    CategoryPtr getCategory() const { return category; }
    void setCategory(CategoryPtr c) { category = std::move(c); }
    SellerPtr getSeller() const { return seller; }
    void setSeller(SellerPtr s) { seller = std::move(s); }

    std::string toString() const;
};
