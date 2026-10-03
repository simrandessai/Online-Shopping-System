#pragma once
#include <array>
#include <string>

enum class PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    UPI,
    NET_BANKING,
    CASH_ON_DELIVERY,
    WALLET
};

// Equivalent of PaymentMethod.values() in Java.
inline const std::array<PaymentMethod, 6> ALL_PAYMENT_METHODS = {
    PaymentMethod::CREDIT_CARD, PaymentMethod::DEBIT_CARD, PaymentMethod::UPI,
    PaymentMethod::NET_BANKING, PaymentMethod::CASH_ON_DELIVERY, PaymentMethod::WALLET};

inline std::string toString(PaymentMethod m) {
    switch (m) {
        case PaymentMethod::CREDIT_CARD:      return "CREDIT_CARD";
        case PaymentMethod::DEBIT_CARD:       return "DEBIT_CARD";
        case PaymentMethod::UPI:              return "UPI";
        case PaymentMethod::NET_BANKING:      return "NET_BANKING";
        case PaymentMethod::CASH_ON_DELIVERY: return "CASH_ON_DELIVERY";
        case PaymentMethod::WALLET:           return "WALLET";
    }
    return "";
}
