"""A Seller user in the system. They can manage their products."""
from role import Role
from user import User


class Seller(User):

    def __init__(self, user_id, name, email, password, phone, address):
        super().__init__(user_id, name, email, password, phone, address, Role.SELLER)
        self.products = []

    # Add Product
    def add_product(self, product):
        self.products.append(product)

    # Delete Product
    def remove_product(self, product):
        if product in self.products:
            self.products.remove(product)

    # View Products
    def view_products(self):
        print("\nSeller Products")
        if not self.products:
            print("No products available.")
            return
        for p in self.products:
            print(p)

    def get_products(self):
        return self.products
