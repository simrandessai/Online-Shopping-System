#include "Payment.h"
#include <iostream>
#include "Utils.h"

Payment::Payment(int paymentId, PaymentMethod paymentMethod, double amount)
    : paymentId(paymentId), paymentMethod(paymentMethod), amount(amount),
      paymentStatus(PaymentStatus::PENDING) {}

// Processes the payment and marks it SUCCESSFUL.
bool Payment::processPayment() {
    paymentStatus = PaymentStatus::SUCCESSFUL;
    std::cout << "\nPayment Successful\n";
    std::cout << "Amount : Rs " << util::num(amount) << "\n";
    std::cout << "Method : " << ::toString(paymentMethod) << "\n";
    return true;
}

std::string Payment::toString() const {
    return "\nPayment ID : " + std::to_string(paymentId) +
           "\nMethod : " + ::toString(paymentMethod) +
           "\nAmount : Rs " + util::num(amount) +
           "\nStatus : " + ::toString(paymentStatus);
}
