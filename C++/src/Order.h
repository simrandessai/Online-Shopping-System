#pragma once
// An order placed by a buyer: items, payment details and order status.
#include <string>
#include <vector>
#include "Fwd.h"
#include "OrderItem.h"
#include "OrderStatus.h"
#include "PaymentMethod.h"

class Order {
private:
    static int nextOrderId;   // static ID generator

    int orderId;
    BuyerPtr buyer;
    std::string orderDate;
    std::vector<OrderItem> items;
    PaymentPtr payment;       // null until a payment is attempted
    OrderStatus orderStatus;

public:
    // Every new order gets a unique ID and starts as PENDING.
    explicit Order(BuyerPtr buyer);

    void addItem(const ProductPtr& product, int quantity);
    void removeItem(const ProductPtr& product);
    double calculateTotal() const;
    bool makePayment(PaymentMethod method);
    void updateOrderStatus(OrderStatus status) { orderStatus = status; }
    void displayOrder() const;

    int getOrderId() const { return orderId; }
    BuyerPtr getBuyer() const { return buyer; }
    const std::string& getOrderDate() const { return orderDate; }
    std::vector<OrderItem>& getItems() { return items; }
    PaymentPtr getPayment() const { return payment; }
    OrderStatus getOrderStatus() const { return orderStatus; }
};
