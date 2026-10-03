#include "CustomerCare.h"
#include <iomanip>
#include <sstream>
#include <stdexcept>
#include "User.h"
#include "Utils.h"

int CustomerCare::nextTicketId = 3001;

CustomerCare::CustomerCare(const std::string& issue, UserPtr raisedBy) {
    if (util::trim(issue).empty())
        throw std::invalid_argument("A ticket needs an issue description.");
    if (!raisedBy)
        throw std::invalid_argument("A ticket must be raised by a user.");
    this->ticketId = nextTicketId++;
    this->issue = util::trim(issue);
    this->raisedBy = std::move(raisedBy);
    this->raisedDate = util::today();
    this->response = "";
    this->status = TicketStatus::OPEN;
}

std::string CustomerCare::toSummary() const {
    std::ostringstream os;
    os << std::left << std::setw(6) << ticketId << ' '
       << std::setw(12) << util::truncate(raisedBy->getName(), 12) << ' '
       << std::setw(12) << ::toString(status) << ' '
       << util::truncate(issue, 40);
    return os.str();
}

std::string CustomerCare::toString() const {
    return "\nTICKET"
           "\nTicket ID : " + std::to_string(ticketId) +
           "\nRaised By : " + raisedBy->getName() +
           "\nDate      : " + raisedDate +
           "\nIssue     : " + issue +
           "\nStatus    : " + ::toString(status) +
           "\nResponse  : " + (response.empty() ? "Awaiting response" : response);
}
