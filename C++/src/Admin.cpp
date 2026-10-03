#include "Admin.h"
#include <iomanip>
#include <iostream>
#include "Category.h"
#include "CustomerCare.h"
#include "Product.h"
#include "Seller.h"
#include "Utils.h"

Admin::Admin(int userId, const std::string& name, const std::string& email,
             const std::string& password, const std::string& phone,
             const std::string& address)
    : User(userId, name, email, password, phone, address, Role::ADMIN) {}

void Admin::manageUsers() { std::cout << "Managing Users...\n"; }

void Admin::manageProducts() { std::cout << "Managing Products...\n"; }

// Removes a product from its seller and its category.
// (The caller is responsible for removing it from the global product list.)
void Admin::removeProduct(const ProductPtr& product) {
    if (!product) {
        std::cout << "Product not found.\n";
        return;
    }
    if (product->getSeller()) product->getSeller()->removeProduct(product);
    if (product->getCategory()) product->getCategory()->removeProduct(product);
    std::cout << product->getProductName() << " removed from the system.\n";
}

void Admin::receiveTicket(const CustomerCarePtr& ticket) {
    if (ticket) {
        tickets.push_back(ticket);
        std::cout << "Ticket " << ticket->getTicketId() << " received by " << name << ".\n";
    }
}

// An OPEN ticket moves to IN_PROGRESS once viewed.
void Admin::viewTicket(const CustomerCarePtr& ticket) {
    if (!ticket) {
        std::cout << "Ticket not found.\n";
        return;
    }
    if (ticket->getStatus() == TicketStatus::OPEN) ticket->setStatus(TicketStatus::IN_PROGRESS);
    std::cout << ticket->toString() << "\n";
}

void Admin::viewAllTickets() const {
    std::cout << "\n SUPPORT TICKETS \n";
    if (tickets.empty()) {
        std::cout << "No support tickets.\n";
        return;
    }
    std::cout << std::left << std::setw(6) << "ID" << ' ' << std::setw(12) << "Buyer" << ' '
              << std::setw(12) << "Status" << ' ' << "Issue" << "\n";
    std::cout << util::repeat("-", 75) << "\n";
    for (const auto& ticket : tickets) std::cout << ticket->toSummary() << "\n";
}

// Replies to a ticket and marks it as RESOLVED.
void Admin::resolveTicket(const CustomerCarePtr& ticket, const std::string& reply) {
    if (!ticket) {
        std::cout << "Ticket not found.\n";
        return;
    }
    if (ticket->isResolved()) {
        std::cout << "Ticket " << ticket->getTicketId() << " is already resolved.\n";
        return;
    }
    if (util::trim(reply).empty()) {
        std::cout << "Reply cannot be empty. Ticket left unresolved.\n";
        return;
    }
    ticket->setResponse(util::trim(reply));
    ticket->setStatus(TicketStatus::RESOLVED);
    std::cout << "Ticket " << ticket->getTicketId() << " resolved.\n";
}

CustomerCarePtr Admin::findTicket(int ticketId) const {
    for (const auto& ticket : tickets)
        if (ticket->getTicketId() == ticketId) return ticket;
    return nullptr;
}
