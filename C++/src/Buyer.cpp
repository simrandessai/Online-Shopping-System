#include "Buyer.h"
#include <iostream>
#include "CustomerCare.h"
#include "Order.h"
#include "Product.h"
#include "Review.h"
#include "Utils.h"

Buyer::Buyer(int userId, const std::string &name, const std::string &email,
             const std::string &password, const std::string &phone,
             const std::string &address)
    : User(userId, name, email, password, phone, address, Role::BUYER),
      cart(userId), wishlist(userId) {}

BuyerPtr Buyer::self()
{
    return std::static_pointer_cast<Buyer>(shared_from_this());
}

// Cart operations
void Buyer::addToCart(const ProductPtr &product, int quantity)
{
    if (cart.addProduct(product, quantity))
        std::cout << quantity << " x " << product->getProductName() << " added to cart.\n";
}

void Buyer::removeFromCart(const ProductPtr &product)
{
    if (cart.removeProduct(product))
        std::cout << product->getProductName() << " removed from cart.\n";
    else
        std::cout << "That product isn't in your cart.\n";
}

void Buyer::viewCart() const { cart.displayCart(); }

// Wishlist operations
void Buyer::addToWishlist(const ProductPtr &product)
{
    if (wishlist.addProduct(product))
        std::cout << product->getProductName() << " added to wishlist.\n";
    else
        std::cout << product->getProductName() << " is already in your wishlist.\n";
}

void Buyer::removeFromWishlist(const ProductPtr &product)
{
    if (wishlist.removeProduct(product))
        std::cout << product->getProductName() << " removed from wishlist.\n";
    else
        std::cout << "That product isn't in your wishlist.\n";
}

void Buyer::moveToWishlist(const ProductPtr &product)
{
    if (!cart.removeProduct(product))
    {
        std::cout << product->getProductName() << " is not in your cart.\n";
        return;
    }
    wishlist.addProduct(product);
    std::cout << product->getProductName() << " moved from cart to wishlist.\n";
}

void Buyer::viewWishlist() const { wishlist.displayWishlist(); }

void Buyer::removeProductReferences(const ProductPtr &product)
{
    if (!product)
        return;
    cart.removeProduct(product);
    wishlist.removeProduct(product);
}

// Order operations
OrderPtr Buyer::placeOrder()
{
    if (!isSignedIn())
    {
        std::cout << "Please log in or register before placing an order.\n";
        return nullptr;
    }
    if (cart.getItems().empty())
    {
        std::cout << "Cart is empty.\n";
        return nullptr;
    }
    // Check every item first so a half-built order is never created.
    for (auto &item : cart.getItems())
    {
        if (item.getQuantity() <= 0 || item.getQuantity() > item.getProduct()->getStock())
        {
            std::cout << "Insufficient stock for " << item.getProduct()->getProductName()
                      << ". The order was not created.\n";
            return nullptr;
        }
    }
    auto order = std::make_shared<Order>(self());
    for (auto &item : cart.getItems())
        order->addItem(item.getProduct(), item.getQuantity());
    std::cout << "Order created (status: PENDING). Proceed to payment.\n";
    return order;
}

OrderPtr Buyer::startDirectOrder()
{
    if (!isSignedIn())
    {
        std::cout << "Please log in or register before placing an order.\n";
        return nullptr;
    }
    return std::make_shared<Order>(self());
}

void Buyer::addOrderToHistory(const OrderPtr &order)
{
    if (order)
        orderHistory.push_back(order);
}

void Buyer::viewOrderHistory() const
{
    std::cout << "\n ORDER HISTORY \n";
    if (orderHistory.empty())
    {
        std::cout << "You haven't placed any orders yet.\n";
        return;
    }
    for (const auto &order : orderHistory)
        order->displayOrder();
}

bool Buyer::hasPurchased(const ProductPtr &product) const
{
    for (const auto &order : orderHistory)
    {
        if (order->getOrderStatus() == OrderStatus::CANCELLED)
            continue;
        for (const auto &item : order->getItems())
            if (item.getProduct()->getProductId() == product->getProductId())
                return true;
    }
    return false;
}

// All products the buyer has purchased, excluding cancelled orders, no duplicates.
std::vector<ProductPtr> Buyer::getPurchasedProducts() const
{
    std::vector<ProductPtr> purchased;
    for (const auto &order : orderHistory)
    {
        if (order->getOrderStatus() == OrderStatus::CANCELLED)
            continue;
        for (const auto &item : order->getItems())
        {
            ProductPtr product = item.getProduct();
            bool alreadyAdded = false;
            for (const auto &existing : purchased)
            {
                if (existing->getProductId() == product->getProductId())
                {
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded)
                purchased.push_back(product);
        }
    }
    return purchased;
}

void Buyer::giveReview(const ProductPtr &product, int rating, const std::string &comment)
{
    if (!hasPurchased(product))
    {
        std::cout << "You can only review products you have purchased.\n";
        return;
    }
    if (rating < 1 || rating > 5)
    {
        std::cout << "Rating should be between 1 and 5.\n";
        return;
    }
    for (const auto &existingReview : product->getReviews())
    {
        if (existingReview->getBuyer().get() == this)
        {
            std::cout << "You have already reviewed this product.\n";
            return;
        }
    }
    if (util::trim(comment).empty())
    {
        std::cout << "Review comment cannot be empty.\n";
        return;
    }
    auto review = std::make_shared<Review>(
        static_cast<int>(product->getReviews().size()) + 1, self(), product, rating,
        util::trim(comment));
    product->addReview(review);
    std::cout << "Review submitted successfully.\n";
}

// Customer care operations
CustomerCarePtr Buyer::raiseTicket(const std::string &issue)
{
    if (!isSignedIn())
    {
        std::cout << "Please log in before raising a support ticket.\n";
        return nullptr;
    }
    if (util::trim(issue).empty())
    {
        std::cout << "Issue description cannot be empty.\n";
        return nullptr;
    }
    auto ticket = std::make_shared<CustomerCare>(util::trim(issue), shared_from_this());
    tickets.push_back(ticket);
    std::cout << "Support ticket raised. Your Ticket ID is " << ticket->getTicketId() << ".\n";
    return ticket;
}

void Buyer::viewTickets() const
{
    std::cout << "\n MY SUPPORT TICKETS \n";
    if (tickets.empty())
    {
        std::cout << "You haven't raised any support tickets.\n";
        return;
    }
    for (const auto &ticket : tickets)
        std::cout << ticket->toString() << "\n";
}

std::string Buyer::toString() const
{
    return "\n BUYER \n" + User::toString();
}
