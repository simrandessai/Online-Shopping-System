/* Author: Simran V Naik Dessai
 * Roll No: 2650
 * Description: This is an online shopping system implemented in C#. 
 * It is the entry point for the application. 
 * It sets up demo data on startup and provides a simple menu-driven interface for users to interact with the system. 
 * Anyone can browse products and fill a cart without signing in. Sign-in (or registration)
 * is only asked for when the visitor places an order.
 */

using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace OnlineShoppingSystem
{
    public class Program
    {
        private static readonly List<Seller> sellers = new List<Seller>();
        private static readonly List<Buyer> buyers = new List<Buyer>();
        private static readonly List<Admin> admins = new List<Admin>();
        private static readonly List<Category> categories = new List<Category>();
        private static readonly List<Product> products = new List<Product>();

        // Cart used by visitors who haven't signed in. Its items are moved into the
        // buyer's own cart as soon as the visitor logs in or registers.
        private static readonly Cart guestCart = new Cart(0);

        private static int nextProductId = 1021;
        // Demo users use IDs 1-8, so new users continue from 9. A counter (instead of
        // list size) keeps IDs unique even after users are deleted.
        private static int nextUserId = 9;

        public static void Main(string[] args)
        {
            InitializeDemoData();
            ShowMainMenu();
        }

        // Demo data for the system, which includes users, categories, and products.
        private static void InitializeDemoData()
        {
            Admin admin = new Admin(1, "Admin", "admin@shop.com", "admin123", "9000000000", "Head Office");
            Seller seller1 = new Seller(2, "Rahul", "rahul@seller.com", "seller123", "9876543210", "Pune");
            Seller seller2 = new Seller(3, "Sonia", "sonia@seller.com", "seller234", "9123000001", "Delhi");
            Buyer buyer1 = new Buyer(7, "Meena", "meena@buyer.com", "buyer456", "9123456780", "Mumbai");
            Buyer buyer2 = new Buyer(8, "Rohit", "rohit@buyer.com", "buyer234", "9123000011", "Delhi");

            Category electronics = new Category(101, "Electronics");
            Category books = new Category(102, "Books");
            Category clothing = new Category(103, "Clothing");
            Category home = new Category(104, "Home Appliances");

            categories.Add(electronics);
            categories.Add(books);
            categories.Add(clothing);
            categories.Add(home);

            // Adding products to the system
            // Electronics
            Product p1 = new Product(1001, "HP Laptop", "16GB RAM, 512GB SSD", 65000, 10, electronics, seller1);
            Product p2 = new Product(1002, "Samsung Galaxy", "8GB RAM, 128GB Storage", 35000, 15, electronics, seller1);
            Product p3 = new Product(1003, "Sony Headphones", "Noise Cancelling", 15000, 20, electronics, seller1);
            Product p4 = new Product(1004, "Apple iPad", "10-inch tablet with 64GB storage", 45000, 12, electronics, seller1);
            Product p5 = new Product(1005, "Logitech Mouse", "Wireless ergonomic mouse", 1500, 35, electronics, seller1);

            // Books
            Product p6 = new Product(1006, "Java Programming", "Beginner to advanced Java guide", 750, 25, books, seller1);
            Product p7 = new Product(1007, "Cooking Made Easy", "Simple recipes for daily meals", 450, 30, books, seller1);
            Product p8 = new Product(1008, "History of Art", "A tour through world art history", 950, 18, books, seller1);
            Product p9 = new Product(1009, "Children's Stories", "Short stories for kids", 350, 40, books, seller1);
            Product p10 = new Product(1010, "Digital Marketing", "Marketing strategies for online business", 550, 22, books, seller1);

            // Clothing
            Product p11 = new Product(1011, "Men's T-Shirt", "Cotton crew neck t-shirt", 599, 50, clothing, seller2);
            Product p12 = new Product(1012, "Women's Jeans", "Slim fit denim jeans", 1299, 40, clothing, seller2);
            Product p13 = new Product(1013, "Summer Dress", "Floral print dress for women", 1499, 30, clothing, seller2);
            Product p14 = new Product(1014, "Jacket", "Water-resistant winter jacket", 2199, 20, clothing, seller2);
            Product p15 = new Product(1015, "Kids' Hoodie", "Warm hoodie for children", 899, 25, clothing, seller2);

            // Home Appliances
            Product p16 = new Product(1016, "Air Fryer", "2.5L electric air fryer", 4999, 15, home, seller2);
            Product p17 = new Product(1017, "Blender", "Multi-purpose kitchen blender", 2999, 18, home, seller2);
            Product p18 = new Product(1018, "Vacuum Cleaner", "Bagless vacuum cleaner", 7999, 10, home, seller2);
            Product p19 = new Product(1019, "Electric Kettle", "1.7L stainless steel kettle", 1299, 20, home, seller2);
            Product p20 = new Product(1020, "Microwave Oven", "20L microwave with grill", 6999, 8, home, seller2);

            // Adding all products to the products list and to their sellers
            Product[] allProducts = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20 };
            foreach (Product product in allProducts)
            {
                products.Add(product);
                product.GetSeller().AddProduct(product);
            }

            // Adding products to categories
            electronics.AddProduct(p1, p2, p3, p4, p5);
            books.AddProduct(p6, p7, p8, p9, p10);
            clothing.AddProduct(p11, p12, p13, p14, p15);
            home.AddProduct(p16, p17, p18, p19, p20);

            // Adding users to the system
            admins.Add(admin);
            sellers.Add(seller1);
            sellers.Add(seller2);
            buyers.Add(buyer1);
            buyers.Add(buyer2);
        }

        // Displays the main menu open to everyone. Browsing comes first, and visitors can
        // shop without signing in. Login is only needed to place an order or use account features.
        private static void ShowMainMenu()
        {
            while (true)
            {
                Console.WriteLine("\nOnline Shopping System");
                Console.WriteLine("1. Browse Products");
                Console.WriteLine("2. Add Product to Cart");
                Console.WriteLine("3. View Cart");
                Console.WriteLine("4. Remove Item from Cart");
                Console.WriteLine("5. Place Order");
                Console.WriteLine("6. Login as Buyer");
                Console.WriteLine("7. Register Buyer");
                Console.WriteLine("8. Login as Seller");
                Console.WriteLine("9. Register Seller");
                Console.WriteLine("10. Login as Admin");
                Console.WriteLine("11. Exit");
                Console.Write("Choose an option: ");
                int option = ReadInt();
                switch (option)
                {
                    case 1:
                        ViewProductsByCategory();
                        break;
                    case 2:
                        AddProductToGuestCart();
                        break;
                    case 3:
                        guestCart.DisplayCart();
                        break;
                    case 4:
                        RemoveFromGuestCart();
                        break;
                    case 5:
                        GuestPlaceOrder();
                        break;
                    case 6:
                        BuyerMenu(LoginBuyer());
                        break;
                    case 7:
                        CreateBuyer();
                        break;
                    case 8:
                        SellerMenu(LoginSeller());
                        break;
                    case 9:
                        CreateSeller();
                        break;
                    case 10:
                        AdminMenu(LoginAdmin());
                        break;
                    case 11:
                        Console.WriteLine("Application exited.");
                        return;
                    default:
                        Console.WriteLine("Invalid option. Please select again.");
                        break;
                }
            }
        }

        // Visitor (not signed in) adds a product to the guest cart.
        private static void AddProductToGuestCart()
        {
            Product product = ChooseProduct("add to cart");
            if (product == null)
            {
                return;
            }
            int quantity = ReadQuantity();
            if (quantity <= 0)
            {
                return;
            }
            if (guestCart.AddProduct(product, quantity))
            {
                Console.WriteLine(quantity + " x " + product.GetProductName() + " added to cart.");
            }
        }

        // Visitor (not signed in) removes a product from the guest cart.
        private static void RemoveFromGuestCart()
        {
            Product product = ChooseProductInCart(guestCart, "remove");
            if (product == null)
            {
                return;
            }
            guestCart.RemoveProduct(product);
            Console.WriteLine(product.GetProductName() + " removed from cart.");
        }

        // A visitor tries to place an order: the cart total is shown, then the visitor
        // must sign in as an existing buyer or register.
        private static void GuestPlaceOrder()
        {
            if (guestCart.GetItems().Count == 0)
            {
                Console.WriteLine("Your cart is empty. Add products to your cart first.");
                return;
            }
            guestCart.DisplayCart();
            Buyer buyer = SignInForCheckout();
            if (buyer == null)
            {
                Console.WriteLine("Order not placed. Your cart has been saved.");
                return;
            }
            PlaceOrder(buyer);
            BuyerMenu(buyer);
        }

        // Verifies the visitor at checkout: log in as an existing buyer, or register.
        // Returns the signed-in buyer, or null if the visitor goes back.
        private static Buyer SignInForCheckout()
        {
            while (true)
            {
                Console.WriteLine("\nPlease sign in to place your order.");
                Console.WriteLine("1. Login as existing buyer");
                Console.WriteLine("2. Register as new buyer");
                Console.WriteLine("0. Back to shopping");
                Console.Write("Choose an option: ");
                int option = ReadInt();
                switch (option)
                {
                    case 1:
                        Buyer existing = LoginBuyer();
                        if (existing != null)
                        {
                            return existing;
                        }
                        break;
                    case 2:
                        Buyer registered = CreateBuyer();
                        if (registered != null)
                        {
                            registered.Login();
                            MergeGuestCart(registered);
                            return registered;
                        }
                        break;
                    case 0:
                        return null;
                    default:
                        Console.WriteLine("Invalid option. Please select again.");
                        break;
                }
            }
        }

        // Moves everything from the guest cart into the buyer's own cart.
        private static void MergeGuestCart(Buyer buyer)
        {
            if (guestCart.GetItems().Count == 0)
            {
                return;
            }
            int moved = 0;
            foreach (CartItem item in guestCart.GetItems())
            {
                if (buyer.GetCart().AddProduct(item.GetProduct(), item.GetQuantity()))
                {
                    moved++;
                }
            }
            guestCart.ClearCart();
            Console.WriteLine(moved + " item(s) from your guest cart were added to your cart.");
        }

        // Prompts user to enter details for creating a new seller.
        private static void CreateSeller()
        {
            Console.WriteLine("\n   CREATE SELLER   ");
            Console.Write("Name: ");
            string name = ReadLine().Trim();
            Console.Write("Email: ");
            string email = ReadLine().Trim();
            Console.Write("Password: ");
            string password = ReadLine().Trim();
            Console.Write("Phone: ");
            string phone = ReadLine().Trim();
            Console.Write("Address: ");
            string address = ReadLine().Trim();

            if (!ValidRegistration(name, email, password, phone, address) || EmailExists(email))
            {
                return;
            }

            Seller seller = new Seller(nextUserId++, name, email, password, phone, address);
            sellers.Add(seller);
            seller.Register();
        }

        // Prompts user to enter details for creating a new buyer.
        // Returns the new buyer, or null if the details were not valid.
        private static Buyer CreateBuyer()
        {
            Console.WriteLine("\n   CREATE BUYER   ");
            Console.Write("Name: ");
            string name = ReadLine().Trim();
            Console.Write("Email: ");
            string email = ReadLine().Trim();
            Console.Write("Password: ");
            string password = ReadLine().Trim();
            Console.Write("Phone: ");
            string phone = ReadLine().Trim();
            Console.Write("Address: ");
            string address = ReadLine().Trim();

            if (!ValidRegistration(name, email, password, phone, address) || EmailExists(email))
            {
                return null;
            }

            Buyer buyer = new Buyer(nextUserId++, name, email, password, phone, address);
            buyers.Add(buyer);
            buyer.Register();
            return buyer;
        }

        // Handles login process for admin user.
        private static Admin LoginAdmin()
        {
            Console.Write("Enter admin email: ");
            string email = ReadLine().Trim();
            Console.Write("Enter admin password: ");
            string password = ReadLine().Trim();
            Admin foundAdmin = null;
            foreach (Admin admin in admins)
            {
                if (admin.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    foundAdmin = admin;
                    break;
                }
            }
            if (foundAdmin != null && foundAdmin.GetPassword().Equals(password))
            {
                foundAdmin.Login();
                return foundAdmin;
            }
            Console.WriteLine("Invalid email or password.");
            return null;
        }

        // Handles login process for seller user.
        private static Seller LoginSeller()
        {
            Console.Write("Enter seller email: ");
            string email = ReadLine().Trim();
            Console.Write("Enter seller password: ");
            string password = ReadLine().Trim();
            Seller foundSeller = null;
            foreach (Seller seller in sellers)
            {
                if (seller.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    foundSeller = seller;
                    break;
                }
            }
            if (foundSeller != null && foundSeller.GetPassword().Equals(password))
            {
                foundSeller.Login();
                return foundSeller;
            }
            Console.WriteLine("Invalid email or password.");
            return null;
        }

        // Handles login process for buyer user. Any items in the
        // guest cart are moved into the buyer's cart on a successful login.
        private static Buyer LoginBuyer()
        {
            Console.Write("Enter buyer email: ");
            string email = ReadLine().Trim();
            Console.Write("Enter buyer password: ");
            string password = ReadLine().Trim();
            Buyer foundBuyer = null;
            foreach (Buyer buyer in buyers)
            {
                if (buyer.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    foundBuyer = buyer;
                    break;
                }
            }
            if (foundBuyer != null && foundBuyer.GetPassword().Equals(password))
            {
                foundBuyer.Login();
                MergeGuestCart(foundBuyer);
                return foundBuyer;
            }
            Console.WriteLine("Invalid email or password.");
            return null;
        }

        // Admin Menu
        private static void AdminMenu(Admin admin)
        {
            if (admin == null)
            {
                return;
            }
            while (true)
            {
                Console.WriteLine("\n   ADMIN MENU   ");
                Console.WriteLine("1. Delete Seller");
                Console.WriteLine("2. Delete Buyer");
                Console.WriteLine("3. Manage Products");
                Console.WriteLine("4. View Support Tickets");
                Console.WriteLine("5. Reply and Resolve Ticket");
                Console.WriteLine("6. Logout");
                Console.Write("Choose an option: ");
                int option = ReadInt();
                switch (option)
                {
                    case 1:
                        DeleteSeller();
                        break;
                    case 2:
                        DeleteBuyer();
                        break;
                    case 3:
                        ManageProducts(admin);
                        break;
                    case 4:
                        ViewSupportTickets(admin);
                        break;
                    case 5:
                        ResolveSupportTicket(admin);
                        break;
                    case 6:
                        admin.Logout();
                        return;
                    default:
                        Console.WriteLine("Invalid option. Please select again.");
                        break;
                }
            }
        }

        // Allows admin to remove a seller and all of the seller's products.
        private static void DeleteSeller()
        {
            Console.Write("Enter seller email to delete: ");
            string email = ReadLine().Trim();
            Seller sellerToDelete = null;
            foreach (Seller seller in sellers)
            {
                if (seller.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    sellerToDelete = seller;
                    break;
                }
            }
            if (sellerToDelete == null)
            {
                Console.WriteLine("Seller not found.");
                return;
            }
            Console.Write("Delete seller " + sellerToDelete.GetName() + " and all their products? (y/n): ");
            if (!ReadLine().Trim().Equals("y", StringComparison.OrdinalIgnoreCase))
            {
                Console.WriteLine("Deletion cancelled.");
                return;
            }
            foreach (Product product in new List<Product>(sellerToDelete.GetProducts()))
            {
                RemoveProductEverywhere(product);
            }
            sellers.Remove(sellerToDelete);
            Console.WriteLine("Seller deleted successfully.");
        }

        // Removes a product from the global list, its category, its seller, and every cart/wishlist.
        private static void RemoveProductEverywhere(Product product)
        {
            if (product == null)
            {
                return;
            }
            products.Remove(product);
            if (product.GetCategory() != null)
            {
                product.GetCategory().RemoveProduct(product);
            }
            if (product.GetSeller() != null)
            {
                product.GetSeller().RemoveProduct(product);
            }
            guestCart.RemoveProduct(product);
            foreach (Buyer buyer in buyers)
            {
                buyer.RemoveProductReferences(product);
            }
        }

        // Allows admin to remove a buyer.
        private static void DeleteBuyer()
        {
            Console.Write("Enter buyer email to delete: ");
            string email = ReadLine().Trim();
            Buyer buyerToDelete = null;
            foreach (Buyer buyer in buyers)
            {
                if (buyer.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    buyerToDelete = buyer;
                    break;
                }
            }
            if (buyerToDelete == null)
            {
                Console.WriteLine("Buyer not found.");
                return;
            }
            Console.Write("Delete buyer " + buyerToDelete.GetName() + "? (y/n): ");
            if (!ReadLine().Trim().Equals("y", StringComparison.OrdinalIgnoreCase))
            {
                Console.WriteLine("Deletion cancelled.");
                return;
            }
            buyers.Remove(buyerToDelete);
            Console.WriteLine("Buyer deleted successfully.");
        }

        // Seller Menu
        private static void SellerMenu(Seller seller)
        {
            if (seller == null)
            {
                return;
            }
            while (true)
            {
                Console.WriteLine("\n   SELLER MENU   ");
                Console.WriteLine("1. View Products");
                Console.WriteLine("2. Add New Product");
                Console.WriteLine("3. Create Category");
                Console.WriteLine("4. View Reviews");
                Console.WriteLine("5. Logout");
                Console.Write("Choose an option: ");
                int option = ReadInt();
                switch (option)
                {
                    case 1:
                        ViewSellerProducts(seller);
                        break;
                    case 2:
                        AddNewProduct(seller);
                        break;
                    case 3:
                        CreateCategory();
                        break;
                    case 4:
                        ViewSellerReviews(seller);
                        break;
                    case 5:
                        seller.Logout();
                        return;
                    default:
                        Console.WriteLine("Invalid option. Please select again.");
                        break;
                }
            }
        }

        // Buyer Menu
        private static void BuyerMenu(Buyer buyer)
        {
            if (buyer == null)
            {
                return;
            }
            while (true)
            {
                Console.WriteLine("\n   BUYER MENU   ");
                Console.WriteLine("1. Browse Products");
                Console.WriteLine("2. Add Product to Cart");
                Console.WriteLine("3. View Cart");
                Console.WriteLine("4. Move Item from Cart to Wishlist");
                Console.WriteLine("5. Place Order");
                Console.WriteLine("6. Add Product to Wishlist");
                Console.WriteLine("7. View Wishlist");
                Console.WriteLine("8. Give Review");
                Console.WriteLine("9. View Product Reviews");
                Console.WriteLine("10. View Order History");
                Console.WriteLine("11. Raise Support Ticket");
                Console.WriteLine("12. View My Support Tickets");
                Console.WriteLine("13. Remove Item from Cart");
                Console.WriteLine("14. Logout");
                Console.Write("Choose an option: ");
                int option = ReadInt();
                switch (option)
                {
                    case 1:
                        ViewProductsByCategory();
                        break;
                    case 2:
                        AddProductToCart(buyer);
                        break;
                    case 3:
                        buyer.ViewCart();
                        break;
                    case 4:
                        MoveCartItemToWishlist(buyer);
                        break;
                    case 5:
                        PlaceOrder(buyer);
                        break;
                    case 6:
                        AddProductToWishlist(buyer);
                        break;
                    case 7:
                        buyer.ViewWishlist();
                        break;
                    case 8:
                        GiveReview(buyer);
                        break;
                    case 9:
                        ViewAllProductReviews(buyer);
                        break;
                    case 10:
                        buyer.ViewOrderHistory();
                        break;
                    case 11:
                        RaiseSupportTicket(buyer);
                        break;
                    case 12:
                        buyer.ViewTickets();
                        break;
                    case 13:
                        RemoveFromCart(buyer);
                        break;
                    case 14:
                        buyer.Logout();
                        return;
                    default:
                        Console.WriteLine("Invalid option. Please select again.");
                        break;
                }
            }
        }

        // Displays products owned by a particular seller.
        private static void ViewSellerProducts(Seller seller)
        {
            List<Product> ownProducts = seller.GetProducts();
            if (ownProducts.Count == 0)
            {
                Console.WriteLine("You have no products assigned yet.");
            }
            else
            {
                Console.WriteLine("\n Products of " + seller.GetName() + " :");
                PrintProductTable(ownProducts);
            }
        }

        // Displays reviews left by buyers for this seller's products.
        private static void ViewSellerReviews(Seller seller)
        {
            List<Product> ownProducts = seller.GetProducts();
            if (ownProducts.Count == 0)
            {
                Console.WriteLine("You have no products to show reviews for.");
                return;
            }
            Console.WriteLine("\n REVIEWS FOR PRODUCTS OF " + seller.GetName().ToUpper() + " ");
            bool reviewsFound = false;
            foreach (Product product in ownProducts)
            {
                if (product.GetReviews().Count == 0)
                {
                    continue;
                }
                reviewsFound = true;
                Console.WriteLine("\nProduct: " + product.GetProductName());
                product.DisplayReviews();
            }
            if (!reviewsFound)
            {
                Console.WriteLine("No buyer reviews are available for your products.");
            }
        }

        // Displays reviews written by other buyers for products.
        private static void ViewAllProductReviews(Buyer buyer)
        {
            if (products.Count == 0)
            {
                Console.WriteLine("No products available.");
                return;
            }
            Console.WriteLine("\n   ALL PRODUCT REVIEWS   ");
            bool reviewsFound = false;
            foreach (Product product in products)
            {
                List<Review> otherBuyerReviews = new List<Review>();
                int totalRating = 0;
                foreach (Review review in product.GetReviews())
                {
                    if (review.GetBuyer() != buyer)
                    {
                        otherBuyerReviews.Add(review);
                        totalRating += review.GetRating();
                    }
                }
                if (otherBuyerReviews.Count == 0)
                {
                    continue;
                }
                reviewsFound = true;
                Console.WriteLine("\nProduct: " + product.GetProductName()
                        + " | Seller: " + (product.GetSeller() != null ? product.GetSeller().GetName() : "N/A")
                        + " | Average Rating: " + string.Format("{0:0.0}", (double)totalRating / otherBuyerReviews.Count)
                        + "/5");
                Console.WriteLine("\n    REVIEWS ");
                foreach (Review review in otherBuyerReviews)
                {
                    Console.WriteLine(review);
                }
            }
            if (!reviewsFound)
            {
                Console.WriteLine("No reviews from other buyers are available.");
            }
        }

        // Allows seller to add a new product.
        private static void AddNewProduct(Seller seller)
        {
            if (categories.Count == 0)
            {
                Console.WriteLine("Create a category first.");
                return;
            }
            Console.WriteLine("\n   ADD NEW PRODUCT   ");
            Console.Write("Product name: ");
            string name = ReadLine().Trim();
            Console.Write("Description: ");
            string description = ReadLine().Trim();
            Console.Write("Price: Rs ");
            double price = ReadDouble();
            Console.Write("Stock: ");
            int stock = ReadInt();

            if (string.IsNullOrEmpty(name) || string.IsNullOrEmpty(description))
            {
                Console.WriteLine("Product name and description cannot be empty.");
                return;
            }
            if (double.IsNaN(price) || double.IsInfinity(price) || price <= 0)
            {
                Console.WriteLine("Price must be a positive number.");
                return;
            }
            if (stock < 0)
            {
                Console.WriteLine("Stock cannot be negative.");
                return;
            }

            for (int i = 0; i < categories.Count; i++)
            {
                Console.WriteLine((i + 1) + ". " + categories[i].GetCategoryName());
            }
            Console.Write("Choose category number: ");
            int categoryChoice = ReadInt();

            if (categoryChoice < 1 || categoryChoice > categories.Count)
            {
                Console.WriteLine("Invalid category.");
                return;
            }

            Category category = categories[categoryChoice - 1];
            Product product = new Product(nextProductId++, name, description, price, stock, category, seller);
            seller.AddProduct(product);
            category.AddProduct(product);
            products.Add(product);
            Console.WriteLine("Product added successfully.");
        }

        // Prints a list of products in a clean tabular format.
        private static void PrintProductTable(List<Product> productList)
        {
            if (productList == null || productList.Count == 0)
            {
                Console.WriteLine("No products to display.");
                return;
            }
            string format = "{0,-6} {1,-20} {2,-10} {3,-8} {4,-15} {5,-15} {6,-6}";
            Console.WriteLine(string.Format(format, "ID", "Name", "Price", "Stock", "Category", "Seller", "Rating"));
            Console.WriteLine(new string('-', 90));
            foreach (Product p in productList)
            {
                Console.WriteLine(string.Format(format,
                        p.GetProductId(),
                        Truncate(p.GetProductName(), 20),
                        "Rs " + p.GetPrice(),
                        p.GetStock(),
                        Truncate(p.GetCategory() != null ? p.GetCategory().GetCategoryName() : "N/A", 15),
                        Truncate(p.GetSeller() != null ? p.GetSeller().GetName() : "N/A", 15),
                        string.Format("{0:0.0}", p.GetAverageRating())));
            }
        }

        private static string Truncate(string text, int maxLength)
        {
            if (text == null)
            {
                return "";
            }
            if (text.Length <= maxLength)
            {
                return text;
            }
            return text.Substring(0, maxLength - 3) + "...";
        }

        // Allows seller to create a new category.
        private static void CreateCategory()
        {
            Console.Write("Enter category name: ");
            string name = ReadLine().Trim();
            if (string.IsNullOrEmpty(name))
            {
                Console.WriteLine("Category name cannot be empty.");
                return;
            }
            if (FindCategoryByName(name) != null)
            {
                Console.WriteLine("That category already exists.");
                return;
            }
            Category category = new Category(categories.Count + 101, name);
            categories.Add(category);
            Console.WriteLine("Category created successfully.");
        }

        // Displays products for a category chosen by number or name.
        // Entering 'all', selecting the All option, or pressing Enter shows every product.
        private static List<Product> SelectProductsByCategory()
        {
            if (categories.Count == 0)
            {
                Console.WriteLine("No categories available.");
                return new List<Product>();
            }
            Console.WriteLine("\nAvailable categories: ");
            for (int i = 0; i < categories.Count; i++)
            {
                Console.WriteLine((i + 1) + ". " + categories[i].GetCategoryName());
            }
            int allOption = categories.Count + 1;
            Console.WriteLine(allOption + ". All Products");
            Console.WriteLine("0. Back");
            Console.Write("Choose category number or name (or press Enter for all): ");
            string input = ReadLine().Trim();
            if (string.IsNullOrEmpty(input) || input.Equals("all", StringComparison.OrdinalIgnoreCase) || input.Equals(allOption.ToString()))
            {
                Console.WriteLine("\n--- ALL PRODUCTS ---");
                PrintProductTable(products);
                return products;
            }
            if (input.Equals("0") || input.Equals("back", StringComparison.OrdinalIgnoreCase))
            {
                return new List<Product>();
            }
            Category selectedCategory = null;
            int choice;
            if (int.TryParse(input, out choice))
            {
                if (choice >= 1 && choice <= categories.Count)
                {
                    selectedCategory = categories[choice - 1];
                }
            }
            if (selectedCategory == null)
            {
                selectedCategory = FindCategoryByName(input);
            }
            if (selectedCategory == null)
            {
                Console.WriteLine("Category not found.");
                return new List<Product>();
            }
            Console.WriteLine("\n--- " + selectedCategory.GetCategoryName().ToUpper() + " PRODUCTS ---");
            PrintProductTable(selectedCategory.GetProducts());
            return selectedCategory.GetProducts();
        }

        private static void ViewProductsByCategory()
        {
            SelectProductsByCategory();
        }

        private static Category FindCategoryByName(string name)
        {
            foreach (Category category in categories)
            {
                if (category.GetCategoryName().Equals(name, StringComparison.OrdinalIgnoreCase))
                {
                    return category;
                }
            }
            return null;
        }

        // Lets the user browse (by category) and pick a product by ID.
        // Returns the chosen product, or null if nothing valid was chosen.
        private static Product ChooseProduct(string action)
        {
            if (products.Count == 0)
            {
                Console.WriteLine("No products available.");
                return null;
            }
            List<Product> availableProducts = SelectProductsByCategory();
            if (availableProducts == null || availableProducts.Count == 0)
            {
                return null;
            }
            Console.Write("Enter product ID to " + action + " (or 0 to cancel): ");
            int productId = ReadInt();
            if (productId == 0)
            {
                return null;
            }
            Product product = FindProductInList(productId, availableProducts);
            if (product == null)
            {
                Console.WriteLine("Invalid product ID for this selection.");
            }
            return product;
        }

        private static int ReadQuantity()
        {
            Console.Write("Quantity: ");
            int quantity = ReadInt();
            if (quantity <= 0)
            {
                Console.WriteLine("Quantity must be positive.");
                return 0;
            }
            return quantity;
        }

        // Shows a cart and lets user pick one of its products by ID.
        private static Product ChooseProductInCart(Cart cart, string action)
        {
            if (cart.GetItems().Count == 0)
            {
                Console.WriteLine("Cart is empty.");
                return null;
            }
            cart.DisplayCart();
            Console.Write("Enter product ID to " + action + " (or 0 to cancel): ");
            int productId = ReadInt();
            if (productId == 0)
            {
                return null;
            }
            Product product = FindProductInCart(productId, cart);
            if (product == null)
            {
                Console.WriteLine("That product isn't in your cart.");
            }
            return product;
        }

        private static void AddProductToCart(Buyer buyer)
        {
            Product product = ChooseProduct("add to cart");
            if (product == null)
            {
                return;
            }
            int quantity = ReadQuantity();
            if (quantity <= 0)
            {
                return;
            }
            buyer.AddToCart(product, quantity);
        }

        private static void RemoveFromCart(Buyer buyer)
        {
            Product product = ChooseProductInCart(buyer.GetCart(), "remove");
            if (product != null)
            {
                buyer.RemoveFromCart(product);
            }
        }

        private static Product FindProductInList(int productId, List<Product> productList)
        {
            foreach (Product product in productList)
            {
                if (product.GetProductId() == productId)
                {
                    return product;
                }
            }
            return null;
        }

        private static void MoveCartItemToWishlist(Buyer buyer)
        {
            Product product = ChooseProductInCart(buyer.GetCart(), "move to wishlist");
            if (product != null)
            {
                buyer.MoveToWishlist(product);
            }
        }

        private static Product FindProductInCart(int productId, Cart cart)
        {
            foreach (CartItem item in cart.GetItems())
            {
                if (item.GetProduct().GetProductId() == productId)
                {
                    return item.GetProduct();
                }
            }
            return null;
        }

        private static void PlaceOrder(Buyer buyer)
        {
            Order order;
            if (buyer.GetCart().GetItems().Count > 0)
            {
                order = buyer.PlaceOrder();
                if (order == null)
                {
                    return;
                }
            }
            else
            {
                Console.WriteLine("Your cart is empty. Let's place an order directly.");
                order = BuildDirectOrder(buyer);
                if (order == null)
                {
                    return;
                }
            }

            Array paymentMethods = Enum.GetValues(typeof(PaymentMethod));
            bool paid = false;
            while (!paid)
            {
                Console.WriteLine("Choose payment method:");
                for (int i = 0; i < paymentMethods.Length; i++)
                {
                    Console.WriteLine((i + 1) + ". " + paymentMethods.GetValue(i));
                }
                Console.WriteLine("0. Cancel order");
                Console.Write("Select payment option: ");
                int paymentChoice = ReadInt();
                if (paymentChoice == 0)
                {
                    CancelUnpaidOrder(buyer, order);
                    return;
                }
                if (paymentChoice < 1 || paymentChoice > paymentMethods.Length)
                {
                    Console.WriteLine("Invalid payment option.");
                    continue;
                }
                PaymentMethod selectedMethod = (PaymentMethod)paymentMethods.GetValue(paymentChoice - 1);
                paid = order.MakePayment(selectedMethod);
                if (!paid)
                {
                    Console.Write("Payment failed. Try again? (y/n): ");
                    if (!ReadLine().Trim().Equals("y", StringComparison.OrdinalIgnoreCase))
                    {
                        CancelUnpaidOrder(buyer, order);
                        return;
                    }
                }
            }
            order.DisplayOrder();
            buyer.AddOrderToHistory(order);
            buyer.GetCart().ClearCart();
        }

        private static void CancelUnpaidOrder(Buyer buyer, Order order)
        {
            order.UpdateOrderStatus(OrderStatus.CANCELLED);
            buyer.AddOrderToHistory(order);
            Console.WriteLine("Order " + order.GetOrderId() + " cancelled. No payment was taken.");
        }

        private static Order BuildDirectOrder(Buyer buyer)
        {
            if (products.Count == 0)
            {
                Console.WriteLine("No products available.");
                return null;
            }
            Order order = buyer.StartDirectOrder();
            if (order == null)
            {
                return null;
            }
            while (true)
            {
                ViewProductsByCategory();
                Console.Write("Enter product ID to add (or 0 to finish): ");
                int productId = ReadInt();
                if (productId == 0)
                {
                    break;
                }
                Product product = FindProductById(productId);
                if (product == null)
                {
                    Console.WriteLine("Product not found.");
                    continue;
                }
                int quantity = ReadQuantity();
                if (quantity <= 0)
                {
                    continue;
                }
                order.AddItem(product, quantity);
            }
            if (order.GetItems().Count == 0)
            {
                Console.WriteLine("No items selected. Order cancelled.");
                return null;
            }
            return order;
        }

        private static void AddProductToWishlist(Buyer buyer)
        {
            Product product = ChooseProduct("add to wishlist");
            if (product != null)
            {
                buyer.AddToWishlist(product);
            }
        }

        private static void GiveReview(Buyer buyer)
        {
            List<Product> purchasedProducts = buyer.GetPurchasedProducts();
            if (purchasedProducts.Count == 0)
            {
                Console.WriteLine("You haven't purchased any products yet.");
                return;
            }
            Console.WriteLine("\n--- YOUR PURCHASED PRODUCTS ---");
            PrintProductTable(purchasedProducts);
            Console.Write("Enter product ID to review: ");
            int productId = ReadInt();
            Product product = FindProductInList(productId, purchasedProducts);
            if (product == null)
            {
                Console.WriteLine("That product isn't in your purchase history.");
                return;
            }
            int rating;
            do
            {
                Console.Write("Rating (1-5): ");
                rating = ReadInt();
                if (rating < 1 || rating > 5)
                {
                    Console.WriteLine("Rating must be between 1 and 5.");
                }
            } while (rating < 1 || rating > 5);

            Console.Write("Comment: ");
            string comment = ReadLine().Trim();
            buyer.GiveReview(product, rating, comment);
        }

        private static Product FindProductById(int productId)
        {
            foreach (Product product in products)
            {
                if (product.GetProductId() == productId)
                {
                    return product;
                }
            }
            return null;
        }

        private static void RaiseSupportTicket(Buyer buyer)
        {
            Console.Write("Describe your issue: ");
            string issue = ReadLine().Trim();
            CustomerCare ticket = buyer.RaiseTicket(issue);
            if (ticket == null)
            {
                return;
            }
            if (admins.Count == 0)
            {
                Console.WriteLine("No admin is available right now. Your ticket has been saved.");
                return;
            }
            admins[0].ReceiveTicket(ticket);
        }

        private static void ViewSupportTickets(Admin admin)
        {
            admin.ViewAllTickets();
            if (admin.GetTickets().Count == 0)
            {
                return;
            }
            Console.Write("Enter ticket ID to open (or 0 to go back): ");
            int ticketId = ReadInt();
            if (ticketId == 0)
            {
                return;
            }
            CustomerCare ticket = admin.FindTicket(ticketId);
            if (ticket == null)
            {
                Console.WriteLine("Ticket not found.");
                return;
            }
            admin.ViewTicket(ticket);
        }

        private static void ResolveSupportTicket(Admin admin)
        {
            admin.ViewAllTickets();
            if (admin.GetTickets().Count == 0)
            {
                return;
            }
            Console.Write("Enter ticket ID to resolve (or 0 to go back): ");
            int ticketId = ReadInt();
            if (ticketId == 0)
            {
                return;
            }
            CustomerCare ticket = admin.FindTicket(ticketId);
            if (ticket == null)
            {
                Console.WriteLine("Ticket not found.");
                return;
            }
            if (ticket.IsResolved())
            {
                Console.WriteLine("Ticket " + ticketId + " is already resolved.");
                return;
            }
            admin.ViewTicket(ticket);
            Console.Write("Enter your reply: ");
            string reply = ReadLine().Trim();
            if (string.IsNullOrEmpty(reply))
            {
                Console.WriteLine("Reply cannot be empty. Ticket left unresolved.");
                return;
            }
            admin.ResolveTicket(ticket, reply);
        }

        private static void ManageProducts(Admin admin)
        {
            admin.ManageProducts();
            PrintProductTable(products);
            if (products.Count == 0)
            {
                return;
            }
            Console.Write("Enter product ID to remove (or 0 to go back): ");
            int productId = ReadInt();
            if (productId == 0)
            {
                return;
            }
            Product product = FindProductById(productId);
            if (product == null)
            {
                Console.WriteLine("Product not found.");
                return;
            }
            Console.Write("Remove " + product.GetProductName() + "? (y/n): ");
            if (!ReadLine().Trim().Equals("y", StringComparison.OrdinalIgnoreCase))
            {
                Console.WriteLine("Removal cancelled.");
                return;
            }
            admin.RemoveProduct(product);
            products.Remove(product);
            guestCart.RemoveProduct(product);
            foreach (Buyer buyer in buyers)
            {
                buyer.RemoveProductReferences(product);
            }
        }

        // Reads a line of text entered by the user.
        private static string ReadLine()
        {
            string line = Console.ReadLine();
            return line ?? "";
        }

        // Repeatedly prompts until the user enters a valid integer.
        private static int ReadInt()
        {
            while (true)
            {
                string input = ReadLine().Trim();
                int value;
                if (int.TryParse(input, out value))
                {
                    return value;
                }
                Console.Write("Invalid number. Enter again: ");
            }
        }

        // Repeatedly prompts until the user enters a decimal number.
        private static double ReadDouble()
        {
            while (true)
            {
                string input = ReadLine().Trim();
                double value;
                if (double.TryParse(input, out value))
                {
                    return value;
                }
                Console.Write("Invalid number. Enter again: ");
            }
        }

        private static bool ValidRegistration(string name, string email, string password, string phone, string address)
        {
            if (string.IsNullOrWhiteSpace(name) || string.IsNullOrWhiteSpace(email) || string.IsNullOrWhiteSpace(password)
                    || string.IsNullOrWhiteSpace(phone) || string.IsNullOrWhiteSpace(address))
            {
                Console.WriteLine("All registration fields are required.");
                return false;
            }
            if (!Regex.IsMatch(email, @"^[^@\s]+@[^@\s]+\.[^@\s]+$"))
            {
                Console.WriteLine("Enter a valid email address.");
                return false;
            }
            return true;
        }

        private static bool EmailExists(string email)
        {
            foreach (Seller seller in sellers)
            {
                if (seller.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("That email is already registered.");
                    return true;
                }
            }
            foreach (Buyer buyer in buyers)
            {
                if (buyer.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("That email is already registered.");
                    return true;
                }
            }
            foreach (Admin admin in admins)
            {
                if (admin.GetEmail().Equals(email, StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("That email is already registered.");
                    return true;
                }
            }
            return false;
        }
    }
}
