#pragma once
// A payment made for an order: ID, method, amount and status.
#include <string>
#include "PaymentMethod.h"
#include "PaymentStatus.h"

class Payment {
private:
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
