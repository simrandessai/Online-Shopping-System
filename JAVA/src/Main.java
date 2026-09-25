
/**
 * Author: Simran V Naik Dessai
 * Roll No: 2650
 * Description: This is a online shopping system implemented in Java.  
 * It sets up some demo data on startup and provides a simple menu-driven interface for users to interact with the system. 
 * Anyone can browse products and fill a cart without signing in. 
 * Sign-in (or registration) is only asked for when the visitor places an order.
 */

import java.util.ArrayList;
import java.util.Scanner;

//The main class initialized here is the start of this online shopping system.
public class Main {

        // Scanner for user input and lists to hold sellers, buyers, admins,
        // categories,and products.
        private static final Scanner scanner = new Scanner(System.in);
        private static final ArrayList<Seller> sellers = new ArrayList<>();
        private static final ArrayList<Buyer> buyers = new ArrayList<>();
        private static final ArrayList<Admin> admins = new ArrayList<>();
        private static final ArrayList<Category> categories = new ArrayList<>();
        private static final ArrayList<Product> products = new ArrayList<>();

        // Cart used by visitors who haven't signed in. Its items are moved into the
        // buyer's own cart as soon as the visitor logs in or registers.
        private static final Cart guestCart = new Cart(0);

        private static int nextProductId = 1021;
        // Demo users use IDs 1-8, so new users continue from 9. A counter (instead of
        // list size) keeps IDs unique even after users are deleted.
        private static int nextUserId = 9;

        public static void main(String[] args) {
                initializeDemoData();
                showMainMenu();
                scanner.close();
        }

        // This is a demo data or the hardcoded data for the system, which includes
        // users, categories, and product.
        // It is used to demonstrate the functionality of the system.
        private static void initializeDemoData() {
                Admin admin = new Admin(1, "Admin", "admin@shop.com", "admin123", "9000000000", "Head Office");
                Seller seller1 = new Seller(2, "Rahul", "rahul@seller.com", "seller123", "9876543210", "Pune");
                Seller seller2 = new Seller(3, "Sonia", "sonia@seller.com", "seller234", "9123000001", "Delhi");
                Buyer buyer1 = new Buyer(7, "Meena", "meena@buyer.com", "buyer456", "9123456780", "Mumbai");
                Buyer buyer2 = new Buyer(8, "Rohit", "rohit@buyer.com", "buyer234", "9123000011", "Delhi");

                Category electronics = new Category(101, "Electronics");
                Category books = new Category(102, "Books");
                Category clothing = new Category(103, "Clothing");
                Category home = new Category(104, "Home Appliances");

                categories.add(electronics);
                categories.add(books);
                categories.add(clothing);
                categories.add(home);

                // Adding products to the system
                // Electronics
                Product p1 = new Product(1001, "HP Laptop", "16GB RAM, 512GB SSD", 65000, 10, electronics, seller1);
                Product p2 = new Product(1002, "Samsung Galaxy", "8GB RAM, 128GB Storage", 35000, 15, electronics,
                                seller1);
                Product p3 = new Product(1003, "Sony Headphones", "Noise Cancelling", 15000, 20, electronics, seller1);
                Product p4 = new Product(1004, "Apple iPad", "10-inch tablet with 64GB storage", 45000, 12, electronics,
                                seller1);
                Product p5 = new Product(1005, "Logitech Mouse", "Wireless ergonomic mouse", 1500, 35, electronics,
                                seller1);
                // Books
                Product p6 = new Product(1006, "Java Programming", "Beginner to advanced Java guide", 750, 25, books,
                                seller1);
                Product p7 = new Product(1007, "Cooking Made Easy", "Simple recipes for daily meals", 450, 30, books,
                                seller1);
                Product p8 = new Product(1008, "History of Art", "A tour through world art history", 950, 18, books,
                                seller1);
                Product p9 = new Product(1009, "Children's Stories", "Short stories for kids", 350, 40, books, seller1);
                Product p10 = new Product(1010, "Digital Marketing", "Marketing strategies for online business", 550,
                                22, books, seller1);
                // Clothing
                Product p11 = new Product(1011, "Men's T-Shirt", "Cotton crew neck t-shirt", 599, 50, clothing,
                                seller2);
                Product p12 = new Product(1012, "Women's Jeans", "Slim fit denim jeans", 1299, 40, clothing, seller2);
                Product p13 = new Product(1013, "Summer Dress", "Floral print dress for women", 1499, 30, clothing,
                                seller2);
                Product p14 = new Product(1014, "Jacket", "Water-resistant winter jacket", 2199, 20, clothing, seller2);
                Product p15 = new Product(1015, "Kids' Hoodie", "Warm hoodie for children", 899, 25, clothing, seller2);
                // Home Appliances
                Product p16 = new Product(1016, "Air Fryer", "2.5L electric air fryer", 4999, 15, home, seller2);
                Product p17 = new Product(1017, "Blender", "Multi-purpose kitchen blender", 2999, 18, home, seller2);
                Product p18 = new Product(1018, "Vacuum Cleaner", "Bagless vacuum cleaner", 7999, 10, home, seller2);
                Product p19 = new Product(1019, "Electric Kettle", "1.7L stainless steel kettle", 1299, 20, home,
                                seller2);
                Product p20 = new Product(1020, "Microwave Oven", "20L microwave with grill", 6999, 8, home, seller2);

                // Adding all products to the products list and to their sellers
                Product[] allProducts = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17,
                                p18, p19, p20 };
                for (Product product : allProducts) {
                        products.add(product);
                        product.getSeller().addProduct(product);
                }

                // Adding products to categories
                electronics.addProduct(p1, p2, p3, p4, p5);
                books.addProduct(p6, p7, p8, p9, p10);
                clothing.addProduct(p11, p12, p13, p14, p15);
                home.addProduct(p16, p17, p18, p19, p20);

                // Adding users to the system
                admins.add(admin);
                sellers.add(seller1);
                sellers.add(seller2);
                buyers.add(buyer1);
                buyers.add(buyer2);
        }

        // This method displays the main menu, which is open to everyone.
        // Browsing comes first, and visitors can shop without signing in.
        // Login is only needed to place an order (or to use buyer, seller and admin
        // features).
        private static void showMainMenu() {
                while (true) {
                        System.out.println("\nOnline Shopping System");
                        System.out.println("1. Browse Products");
                        System.out.println("2. Add Product to Cart");
                        System.out.println("3. View Cart");
                        System.out.println("4. Remove Item from Cart");
                        System.out.println("5. Place Order");
                        System.out.println("6. Login as Buyer");
                        System.out.println("7. Register Buyer");
                        System.out.println("8. Login as Seller");
                        System.out.println("9. Register Seller");
                        System.out.println("10. Login as Admin");
                        System.out.println("11. Exit");
                        System.out.print("Choose an option: ");
                        int option = readInt();
                        switch (option) {
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
                                        System.out.println("Application exited.");
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // Visitor (not signed in) adds a product to the guest cart.
        private static void addProductToGuestCart() {
                Product product = chooseProduct("add to cart");
                if (product == null) {
                        return;
                }
                int quantity = readQuantity();
                if (quantity <= 0) {
                        return;
                }
                if (guestCart.addProduct(product, quantity)) {
                        System.out.println(quantity + " x " + product.getProductName() + " added to cart.");
                }
        }

        // Visitor (not signed in) removes a product from the guest cart.
        private static void removeFromGuestCart() {
                Product product = chooseProductInCart(guestCart, "remove");
                if (product == null) {
                        return;
                }
                guestCart.removeProduct(product);
                System.out.println(product.getProductName() + " removed from cart.");
        }

        // A visitor tries to place an order: the cart total is shown, then the visitor
        // must sign in as an existing buyer or register.
        // After that the order continues exactly like a normal buyer order (create
        // order -> payment).
        private static void guestPlaceOrder() {
                if (guestCart.getItems().isEmpty()) {
                        System.out.println("Your cart is empty. Add products to your cart first.");
                        return;
                }
                guestCart.displayCart();
                Buyer buyer = signInForCheckout();
                if (buyer == null) {
                        System.out.println("Order not placed. Your cart has been saved.");
                        return;
                }
                placeOrder(buyer);
                buyerMenu(buyer);
        }

        // Verifies the visitor at checkout: log in as an existing buyer, or register.
        // Returns the signed-in buyer, or null if the visitor goes back.
        private static Buyer signInForCheckout() {
                while (true) {
                        System.out.println("\nPlease sign in to place your order.");
                        System.out.println("1. Login as existing buyer");
                        System.out.println("2. Register as new buyer");
                        System.out.println("0. Back to shopping");
                        System.out.print("Choose an option: ");
                        int option = readInt();
                        switch (option) {
                                case 1: {
                                        Buyer existing = loginBuyer();
                                        if (existing != null) {
                                                return existing;
                                        }
                                        break;
                                }
                                case 2: {
                                        Buyer registered = createBuyer();
                                        if (registered != null) {
                                                registered.login();
                                                mergeGuestCart(registered);
                                                return registered;
                                        }
                                        break;
                                }
                                case 0:
                                        return null;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // Moves everything from the guest cart into the buyer's own cart.
        private static void mergeGuestCart(Buyer buyer) {
                if (guestCart.getItems().isEmpty()) {
                        return;
                }
                int moved = 0;
                for (CartItem item : guestCart.getItems()) {
                        if (buyer.getCart().addProduct(item.getProduct(), item.getQuantity())) {
                                moved++;
                        }
                }
                guestCart.clearCart();
                System.out.println(moved + " item(s) from your guest cart were added to your cart.");
        }

        // This method prompts the user to enter details for creating a new seller.
        private static void createSeller() {
                System.out.println("\n   CREATE SELLER   ");
                System.out.print("Name: ");
                String name = readLine().trim();
                System.out.print("Email: ");
                String email = readLine().trim();
                System.out.print("Password: ");
                String password = readLine().trim();
                System.out.print("Phone: ");
                String phone = readLine().trim();
                System.out.print("Address: ");
                String address = readLine().trim();

                if (!validRegistration(name, email, password, phone, address)
                                || emailExists(email)) {
                        return;
                }

                Seller seller = new Seller(nextUserId++, name, email, password, phone, address);
                sellers.add(seller);
                seller.register();
        }

        // This method prompts the user to enter details for creating a new buyer.
        // Returns the new buyer, or null if the details were not valid.
        private static Buyer createBuyer() {
                System.out.println("\n   CREATE BUYER   ");
                System.out.print("Name: ");
                String name = readLine().trim();
                System.out.print("Email: ");
                String email = readLine().trim();
                System.out.print("Password: ");
                String password = readLine().trim();
                System.out.print("Phone: ");
                String phone = readLine().trim();
                System.out.print("Address: ");
                String address = readLine().trim();

                if (!validRegistration(name, email, password, phone, address)
                                || emailExists(email)) {
                        return null;
                }

                Buyer buyer = new Buyer(nextUserId++, name, email, password, phone, address);
                buyers.add(buyer);
                buyer.register();
                return buyer;
        }

        // This method handles the login process for the admin user.
        private static Admin loginAdmin() {
                System.out.print("Enter admin email: ");
                String email = readLine().trim();
                System.out.print("Enter admin password: ");
                String password = readLine().trim();
                Admin foundAdmin = null;
                for (Admin admin : admins) {
                        if (admin.getEmail().equalsIgnoreCase(email)) {
                                foundAdmin = admin;
                                break;
                        }
                }
                if (foundAdmin != null && foundAdmin.getPassword().equals(password)) {
                        foundAdmin.login();
                        return foundAdmin;
                }
                System.out.println("Invalid email or password.");
                return null;
        }

        // This method handles the login process for the seller user.
        private static Seller loginSeller() {
                System.out.print("Enter seller email: ");
                String email = readLine().trim();
                System.out.print("Enter seller password: ");
                String password = readLine().trim();
                Seller foundSeller = null;
                for (Seller seller : sellers) {
                        if (seller.getEmail().equalsIgnoreCase(email)) {
                                foundSeller = seller;
                                break;
                        }
                }
                if (foundSeller != null && foundSeller.getPassword().equals(password)) {
                        foundSeller.login();
                        return foundSeller;
                }
                System.out.println("Invalid email or password.");
                return null;
        }

        // This method handles the login process for the buyer user.
        // Any items in the guest cart are moved into the buyer's cart on a successful
        // login.
        private static Buyer loginBuyer() {
                System.out.print("Enter buyer email: ");
                String email = readLine().trim();
                System.out.print("Enter buyer password: ");
                String password = readLine().trim();
                Buyer foundBuyer = null;
                for (Buyer buyer : buyers) {
                        if (buyer.getEmail().equalsIgnoreCase(email)) {
                                foundBuyer = buyer;
                                break;
                        }
                }
                if (foundBuyer != null && foundBuyer.getPassword().equals(password)) {
                        foundBuyer.login();
                        mergeGuestCart(foundBuyer);
                        return foundBuyer;
                }
                System.out.println("Invalid email or password.");
                return null;
        }

        // This method displays the admin menu.
        private static void adminMenu(Admin admin) {
                if (admin == null)
                        return;
                while (true) {
                        System.out.println("\n   ADMIN MENU   ");
                        System.out.println("1. Delete Seller");
                        System.out.println("2. Delete Buyer");
                        System.out.println("3. Manage Products");
                        System.out.println("4. View Support Tickets");
                        System.out.println("5. Reply and Resolve Ticket");
                        System.out.println("6. Logout");
                        System.out.print("Choose an option: ");
                        int option = readInt();
                        switch (option) {
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
                                        admin.logout();
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // Allows the admin to remove a seller and the seller's products.
        private static void deleteSeller() {
                System.out.print("Enter seller email to delete: ");
                String email = readLine().trim();
                Seller sellerToDelete = null;
                for (Seller seller : sellers) {
                        if (seller.getEmail().equalsIgnoreCase(email)) {
                                sellerToDelete = seller;
                                break;
                        }
                }
                if (sellerToDelete == null) {
                        System.out.println("Seller not found.");
                        return;
                }
                System.out.print("Delete seller " + sellerToDelete.getName() + " and all their products? (y/n): ");
                if (!readLine().trim().equalsIgnoreCase("y")) {
                        System.out.println("Deletion cancelled.");
                        return;
                }
                for (Product product : new ArrayList<>(sellerToDelete.getProducts())) {
                        removeProductEverywhere(product);
                }
                sellers.remove(sellerToDelete);
                System.out.println("Seller deleted successfully.");
        }

        // Removes a product from the global list, its category, its seller and every
        // cart and wishlist that still holds it (including the guest cart).
        private static void removeProductEverywhere(Product product) {
                if (product == null) {
                        return;
                }
                products.remove(product);
                if (product.getCategory() != null) {
                        product.getCategory().removeProduct(product);
                }
                if (product.getSeller() != null) {
                        product.getSeller().removeProduct(product);
                }
                guestCart.removeProduct(product);
                for (Buyer buyer : buyers) {
                        buyer.removeProductReferences(product);
                }
        }

        // Allows the admin to remove a buyer.
        private static void deleteBuyer() {
                System.out.print("Enter buyer email to delete: ");
                String email = readLine().trim();
                Buyer buyerToDelete = null;
                for (Buyer buyer : buyers) {
                        if (buyer.getEmail().equalsIgnoreCase(email)) {
                                buyerToDelete = buyer;
                                break;
                        }
                }
                if (buyerToDelete == null) {
                        System.out.println("Buyer not found.");
                        return;
                }
                System.out.print("Delete buyer " + buyerToDelete.getName() + "? (y/n): ");
                if (!readLine().trim().equalsIgnoreCase("y")) {
                        System.out.println("Deletion cancelled.");
                        return;
                }
                buyers.remove(buyerToDelete);
                System.out.println("Buyer deleted successfully.");
        }

        // This method displays the seller menu.
        private static void sellerMenu(Seller seller) {
                if (seller == null)
                        return;
                while (true) {
                        System.out.println("\n   SELLER MENU   ");
                        System.out.println("1. View Products");
                        System.out.println("2. Add New Product");
                        System.out.println("3. Create Category");
                        System.out.println("4. View Reviews");
                        System.out.println("5. Logout");
                        System.out.print("Choose an option: ");
                        int option = readInt();
                        switch (option) {
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
                                        seller.logout();
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // This method displays the buyer menu.
        private static void buyerMenu(Buyer buyer) {
                if (buyer == null)
                        return;
                while (true) {
                        System.out.println("\n   BUYER MENU   ");
                        System.out.println("1. Browse Products");
                        System.out.println("2. Add Product to Cart");
                        System.out.println("3. View Cart");
                        System.out.println("4. Move Item from Cart to Wishlist");
                        System.out.println("5. Place Order");
                        System.out.println("6. Add Product to Wishlist");
                        System.out.println("7. View Wishlist");
                        System.out.println("8. Give Review");
                        System.out.println("9. View Product Reviews");
                        System.out.println("10. View Order History");
                        System.out.println("11. Raise Support Ticket");
                        System.out.println("12. View My Support Tickets");
                        System.out.println("13. Remove Item from Cart");
                        System.out.println("14. Logout");
                        System.out.print("Choose an option: ");
                        int option = readInt();
                        switch (option) {
                                case 1:
                                        viewProductsByCategory();
                                        break;
                                case 2:
                                        addProductToCart(buyer);
                                        break;
                                case 3:
                                        buyer.viewCart();
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
                                        buyer.viewWishlist();
                                        break;
                                case 8:
                                        giveReview(buyer);
                                        break;
                                case 9:
                                        viewAllProductReviews(buyer);
                                        break;
                                case 10:
                                        buyer.viewOrderHistory();
                                        break;
                                case 11:
                                        raiseSupportTicket(buyer);
                                        break;
                                case 12:
                                        buyer.viewTickets();
                                        break;
                                case 13:
                                        removeFromCart(buyer);
                                        break;
                                case 14:
                                        buyer.logout();
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // This method displays the products owned by a particular seller.
        private static void viewSellerProducts(Seller seller) {
                ArrayList<Product> ownProducts = seller.getProducts();

                if (ownProducts.isEmpty()) {
                        System.out.println("You have no products assigned yet.");
                } else {
                        System.out.println("\n Products of " + seller.getName() + " :");
                        printProductTable(ownProducts);
                }
        }

        // Displays reviews left by buyers for this seller's products.
        private static void viewSellerReviews(Seller seller) {
                ArrayList<Product> ownProducts = seller.getProducts();
                if (ownProducts.isEmpty()) {
                        System.out.println("You have no products to show reviews for.");
                        return;
                }
                System.out.println("\n REVIEWS FOR PRODUCTS OF " + seller.getName().toUpperCase() + " ");
                boolean reviewsFound = false;
                for (Product product : ownProducts) {
                        if (product.getReviews().isEmpty()) {
                                continue;
                        }

                        reviewsFound = true;
                        System.out.println("\nProduct: " + product.getProductName());
                        product.displayReviews();
                }
                if (!reviewsFound) {
                        System.out.println("No buyer reviews are available for your products.");
                }
        }

        // Displays only reviews written by other buyers for products with reviews.
        private static void viewAllProductReviews(Buyer buyer) {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return;
                }
                System.out.println("\n   ALL PRODUCT REVIEWS   ");
                boolean reviewsFound = false;
                for (Product product : products) {
                        ArrayList<Review> otherBuyerReviews = new ArrayList<>();
                        int totalRating = 0;
                        for (Review review : product.getReviews()) {
                                if (review.getBuyer() != buyer) {
                                        otherBuyerReviews.add(review);
                                        totalRating += review.getRating();
                                }
                        }
                        if (otherBuyerReviews.isEmpty()) {
                                continue;
                        }
                        reviewsFound = true;
                        System.out.println("\nProduct: " + product.getProductName()
                                        + " | Seller: " + product.getSeller().getName()
                                        + " | Average Rating: " + String.format("%.1f",
                                                        (double) totalRating / otherBuyerReviews.size())
                                        + "/5");
                        System.out.println("\n    REVIEWS ");
                        for (Review review : otherBuyerReviews) {
                                System.out.println(review);
                        }
                }
                if (!reviewsFound) {
                        System.out.println("No reviews from other buyers are available.");
                }
        }

        // This method allows a seller to add a new product to the system.
        private static void addNewProduct(Seller seller) {
                if (categories.isEmpty()) {
                        System.out.println("Create a category first.");
                        return;
                }
                System.out.println("\n   ADD NEW PRODUCT   ");
                System.out.print("Product name: ");
                String name = readLine().trim();
                System.out.print("Description: ");
                String description = readLine().trim();
                System.out.print("Price: Rs ");
                double price = readDouble();
                System.out.print("Stock: ");
                int stock = readInt();

                if (name.isEmpty() || description.isEmpty()) {
                        System.out.println("Product name and description cannot be empty.");
                        return;
                }
                if (!Double.isFinite(price) || price <= 0) {
                        System.out.println("Price must be a positive number.");
                        return;
                }
                if (stock < 0) {
                        System.out.println("Stock cannot be negative.");
                        return;
                }

                for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
                }
                System.out.print("Choose category number: ");
                int categoryChoice = readInt();

                if (categoryChoice < 1 || categoryChoice > categories.size()) {
                        System.out.println("Invalid category.");
                        return;
                }

                Category category = categories.get(categoryChoice - 1);
                Product product = new Product(nextProductId++, name, description, price, stock, category,
                                seller);
                seller.addProduct(product);
                category.addProduct(product);
                products.add(product);
                System.out.println("Product added successfully.");
        }

        // Prints a list of products in a clean tabular format.
        private static void printProductTable(ArrayList<Product> productList) {
                if (productList.isEmpty()) {
                        System.out.println("No products to display.");
                        return;
                }
                String format = "%-6s %-20s %-10s %-8s %-15s %-15s %-6s%n";
                System.out.printf(format, "ID", "Name", "Price", "Stock", "Category", "Seller", "Rating");
                System.out.println("-".repeat(90));
                for (Product p : productList) {
                        System.out.printf(format,
                                        p.getProductId(),
                                        truncate(p.getProductName(), 20),
                                        "Rs " + p.getPrice(),
                                        p.getStock(),
                                        truncate(p.getCategory() != null ? p.getCategory().getCategoryName() : "N/A",
                                                        15),
                                        truncate(p.getSeller() != null ? p.getSeller().getName() : "N/A", 15),
                                        String.format("%.1f", p.getAverageRating()));
                }
        }

        // Helps in order to prevent long names from breaking table alignment.
        private static String truncate(String text, int maxLength) {
                if (text.length() <= maxLength) {
                        return text;
                }
                return text.substring(0, maxLength - 3) + "...";
        }

        // This method allows a seller to create a new category in the system.
        private static void createCategory() {
                System.out.print("Enter category name: ");
                String name = readLine().trim();
                if (name.isEmpty()) {
                        System.out.println("Category name cannot be empty.");
                        return;
                }
                if (findCategoryByName(name) != null) {
                        System.out.println("That category already exists.");
                        return;
                }
                Category category = new Category(categories.size() + 101, name);
                categories.add(category);
                System.out.println("Category created successfully.");
        }

        // Displays products for a category chosen by number or name.
        // Entering 'all', selecting the All option, or pressing Enter shows every
        // product.
        private static ArrayList<Product> selectProductsByCategory() {
                if (categories.isEmpty()) {
                        System.out.println("No categories available.");
                        return new ArrayList<>();
                }
                System.out.println("\nAvailable categories: ");
                for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
                }
                int allOption = categories.size() + 1;
                System.out.println(allOption + ". All Products");
                System.out.println("0. Back");
                System.out.print("Choose category number or name (or press Enter for all): ");
                String input = readLine().trim();
                if (input.isEmpty() || input.equalsIgnoreCase("all") || input.equals(String.valueOf(allOption))) {
                        System.out.println("\n--- ALL PRODUCTS ---");
                        printProductTable(products);
                        return products;
                }
                if (input.equals("0") || input.equalsIgnoreCase("back")) {
                        return new ArrayList<>();
                }
                Category selectedCategory = null;
                try {
                        int choice = Integer.parseInt(input);
                        if (choice >= 1 && choice <= categories.size()) {
                                selectedCategory = categories.get(choice - 1);
                        }
                } catch (NumberFormatException ignored) {
                }
                if (selectedCategory == null) {
                        selectedCategory = findCategoryByName(input);
                }
                if (selectedCategory == null) {
                        System.out.println("Category not found.");
                        return new ArrayList<>();
                }
                System.out.println("\n--- " + selectedCategory.getCategoryName().toUpperCase() + " PRODUCTS ---");
                printProductTable(selectedCategory.getProducts());
                return selectedCategory.getProducts();
        }

        // Kept for the "Browse Products" menu option, which doesn't need the list back.
        private static void viewProductsByCategory() {
                selectProductsByCategory();
        }

        // Finds a category by name, ignoring case.
        private static Category findCategoryByName(String name) {
                for (Category category : categories) {
                        if (category.getCategoryName().equalsIgnoreCase(name)) {
                                return category;
                        }
                }
                return null;
        }

        // Lets the user browse (by category) and pick a product by ID.
        // Returns the chosen product, or null if nothing valid was chosen.
        private static Product chooseProduct(String action) {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return null;
                }
                ArrayList<Product> availableProducts = selectProductsByCategory();
                if (availableProducts == null || availableProducts.isEmpty()) {
                        return null;
                }
                System.out.print("Enter product ID to " + action + " (or 0 to cancel): ");
                int productId = readInt();
                if (productId == 0) {
                        return null;
                }
                Product product = findProductInList(productId, availableProducts);
                if (product == null) {
                        System.out.println("Invalid product ID for this selection.");
                }
                return product;
        }

        // Asks for a quantity. Returns 0 (after a message) if it isn't positive.
        private static int readQuantity() {
                System.out.print("Quantity: ");
                int quantity = readInt();
                if (quantity <= 0) {
                        System.out.println("Quantity must be positive.");
                        return 0;
                }
                return quantity;
        }

        // Shows a cart and lets the user pick one of its products by ID.
        // Returns the chosen product, or null if the cart is empty or the ID is
        // invalid.
        private static Product chooseProductInCart(Cart cart, String action) {
                if (cart.getItems().isEmpty()) {
                        System.out.println("Cart is empty.");
                        return null;
                }
                cart.displayCart();
                System.out.print("Enter product ID to " + action + " (or 0 to cancel): ");
                int productId = readInt();
                if (productId == 0) {
                        return null;
                }
                Product product = findProductInCart(productId, cart);
                if (product == null) {
                        System.out.println("That product isn't in your cart.");
                }
                return product;
        }

        // This method allows a buyer to add a product to their cart based on the
        // product ID they input.
        private static void addProductToCart(Buyer buyer) {
                Product product = chooseProduct("add to cart");
                if (product == null) {
                        return;
                }
                int quantity = readQuantity();
                if (quantity <= 0) {
                        return;
                }
                buyer.addToCart(product, quantity);
        }

        // This method allows a buyer to remove an item from their cart.
        private static void removeFromCart(Buyer buyer) {
                Product product = chooseProductInCart(buyer.getCart(), "remove");
                if (product != null) {
                        buyer.removeFromCart(product);
                }
        }

        // Finds a product by ID, but only within a specific list (e.g. a category's
        // products).
        private static Product findProductInList(int productId, ArrayList<Product> productList) {
                for (Product product : productList) {
                        if (product.getProductId() == productId) {
                                return product;
                        }
                }
                return null;
        }

        // This method allows a buyer to move an item from their cart to their wishlist.
        private static void moveCartItemToWishlist(Buyer buyer) {
                Product product = chooseProductInCart(buyer.getCart(), "move to wishlist");
                if (product != null) {
                        buyer.moveToWishlist(product);
                }
        }

        // Finds a product by ID, but only among items currently in the given cart.
        private static Product findProductInCart(int productId, Cart cart) {
                for (CartItem item : cart.getItems()) {
                        if (item.getProduct().getProductId() == productId) {
                                return item.getProduct();
                        }
                }
                return null;
        }

        // This method allows a buyer to place an order either from their cart or by
        // selecting products directly.
        // Payment can be retried if it fails; stock is only deducted once payment
        // succeeds.
        private static void placeOrder(Buyer buyer) {
                Order order;
                if (!buyer.getCart().getItems().isEmpty()) {
                        order = buyer.placeOrder();
                        if (order == null) {
                                return;
                        }
                } else {
                        System.out.println("Your cart is empty. Let's place an order directly.");
                        order = buildDirectOrder(buyer);
                        if (order == null) {
                                return;
                        }
                }

                boolean paid = false;
                while (!paid) {
                        System.out.println("Choose payment method:");
                        for (PaymentMethod method : PaymentMethod.values()) {
                                System.out.println((method.ordinal() + 1) + ". " + method);
                        }
                        System.out.println("0. Cancel order");
                        System.out.print("Select payment option: ");
                        int paymentChoice = readInt();
                        if (paymentChoice == 0) {
                                cancelUnpaidOrder(buyer, order);
                                return;
                        }
                        if (paymentChoice < 1 || paymentChoice > PaymentMethod.values().length) {
                                System.out.println("Invalid payment option.");
                                continue;
                        }
                        paid = order.makePayment(PaymentMethod.values()[paymentChoice - 1]);
                        if (!paid) {
                                System.out.print("Payment failed. Try again? (y/n): ");
                                if (!readLine().trim().equalsIgnoreCase("y")) {
                                        cancelUnpaidOrder(buyer, order);
                                        return;
                                }
                        }
                }
                order.displayOrder();
                buyer.addOrderToHistory(order);
                buyer.getCart().clearCart();
        }

        // Cancels an order that was never paid for. No stock was deducted, and the cart
        // is left as it was so the buyer can try again later.
        private static void cancelUnpaidOrder(Buyer buyer, Order order) {
                order.updateOrderStatus(OrderStatus.CANCELLED);
                buyer.addOrderToHistory(order);
                System.out.println("Order " + order.getOrderId() + " cancelled. No payment was taken.");
        }

        // This method allows a buyer to build an order by selecting products directly.
        private static Order buildDirectOrder(Buyer buyer) {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return null;
                }
                Order order = buyer.startDirectOrder();
                if (order == null) {
                        return null;
                }
                while (true) {
                        viewProductsByCategory();
                        System.out.print("Enter product ID to add (or 0 to finish): ");
                        int productId = readInt();
                        if (productId == 0) {
                                break;
                        }
                        Product product = findProductById(productId);
                        if (product == null) {
                                System.out.println("Product not found.");
                                continue;
                        }
                        int quantity = readQuantity();
                        if (quantity <= 0) {
                                continue;
                        }
                        order.addItem(product, quantity);
                }
                if (order.getItems().isEmpty()) {
                        System.out.println("No items selected. Order cancelled.");
                        return null;
                }
                return order;
        }

        // This method allows a buyer to add a product to their wishlist.
        private static void addProductToWishlist(Buyer buyer) {
                Product product = chooseProduct("add to wishlist");
                if (product != null) {
                        buyer.addToWishlist(product);
                }
        }

        // This method allows a buyer to give a review for a product they purchased.
        private static void giveReview(Buyer buyer) {
                ArrayList<Product> purchasedProducts = buyer.getPurchasedProducts();
                if (purchasedProducts.isEmpty()) {
                        System.out.println("You haven't purchased any products yet.");
                        return;
                }
                System.out.println("\n--- YOUR PURCHASED PRODUCTS ---");
                printProductTable(purchasedProducts);
                System.out.print("Enter product ID to review: ");
                int productId = readInt();
                Product product = findProductInList(productId, purchasedProducts);
                if (product == null) {
                        System.out.println("That product isn't in your purchase history.");
                        return;
                }
                int rating;
                do {
                        System.out.print("Rating (1-5): ");
                        rating = readInt();
                        if (rating < 1 || rating > 5) {
                                System.out.println("Rating must be between 1 and 5.");
                        }
                } while (rating < 1 || rating > 5);
                System.out.print("Comment: ");
                String comment = readLine().trim();
                buyer.giveReview(product, rating, comment);
        }

        // This method finds a product by its ID from the list of products.
        private static Product findProductById(int productId) {
                for (Product product : products) {
                        if (product.getProductId() == productId) {
                                return product;
                        }
                }
                return null;
        }

        // This method allows a buyer to raise a customer care ticket, which is then
        // passed on to an admin.
        private static void raiseSupportTicket(Buyer buyer) {
                System.out.print("Describe your issue: ");
                String issue = readLine().trim();
                CustomerCare ticket = buyer.raiseTicket(issue);
                if (ticket == null) {
                        return;
                }
                if (admins.isEmpty()) {
                        System.out.println("No admin is available right now. Your ticket has been saved.");
                        return;
                }
                admins.get(0).receiveTicket(ticket);
        }

        // Lists all support tickets and lets the admin open one to read it.
        // Opening an OPEN ticket moves it to IN_PROGRESS.
        private static void viewSupportTickets(Admin admin) {
                admin.viewAllTickets();
                if (admin.getTickets().isEmpty()) {
                        return;
                }
                System.out.print("Enter ticket ID to open (or 0 to go back): ");
                int ticketId = readInt();
                if (ticketId == 0) {
                        return;
                }
                CustomerCare ticket = admin.findTicket(ticketId);
                if (ticket == null) {
                        System.out.println("Ticket not found.");
                        return;
                }
                admin.viewTicket(ticket);
        }

        // Lets the admin reply to a ticket and mark it as resolved.
        private static void resolveSupportTicket(Admin admin) {
                admin.viewAllTickets();
                if (admin.getTickets().isEmpty()) {
                        return;
                }
                System.out.print("Enter ticket ID to resolve (or 0 to go back): ");
                int ticketId = readInt();
                if (ticketId == 0) {
                        return;
                }
                CustomerCare ticket = admin.findTicket(ticketId);
                if (ticket == null) {
                        System.out.println("Ticket not found.");
                        return;
                }
                if (ticket.isResolved()) {
                        System.out.println("Ticket " + ticketId + " is already resolved.");
                        return;
                }
                admin.viewTicket(ticket);
                System.out.print("Enter your reply: ");
                String reply = readLine().trim();
                if (reply.isEmpty()) {
                        System.out.println("Reply cannot be empty. Ticket left unresolved.");
                        return;
                }
                admin.resolveTicket(ticket, reply);
        }

        // Lets the admin view every product in the system and remove one.
        private static void manageProducts(Admin admin) {
                admin.manageProducts();
                printProductTable(products);
                if (products.isEmpty()) {
                        return;
                }
                System.out.print("Enter product ID to remove (or 0 to go back): ");
                int productId = readInt();
                if (productId == 0) {
                        return;
                }
                Product product = findProductById(productId);
                if (product == null) {
                        System.out.println("Product not found.");
                        return;
                }
                System.out.print("Remove " + product.getProductName() + "? (y/n): ");
                if (!readLine().trim().equalsIgnoreCase("y")) {
                        System.out.println("Removal cancelled.");
                        return;
                }
                admin.removeProduct(product);
                products.remove(product);
                guestCart.removeProduct(product);
                for (Buyer buyer : buyers) {
                        buyer.removeProductReferences(product);
                }
        }

        // Reads a line of text entered by the user.
        private static String readLine() {
                if (scanner.hasNextLine()) {
                        return scanner.nextLine();
                }
                return "";
        }

        // Repeatedly prompts until the user enters a valid integer.
        private static int readInt() {
                while (true) {
                        try {
                                int value = Integer.parseInt(readLine().trim());
                                return value;
                        } catch (NumberFormatException e) {
                                System.out.print("Invalid number. Enter again: ");
                        }
                }
        }

        // Repeatedly prompts until the user enters a decimal number.
        private static double readDouble() {
                while (true) {
                        try {
                                double value = Double.parseDouble(readLine().trim());
                                return value;
                        } catch (NumberFormatException e) {
                                System.out.print("Invalid number. Enter again: ");
                        }
                }
        }

        private static boolean validRegistration(String name, String email,
                        String password, String phone, String address) {
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()
                                || phone.isEmpty() || address.isEmpty()) {
                        System.out.println("All registration fields are required.");
                        return false;
                }
                if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
                        System.out.println("Enter a valid email address.");
                        return false;
                }
                return true;
        }

        private static boolean emailExists(String email) {
                for (Seller seller : sellers) {
                        if (seller.getEmail().equalsIgnoreCase(email)) {
                                System.out.println("That email is already registered.");
                                return true;
                        }
                }
                for (Buyer buyer : buyers) {
                        if (buyer.getEmail().equalsIgnoreCase(email)) {
                                System.out.println("That email is already registered.");
                                return true;
                        }
                }
                for (Admin admin : admins) {
                        if (admin.getEmail().equalsIgnoreCase(email)) {
                                System.out.println("That email is already registered.");
                                return true;
                        }
                }
                return false;
        }
}