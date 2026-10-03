#pragma once
// A user's wishlist: products they are interested in.
#include <vector>
#include "Fwd.h"

class Wishlist {
private:
    int wishlistId;
    std::vector<ProductPtr> products;

public:
    explicit Wishlist(int wishlistId);

    bool addProduct(const ProductPtr& product);
    bool removeProduct(const ProductPtr& product);
    void displayWishlist() const;
    std::vector<ProductPtr>& getProducts() { return products; }
};
