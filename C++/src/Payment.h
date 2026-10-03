#pragma once
// A payment made for an order: ID, method, amount and status.
#include <string>
#include "PaymentMethod.h"
#include "PaymentStatus.h"

class Payment {
private:
    // Simulated chance that an online payment is declined (Cash on Delivery never
    // fails). Set to 0 to make every payment succeed.
    static constexpr double FAILURE_CHANCE = 0.10;

    int paymentId;
    PaymentMethod paymentMethod;
    double amount;
    PaymentStatus paymentStatus;

public:
    Payment(int paymentId, PaymentMethod paymentMethod, double amount);

    bool processPayment();

    int getPaymentId() const { return paymentId; }
    PaymentMethod getPaymentMethod() const { return paymentMethod; }
    double getAmount() const { return amount; }
    PaymentStatus getPaymentStatus() const { return paymentStatus; }
    std::string toString() const;
};
