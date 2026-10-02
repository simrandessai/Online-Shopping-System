"""
Represents a shopping cart, holding items before they're checked out into
an order. Buyers have their own cart; visitors who haven't signed in use a
temporary guest cart that is merged into their cart when they sign in.
"""
from cart_item import CartItem


class Cart:

    def __init__(self, cart_id):
        self.cart_id = cart_id
        self.items = []

    # Add product to cart. If the product already exists, increase its quantity.
    def add_product(self, product, quantity):
        if product is None or quantity <= 0:
            return False
        for item in self.items:
            if item.get_product().get_product_id() == product.get_product_id():
                if item.get_quantity() + quantity > product.get_stock():
                    print("Cannot add more than the available stock.")
                    return False
                item.set_quantity(item.get_quantity() + quantity)
                return True
        if quantity > product.get_stock():
            print("Cannot add more than the available stock.")
            return False
        self.items.append(CartItem(product, quantity))
        return True

    # Removes a product from the cart based on its product ID.
    # Returns True if something was actually removed.
    def remove_product(self, product):
        if product is None:
            return False
        before = len(self.items)
        self.items = [item for item in self.items
                      if item.get_product().get_product_id() != product.get_product_id()]
        return len(self.items) != before

    # Calculates the total price of everything in the cart.
    def calculate_total(self):
        total = 0.0
        for item in self.items:
            total += item.get_total_price()
        return total

    # Displays the contents of the cart, including each item's details and the grand total.
    def display_cart(self):
        print("\n CART ")
        if not self.items:
            print("Cart is empty.")
        else:
            for item in self.items:
                print(item)
            print(f"Grand Total : Rs {self.calculate_total()}")

    # Clears all items from the cart.
    def clear_cart(self):
        self.items.clear()

    # Getter for cart items.
    def get_items(self):
        return self.items
