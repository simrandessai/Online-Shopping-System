"""Categorises products into different groups for organization and filtering."""


class Category:

    def __init__(self, category_id, category_name):
        self.category_id = category_id
        self.category_name = category_name
        # One Category contains many Products
        self.products = []

    # Add one or more Products
    def add_product(self, *products_to_add):
        for product in products_to_add:
            self.products.append(product)

    # Remove Product
    def remove_product(self, product):
        if product in self.products:
            self.products.remove(product)

    # To Display Products
    def display_products(self):
        print(f"\nCategory : {self.category_name}")
        for p in self.products:
            print(p)

    # Getters & Setters
    def get_category_id(self):
        return self.category_id

    def set_category_id(self, category_id):
        self.category_id = category_id

    def get_category_name(self):
        return self.category_name

    def set_category_name(self, category_name):
        self.category_name = category_name

    def get_products(self):
        return self.products

    def __str__(self):
        return f"Category ID : {self.category_id}\nCategory : {self.category_name}"
