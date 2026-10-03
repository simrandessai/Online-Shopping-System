#pragma once
#include <string>

enum class Role {
    ADMIN,
    SELLER,
    BUYER
};

inline std::string toString(Role r) {
    switch (r) {
        case Role::ADMIN:  return "ADMIN";
        case Role::SELLER: return "SELLER";
        case Role::BUYER:  return "BUYER";
    }
    return "";
}
