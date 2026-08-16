/* Author: Simran V Naik Dessai
*  Roll No: 2650
* Description: This is a console-based online shopping system implemented in Java. 
* It is the entry point for the application. It sets up some demo data on startup and provides 
* a simple menu-driven interface for users to interact with the system. This drives the whole 
* experience from there — letting users register or log in as a Seller or Buyer, browse products 
* by category, manage a cart and wishlist, place orders, and raise customer care tickets. 
* Each handles their own part of the system, while Admins can manage users and view tickets. 
* The system is designed to be simple and easy to use, demonstrating basic e-commerce functionality in a console environment.
*/

import java.util.ArrayList;
import java.util.Scanner;

//The main class initialized here is the start of this console-based online shopping system.
public class Main {

        // Scanner for user input and lists to hold sellers, buyers, admins,
        // categories,and products.
        private static final Scanner scanner = new Scanner(System.in);
        private static final ArrayList<Seller> sellers = new ArrayList<>();
        private static final ArrayList<Buyer> buyers = new ArrayList<>();
        private static final ArrayList<Admin> admins = new ArrayList<>();
        private static final ArrayList<Category> categories = new ArrayList<>();
        private static final ArrayList<Product> products = new ArrayList<>();

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

                // Adding all products to the products list
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

                // Adding products to sellers
                admins.add(admin);
                sellers.add(seller1);
                sellers.add(seller2);
                buyers.add(buyer1);
                buyers.add(buyer2);
        }

        // This method will display the main menu and handles user input for different
        // roles.

        private static void showMainMenu() {
                while (true) {
                        System.out.println("\n   Online Shopping System   ");
                        System.out.println("1. Register Seller");
                        System.out.println("2. Register Buyer");
                        System.out.println("3. Login as Admin");
                        System.out.println("4. Login as Seller");
                        System.out.println("5. Login as Buyer");
                        System.out.println("6. Exit");
                        System.out.print("Choose an option: ");

                        int option = readInt();

                        switch (option) {
                                case 1:
                                        createSeller();
                                        break;
                                case 2:
                                        createBuyer();
                                        break;
                                case 3:
                                        adminMenu(loginAdmin());
                                        break;
                                case 4:
                                        sellerMenu(loginSeller());
                                        break;
                                case 5:
                                        buyerMenu(loginBuyer());
                                        break;
                                case 6:
                                        System.out.println("Application exited.");
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // This method handles the login process for the admin user.
        private static Admin loginAdmin() {
                final int MAX_ATTEMPTS = 3;
                int attempts = 0;

                while (attempts < MAX_ATTEMPTS) {
                        System.out.print("Enter admin email: ");
                        String email = scanner.nextLine().trim();
                        System.out.print("Enter admin password: ");
                        String password = scanner.nextLine().trim();
                        Admin foundAdmin = null;
                        for (Admin admin : admins) {
                                if (admin.getEmail().equals(email)) {
                                        foundAdmin = admin;
                                        break;
                                }
                        }
                        if (foundAdmin != null && foundAdmin.getPassword().equals(password)) {
                                foundAdmin.login();
                                return foundAdmin;
                        }
                        attempts++;
                        int remaining = MAX_ATTEMPTS - attempts;
                        if (remaining > 0) {
                                System.out.println("Invalid admin credentials. Attempts remaining: " + remaining);
                        } else {
                                System.out.println("Too many failed attempts. Access denied for security reasons.");
                        }
                }
                return null;
        }

        // This method handles the login process for the seller user.
        private static Seller loginSeller() {
                final int MAX_ATTEMPTS = 3;
                int attempts = 0;

                while (attempts < MAX_ATTEMPTS) {
                        System.out.print("Enter seller email: ");
                        String email = scanner.nextLine().trim();
                        System.out.print("Enter seller password: ");
                        String password = scanner.nextLine().trim();

                        Seller foundSeller = null;
                        for (Seller seller : sellers) {
                                if (seller.getEmail().equals(email)) {
                                        foundSeller = seller;
                                        break;
                                }
                        }

                        if (foundSeller != null && foundSeller.getPassword().equals(password)) {
                                foundSeller.login();
                                return foundSeller;
                        }

                        attempts++;
                        int remaining = MAX_ATTEMPTS - attempts;

                        if (remaining > 0) {
                                System.out.println("Invalid seller credentials. Attempts remaining: " + remaining);
                        } else {
                                System.out.println("Too many failed attempts. Access denied for security reasons.");
                        }
                }
                return null;
        }

        // This method handles the login process for the buyer user.
        private static Buyer loginBuyer() {
                final int MAX_ATTEMPTS = 3;
                int attempts = 0;

                while (attempts < MAX_ATTEMPTS) {
                        System.out.print("Enter buyer email: ");
                        String email = scanner.nextLine().trim();
                        System.out.print("Enter buyer password: ");
                        String password = scanner.nextLine().trim();

                        Buyer foundBuyer = null;
                        for (Buyer buyer : buyers) {
                                if (buyer.getEmail().equals(email)) {
                                        foundBuyer = buyer;
                                        break;
                                }
                        }
                        if (foundBuyer != null && foundBuyer.getPassword().equals(password)) {
                                foundBuyer.login();
                                return foundBuyer;
                        }
                        attempts++;
                        int remaining = MAX_ATTEMPTS - attempts;

                        if (remaining > 0) {
                                System.out.println("Invalid buyer credentials. Attempts remaining: " + remaining);
                        } else {
                                System.out.println("Too many failed attempts. Access denied for security reasons.");
                        }
                }
                return null;
        }

        // This method displays the admin menu.
        private static void adminMenu(Admin admin) {
                if (admin == null)
                        return;

                while (true) {
                        System.out.println("\n   ADMIN MENU   ");
                        System.out.println("1. View Tickets");
                        System.out.println("2. Create Seller");
                        System.out.println("3. Create Buyer");
                        System.out.println("4. Logout");
                        System.out.print("Choose an option: ");

                        int option = readInt();

                        switch (option) {
                                case 1:
                                        admin.viewTickets();
                                        break;
                                case 2:
                                        createSeller();
                                        break;
                                case 3:
                                        createBuyer();
                                        break;
                                case 4:
                                        admin.logout();
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
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
                        System.out.println("4. Logout");
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
                        System.out.println("1. View Products");
                        System.out.println("2. Add Product to Cart");
                        System.out.println("3. View Cart");
                        System.out.println("4. Move Item from Cart to Wishlist");
                        System.out.println("5. Place Order");
                        System.out.println("6. Add Product to Wishlist");
                        System.out.println("7. View Wishlist");
                        System.out.println("8. Give Review");
                        System.out.println("9. Logout");
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
                                        buyer.logout();
                                        return;
                                default:
                                        System.out.println("Invalid option. Please select again.");
                        }
                }
        }

        // This method prompts the user to enter details for creating a new seller.
        private static void createSeller() {
                System.out.println("\n   CREATE SELLER   ");
                System.out.print("Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();
                System.out.print("Password: ");
                String password = scanner.nextLine().trim();
                System.out.print("Phone: ");
                String phone = scanner.nextLine().trim();
                System.out.print("Address: ");
                String address = scanner.nextLine().trim();

                Seller seller = new Seller(sellers.size() + 2, name, email, password, phone, address);
                sellers.add(seller);
                System.out.println("Seller created successfully.");
        }

        // This method prompts the user to enter details for creating a new buyer.
        private static void createBuyer() {
                System.out.println("\n   CREATE BUYER   ");
                System.out.print("Name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();
                System.out.print("Password: ");
                String password = scanner.nextLine().trim();
                System.out.print("Phone: ");
                String phone = scanner.nextLine().trim();
                System.out.print("Address: ");
                String address = scanner.nextLine().trim();

                Buyer buyer = new Buyer(buyers.size() + 3, name, email, password, phone, address);
                buyers.add(buyer);
                System.out.println("Buyer created successfully.");
        }

        // This method displays the products owned by a particular seller.
        private static void viewSellerProducts(Seller seller) {
                ArrayList<Product> ownProducts = seller.getProducts();

                if (ownProducts.isEmpty()) {
                        System.out.println(
                                        "You have no products assigned yet. Showing all available products instead:");
                        printProductTable(products);
                } else {
                        System.out.println("\n Products of " + seller.getName() + " :");
                        printProductTable(ownProducts);
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
                String name = scanner.nextLine().trim();
                System.out.print("Description: ");
                String description = scanner.nextLine().trim();
                System.out.print("Price: Rs ");
                double price = readDouble();
                System.out.print("Stock: ");
                int stock = readInt();

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
                Product product = new Product(1000 + products.size() + 1, name, description, price, stock, category,
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
                                        truncate(p.getCategory().getCategoryName(), 15),
                                        truncate(p.getSeller().getName(), 15),
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
                String name = scanner.nextLine().trim();
                Category category = new Category(categories.size() + 101, name);
                categories.add(category);
                System.out.println("Category created successfully.");
        }

        // Displays products for a category the buyer types in directly.
        private static ArrayList<Product> selectProductsByCategory() {
                if (categories.isEmpty()) {
                        System.out.println("No categories available.");
                        return new ArrayList<>();
                }

                System.out.println("\nAvailable categories: ");
                for (Category category : categories) {
                        System.out.print(category.getCategoryName() + "  ");
                }
                System.out.println();

                System.out.print("Enter category name (or 'all' to view everything): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("all")) {
                        printProductTable(products);
                        return products;
                }

                Category selectedCategory = findCategoryByName(input);

                if (selectedCategory == null) {
                        System.out.println("Category not found.");
                        return new ArrayList<>();
                }

                System.out.println("\n--- " + selectedCategory.getCategoryName().toUpperCase() + " PRODUCTS ---");
                printProductTable(selectedCategory.getProducts());
                return selectedCategory.getProducts();
        }

        // Kept for the "View Products" menu option, which doesn't need the list back.
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

        // This method allows a buyer to add a product to their cart basedd on the
        // product ID they input.
        private static void addProductToCart(Buyer buyer) {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return;
                }

                ArrayList<Product> availableProducts = selectProductsByCategory();

                if (availableProducts.isEmpty()) {
                        System.out.println("No products available in this selection.");
                        return;
                }

                System.out.print("Enter product ID to add to cart: ");
                int productId = readInt();

                Product product = findProductInList(productId, availableProducts);

                if (product == null) {
                        System.out.println("Invalid product ID for this selection.");
                        return;
                }

                System.out.print("Quantity: ");
                int quantity = readInt();

                if (quantity <= 0) {
                        System.out.println("Quantity must be positive.");
                        return;
                }

                buyer.addToCart(product, quantity);
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
                if (buyer.getCart().getItems().isEmpty()) {
                        System.out.println("Cart is empty.");
                        return;
                }

                buyer.viewCart();
                System.out.print("Enter product ID to move to wishlist: ");
                int productId = readInt();
                Product product = findProductInCart(productId, buyer.getCart());

                if (product == null) {
                        System.out.println("That product isn't in your cart.");
                        return;
                }

                buyer.moveToWishlist(product);
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
        private static void placeOrder(Buyer buyer) {
                Order order;
                if (!buyer.getCart().getItems().isEmpty()) {
                        order = buyer.placeOrder(5000 + (int) (Math.random() * 100));
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
                System.out.println("Choose payment method:");
                for (PaymentMethod method : PaymentMethod.values()) {
                        System.out.println(method.ordinal() + 1 + ". " + method);
                }
                System.out.print("Select payment option: ");
                int paymentChoice = readInt();

                if (paymentChoice < 1 || paymentChoice > PaymentMethod.values().length) {
                        System.out.println("Invalid payment option.");
                        return;
                }
                order.makePayment(PaymentMethod.values()[paymentChoice - 1]);
                order.displayOrder();
                buyer.addOrderToHistory(order);
                buyer.getCart().clearCart();

        }

        // This method allows a buyer to build an order by selecting products directly.
        private static Order buildDirectOrder(Buyer buyer) {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return null;
                }
                Order order = buyer.startDirectOrder(5000 + (int) (Math.random() * 100));
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
                        System.out.print("Quantity: ");
                        int quantity = readInt();
                        if (quantity <= 0) {
                                System.out.println("Quantity must be positive.");
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
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return;
                }
                ArrayList<Product> availableProducts = selectProductsByCategory();
                if (availableProducts.isEmpty()) {
                        System.out.println("No products available in this selection.");
                        return;
                }
                System.out.print("Enter product ID to add to wishlist: ");
                int productId = readInt();
                Product product = findProductInList(productId, availableProducts);
                if (product == null) {
                        System.out.println("Invalid product ID for this selection.");
                        return;
                }
                buyer.addToWishlist(product);
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
                System.out.print("Rating (1-5): ");
                int rating = readInt();
                System.out.print("Comment: ");
                String comment = scanner.nextLine().trim();
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

        // Repeatedly prompts until the user enters a valid integer.
        private static int readInt() {
                while (true) {
                        try {
                                int value = Integer.parseInt(scanner.nextLine().trim());
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
                                double value = Double.parseDouble(scanner.nextLine().trim());
                                return value;
                        } catch (NumberFormatException e) {
                                System.out.print("Invalid number. Enter again: ");
                        }
                }
        }
}
