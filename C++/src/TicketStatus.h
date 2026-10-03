#pragma once
#include <string>

// Tracks where a customer care ticket is in its lifecycle.
enum class TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED
};

inline std::string toString(TicketStatus s) {
    switch (s) {
        case TicketStatus::OPEN:        return "OPEN";
        case TicketStatus::IN_PROGRESS: return "IN_PROGRESS";
        case TicketStatus::RESOLVED:    return "RESOLVED";
    }
    return "";
}
