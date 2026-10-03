#pragma once
// Groups products into categories for organisation and filtering.
#include <initializer_list>
#include <string>
#include <vector>
#include "Fwd.h"

class Category {
private:
    int categoryId;
    std::string categoryName;

    // One Category contains many Products
    std::vector<ProductPtr> products;

public:
    Category(int categoryId, const std::string& categoryName);

    void addProduct(ProductPtr product);
    void addProduct(std::initializer_list<ProductPtr> productsToAdd);  // add several at once
    void removeProduct(const ProductPtr& product);
    void displayProducts() const;

    int getCategoryId() const { return categoryId; }
    void setCategoryId(int id) { categoryId = id; }
    const std::string& getCategoryName() const { return categoryName; }
    void setCategoryName(const std::string& n) { categoryName = n; }
    std::vector<ProductPtr>& getProducts() { return products; }

    std::string toString() const;
};
