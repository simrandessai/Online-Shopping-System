"""
Author: Simran V Naik Dessai
Roll No: 2650
Description: This is an online shopping system implemented in Python.
It sets up some demo data on startup and provides a simple menu-driven interface
for users to interact with the system.
Anyone can browse products and fill a cart without signing in.
Sign-in (or registration) is only asked for when the visitor places an order.
"""
import math
import re
import sys

from admin import Admin
from buyer import Buyer
from cart import Cart
from category import Category
from order_status import OrderStatus
from payment_method import PaymentMethod
from product import Product
from seller import Seller

# Lists to hold sellers, buyers, admins, categories, and products.
sellers = []
buyers = []
admins = []
categories = []
products = []

# Cart used by visitors who haven't signed in. Its items are moved into the
# buyer's own cart as soon as the visitor logs in or registers.
guest_cart = Cart(0)

next_product_id = 1021
# Demo users use IDs 1-8, so new users continue from 9. A counter (instead of
# list size) keeps IDs unique even after users are deleted.
next_user_id = 9



# Input helpers
def prompt(text):
    """Prints a prompt without a trailing newline."""
    print(text, end="", flush=True)


def read_line():
    """Reads a line of text entered by the user (exits cleanly if input ends)."""
    try:
        return input()
    except EOFError:
        print()
        print("Input ended. Application exited.")
        sys.exit(0)


def read_int():
    """Repeatedly prompts until the user enters a valid integer."""
    while True:
        try:
            return int(read_line().strip())
        except ValueError:
            prompt("Invalid number. Enter again: ")


def read_double():
    """Repeatedly prompts until the user enters a decimal number."""
    while True:
        try:
            return float(read_line().strip())
        except ValueError:
            prompt("Invalid number. Enter again: ")


def read_quantity():
    """Asks for a quantity. Returns 0 (after a message) if it isn't positive."""
    prompt("Quantity: ")
    quantity = read_int()
    if quantity <= 0:
        print("Quantity must be positive.")
        return 0
    return quantity


def truncate(text, max_length):
    """Helps prevent long names from breaking table alignment."""
    if len(text) <= max_length:
        return text
    return text[:max_length - 3] + "..."


# Demo data
def initialize_demo_data():
    """Hardcoded demo data: users, categories, and products."""
    admin = Admin(1, "Admin", "admin@shop.com", "admin123", "9000000000", "Head Office")
    seller1 = Seller(2, "Rahul", "rahul@seller.com", "seller123", "9876543210", "Pune")
    seller2 = Seller(3, "Sonia", "sonia@seller.com", "seller234", "9123000001", "Delhi")
    buyer1 = Buyer(7, "Meena", "meena@buyer.com", "buyer456", "9123456780", "Mumbai")
    buyer2 = Buyer(8, "Rohit", "rohit@buyer.com", "buyer234", "9123000011", "Delhi")

    electronics = Category(101, "Electronics")
    books = Category(102, "Books")
    clothing = Category(103, "Clothing")
    home = Category(104, "Home Appliances")

    categories.extend([electronics, books, clothing, home])

    # Electronics
    p1 = Product(1001, "HP Laptop", "16GB RAM, 512GB SSD", 65000, 10, electronics, seller1)
    p2 = Product(1002, "Samsung Galaxy", "8GB RAM, 128GB Storage", 35000, 15, electronics, seller1)
    p3 = Product(1003, "Sony Headphones", "Noise Cancelling", 15000, 20, electronics, seller1)
    p4 = Product(1004, "Apple iPad", "10-inch tablet with 64GB storage", 45000, 12, electronics, seller1)
    p5 = Product(1005, "Logitech Mouse", "Wireless ergonomic mouse", 1500, 35, electronics, seller1)
    # Books
    p6 = Product(1006, "Java Programming", "Beginner to advanced Java guide", 750, 25, books, seller1)
    p7 = Product(1007, "Cooking Made Easy", "Simple recipes for daily meals", 450, 30, books, seller1)
    p8 = Product(1008, "History of Art", "A tour through world art history", 950, 18, books, seller1)
    p9 = Product(1009, "Children's Stories", "Short stories for kids", 350, 40, books, seller1)
    p10 = Product(1010, "Digital Marketing", "Marketing strategies for online business", 550, 22, books, seller1)
    # Clothing
    p11 = Product(1011, "Men's T-Shirt", "Cotton crew neck t-shirt", 599, 50, clothing, seller2)
    p12 = Product(1012, "Women's Jeans", "Slim fit denim jeans", 1299, 40, clothing, seller2)
    p13 = Product(1013, "Summer Dress", "Floral print dress for women", 1499, 30, clothing, seller2)
    p14 = Product(1014, "Jacket", "Water-resistant winter jacket", 2199, 20, clothing, seller2)
    p15 = Product(1015, "Kids' Hoodie", "Warm hoodie for children", 899, 25, clothing, seller2)
    # Home Appliances
    p16 = Product(1016, "Air Fryer", "2.5L electric air fryer", 4999, 15, home, seller2)
    p17 = Product(1017, "Blender", "Multi-purpose kitchen blender", 2999, 18, home, seller2)
    p18 = Product(1018, "Vacuum Cleaner", "Bagless vacuum cleaner", 7999, 10, home, seller2)
    p19 = Product(1019, "Electric Kettle", "1.7L stainless steel kettle", 1299, 20, home, seller2)
    p20 = Product(1020, "Microwave Oven", "20L microwave with grill", 6999, 8, home, seller2)

    # Adding all products to the products list and to their sellers
    all_products = [p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
                    p11, p12, p13, p14, p15, p16, p17, p18, p19, p20]
    for product in all_products:
        products.append(product)
        product.get_seller().add_product(product)

    # Adding products to categories
    electronics.add_product(p1, p2, p3, p4, p5)
    books.add_product(p6, p7, p8, p9, p10)
    clothing.add_product(p11, p12, p13, p14, p15)
    home.add_product(p16, p17, p18, p19, p20)

    # Adding users to the system
    admins.append(admin)
    sellers.extend([seller1, seller2])
    buyers.extend([buyer1, buyer2])


# Main menu
def show_main_menu():
    """Main menu, open to everyone. Login is only needed to place an order
    (or to use buyer, seller and admin features)."""
    while True:
        print("\nOnline Shopping System")
        print("1. Browse Products")
        print("2. Add Product to Cart")
        print("3. View Cart")
        print("4. Remove Item from Cart")
        print("5. Place Order")
        print("6. Login as Buyer")
        print("7. Register Buyer")
        print("8. Login as Seller")
        print("9. Register Seller")
        print("10. Login as Admin")
        print("11. Exit")
        prompt("Choose an option: ")
        option = read_int()
        if option == 1:
            view_products_by_category()
        elif option == 2:
            add_product_to_guest_cart()
        elif option == 3:
            guest_cart.display_cart()
        elif option == 4:
            remove_from_guest_cart()
        elif option == 5:
            guest_place_order()
        elif option == 6:
            buyer_menu(login_buyer())
        elif option == 7:
            create_buyer()
        elif option == 8:
            seller_menu(login_seller())
        elif option == 9:
            create_seller()
        elif option == 10:
            admin_menu(login_admin())
        elif option == 11:
            print("Application exited.")
            return
        else:
            print("Invalid option. Please select again.")


# Guest (not signed in) features
def add_product_to_guest_cart():
    product = choose_product("add to cart")
    if product is None:
        return
    quantity = read_quantity()
    if quantity <= 0:
        return
    if guest_cart.add_product(product, quantity):
        print(f"{quantity} x {product.get_product_name()} added to cart.")


def remove_from_guest_cart():
    product = choose_product_in_cart(guest_cart, "remove")
    if product is None:
        return
    guest_cart.remove_product(product)
    print(f"{product.get_product_name()} removed from cart.")


def guest_place_order():
    """A visitor tries to place an order: the cart total is shown, then the
    visitor must sign in as an existing buyer or register. After that the order
    continues exactly like a normal buyer order (create order -> payment)."""
    if not guest_cart.get_items():
        print("Your cart is empty. Add products to your cart first.")
        return
    guest_cart.display_cart()
    buyer = sign_in_for_checkout()
    if buyer is None:
        print("Order not placed. Your cart has been saved.")
        return
    place_order(buyer)
    buyer_menu(buyer)


def sign_in_for_checkout():
    """Log in as an existing buyer, or register.
    Returns the signed-in buyer, or None if the visitor goes back."""
    while True:
        print("\nPlease sign in to place your order.")
        print("1. Login as existing buyer")
        print("2. Register as new buyer")
        print("0. Back to shopping")
        prompt("Choose an option: ")
        option = read_int()
        if option == 1:
            existing = login_buyer()
            if existing is not None:
                return existing
        elif option == 2:
            registered = create_buyer()
            if registered is not None:
                registered.login()
                merge_guest_cart(registered)
                return registered
        elif option == 0:
            return None
        else:
            print("Invalid option. Please select again.")


def merge_guest_cart(buyer):
    """Moves everything from the guest cart into the buyer's own cart."""
    if not guest_cart.get_items():
        return
    moved = 0
    for item in guest_cart.get_items():
        if buyer.get_cart().add_product(item.get_product(), item.get_quantity()):
            moved += 1
    guest_cart.clear_cart()
    print(f"{moved} item(s) from your guest cart were added to your cart.")


# Registration and login
def read_registration_details():
    prompt("Name: ")
    name = read_line().strip()
    prompt("Email: ")
    email = read_line().strip()
    prompt("Password: ")
    password = read_line().strip()
    prompt("Phone: ")
    phone = read_line().strip()
    prompt("Address: ")
    address = read_line().strip()
    return name, email, password, phone, address


def create_seller():
    global next_user_id
    print("\n   CREATE SELLER   ")
    name, email, password, phone, address = read_registration_details()

    if not valid_registration(name, email, password, phone, address) or email_exists(email):
        return

    seller = Seller(next_user_id, name, email, password, phone, address)
    next_user_id += 1
    sellers.append(seller)
    seller.register()


def create_buyer():
    """Returns the new buyer, or None if the details were not valid."""
    global next_user_id
    print("\n   CREATE BUYER   ")
    name, email, password, phone, address = read_registration_details()

    if not valid_registration(name, email, password, phone, address) or email_exists(email):
        return None

    buyer = Buyer(next_user_id, name, email, password, phone, address)
    next_user_id += 1
    buyers.append(buyer)
    buyer.register()
    return buyer


def find_by_email(user_list, email):
    for user in user_list:
        if user.get_email().lower() == email.lower():
            return user
    return None


def login_admin():
    prompt("Enter admin email: ")
    email = read_line().strip()
    prompt("Enter admin password: ")
    password = read_line().strip()
    found = find_by_email(admins, email)
    if found is not None and found.get_password() == password:
        found.login()
        return found
    print("Invalid email or password.")
    return None


def login_seller():
    prompt("Enter seller email: ")
    email = read_line().strip()
    prompt("Enter seller password: ")
    password = read_line().strip()
    found = find_by_email(sellers, email)
    if found is not None and found.get_password() == password:
        found.login()
        return found
    print("Invalid email or password.")
    return None


def login_buyer():
    """Any items in the guest cart are moved into the buyer's cart on a successful login."""
    prompt("Enter buyer email: ")
    email = read_line().strip()
    prompt("Enter buyer password: ")
    password = read_line().strip()
    found = find_by_email(buyers, email)
    if found is not None and found.get_password() == password:
        found.login()
        merge_guest_cart(found)
        return found
    print("Invalid email or password.")
    return None


def valid_registration(name, email, password, phone, address):
    if not name or not email or not password or not phone or not address:
        print("All registration fields are required.")
        return False
    if not re.fullmatch(r"[^@\s]+@[^@\s]+\.[^@\s]+", email):
        print("Enter a valid email address.")
        return False
    return True


def email_exists(email):
    for user_list in (sellers, buyers, admins):
        if find_by_email(user_list, email) is not None:
            print("That email is already registered.")
            return True
    return False


# Admin
def admin_menu(admin):
    if admin is None:
        return
    while True:
        print("\n   ADMIN MENU   ")
        print("1. Delete Seller")
        print("2. Delete Buyer")
        print("3. Manage Products")
        print("4. View Support Tickets")
        print("5. Reply and Resolve Ticket")
        print("6. Logout")
        prompt("Choose an option: ")
        option = read_int()
        if option == 1:
            delete_seller()
        elif option == 2:
            delete_buyer()
        elif option == 3:
            manage_products(admin)
        elif option == 4:
            view_support_tickets(admin)
        elif option == 5:
            resolve_support_ticket(admin)
        elif option == 6:
            admin.logout()
            return
        else:
            print("Invalid option. Please select again.")


def delete_seller():
    """Allows the admin to remove a seller and the seller's products."""
    prompt("Enter seller email to delete: ")
    email = read_line().strip()
    seller_to_delete = find_by_email(sellers, email)
    if seller_to_delete is None:
        print("Seller not found.")
        return
    prompt(f"Delete seller {seller_to_delete.get_name()} and all their products? (y/n): ")
    if read_line().strip().lower() != "y":
        print("Deletion cancelled.")
        return
    for product in list(seller_to_delete.get_products()):
        remove_product_everywhere(product)
    sellers.remove(seller_to_delete)
    print("Seller deleted successfully.")


def remove_product_everywhere(product):
    """Removes a product from the global list, its category, its seller and every
    cart and wishlist that still holds it (including the guest cart)."""
    if product is None:
        return
    if product in products:
        products.remove(product)
    if product.get_category() is not None:
        product.get_category().remove_product(product)
    if product.get_seller() is not None:
        product.get_seller().remove_product(product)
    guest_cart.remove_product(product)
    for buyer in buyers:
        buyer.remove_product_references(product)


def delete_buyer():
    """Allows the admin to remove a buyer."""
    prompt("Enter buyer email to delete: ")
    email = read_line().strip()
    buyer_to_delete = find_by_email(buyers, email)
    if buyer_to_delete is None:
        print("Buyer not found.")
        return
    prompt(f"Delete buyer {buyer_to_delete.get_name()}? (y/n): ")
    if read_line().strip().lower() != "y":
        print("Deletion cancelled.")
        return
    buyers.remove(buyer_to_delete)
    print("Buyer deleted successfully.")


def view_support_tickets(admin):
    """Lists all support tickets and lets the admin open one to read it.
    Opening an OPEN ticket moves it to IN_PROGRESS."""
    admin.view_all_tickets()
    if not admin.get_tickets():
        return
    prompt("Enter ticket ID to open (or 0 to go back): ")
    ticket_id = read_int()
    if ticket_id == 0:
        return
    ticket = admin.find_ticket(ticket_id)
    if ticket is None:
        print("Ticket not found.")
        return
    admin.view_ticket(ticket)


def resolve_support_ticket(admin):
    """Lets the admin reply to a ticket and mark it as resolved."""
    admin.view_all_tickets()
    if not admin.get_tickets():
        return
    prompt("Enter ticket ID to resolve (or 0 to go back): ")
    ticket_id = read_int()
    if ticket_id == 0:
        return
    ticket = admin.find_ticket(ticket_id)
    if ticket is None:
        print("Ticket not found.")
        return
    if ticket.is_resolved():
        print(f"Ticket {ticket_id} is already resolved.")
        return
    admin.view_ticket(ticket)
    prompt("Enter your reply: ")
    reply = read_line().strip()
    if not reply:
        print("Reply cannot be empty. Ticket left unresolved.")
        return
    admin.resolve_ticket(ticket, reply)


def manage_products(admin):
    """Lets the admin view every product in the system and remove one."""
    admin.manage_products()
    print_product_table(products)
    if not products:
        return
    prompt("Enter product ID to remove (or 0 to go back): ")
    product_id = read_int()
    if product_id == 0:
        return
    product = find_product_by_id(product_id)
    if product is None:
        print("Product not found.")
        return
    prompt(f"Remove {product.get_product_name()}? (y/n): ")
    if read_line().strip().lower() != "y":
        print("Removal cancelled.")
        return
    admin.remove_product(product)
    products.remove(product)
    guest_cart.remove_product(product)
    for buyer in buyers:
        buyer.remove_product_references(product)


# Seller
def seller_menu(seller):
    if seller is None:
        return
    while True:
        print("\n   SELLER MENU   ")
        print("1. View Products")
        print("2. Add New Product")
        print("3. Create Category")
        print("4. View Reviews")
        print("5. Logout")
        prompt("Choose an option: ")
        option = read_int()
        if option == 1:
            view_seller_products(seller)
        elif option == 2:
            add_new_product(seller)
        elif option == 3:
            create_category()
        elif option == 4:
            view_seller_reviews(seller)
        elif option == 5:
            seller.logout()
            return
        else:
            print("Invalid option. Please select again.")


def view_seller_products(seller):
    own_products = seller.get_products()
    if not own_products:
        print("You have no products assigned yet.")
    else:
        print(f"\n Products of {seller.get_name()} :")
        print_product_table(own_products)


def view_seller_reviews(seller):
    """Displays reviews left by buyers for this seller's products."""
    own_products = seller.get_products()
    if not own_products:
        print("You have no products to show reviews for.")
        return
    print(f"\n REVIEWS FOR PRODUCTS OF {seller.get_name().upper()} ")
    reviews_found = False
    for product in own_products:
        if not product.get_reviews():
            continue
        reviews_found = True
        print(f"\nProduct: {product.get_product_name()}")
        product.display_reviews()
    if not reviews_found:
        print("No buyer reviews are available for your products.")


def add_new_product(seller):
    """Allows a seller to add a new product to the system."""
    global next_product_id
    if not categories:
        print("Create a category first.")
        return
    print("\n   ADD NEW PRODUCT   ")
    prompt("Product name: ")
    name = read_line().strip()
    prompt("Description: ")
    description = read_line().strip()
    prompt("Price: Rs ")
    price = read_double()
    prompt("Stock: ")
    stock = read_int()

    if not name or not description:
        print("Product name and description cannot be empty.")
        return
    if not math.isfinite(price) or price <= 0:
        print("Price must be a positive number.")
        return
    if stock < 0:
        print("Stock cannot be negative.")
        return

    for i, category in enumerate(categories):
        print(f"{i + 1}. {category.get_category_name()}")
    prompt("Choose category number: ")
    category_choice = read_int()

    if category_choice < 1 or category_choice > len(categories):
        print("Invalid category.")
        return

    category = categories[category_choice - 1]
    product = Product(next_product_id, name, description, price, stock, category, seller)
    next_product_id += 1
    seller.add_product(product)
    category.add_product(product)
    products.append(product)
    print("Product added successfully.")


def create_category():
    """Allows a seller to create a new category in the system."""
    prompt("Enter category name: ")
    name = read_line().strip()
    if not name:
        print("Category name cannot be empty.")
        return
    if find_category_by_name(name) is not None:
        print("That category already exists.")
        return
    category = Category(len(categories) + 101, name)
    categories.append(category)
    print("Category created successfully.")


# Buyer
def buyer_menu(buyer):
    if buyer is None:
        return
    while True:
        print("\n   BUYER MENU   ")
        print("1. Browse Products")
        print("2. Add Product to Cart")
        print("3. View Cart")
        print("4. Move Item from Cart to Wishlist")
        print("5. Place Order")
        print("6. Add Product to Wishlist")
        print("7. View Wishlist")
        print("8. Give Review")
        print("9. View Product Reviews")
        print("10. View Order History")
        print("11. Raise Support Ticket")
        print("12. View My Support Tickets")
        print("13. Remove Item from Cart")
        print("14. Logout")
        prompt("Choose an option: ")
        option = read_int()
        if option == 1:
            view_products_by_category()
        elif option == 2:
            add_product_to_cart(buyer)
        elif option == 3:
            buyer.view_cart()
        elif option == 4:
            move_cart_item_to_wishlist(buyer)
        elif option == 5:
            place_order(buyer)
        elif option == 6:
            add_product_to_wishlist(buyer)
        elif option == 7:
            buyer.view_wishlist()
        elif option == 8:
            give_review(buyer)
        elif option == 9:
            view_all_product_reviews(buyer)
        elif option == 10:
            buyer.view_order_history()
        elif option == 11:
            raise_support_ticket(buyer)
        elif option == 12:
            buyer.view_tickets()
        elif option == 13:
            remove_from_cart(buyer)
        elif option == 14:
            buyer.logout()
            return
        else:
            print("Invalid option. Please select again.")


def view_all_product_reviews(buyer):
    """Displays only reviews written by other buyers for products with reviews."""
    if not products:
        print("No products available.")
        return
    print("\n   ALL PRODUCT REVIEWS   ")
    reviews_found = False
    for product in products:
        other_buyer_reviews = []
        total_rating = 0
        for review in product.get_reviews():
            if review.get_buyer() is not buyer:
                other_buyer_reviews.append(review)
                total_rating += review.get_rating()
        if not other_buyer_reviews:
            continue
        reviews_found = True
        average = total_rating / len(other_buyer_reviews)
        print(f"\nProduct: {product.get_product_name()}"
              f" | Seller: {product.get_seller().get_name()}"
              f" | Average Rating: {average:.1f}/5")
        print("\n    REVIEWS ")
        for review in other_buyer_reviews:
            print(review)
    if not reviews_found:
        print("No reviews from other buyers are available.")


def add_product_to_cart(buyer):
    """Allows a buyer to add a product to their cart based on the product ID they input."""
    product = choose_product("add to cart")
    if product is None:
        return
    quantity = read_quantity()
    if quantity <= 0:
        return
    buyer.add_to_cart(product, quantity)


def remove_from_cart(buyer):
    product = choose_product_in_cart(buyer.get_cart(), "remove")
    if product is not None:
        buyer.remove_from_cart(product)


def move_cart_item_to_wishlist(buyer):
    product = choose_product_in_cart(buyer.get_cart(), "move to wishlist")
    if product is not None:
        buyer.move_to_wishlist(product)


def add_product_to_wishlist(buyer):
    product = choose_product("add to wishlist")
    if product is not None:
        buyer.add_to_wishlist(product)


def place_order(buyer):
    """Places an order either from the cart or by selecting products directly.
    Payment can be retried if it fails; stock is only deducted once payment succeeds."""
    if buyer.get_cart().get_items():
        order = buyer.place_order()
        if order is None:
            return
    else:
        print("Your cart is empty. Let's place an order directly.")
        order = build_direct_order(buyer)
        if order is None:
            return

    methods = list(PaymentMethod)
    paid = False
    while not paid:
        print("Choose payment method:")
        for index, method in enumerate(methods):
            print(f"{index + 1}. {method}")
        print("0. Cancel order")
        prompt("Select payment option: ")
        payment_choice = read_int()
        if payment_choice == 0:
            cancel_unpaid_order(buyer, order)
            return
        if payment_choice < 1 or payment_choice > len(methods):
            print("Invalid payment option.")
            continue
        paid = order.make_payment(methods[payment_choice - 1])
        if not paid:
            prompt("Payment failed. Try again? (y/n): ")
            if read_line().strip().lower() != "y":
                cancel_unpaid_order(buyer, order)
                return
    order.display_order()
    buyer.add_order_to_history(order)
    buyer.get_cart().clear_cart()


def cancel_unpaid_order(buyer, order):
    """Cancels an order that was never paid for. No stock was deducted, and the
    cart is left as it was so the buyer can try again later."""
    order.update_order_status(OrderStatus.CANCELLED)
    buyer.add_order_to_history(order)
    print(f"Order {order.get_order_id()} cancelled. No payment was taken.")


def build_direct_order(buyer):
    """Allows a buyer to build an order by selecting products directly."""
    if not products:
        print("No products available.")
        return None
    order = buyer.start_direct_order()
    if order is None:
        return None
    while True:
        view_products_by_category()
        prompt("Enter product ID to add (or 0 to finish): ")
        product_id = read_int()
        if product_id == 0:
            break
        product = find_product_by_id(product_id)
        if product is None:
            print("Product not found.")
            continue
        quantity = read_quantity()
        if quantity <= 0:
            continue
        order.add_item(product, quantity)
    if not order.get_items():
        print("No items selected. Order cancelled.")
        return None
    return order


def give_review(buyer):
    """Allows a buyer to give a review for a product they purchased."""
    purchased_products = buyer.get_purchased_products()
    if not purchased_products:
        print("You haven't purchased any products yet.")
        return
    print("\n--- YOUR PURCHASED PRODUCTS ---")
    print_product_table(purchased_products)
    prompt("Enter product ID to review: ")
    product_id = read_int()
    product = find_product_in_list(product_id, purchased_products)
    if product is None:
        print("That product isn't in your purchase history.")
        return
    while True:
        prompt("Rating (1-5): ")
        rating = read_int()
        if 1 <= rating <= 5:
            break
        print("Rating must be between 1 and 5.")
    prompt("Comment: ")
    comment = read_line().strip()
    buyer.give_review(product, rating, comment)


def raise_support_ticket(buyer):
    """Allows a buyer to raise a customer care ticket, which is passed on to an admin."""
    prompt("Describe your issue: ")
    issue = read_line().strip()
    ticket = buyer.raise_ticket(issue)
    if ticket is None:
        return
    if not admins:
        print("No admin is available right now. Your ticket has been saved.")
        return
    admins[0].receive_ticket(ticket)


# Browsing and product selection
def print_product_table(product_list):
    """Prints a list of products in a clean tabular format."""
    if not product_list:
        print("No products to display.")
        return
    row = "{:<6} {:<20} {:<10} {:<8} {:<15} {:<15} {:<6}"
    print(row.format("ID", "Name", "Price", "Stock", "Category", "Seller", "Rating"))
    print("-" * 90)
    for p in product_list:
        category_name = p.get_category().get_category_name() if p.get_category() is not None else "N/A"
        seller_name = p.get_seller().get_name() if p.get_seller() is not None else "N/A"
        print(row.format(
            p.get_product_id(),
            truncate(p.get_product_name(), 20),
            "Rs " + str(p.get_price()),
            p.get_stock(),
            truncate(category_name, 15),
            truncate(seller_name, 15),
            f"{p.get_average_rating():.1f}"))


def find_category_by_name(name):
    """Finds a category by name, ignoring case."""
    for category in categories:
        if category.get_category_name().lower() == name.lower():
            return category
    return None


def select_products_by_category():
    """Displays products for a category chosen by number or name.
    Entering 'all', selecting the All option, or pressing Enter shows every product."""
    if not categories:
        print("No categories available.")
        return []
    print("\nAvailable categories: ")
    for i, category in enumerate(categories):
        print(f"{i + 1}. {category.get_category_name()}")
    all_option = len(categories) + 1
    print(f"{all_option}. All Products")
    print("0. Back")
    prompt("Choose category number or name (or press Enter for all): ")
    user_input = read_line().strip()
    if not user_input or user_input.lower() == "all" or user_input == str(all_option):
        print("\n--- ALL PRODUCTS ---")
        print_product_table(products)
        return products
    if user_input == "0" or user_input.lower() == "back":
        return []
    selected_category = None
    try:
        choice = int(user_input)
        if 1 <= choice <= len(categories):
            selected_category = categories[choice - 1]
    except ValueError:
        pass
    if selected_category is None:
        selected_category = find_category_by_name(user_input)
    if selected_category is None:
        print("Category not found.")
        return []
    print(f"\n--- {selected_category.get_category_name().upper()} PRODUCTS ---")
    print_product_table(selected_category.get_products())
    return selected_category.get_products()


def view_products_by_category():
    """Used for the "Browse Products" menu option, which doesn't need the list back."""
    select_products_by_category()


def choose_product(action):
    """Lets the user browse (by category) and pick a product by ID.
    Returns the chosen product, or None if nothing valid was chosen."""
    if not products:
        print("No products available.")
        return None
    available_products = select_products_by_category()
    if not available_products:
        return None
    prompt(f"Enter product ID to {action} (or 0 to cancel): ")
    product_id = read_int()
    if product_id == 0:
        return None
    product = find_product_in_list(product_id, available_products)
    if product is None:
        print("Invalid product ID for this selection.")
    return product


def choose_product_in_cart(cart, action):
    """Shows a cart and lets the user pick one of its products by ID.
    Returns the chosen product, or None if the cart is empty or the ID is invalid."""
    if not cart.get_items():
        print("Cart is empty.")
        return None
    cart.display_cart()
    prompt(f"Enter product ID to {action} (or 0 to cancel): ")
    product_id = read_int()
    if product_id == 0:
        return None
    product = find_product_in_cart(product_id, cart)
    if product is None:
        print("That product isn't in your cart.")
    return product


def find_product_in_list(product_id, product_list):
    """Finds a product by ID, but only within a specific list."""
    for product in product_list:
        if product.get_product_id() == product_id:
            return product
    return None


def find_product_in_cart(product_id, cart):
    """Finds a product by ID, but only among items currently in the given cart."""
    for item in cart.get_items():
        if item.get_product().get_product_id() == product_id:
            return item.get_product()
    return None


def find_product_by_id(product_id):
    """Finds a product by its ID from the list of all products."""
    return find_product_in_list(product_id, products)


def main():
    initialize_demo_data()
    show_main_menu()


if __name__ == "__main__":
    main()
