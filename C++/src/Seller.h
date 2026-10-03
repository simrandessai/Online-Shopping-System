#pragma once
// A Seller user. They can manage their products.
#include <string>
#include <vector>
#include "Fwd.h"
#include "User.h"

class Seller : public User {
private:
    std::vector<ProductPtr> products;

public:
    Seller(int userId, const std::string& name, const std::string& email,
           const std::string& password, const std::string& phone,
           const std::string& address);

    void addProduct(ProductPtr product);
    void removeProduct(const ProductPtr& product);
    void viewProducts() const;
    std::vector<ProductPtr>& getProducts() { return products; }
};
