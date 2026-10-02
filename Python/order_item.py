"""An item in an order, containing a product, quantity, and price."""


class OrderItem:

    def __init__(self, product, quantity):
        self.product = product
        self.quantity = quantity
        self.price = product.get_price()

    def get_product(self):
        return self.product

    def get_quantity(self):
        return self.quantity

    def get_price(self):
        return self.price

    def get_sub_total(self):
        return self.price * self.quantity

    def __str__(self):
        return (f"{self.product.get_product_name()}"
                f" | Qty : {self.quantity}"
                f" | Price : Rs {self.price}"
                f" | Total : Rs {self.get_sub_total()}")
