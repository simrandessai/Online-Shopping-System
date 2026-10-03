#pragma once
#include <string>

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
};

inline std::string toString(OrderStatus s) {
    switch (s) {
        case OrderStatus::PENDING:          return "PENDING";
        case OrderStatus::CONFIRMED:        return "CONFIRMED";
        case OrderStatus::SHIPPED:          return "SHIPPED";
        case OrderStatus::OUT_FOR_DELIVERY: return "OUT_FOR_DELIVERY";
        case OrderStatus::DELIVERED:        return "DELIVERED";
        case OrderStatus::CANCELLED:        return "CANCELLED";
    }
    return "";
}
