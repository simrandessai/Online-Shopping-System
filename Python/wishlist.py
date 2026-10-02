"""
A user's wishlist, containing a list of products they are interested in.
It provides methods to add/remove products and display the wishlist.
"""


class Wishlist:

    def __init__(self, wishlist_id):
        self.wishlist_id = wishlist_id
        self.products = []

    # Add product to wishlist. Returns False if it was None or already there.
    def add_product(self, product):
        if product is None or product in self.products:
            return False
        self.products.append(product)
        return True

    # Remove product from wishlist. Returns True if it was actually there.
    def remove_product(self, product):
        if product in self.products:
            self.products.remove(product)
            return True
        return False

    def display_wishlist(self):
        print("\n WISHLIST ")
        if not self.products:
            print("Wishlist is empty.")
            return
        for p in self.products:
            print(p.get_product_name())

    def get_products(self):
        return self.products
