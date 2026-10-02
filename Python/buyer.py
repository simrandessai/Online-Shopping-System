"""
A Buyer user in the system. They can manage their cart, wishlist,
and place orders.
"""
from cart import Cart
from customer_care import CustomerCare
from order import Order
from order_status import OrderStatus
from review import Review
from role import Role
from user import User
from wishlist import Wishlist


class Buyer(User):

    def __init__(self, user_id, name, email, password, phone, address):
        super().__init__(user_id, name, email, password, phone, address, Role.BUYER)

        # Relationships
        self.cart = Cart(user_id)
        self.wishlist = Wishlist(user_id)
        self.order_history = []
        self.tickets = []

    # Cart Methods
    def add_to_cart(self, product, quantity):
        if self.cart.add_product(product, quantity):
            print(f"{quantity} x {product.get_product_name()} added to cart.")

    def remove_from_cart(self, product):
        if self.cart.remove_product(product):
            print(f"{product.get_product_name()} removed from cart.")
        else:
            print("That product isn't in your cart.")

    def view_cart(self):
        self.cart.display_cart()

    def get_cart(self):
        return self.cart

    # Wishlist Methods
    def add_to_wishlist(self, product):
        if self.wishlist.add_product(product):
            print(f"{product.get_product_name()} added to wishlist.")
        else:
            print(f"{product.get_product_name()} is already in your wishlist.")

    def remove_from_wishlist(self, product):
        if self.wishlist.remove_product(product):
            print(f"{product.get_product_name()} removed from wishlist.")
        else:
            print("That product isn't in your wishlist.")

    def move_to_wishlist(self, product):
        if not self.cart.remove_product(product):
            print(f"{product.get_product_name()} is not in your cart.")
            return
        self.wishlist.add_product(product)
        print(f"{product.get_product_name()} moved from cart to wishlist.")

    def view_wishlist(self):
        self.wishlist.display_wishlist()

    def get_wishlist(self):
        return self.wishlist

    def remove_product_references(self, product):
        if product is None:
            return
        self.cart.remove_product(product)
        self.wishlist.remove_product(product)

    # Order Methods

    # Places the order directly from the cart.
    def place_order(self):
        if not self.is_signed_in():
            print("Please log in or register before placing an order.")
            return None
        if not self.cart.get_items():
            print("Cart is empty.")
            return None
        # Check every item first so a half-built order is never created.
        for item in self.cart.get_items():
            if item.get_quantity() <= 0 or item.get_quantity() > item.get_product().get_stock():
                print(f"Insufficient stock for {item.get_product().get_product_name()}. "
                      "The order was not created.")
                return None
        order = Order(self)
        for item in self.cart.get_items():
            order.add_item(item.get_product(), item.get_quantity())
        print("Order created (status: PENDING). Proceed to payment.")
        return order

    # Starts a direct order without using the cart.
    def start_direct_order(self):
        if not self.is_signed_in():
            print("Please log in or register before placing an order.")
            return None
        return Order(self)

    def add_order_to_history(self, order):
        if order is not None:
            self.order_history.append(order)

    def get_order_history(self):
        return self.order_history

    # Displays every order the buyer has placed.
    def view_order_history(self):
        print("\n ORDER HISTORY ")
        if not self.order_history:
            print("You haven't placed any orders yet.")
            return
        for order in self.order_history:
            order.display_order()

    # To check if the buyer has purchased a specific product.
    def has_purchased(self, product):
        for order in self.order_history:
            if order.get_order_status() == OrderStatus.CANCELLED:
                continue
            for item in order.get_items():
                if item.get_product().get_product_id() == product.get_product_id():
                    return True
        return False

    # Retrieves a list of all products the buyer has purchased, excluding
    # cancelled orders and ensuring no duplicates.
    def get_purchased_products(self):
        purchased = []
        for order in self.order_history:
            if order.get_order_status() == OrderStatus.CANCELLED:
                continue
            for item in order.get_items():
                product = item.get_product()
                already_added = any(existing.get_product_id() == product.get_product_id()
                                    for existing in purchased)
                if not already_added:
                    purchased.append(product)
        return purchased

    # Gives a review for a purchased product.
    def give_review(self, product, rating, comment):
        if not self.has_purchased(product):
            print("You can only review products you have purchased.")
            return
        if rating < 1 or rating > 5:
            print("Rating should be between 1 and 5.")
            return
        for existing_review in product.get_reviews():
            if existing_review.get_buyer() is self:
                print("You have already reviewed this product.")
                return
        if comment is None or not comment.strip():
            print("Review comment cannot be empty.")
            return
        review = Review(len(product.get_reviews()) + 1, self, product,
                        rating, comment.strip())
        product.add_review(review)
        print("Review submitted successfully.")

    # Customer Care Methods

    # Raises a new support ticket. The caller passes it on to an admin.
    def raise_ticket(self, issue):
        if not self.is_signed_in():
            print("Please log in before raising a support ticket.")
            return None
        if issue is None or not issue.strip():
            print("Issue description cannot be empty.")
            return None
        ticket = CustomerCare(issue.strip(), self)
        self.tickets.append(ticket)
        print(f"Support ticket raised. Your Ticket ID is {ticket.get_ticket_id()}.")
        return ticket

    # Shows the buyer's own tickets along with any admin replies.
    def view_tickets(self):
        print("\n MY SUPPORT TICKETS ")
        if not self.tickets:
            print("You haven't raised any support tickets.")
            return
        for ticket in self.tickets:
            print(ticket)

    def get_tickets(self):
        return self.tickets

    def __str__(self):
        return "\n BUYER \n" + super().__str__()
