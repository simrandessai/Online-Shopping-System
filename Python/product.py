"""
Represents the products in an online shopping system.
It contains details such as the product ID, name, description, price, stock
quantity, category, seller, and reviews. It provides methods to manage stock,
add and remove reviews, calculate average ratings, and display product details.
"""


class Product:

    def __init__(self, product_id, product_name, description, price, stock,
                 category, seller):
        self.product_id = product_id
        self.product_name = product_name
        self.description = description
        self.price = float(price)
        self.stock = stock

        # Relationships
        self.category = category
        self.seller = seller

        # One Product can have many Reviews
        self.reviews = []

    # Stock Methods

    # Adds (or, with a negative number, removes) stock. Stock can never go below zero.
    def update_stock(self, quantity):
        if self.stock + quantity < 0:
            print("Stock cannot go below zero.")
            return
        self.stock += quantity

    # Reduces stock for a sale. Returns True if the stock was reduced,
    # so the caller (e.g. Order) knows whether the sale can go ahead.
    def reduce_stock(self, quantity):
        if quantity <= 0:
            print("Quantity must be greater than zero.")
            return False
        if self.stock < quantity:
            print("Insufficient Stock.")
            return False
        self.stock -= quantity
        return True

    # Review Methods
    def add_review(self, review):
        if review is not None:
            self.reviews.append(review)

    def remove_review(self, review):
        if review in self.reviews:
            self.reviews.remove(review)

    def get_reviews(self):
        return self.reviews

    def display_reviews(self):
        print("\nREVIEWS")
        if not self.reviews:
            print("No Reviews Available.")
            return
        for review in self.reviews:
            print(review)

    # Calculate Average Rating
    def get_average_rating(self):
        if not self.reviews:
            return 0
        total = 0
        for review in self.reviews:
            total += review.get_rating()
        return total / len(self.reviews)

    # GETTERS & SETTERS
    def get_product_id(self):
        return self.product_id

    def set_product_id(self, product_id):
        self.product_id = product_id

    def get_product_name(self):
        return self.product_name

    def set_product_name(self, product_name):
        self.product_name = product_name

    def get_description(self):
        return self.description

    def set_description(self, description):
        self.description = description

    def get_price(self):
        return self.price

    def set_price(self, price):
        if price < 0:
            print("Price cannot be negative.")
            return
        self.price = float(price)

    def get_stock(self):
        return self.stock

    def set_stock(self, stock):
        if stock < 0:
            print("Stock cannot be negative.")
            return
        self.stock = stock

    def get_category(self):
        return self.category

    def set_category(self, category):
        self.category = category

    def get_seller(self):
        return self.seller

    def set_seller(self, seller):
        self.seller = seller

    # Display Product Details
    def __str__(self):
        return ("\nPRODUCT"
                f"\nProduct ID : {self.product_id}"
                f"\nName       : {self.product_name}"
                f"\nDescription: {self.description}"
                f"\nPrice      : Rs {self.price}"
                f"\nStock      : {self.stock}"
                f"\nCategory   : {self.category.get_category_name() if self.category is not None else 'N/A'}"
                f"\nSeller     : {self.seller.get_name() if self.seller is not None else 'N/A'}"
                f"\nRating     : {self.get_average_rating():.1f}/5")
