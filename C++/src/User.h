#pragma once
/**
 * Represents a user in the online shopping system: user ID, name, email,
 * password, phone number, address.
 *
 * Derives from enable_shared_from_this so that a user can hand out a shared_ptr
 * to itself (needed when an Order or Ticket stores its owner).
 * Users must therefore be created with std::make_shared.
 */
#include <memory>
#include <optional>
#include <string>
#include "Role.h"

class User : public std::enable_shared_from_this<User> {
protected:
    int userId = 0;
    std::string name;
    std::string email;
    std::string password;
    std::string phone;
    std::string address;
    std::optional<Role> role;   // may be empty (Java: null)
    bool signedIn = false;

public:
    // Constructors
    User() = default;
    User(int userId, const std::string& name, const std::string& email,
         const std::string& password, const std::string& phone,
         const std::string& address);
    User(int userId, const std::string& name, const std::string& email,
         const std::string& password, const std::string& phone,
         const std::string& address, std::optional<Role> role);
    virtual ~User() = default;

    // 'register' is a reserved word in C++, so the method is called registerUser().
    void registerUser();
    void login();
    void logout();
    bool isSignedIn() const;
    void updateProfile(const std::string& phone, const std::string& address);

    // Getters & Setters
    int getUserId() const { return userId; }
    void setUserId(int id) { userId = id; }
    const std::string& getName() const { return name; }
    void setName(const std::string& n) { name = n; }
    const std::string& getEmail() const { return email; }
    void setEmail(const std::string& e) { email = e; }
    const std::string& getPassword() const { return password; }
    void setPassword(const std::string& p) { password = p; }
    const std::string& getPhone() const { return phone; }
    void setPhone(const std::string& p) { phone = p; }
    const std::string& getAddress() const { return address; }
    void setAddress(const std::string& a) { address = a; }
    std::optional<Role> getRole() const { return role; }
    void setRole(Role r) { role = r; }

    virtual std::string toString() const;
};
