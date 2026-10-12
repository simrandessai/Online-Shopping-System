"""
Represents a payment made for an order. It contains details such as the
payment ID, payment method, amount, and payment status.
"""
from payment_status import PaymentStatus


class Payment:

    def __init__(self, payment_id, payment_method, amount):
        self.payment_id = payment_id
        self.payment_method = payment_method
        self.amount = amount
        self.payment_status = PaymentStatus.PENDING

    # Processes the payment. Returns True and marks it SUCCESSFUL.
    def process_payment(self):
        self.payment_status = PaymentStatus.SUCCESSFUL
        print("\nPayment Successful")
        print(f"Amount : Rs {self.amount}")
        print(f"Method : {self.payment_method}")
        return True

    def get_payment_id(self):
        return self.payment_id

    def get_payment_method(self):
        return self.payment_method

    def get_amount(self):
        return self.amount

    def get_payment_status(self):
        return self.payment_status

    def __str__(self):
        return (f"\nPayment ID : {self.payment_id}"
                f"\nMethod : {self.payment_method}"
                f"\nAmount : Rs {self.amount}"
                f"\nStatus : {self.payment_status}")
