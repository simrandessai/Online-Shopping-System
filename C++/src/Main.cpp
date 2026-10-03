/**
 * Author: Simran V Naik Dessai
 * Roll No: 2650
 * Description: An online shopping system (C++ port of the Java version).
 * It sets up some demo data on startup and provides a simple menu-driven
 * interface. Anyone can browse products and fill a cart without signing in.
 * Sign-in (or registration) is only asked for when the visitor places an order.
 */
#include <algorithm>
#include <cmath>
#include <cstdlib>
#include <iomanip>
#include <iostream>
#include <memory>
#include <regex>
#include <string>
#include <vector>

#include "Admin.h"
#include "Buyer.h"
#include "Cart.h"
#include "Category.h"
#include "CustomerCare.h"
#include "Order.h"
#include "Product.h"
#include "Review.h"
#include "Seller.h"
#include "Utils.h"

// Global state: lists of sellers, buyers, admins, categories and products.
static std::vector<SellerPtr> sellers;
static std::vector<BuyerPtr> buyers;
static std::vector<AdminPtr> admins;
static std::vector<CategoryPtr> categories;
static std::vector<ProductPtr> products;

// Cart used by visitors who haven't signed in. Its items are moved into the
// buyer's own cart as soon as the visitor logs in or registers.
static Cart guestCart(0);

static int nextProductId = 1021;
// Demo users use IDs 1-8, so new users continue from 9. A counter (instead of
// list size) keeps IDs unique even after users are deleted.
static int nextUserId = 9;

static bool inputClosed = false;

// Prototypes
static void initializeDemoData();
static void showMainMenu();
static void addProductToGuestCart();
static void removeFromGuestCart();
static void guestPlaceOrder();
static BuyerPtr signInForCheckout();
static void mergeGuestCart(const BuyerPtr &buyer);
static void createSeller();
static BuyerPtr createBuyer();
static AdminPtr loginAdmin();
static SellerPtr loginSeller();
static BuyerPtr loginBuyer();
static void adminMenu(const AdminPtr &admin);
static void deleteSeller();
static void removeProductEverywhere(const ProductPtr &product);
static void deleteBuyer();
static void sellerMenu(const SellerPtr &seller);
static void buyerMenu(const BuyerPtr &buyer);
static void viewSellerProducts(const SellerPtr &seller);
static void viewSellerReviews(const SellerPtr &seller);
static void viewAllProductReviews(const BuyerPtr &buyer);
static void addNewProduct(const SellerPtr &seller);
static void printProductTable(const std::vector<ProductPtr> &productList);
static void createCategory();
static std::vector<ProductPtr> selectProductsByCategory();
static void viewProductsByCategory();
static CategoryPtr findCategoryByName(const std::string &name);
static ProductPtr chooseProduct(const std::string &action);
static int readQuantity();
static ProductPtr chooseProductInCart(Cart &cart, const std::string &action);
static void addProductToCart(const BuyerPtr &buyer);
static void removeFromCart(const BuyerPtr &buyer);
static ProductPtr findProductInList(int productId, const std::vector<ProductPtr> &productList);
static void moveCartItemToWishlist(const BuyerPtr &buyer);
static ProductPtr findProductInCart(int productId, Cart &cart);
static void placeOrder(const BuyerPtr &buyer);
static void cancelUnpaidOrder(const BuyerPtr &buyer, const OrderPtr &order);
static OrderPtr buildDirectOrder(const BuyerPtr &buyer);
static void addProductToWishlist(const BuyerPtr &buyer);
static void giveReview(const BuyerPtr &buyer);
static ProductPtr findProductById(int productId);
static void raiseSupportTicket(const BuyerPtr &buyer);
static void viewSupportTickets(const AdminPtr &admin);
static void resolveSupportTicket(const AdminPtr &admin);
static void manageProducts(const AdminPtr &admin);
static std::string readLine();
static int readInt();
static double readDouble();
static bool validRegistration(const std::string &name, const std::string &email,
                              const std::string &password, const std::string &phone,
                              const std::string &address);
static bool emailExists(const std::string &email);

int main()
{
    initializeDemoData();
    showMainMenu();
    return 0;
}

// Hardcoded demo data: users, categories and products.
static void initializeDemoData()
{
    auto admin = std::make_shared<Admin>(1, "Admin", "admin@shop.com", "admin123", "9000000000", "Head Office");
    auto seller1 = std::make_shared<Seller>(2, "Rahul", "rahul@seller.com", "seller123", "9876543210", "Pune");
    auto seller2 = std::make_shared<Seller>(3, "Sonia", "sonia@seller.com", "seller234", "9123000001", "Delhi");
    auto buyer1 = std::make_shared<Buyer>(7, "Meena", "meena@buyer.com", "buyer456", "9123456780", "Mumbai");
    auto buyer2 = std::make_shared<Buyer>(8, "Rohit", "rohit@buyer.com", "buyer234", "9123000011", "Delhi");

    auto electronics = std::make_shared<Category>(101, "Electronics");
    auto books = std::make_shared<Category>(102, "Books");
    auto clothing = std::make_shared<Category>(103, "Clothing");
    auto home = std::make_shared<Category>(104, "Home Appliances");

    categories.push_back(electronics);
    categories.push_back(books);
    categories.push_back(clothing);
    categories.push_back(home);

    auto mk = [](int id, const char *name, const char *desc, double price, int stock,
                 const CategoryPtr &c, const SellerPtr &s)
    {
        return std::make_shared<Product>(id, name, desc, price, stock, c, s);
    };

    // Electronics
    auto p1 = mk(1001, "HP Laptop", "16GB RAM, 512GB SSD", 65000, 10, electronics, seller1);
    auto p2 = mk(1002, "Samsung Galaxy", "8GB RAM, 128GB Storage", 35000, 15, electronics, seller1);
    auto p3 = mk(1003, "Sony Headphones", "Noise Cancelling", 15000, 20, electronics, seller1);
    auto p4 = mk(1004, "Apple iPad", "10-inch tablet with 64GB storage", 45000, 12, electronics, seller1);
    auto p5 = mk(1005, "Logitech Mouse", "Wireless ergonomic mouse", 1500, 35, electronics, seller1);
    // Books
    auto p6 = mk(1006, "Java Programming", "Beginner to advanced Java guide", 750, 25, books, seller1);
    auto p7 = mk(1007, "Cooking Made Easy", "Simple recipes for daily meals", 450, 30, books, seller1);
    auto p8 = mk(1008, "History of Art", "A tour through world art history", 950, 18, books, seller1);
    auto p9 = mk(1009, "Children's Stories", "Short stories for kids", 350, 40, books, seller1);
    auto p10 = mk(1010, "Digital Marketing", "Marketing strategies for online business", 550, 22, books, seller1);
    // Clothing
    auto p11 = mk(1011, "Men's T-Shirt", "Cotton crew neck t-shirt", 599, 50, clothing, seller2);
    auto p12 = mk(1012, "Women's Jeans", "Slim fit denim jeans", 1299, 40, clothing, seller2);
    auto p13 = mk(1013, "Summer Dress", "Floral print dress for women", 1499, 30, clothing, seller2);
    auto p14 = mk(1014, "Jacket", "Water-resistant winter jacket", 2199, 20, clothing, seller2);
    auto p15 = mk(1015, "Kids' Hoodie", "Warm hoodie for children", 899, 25, clothing, seller2);
    // Home Appliances
    auto p16 = mk(1016, "Air Fryer", "2.5L electric air fryer", 4999, 15, home, seller2);
    auto p17 = mk(1017, "Blender", "Multi-purpose kitchen blender", 2999, 18, home, seller2);
    auto p18 = mk(1018, "Vacuum Cleaner", "Bagless vacuum cleaner", 7999, 10, home, seller2);
    auto p19 = mk(1019, "Electric Kettle", "1.7L stainless steel kettle", 1299, 20, home, seller2);
    auto p20 = mk(1020, "Microwave Oven", "20L microwave with grill", 6999, 8, home, seller2);

    // Add all products to the global list and to their sellers
    std::vector<ProductPtr> allProducts = {p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
                                           p11, p12, p13, p14, p15, p16, p17, p18, p19, p20};
    for (const auto &product : allProducts)
    {
        products.push_back(product);
        product->getSeller()->addProduct(product);
    }

    // Add products to categories
    electronics->addProduct({p1, p2, p3, p4, p5});
    books->addProduct({p6, p7, p8, p9, p10});
    clothing->addProduct({p11, p12, p13, p14, p15});
    home->addProduct({p16, p17, p18, p19, p20});

    // Add users to the system
    admins.push_back(admin);
    sellers.push_back(seller1);
    sellers.push_back(seller2);
    buyers.push_back(buyer1);
    buyers.push_back(buyer2);
}

// Main menu, open to everyone. Login is only needed to place an order (or to
// use buyer, seller and admin features).
static void showMainMenu()
{
    while (true)
    {
        std::cout << "\nOnline Shopping System\n";
        std::cout << "1. Browse Products\n";
        std::cout << "2. Add Product to Cart\n";
        std::cout << "3. View Cart\n";
        std::cout << "4. Remove Item from Cart\n";
        std::cout << "5. Place Order\n";
        std::cout << "6. Login as Buyer\n";
        std::cout << "7. Register Buyer\n";
        std::cout << "8. Login as Seller\n";
        std::cout << "9. Register Seller\n";
        std::cout << "10. Login as Admin\n";
        std::cout << "11. Exit\n";
        std::cout << "Choose an option: ";
        int option = readInt();
        switch (option)
        {
        case 1:
            viewProductsByCategory();
            break;
        case 2:
            addProductToGuestCart();
            break;
        case 3:
            guestCart.displayCart();
            break;
        case 4:
            removeFromGuestCart();
            break;
        case 5:
            guestPlaceOrder();
            break;
        case 6:
            buyerMenu(loginBuyer());
            break;
        case 7:
            createBuyer();
            break;
        case 8:
            sellerMenu(loginSeller());
            break;
        case 9:
            createSeller();
            break;
        case 10:
            adminMenu(loginAdmin());
            break;
        case 11:
            std::cout << "Application exited.\n";
            return;
        default:
            std::cout << "Invalid option. Please select again.\n";
        }
    }
}

// Visitor (not signed in) adds a product to the guest cart.
static void addProductToGuestCart()
{
    ProductPtr product = chooseProduct("add to cart");
    if (!product)
        return;
    int quantity = readQuantity();
    if (quantity <= 0)
        return;
    if (guestCart.addProduct(product, quantity))
        std::cout << quantity << " x " << product->getProductName() << " added to cart.\n";
}

// Visitor (not signed in) removes a product from the guest cart.
static void removeFromGuestCart()
{
    ProductPtr product = chooseProductInCart(guestCart, "remove");
    if (!product)
        return;
    guestCart.removeProduct(product);
    std::cout << product->getProductName() << " removed from cart.\n";
}

// A visitor tries to place an order: the cart total is shown, then the visitor
// must sign in as an existing buyer or register. After that the order continues
// exactly like a normal buyer order (create order -> payment).
static void guestPlaceOrder()
{
    if (guestCart.getItems().empty())
    {
        std::cout << "Your cart is empty. Add products to your cart first.\n";
        return;
    }
    guestCart.displayCart();
    BuyerPtr buyer = signInForCheckout();
    if (!buyer)
    {
        std::cout << "Order not placed. Your cart has been saved.\n";
        return;
    }
    placeOrder(buyer);
    buyerMenu(buyer);
}

// Log in as an existing buyer, or register. Returns the signed-in buyer, or
// nullptr if the visitor goes back.
static BuyerPtr signInForCheckout()
{
    while (true)
    {
        std::cout << "\nPlease sign in to place your order.\n";
        std::cout << "1. Login as existing buyer\n";
        std::cout << "2. Register as new buyer\n";
        std::cout << "0. Back to shopping\n";
        std::cout << "Choose an option: ";
        int option = readInt();
        switch (option)
        {
        case 1:
        {
            BuyerPtr existing = loginBuyer();
            if (existing)
                return existing;
            break;
        }
        case 2:
        {
            BuyerPtr registered = createBuyer();
            if (registered)
            {
                registered->login();
                mergeGuestCart(registered);
                return registered;
            }
            break;
        }
        case 0:
            return nullptr;
        default:
            std::cout << "Invalid option. Please select again.\n";
        }
    }
}

// Moves everything from the guest cart into the buyer's own cart.
static void mergeGuestCart(const BuyerPtr &buyer)
{
    if (guestCart.getItems().empty())
        return;
    int moved = 0;
    for (auto &item : guestCart.getItems())
    {
        if (buyer->getCart().addProduct(item.getProduct(), item.getQuantity()))
            moved++;
    }
    guestCart.clearCart();
    std::cout << moved << " item(s) from your guest cart were added to your cart.\n";
}

static void createSeller()
{
    std::cout << "\n   CREATE SELLER   \n";
    std::cout << "Name: ";
    std::string name = util::trim(readLine());
    std::cout << "Email: ";
    std::string email = util::trim(readLine());
    std::cout << "Password: ";
    std::string password = util::trim(readLine());
    std::cout << "Phone: ";
    std::string phone = util::trim(readLine());
    std::cout << "Address: ";
    std::string address = util::trim(readLine());

    if (!validRegistration(name, email, password, phone, address) || emailExists(email))
        return;

    auto seller = std::make_shared<Seller>(nextUserId++, name, email, password, phone, address);
    sellers.push_back(seller);
    seller->registerUser();
}

// Returns the new buyer, or nullptr if the details were not valid.
static BuyerPtr createBuyer()
{
    std::cout << "\n   CREATE BUYER   \n";
    std::cout << "Name: ";
    std::string name = util::trim(readLine());
    std::cout << "Email: ";
    std::string email = util::trim(readLine());
    std::cout << "Password: ";
    std::string password = util::trim(readLine());
    std::cout << "Phone: ";
    std::string phone = util::trim(readLine());
    std::cout << "Address: ";
    std::string address = util::trim(readLine());

    if (!validRegistration(name, email, password, phone, address) || emailExists(email))
        return nullptr;

    auto buyer = std::make_shared<Buyer>(nextUserId++, name, email, password, phone, address);
    buyers.push_back(buyer);
    buyer->registerUser();
    return buyer;
}

static AdminPtr loginAdmin()
{
    std::cout << "Enter admin email: ";
    std::string email = util::trim(readLine());
    std::cout << "Enter admin password: ";
    std::string password = util::trim(readLine());
    AdminPtr found;
    for (const auto &admin : admins)
    {
        if (util::equalsIgnoreCase(admin->getEmail(), email))
        {
            found = admin;
            break;
        }
    }
    if (found && found->getPassword() == password)
    {
        found->login();
        return found;
    }
    std::cout << "Invalid email or password.\n";
    return nullptr;
}

static SellerPtr loginSeller()
{
    std::cout << "Enter seller email: ";
    std::string email = util::trim(readLine());
    std::cout << "Enter seller password: ";
    std::string password = util::trim(readLine());
    SellerPtr found;
    for (const auto &seller : sellers)
    {
        if (util::equalsIgnoreCase(seller->getEmail(), email))
        {
            found = seller;
            break;
        }
    }
    if (found && found->getPassword() == password)
    {
        found->login();
        return found;
    }
    std::cout << "Invalid email or password.\n";
    return nullptr;
}

// Any items in the guest cart are moved into the buyer's cart on a successful login.
static BuyerPtr loginBuyer()
{
    std::cout << "Enter buyer email: ";
    std::string email = util::trim(readLine());
    std::cout << "Enter buyer password: ";
    std::string password = util::trim(readLine());
    BuyerPtr found;
    for (const auto &buyer : buyers)
    {
        if (util::equalsIgnoreCase(buyer->getEmail(), email))
        {
            found = buyer;
            break;
        }
    }
    if (found && found->getPassword() == password)
    {
        found->login();
        mergeGuestCart(found);
        return found;
    }
    std::cout << "Invalid email or password.\n";
    return nullptr;
}

static void adminMenu(const AdminPtr &admin)
{
    if (!admin)
        return;
    while (true)
    {
        std::cout << "\n   ADMIN MENU   \n";
        std::cout << "1. Delete Seller\n";
        std::cout << "2. Delete Buyer\n";
        std::cout << "3. Manage Products\n";
        std::cout << "4. View Support Tickets\n";
        std::cout << "5. Reply and Resolve Ticket\n";
        std::cout << "6. Logout\n";
        std::cout << "Choose an option: ";
        int option = readInt();
        switch (option)
        {
        case 1:
            deleteSeller();
            break;
        case 2:
            deleteBuyer();
            break;
        case 3:
            manageProducts(admin);
            break;
        case 4:
            viewSupportTickets(admin);
            break;
        case 5:
            resolveSupportTicket(admin);
            break;
        case 6:
            admin->logout();
            return;
        default:
            std::cout << "Invalid option. Please select again.\n";
        }
    }
}

// Allows the admin to remove a seller and the seller's products.
static void deleteSeller()
{
    std::cout << "Enter seller email to delete: ";
    std::string email = util::trim(readLine());
    SellerPtr toDelete;
    for (const auto &seller : sellers)
    {
        if (util::equalsIgnoreCase(seller->getEmail(), email))
        {
            toDelete = seller;
            break;
        }
    }
    if (!toDelete)
    {
        std::cout << "Seller not found.\n";
        return;
    }
    std::cout << "Delete seller " << toDelete->getName() << " and all their products? (y/n): ";
    if (!util::equalsIgnoreCase(util::trim(readLine()), "y"))
    {
        std::cout << "Deletion cancelled.\n";
        return;
    }
    std::vector<ProductPtr> copy = toDelete->getProducts(); // iterate over a copy
    for (const auto &product : copy)
        removeProductEverywhere(product);
    util::removeOne(sellers, toDelete);
    std::cout << "Seller deleted successfully.\n";
}

// Removes a product from the global list, its category, its seller and every
// cart and wishlist that still holds it (including the guest cart).
static void removeProductEverywhere(const ProductPtr &product)
{
    if (!product)
        return;
    util::removeOne(products, product);
    if (product->getCategory())
        product->getCategory()->removeProduct(product);
    if (product->getSeller())
        product->getSeller()->removeProduct(product);
    guestCart.removeProduct(product);
    for (const auto &buyer : buyers)
        buyer->removeProductReferences(product);
}

static void deleteBuyer()
{
    std::cout << "Enter buyer email to delete: ";
    std::string email = util::trim(readLine());
    BuyerPtr toDelete;
    for (const auto &buyer : buyers)
    {
        if (util::equalsIgnoreCase(buyer->getEmail(), email))
        {
            toDelete = buyer;
            break;
        }
    }
    if (!toDelete)
    {
        std::cout << "Buyer not found.\n";
        return;
    }
    std::cout << "Delete buyer " << toDelete->getName() << "? (y/n): ";
    if (!util::equalsIgnoreCase(util::trim(readLine()), "y"))
    {
        std::cout << "Deletion cancelled.\n";
        return;
    }
    util::removeOne(buyers, toDelete);
    std::cout << "Buyer deleted successfully.\n";
}

static void sellerMenu(const SellerPtr &seller)
{
    if (!seller)
        return;
    while (true)
    {
        std::cout << "\n   SELLER MENU   \n";
        std::cout << "1. View Products\n";
        std::cout << "2. Add New Product\n";
        std::cout << "3. Create Category\n";
        std::cout << "4. View Reviews\n";
        std::cout << "5. Logout\n";
        std::cout << "Choose an option: ";
        int option = readInt();
        switch (option)
        {
        case 1:
            viewSellerProducts(seller);
            break;
        case 2:
            addNewProduct(seller);
            break;
        case 3:
            createCategory();
            break;
        case 4:
            viewSellerReviews(seller);
            break;
        case 5:
            seller->logout();
            return;
        default:
            std::cout << "Invalid option. Please select again.\n";
        }
    }
}

static void buyerMenu(const BuyerPtr &buyer)
{
    if (!buyer)
        return;
    while (true)
    {
        std::cout << "\n   BUYER MENU   \n";
        std::cout << "1. Browse Products\n";
        std::cout << "2. Add Product to Cart\n";
        std::cout << "3. View Cart\n";
        std::cout << "4. Move Item from Cart to Wishlist\n";
        std::cout << "5. Place Order\n";
        std::cout << "6. Add Product to Wishlist\n";
        std::cout << "7. View Wishlist\n";
        std::cout << "8. Give Review\n";
        std::cout << "9. View Product Reviews\n";
        std::cout << "10. View Order History\n";
        std::cout << "11. Raise Support Ticket\n";
        std::cout << "12. View My Support Tickets\n";
        std::cout << "13. Remove Item from Cart\n";
        std::cout << "14. Logout\n";
        std::cout << "Choose an option: ";
        int option = readInt();
        switch (option)
        {
        case 1:
            viewProductsByCategory();
            break;
        case 2:
            addProductToCart(buyer);
            break;
        case 3:
            buyer->viewCart();
            break;
        case 4:
            moveCartItemToWishlist(buyer);
            break;
        case 5:
            placeOrder(buyer);
            break;
        case 6:
            addProductToWishlist(buyer);
            break;
        case 7:
            buyer->viewWishlist();
            break;
        case 8:
            giveReview(buyer);
            break;
        case 9:
            viewAllProductReviews(buyer);
            break;
        case 10:
            buyer->viewOrderHistory();
            break;
        case 11:
            raiseSupportTicket(buyer);
            break;
        case 12:
            buyer->viewTickets();
            break;
        case 13:
            removeFromCart(buyer);
            break;
        case 14:
            buyer->logout();
            return;
        default:
            std::cout << "Invalid option. Please select again.\n";
        }
    }
}

static void viewSellerProducts(const SellerPtr &seller)
{
    auto &ownProducts = seller->getProducts();
    if (ownProducts.empty())
    {
        std::cout << "You have no products assigned yet.\n";
    }
    else
    {
        std::cout << "\n Products of " << seller->getName() << " :\n";
        printProductTable(ownProducts);
    }
}

// Reviews left by buyers for this seller's products.
static void viewSellerReviews(const SellerPtr &seller)
{
    auto &ownProducts = seller->getProducts();
    if (ownProducts.empty())
    {
        std::cout << "You have no products to show reviews for.\n";
        return;
    }
    std::cout << "\n REVIEWS FOR PRODUCTS OF " << util::toUpper(seller->getName()) << " \n";
    bool reviewsFound = false;
    for (const auto &product : ownProducts)
    {
        if (product->getReviews().empty())
            continue;
        reviewsFound = true;
        std::cout << "\nProduct: " << product->getProductName() << "\n";
        product->displayReviews();
    }
    if (!reviewsFound)
        std::cout << "No buyer reviews are available for your products.\n";
}

// Only reviews written by other buyers, for products that have any.
static void viewAllProductReviews(const BuyerPtr &buyer)
{
    if (products.empty())
    {
        std::cout << "No products available.\n";
        return;
    }
    std::cout << "\n   ALL PRODUCT REVIEWS   \n";
    bool reviewsFound = false;
    for (const auto &product : products)
    {
        std::vector<ReviewPtr> others;
        int totalRating = 0;
        for (const auto &review : product->getReviews())
        {
            if (review->getBuyer() != buyer)
            {
                others.push_back(review);
                totalRating += review->getRating();
            }
        }
        if (others.empty())
            continue;
        reviewsFound = true;
        std::cout << "\nProduct: " << product->getProductName()
                  << " | Seller: " << product->getSeller()->getName()
                  << " | Average Rating: "
                  << util::fixed1(static_cast<double>(totalRating) / others.size()) << "/5\n";
        std::cout << "\n    REVIEWS \n";
        for (const auto &review : others)
            std::cout << review->toString() << "\n";
    }
    if (!reviewsFound)
        std::cout << "No reviews from other buyers are available.\n";
}

static void addNewProduct(const SellerPtr &seller)
{
    if (categories.empty())
    {
        std::cout << "Create a category first.\n";
        return;
    }
    std::cout << "\n   ADD NEW PRODUCT   \n";
    std::cout << "Product name: ";
    std::string name = util::trim(readLine());
    std::cout << "Description: ";
    std::string description = util::trim(readLine());
    std::cout << "Price: Rs ";
    double price = readDouble();
    std::cout << "Stock: ";
    int stock = readInt();

    if (name.empty() || description.empty())
    {
        std::cout << "Product name and description cannot be empty.\n";
        return;
    }
    if (!std::isfinite(price) || price <= 0)
    {
        std::cout << "Price must be a positive number.\n";
        return;
    }
    if (stock < 0)
    {
        std::cout << "Stock cannot be negative.\n";
        return;
    }

    for (size_t i = 0; i < categories.size(); i++)
        std::cout << (i + 1) << ". " << categories[i]->getCategoryName() << "\n";
    std::cout << "Choose category number: ";
    int categoryChoice = readInt();

    if (categoryChoice < 1 || categoryChoice > static_cast<int>(categories.size()))
    {
        std::cout << "Invalid category.\n";
        return;
    }

    CategoryPtr category = categories[categoryChoice - 1];
    auto product = std::make_shared<Product>(nextProductId++, name, description, price, stock,
                                             category, seller);
    seller->addProduct(product);
    category->addProduct(product);
    products.push_back(product);
    std::cout << "Product added successfully.\n";
}

// Prints a list of products in a clean tabular format.
static void printProductTable(const std::vector<ProductPtr> &productList)
{
    if (productList.empty())
    {
        std::cout << "No products to display.\n";
        return;
    }
    auto row = [](const std::string &id, const std::string &name, const std::string &price,
                  const std::string &stock, const std::string &cat, const std::string &seller,
                  const std::string &rating)
    {
        std::cout << std::left << std::setw(6) << id << ' ' << std::setw(20) << name << ' '
                  << std::setw(10) << price << ' ' << std::setw(8) << stock << ' '
                  << std::setw(15) << cat << ' ' << std::setw(15) << seller << ' '
                  << std::setw(6) << rating << "\n";
    };
    row("ID", "Name", "Price", "Stock", "Category", "Seller", "Rating");
    std::cout << util::repeat("-", 90) << "\n";
    for (const auto &p : productList)
    {
        row(std::to_string(p->getProductId()),
            util::truncate(p->getProductName(), 20),
            "Rs " + util::num(p->getPrice()),
            std::to_string(p->getStock()),
            util::truncate(p->getCategory() ? p->getCategory()->getCategoryName() : "N/A", 15),
            util::truncate(p->getSeller() ? p->getSeller()->getName() : "N/A", 15),
            util::fixed1(p->getAverageRating()));
    }
}

static void createCategory()
{
    std::cout << "Enter category name: ";
    std::string name = util::trim(readLine());
    if (name.empty())
    {
        std::cout << "Category name cannot be empty.\n";
        return;
    }
    if (findCategoryByName(name))
    {
        std::cout << "That category already exists.\n";
        return;
    }
    auto category = std::make_shared<Category>(static_cast<int>(categories.size()) + 101, name);
    categories.push_back(category);
    std::cout << "Category created successfully.\n";
}

// Displays products for a category chosen by number or name. Entering 'all',
// selecting the All option, or pressing Enter shows every product.
static std::vector<ProductPtr> selectProductsByCategory()
{
    if (categories.empty())
    {
        std::cout << "No categories available.\n";
        return {};
    }
    std::cout << "\nAvailable categories: \n";
    for (size_t i = 0; i < categories.size(); i++)
        std::cout << (i + 1) << ". " << categories[i]->getCategoryName() << "\n";
    int allOption = static_cast<int>(categories.size()) + 1;
    std::cout << allOption << ". All Products\n";
    std::cout << "0. Back\n";
    std::cout << "Choose category number or name (or press Enter for all): ";
    std::string input = util::trim(readLine());
    if (input.empty() || util::equalsIgnoreCase(input, "all") || input == std::to_string(allOption))
    {
        std::cout << "\n--- ALL PRODUCTS ---\n";
        printProductTable(products);
        return products;
    }
    if (input == "0" || util::equalsIgnoreCase(input, "back"))
        return {};

    CategoryPtr selected;
    try
    {
        size_t pos = 0;
        int choice = std::stoi(input, &pos);
        if (pos == input.size() && choice >= 1 && choice <= static_cast<int>(categories.size()))
            selected = categories[choice - 1];
    }
    catch (...)
    {
        // i not a number, try to match by name instead
    }
    if (!selected)
        selected = findCategoryByName(input);
    if (!selected)
    {
        std::cout << "Category not found.\n";
        return {};
    }
    std::cout << "\n--- " << util::toUpper(selected->getCategoryName()) << " PRODUCTS ---\n";
    printProductTable(selected->getProducts());
    return selected->getProducts();
}

static void viewProductsByCategory() { selectProductsByCategory(); }

static CategoryPtr findCategoryByName(const std::string &name)
{
    for (const auto &category : categories)
        if (util::equalsIgnoreCase(category->getCategoryName(), name))
            return category;
    return nullptr;
}

// Browse (by category) and pick a product by ID. Returns nullptr if nothing valid was chosen.
static ProductPtr chooseProduct(const std::string &action)
{
    if (products.empty())
    {
        std::cout << "No products available.\n";
        return nullptr;
    }
    std::vector<ProductPtr> available = selectProductsByCategory();
    if (available.empty())
        return nullptr;
    std::cout << "Enter product ID to " << action << " (or 0 to cancel): ";
    int productId = readInt();
    if (productId == 0)
        return nullptr;
    ProductPtr product = findProductInList(productId, available);
    if (!product)
        std::cout << "Invalid product ID for this selection.\n";
    return product;
}

// Returns 0 (after a message) if the quantity isn't more than 0. Otherwise returns the quantity.
static int readQuantity()
{
    std::cout << "Quantity: ";
    int quantity = readInt();
    if (quantity <= 0)
    {
        std::cout << "Quantity must be positive.\n";
        return 0;
    }
    return quantity;
}

// Shows a cart and lets the user pick one of its products by ID.
static ProductPtr chooseProductInCart(Cart &cart, const std::string &action)
{
    if (cart.getItems().empty())
    {
        std::cout << "Cart is empty.\n";
        return nullptr;
    }
    cart.displayCart();
    std::cout << "Enter product ID to " << action << " (or 0 to cancel): ";
    int productId = readInt();
    if (productId == 0)
        return nullptr;
    ProductPtr product = findProductInCart(productId, cart);
    if (!product)
        std::cout << "That product isn't in your cart.\n";
    return product;
}

static void addProductToCart(const BuyerPtr &buyer)
{
    ProductPtr product = chooseProduct("add to cart");
    if (!product)
        return;
    int quantity = readQuantity();
    if (quantity <= 0)
        return;
    buyer->addToCart(product, quantity);
}

static void removeFromCart(const BuyerPtr &buyer)
{
    ProductPtr product = chooseProductInCart(buyer->getCart(), "remove");
    if (product)
        buyer->removeFromCart(product);
}

// Finds a product by ID, but only within a specific list.
static ProductPtr findProductInList(int productId, const std::vector<ProductPtr> &productList)
{
    for (const auto &product : productList)
        if (product->getProductId() == productId)
            return product;
    return nullptr;
}

static void moveCartItemToWishlist(const BuyerPtr &buyer)
{
    ProductPtr product = chooseProductInCart(buyer->getCart(), "move to wishlist");
    if (product)
        buyer->moveToWishlist(product);
}

static ProductPtr findProductInCart(int productId, Cart &cart)
{
    for (auto &item : cart.getItems())
        if (item.getProduct()->getProductId() == productId)
            return item.getProduct();
    return nullptr;
}

// Place an order from the cart, or by selecting products directly. Payment can
// be retried if it fails; stock is only deducted once payment succeeds.
static void placeOrder(const BuyerPtr &buyer)
{
    OrderPtr order;
    if (!buyer->getCart().getItems().empty())
    {
        order = buyer->placeOrder();
        if (!order)
            return;
    }
    else
    {
        std::cout << "Your cart is empty. Let's place an order directly.\n";
        order = buildDirectOrder(buyer);
        if (!order)
            return;
    }

    bool paid = false;
    while (!paid)
    {
        std::cout << "Choose payment method:\n";
        for (size_t i = 0; i < ALL_PAYMENT_METHODS.size(); i++)
            std::cout << (i + 1) << ". " << toString(ALL_PAYMENT_METHODS[i]) << "\n";
        std::cout << "0. Cancel order\n";
        std::cout << "Select payment option: ";
        int paymentChoice = readInt();
        if (paymentChoice == 0)
        {
            cancelUnpaidOrder(buyer, order);
            return;
        }
        if (paymentChoice < 1 || paymentChoice > static_cast<int>(ALL_PAYMENT_METHODS.size()))
        {
            std::cout << "Invalid payment option.\n";
            continue;
        }
        paid = order->makePayment(ALL_PAYMENT_METHODS[paymentChoice - 1]);
        if (!paid)
        {
            std::cout << "Payment failed. Try again? (y/n): ";
            if (!util::equalsIgnoreCase(util::trim(readLine()), "y"))
            {
                cancelUnpaidOrder(buyer, order);
                return;
            }
        }
    }
    order->displayOrder();
    buyer->addOrderToHistory(order);
    buyer->getCart().clearCart();
}

// Cancels an order that was never paid for. No stock was deducted, and the cart
// is left as it was so the buyer can try again later.
static void cancelUnpaidOrder(const BuyerPtr &buyer, const OrderPtr &order)
{
    order->updateOrderStatus(OrderStatus::CANCELLED);
    buyer->addOrderToHistory(order);
    std::cout << "Order " << order->getOrderId() << " cancelled. No payment was taken.\n";
}

static OrderPtr buildDirectOrder(const BuyerPtr &buyer)
{
    if (products.empty())
    {
        std::cout << "No products available.\n";
        return nullptr;
    }
    OrderPtr order = buyer->startDirectOrder();
    if (!order)
        return nullptr;
    while (true)
    {
        viewProductsByCategory();
        std::cout << "Enter product ID to add (or 0 to finish): ";
        int productId = readInt();
        if (productId == 0)
            break;
        ProductPtr product = findProductById(productId);
        if (!product)
        {
            std::cout << "Product not found.\n";
            continue;
        }
        int quantity = readQuantity();
        if (quantity <= 0)
            continue;
        order->addItem(product, quantity);
    }
    if (order->getItems().empty())
    {
        std::cout << "No items selected. Order cancelled.\n";
        return nullptr;
    }
    return order;
}

static void addProductToWishlist(const BuyerPtr &buyer)
{
    ProductPtr product = chooseProduct("add to wishlist");
    if (product)
        buyer->addToWishlist(product);
}

static void giveReview(const BuyerPtr &buyer)
{
    std::vector<ProductPtr> purchased = buyer->getPurchasedProducts();
    if (purchased.empty())
    {
        std::cout << "You haven't purchased any products yet.\n";
        return;
    }
    std::cout << "\n--- YOUR PURCHASED PRODUCTS ---\n";
    printProductTable(purchased);
    std::cout << "Enter product ID to review: ";
    int productId = readInt();
    ProductPtr product = findProductInList(productId, purchased);
    if (!product)
    {
        std::cout << "That product isn't in your purchase history.\n";
        return;
    }
    int rating;
    do
    {
        std::cout << "Rating (1-5): ";
        rating = readInt();
        if (rating < 1 || rating > 5)
            std::cout << "Rating must be between 1 and 5.\n";
    } while (rating < 1 || rating > 5);
    std::cout << "Comment: ";
    std::string comment = util::trim(readLine());
    buyer->giveReview(product, rating, comment);
}

static ProductPtr findProductById(int productId)
{
    for (const auto &product : products)
        if (product->getProductId() == productId)
            return product;
    return nullptr;
}

// Buyer raises a ticket, which is then passed on to an admin.
static void raiseSupportTicket(const BuyerPtr &buyer)
{
    std::cout << "Describe your issue: ";
    std::string issue = util::trim(readLine());
    CustomerCarePtr ticket = buyer->raiseTicket(issue);
    if (!ticket)
        return;
    if (admins.empty())
    {
        std::cout << "No admin is available right now. Your ticket has been saved.\n";
        return;
    }
    admins[0]->receiveTicket(ticket);
}

// Lists all tickets and lets the admin open one. Opening an OPEN ticket moves it to IN_PROGRESS.
static void viewSupportTickets(const AdminPtr &admin)
{
    admin->viewAllTickets();
    if (admin->getTickets().empty())
        return;
    std::cout << "Enter ticket ID to open (or 0 to go back): ";
    int ticketId = readInt();
    if (ticketId == 0)
        return;
    CustomerCarePtr ticket = admin->findTicket(ticketId);
    if (!ticket)
    {
        std::cout << "Ticket not found.\n";
        return;
    }
    admin->viewTicket(ticket);
}

static void resolveSupportTicket(const AdminPtr &admin)
{
    admin->viewAllTickets();
    if (admin->getTickets().empty())
        return;
    std::cout << "Enter ticket ID to resolve (or 0 to go back): ";
    int ticketId = readInt();
    if (ticketId == 0)
        return;
    CustomerCarePtr ticket = admin->findTicket(ticketId);
    if (!ticket)
    {
        std::cout << "Ticket not found.\n";
        return;
    }
    if (ticket->isResolved())
    {
        std::cout << "Ticket " << ticketId << " is already resolved.\n";
        return;
    }
    admin->viewTicket(ticket);
    std::cout << "Enter your reply: ";
    std::string reply = util::trim(readLine());
    if (reply.empty())
    {
        std::cout << "Reply cannot be empty. Ticket left unresolved.\n";
        return;
    }
    admin->resolveTicket(ticket, reply);
}

// Admin views every product and removes one.
static void manageProducts(const AdminPtr &admin)
{
    admin->manageProducts();
    printProductTable(products);
    if (products.empty())
        return;
    std::cout << "Enter product ID to remove (or 0 to go back): ";
    int productId = readInt();
    if (productId == 0)
        return;
    ProductPtr product = findProductById(productId);
    if (!product)
    {
        std::cout << "Product not found.\n";
        return;
    }
    std::cout << "Remove " << product->getProductName() << "? (y/n): ";
    if (!util::equalsIgnoreCase(util::trim(readLine()), "y"))
    {
        std::cout << "Removal cancelled.\n";
        return;
    }
    admin->removeProduct(product);
    util::removeOne(products, product);
    guestCart.removeProduct(product);
    for (const auto &buyer : buyers)
        buyer->removeProductReferences(product);
}

// Input helpers

// Reads a line of text entered by the user ("" at end of input).
static std::string readLine()
{
    std::string line;
    if (std::getline(std::cin, line))
    {
        if (!line.empty() && line.back() == '\r')
            line.pop_back();
        return line;
    }
    inputClosed = true;
    return "";
}

static bool parseInt(const std::string &s, int &out)
{
    try
    {
        size_t pos = 0;
        int v = std::stoi(s, &pos);
        if (pos != s.size())
            return false;
        out = v;
        return true;
    }
    catch (...)
    {
        return false;
    }
}

static bool parseDouble(const std::string &s, double &out)
{
    try
    {
        size_t pos = 0;
        double v = std::stod(s, &pos);
        if (pos != s.size())
            return false;
        out = v;
        return true;
    }
    catch (...)
    {
        return false;
    }
}

// Repeatedly prompts until the user enters a valid integer.
// (Exits cleanly if the input stream ends, instead of looping forever.)
static int readInt()
{
    while (true)
    {
        int value;
        if (parseInt(util::trim(readLine()), value))
            return value;
        if (inputClosed)
        {
            std::cout << "\nInput ended. Exiting.\n";
            std::exit(0);
        }
        std::cout << "Invalid number. Enter again: ";
    }
}

// Repeatedly prompts until the user enters a decimal number.
static double readDouble()
{
    while (true)
    {
        double value;
        if (parseDouble(util::trim(readLine()), value))
            return value;
        if (inputClosed)
        {
            std::cout << "\nInput ended. Exiting.\n";
            std::exit(0);
        }
        std::cout << "Invalid number. Enter again: ";
    }
}

static bool validRegistration(const std::string &name, const std::string &email,
                              const std::string &password, const std::string &phone,
                              const std::string &address)
{
    if (name.empty() || email.empty() || password.empty() || phone.empty() || address.empty())
    {
        std::cout << "All registration fields are required.\n";
        return false;
    }
    static const std::regex emailPattern("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    if (!std::regex_match(email, emailPattern))
    {
        std::cout << "Enter a valid email address.\n";
        return false;
    }
    return true;
}

static bool emailExists(const std::string &email)
{
    for (const auto &seller : sellers)
        if (util::equalsIgnoreCase(seller->getEmail(), email))
        {
            std::cout << "That email is already registered.\n";
            return true;
        }
    for (const auto &buyer : buyers)
        if (util::equalsIgnoreCase(buyer->getEmail(), email))
        {
            std::cout << "That email is already registered.\n";
            return true;
        }
    for (const auto &admin : admins)
        if (util::equalsIgnoreCase(admin->getEmail(), email))
        {
            std::cout << "That email is already registered.\n";
            return true;
        }
    return false;
}
