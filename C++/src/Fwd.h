#pragma once
#include <memory>

// Forward declarations + shared_ptr aliases (Java references -> shared_ptr)
class User;
class Admin;
class Buyer;
class Seller;
class Product;
class Category;
class Cart;
class Wishlist;
class Order;
class Review;
class CustomerCare;
class Payment;

using UserPtr         = std::shared_ptr<User>;
using AdminPtr        = std::shared_ptr<Admin>;
using BuyerPtr        = std::shared_ptr<Buyer>;
using SellerPtr       = std::shared_ptr<Seller>;
using ProductPtr      = std::shared_ptr<Product>;
using CategoryPtr     = std::shared_ptr<Category>;
using OrderPtr        = std::shared_ptr<Order>;
using ReviewPtr       = std::shared_ptr<Review>;
using CustomerCarePtr = std::shared_ptr<CustomerCare>;
using PaymentPtr      = std::shared_ptr<Payment>;
