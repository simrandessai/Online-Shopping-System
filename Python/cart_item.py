"""An individual item in the shopping cart, linking a product with its quantity."""


class CartItem:

    def __init__(self, product, quantity):
        self.product = product
        self.quantity = quantity

    def get_product(self):
        return self.product

    def get_quantity(self):
        return self.quantity

    def set_quantity(self, quantity):
        self.quantity = quantity

    def get_total_price(self):
        return self.product.get_price() * self.quantity

    def __str__(self):
        return (f"ID: {self.product.get_product_id()}"
                f" | {self.product.get_product_name()}"
                f" | Qty : {self.quantity}"
                f" | Total : Rs {self.get_total_price()}")
