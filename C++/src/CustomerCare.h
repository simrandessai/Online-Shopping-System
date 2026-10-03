#pragma once
/**
 * A customer care (support) ticket. A buyer raises a ticket describing an
 * issue, and an admin handles it by replying and resolving it.
 */
#include <string>
#include "Fwd.h"
#include "TicketStatus.h"

class CustomerCare {
private:
    static int nextTicketId;   // static ID generator

    int ticketId;
    std::string issue;
    std::string response;
    TicketStatus status;
    UserPtr raisedBy;          // the user who raised this ticket
    std::string raisedDate;

public:
    // Every new ticket starts as OPEN with no response.
    // Throws std::invalid_argument if issue is empty or raisedBy is null.
    CustomerCare(const std::string& issue, UserPtr raisedBy);

    void setStatus(TicketStatus s) { status = s; }
    void setResponse(const std::string& reply) { response = reply; }

    int getTicketId() const { return ticketId; }
    const std::string& getIssue() const { return issue; }
    const std::string& getResponse() const { return response; }
    TicketStatus getStatus() const { return status; }
    UserPtr getRaisedBy() const { return raisedBy; }
    const std::string& getRaisedDate() const { return raisedDate; }
    bool isResolved() const { return status == TicketStatus::RESOLVED; }

    std::string toSummary() const;   // one-line summary for lists
    std::string toString() const;
};
