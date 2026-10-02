"""Places an order for a buyer, containing multiple items, payment details, and order status."""
from datetime import date

from order_item import OrderItem
from order_status import OrderStatus
from payment import Payment


class Order:

    # Static ID Generator
    _next_order_id = 1

    # Constructor: every new order gets a unique ID and starts as PENDING.
    def __init__(self, buyer):
        self.order_id = Order._next_order_id
        Order._next_order_id += 1
        self.buyer = buyer
        self.order_date = date.today()
        self.items = []
        self.payment = None
        self.order_status = OrderStatus.PENDING

    # Add Item to Order.
    # Stock is only checked here; it is deducted once payment succeeds.
    def add_item(self, product, quantity):
        if product is None or quantity <= 0:
            print("Product and quantity must be valid.")
            return
        already_in_order = 0
        for item in self.items:
            if item.get_product().get_product_id() == product.get_product_id():
                already_in_order += item.get_quantity()
        if product.get_stock() < already_in_order + quantity:
            print(f"Insufficient stock for {product.get_product_name()}")
            return
        self.items.append(OrderItem(product, quantity))

    # Remove Item from Order
    def remove_item(self, product):
        self.items = [item for item in self.items
                      if item.get_product().get_product_id() != product.get_product_id()]

    # Calculate Total Amount of the Order
    def calculate_total(self):
        total = 0.0
        for item in self.items:
            total += item.get_sub_total()
        return total

    # Payment Method.
    # On success: stock is reduced and the order becomes CONFIRMED.
    # On failure: the order stays PENDING and no stock is deducted.
    def make_payment(self, method):
        if self.order_status == OrderStatus.CONFIRMED:
            print("This order has already been paid.")
            return False
        if self.order_status == OrderStatus.CANCELLED:
            print("This order has been cancelled.")
            return False
        if not self.items or method is None:
            print("Cannot process payment for an empty order.")
            return False
        self.payment = Payment(self.order_id, method, self.calculate_total())
        paid = self.payment.process_payment()
        if paid:
            for item in self.items:
                item.get_product().reduce_stock(item.get_quantity())
            self.order_status = OrderStatus.CONFIRMED
        return paid

    # Update Order Status
    def update_order_status(self, status):
        self.order_status = status

    # Display Order Details
    def display_order(self):
        print("\n ORDER ")
        print(f"Order ID   : {self.order_id}")
        print(f"Buyer      : {self.buyer.get_name()}")
        print(f"Order Date : {self.order_date}")
        print()
        for item in self.items:
            print(item)
        print(f"Total Amount : Rs {self.calculate_total()}")
        print(f"Status       : {self.order_status}")
        if self.payment is not None:
            print(self.payment)

    # GETTERS
    def get_order_id(self):
        return self.order_id

    def get_buyer(self):
        return self.buyer

    def get_order_date(self):
        return self.order_date

    def get_items(self):
        return self.items

    def get_payment(self):
        return self.payment

    def get_order_status(self):
        return self.order_status
