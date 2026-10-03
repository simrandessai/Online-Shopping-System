#include "User.h"
#include <iostream>

User::User(int userId, const std::string& name, const std::string& email,
           const std::string& password, const std::string& phone,
           const std::string& address)
    : User(userId, name, email, password, phone, address, std::nullopt) {}

User::User(int userId, const std::string& name, const std::string& email,
           const std::string& password, const std::string& phone,
           const std::string& address, std::optional<Role> role)
    : userId(userId), name(name), email(email), password(password),
      phone(phone), address(address), role(role) {}

void User::registerUser() {
    std::cout << name << " registered successfully as "
              << (role ? ::toString(*role) : "USER") << ".\n";
}

void User::login() {
    signedIn = true;
    std::cout << name << " logged in.\n";
}

void User::logout() {
    signedIn = false;
    std::cout << name << " logged out.\n";
}

bool User::isSignedIn() const { return signedIn; }

void User::updateProfile(const std::string& phone, const std::string& address) {
    this->phone = phone;
    this->address = address;
}

std::string User::toString() const {
    return "User ID : " + std::to_string(userId) +
           "\nName : " + name +
           "\nEmail : " + email +
           "\nPhone : " + phone +
           "\nAddress : " + address +
           "\nRole : " + (role ? ::toString(*role) : "N/A");
}
