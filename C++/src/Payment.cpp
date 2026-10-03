#include "Payment.h"
#include <iostream>
#include <random>
#include "Utils.h"

Payment::Payment(int paymentId, PaymentMethod paymentMethod, double amount)
    : paymentId(paymentId), paymentMethod(paymentMethod), amount(amount),
      paymentStatus(PaymentStatus::PENDING) {}

// Returns true and marks it SUCCESSFUL if it went through, otherwise marks it
// FAILED and returns false.
bool Payment::processPayment() {
    static std::mt19937 rng{std::random_device{}()};
    static std::uniform_real_distribution<double> dist(0.0, 1.0);

    bool approved = paymentMethod == PaymentMethod::CASH_ON_DELIVERY ||
                    dist(rng) >= FAILURE_CHANCE;

    if (approved) {
        paymentStatus = PaymentStatus::SUCCESSFUL;
        std::cout << "\nPayment Successful\n";
        std::cout << "Amount : Rs " << util::num(amount) << "\n";
        std::cout << "Method : " << ::toString(paymentMethod) << "\n";
        return true;
    }

    paymentStatus = PaymentStatus::FAILED;
    std::cout << "\nPayment Failed\n";
    std::cout << "Amount : Rs " << util::num(amount) << "\n";
    std::cout << "Method : " << ::toString(paymentMethod) << "\n";
    return false;
}

std::string Payment::toString() const {
    return "\nPayment ID : " + std::to_string(paymentId) +
           "\nMethod : " + ::toString(paymentMethod) +
           "\nAmount : Rs " + util::num(amount) +
           "\nStatus : " + ::toString(paymentStatus);
}
