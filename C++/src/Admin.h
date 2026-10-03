#pragma once
/**
 * An Admin user. They can manage users and products, and handle customer care
 * tickets raised by buyers.
 */
#include <string>
#include <vector>
#include "Fwd.h"
#include "User.h"

class Admin : public User {
private:
    // Tickets received from buyers, waiting to be handled.
    std::vector<CustomerCarePtr> tickets;

public:
    Admin(int userId, const std::string& name, const std::string& email,
          const std::string& password, const std::string& phone,
          const std::string& address);

    void manageUsers();
    void manageProducts();
    void removeProduct(const ProductPtr& product);

    void receiveTicket(const CustomerCarePtr& ticket);
    void viewTicket(const CustomerCarePtr& ticket);
    void viewAllTickets() const;
    void resolveTicket(const CustomerCarePtr& ticket, const std::string& reply);
    CustomerCarePtr findTicket(int ticketId) const;   // nullptr if not found
    std::vector<CustomerCarePtr>& getTickets() { return tickets; }
};
