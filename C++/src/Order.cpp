#include "Order.h"
#include <algorithm>
#include <iostream>
#include "Buyer.h"
#include "Payment.h"
#include "Product.h"
#include "Utils.h"

int Order::nextOrderId = 1;

Order::Order(BuyerPtr buyer)
    : orderId(nextOrderId++), buyer(std::move(buyer)), orderDate(util::today()),
      orderStatus(OrderStatus::PENDING) {}

// Stock is only checked here; it is deducted once payment succeeds.
void Order::addItem(const ProductPtr& product, int quantity) {
    if (!product || quantity <= 0) {
        std::cout << "Product and quantity must be valid.\n";
        return;
    }
    int alreadyInOrder = 0;
    for (const auto& item : items) {
        if (item.getProduct()->getProductId() == product->getProductId())
            alreadyInOrder += item.getQuantity();
    }
    if (product->getStock() < alreadyInOrder + quantity) {
        std::cout << "Insufficient stock for " << product->getProductName() << "\n";
        return;
    }
    items.emplace_back(product, quantity);
}

void Order::removeItem(const ProductPtr& product) {
    items.erase(std::remove_if(items.begin(), items.end(), [&](const OrderItem& item) {
                    return item.getProduct()->getProductId() == product->getProductId();
                }),
                items.end());
}

double Order::calculateTotal() const {
    double total = 0;
    for (const auto& item : items) total += item.getSubTotal();
    return total;
}

// On success: stock is reduced and the order becomes CONFIRMED.
// On failure: the order stays PENDING and no stock is deducted.
bool Order::makePayment(PaymentMethod method) {
    if (orderStatus == OrderStatus::CONFIRMED) {
        std::cout << "This order has already been paid.\n";
        return false;
    }
    if (orderStatus == OrderStatus::CANCELLED) {
        std::cout << "This order has been cancelled.\n";
        return false;
    }
    if (items.empty()) {
        std::cout << "Cannot process payment for an empty order.\n";
        return false;
    }
    payment = std::make_shared<Payment>(orderId, method, calculateTotal());
    bool paid = payment->processPayment();
    if (paid) {
        for (auto& item : items) item.getProduct()->reduceStock(item.getQuantity());
        orderStatus = OrderStatus::CONFIRMED;
    }
    return paid;
}

void Order::displayOrder() const {
    std::cout << "\n ORDER \n";
    std::cout << "Order ID   : " << orderId << "\n";
    std::cout << "Buyer      : " << buyer->getName() << "\n";
    std::cout << "Order Date : " << orderDate << "\n";
    std::cout << "\n";
    for (const auto& item : items) std::cout << item.toString() << "\n";
    std::cout << "Total Amount : Rs " << util::num(calculateTotal()) << "\n";
    std::cout << "Status       : " << ::toString(orderStatus) << "\n";
    if (payment) std::cout << payment->toString() << "\n";
}
