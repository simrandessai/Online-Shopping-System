#pragma once
#include <string>

enum class PaymentStatus {
    PENDING,
    SUCCESSFUL,
    FAILED,
    REFUNDED
};

inline std::string toString(PaymentStatus s) {
    switch (s) {
        case PaymentStatus::PENDING:    return "PENDING";
        case PaymentStatus::SUCCESSFUL: return "SUCCESSFUL";
        case PaymentStatus::FAILED:     return "FAILED";
        case PaymentStatus::REFUNDED:   return "REFUNDED";
    }
    return "";
}
