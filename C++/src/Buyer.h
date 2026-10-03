#pragma once
// A Buyer user. They can manage their cart and wishlist, and place orders.
#include <string>
#include <vector>
#include "Cart.h"
#include "Fwd.h"
#include "User.h"
#include "Wishlist.h"

class Buyer : public User {
private:
    Cart cart;
    Wishlist wishlist;
    std::vector<OrderPtr> orderHistory;
    std::vector<CustomerCarePtr> tickets;

    BuyerPtr self();   // shared_ptr to this buyer

public:
    Buyer(int userId, const std::string& name, const std::string& email,
          const std::string& password, const std::string& phone,
          const std::string& address);

    // Cart methods
    void addToCart(const ProductPtr& product, int quantity);
    void removeFromCart(const ProductPtr& product);
    void viewCart() const;
    Cart& getCart() { return cart; }

    // Wishlist methods
    void addToWishlist(const ProductPtr& product);
    void removeFromWishlist(const ProductPtr& product);
    void moveToWishlist(const ProductPtr& product);
    void viewWishlist() const;
    Wishlist& getWishlist() { return wishlist; }
    void removeProductReferences(const ProductPtr& product);

    // Order methods
    OrderPtr placeOrder();          // places the order directly from the cart
    OrderPtr startDirectOrder();    // starts a direct order without using the cart
    void addOrderToHistory(const OrderPtr& order);
    std::vector<OrderPtr>& getOrderHistory() { return orderHistory; }
    void viewOrderHistory() const;
    bool hasPurchased(const ProductPtr& product) const;
    std::vector<ProductPtr> getPurchasedProducts() const;
    void giveReview(const ProductPtr& product, int rating, const std::string& comment);

    // Customer care methods
    CustomerCarePtr raiseTicket(const std::string& issue);
    void viewTickets() const;
    std::vector<CustomerCarePtr>& getTickets() { return tickets; }

    std::string toString() const override;
};
