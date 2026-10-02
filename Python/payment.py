"""
Represents a payment made for an order. It contains details such as the
payment ID, payment method, amount, and payment status.
"""
import random

from payment_method import PaymentMethod
from payment_status import PaymentStatus


class Payment:

    # Simulated chance that an online payment is declined (Cash on Delivery never fails).
    # Set to 0 to make every payment succeed.
    FAILURE_CHANCE = 0.10

    def __init__(self, payment_id, payment_method, amount):
        self.payment_id = payment_id
        self.payment_method = payment_method
        self.amount = amount
        self.payment_status = PaymentStatus.PENDING

    # Processes the payment. Returns True and marks it SUCCESSFUL if it went
    # through, otherwise marks it FAILED and returns False.
    def process_payment(self):
        approved = (self.payment_method == PaymentMethod.CASH_ON_DELIVERY
                    or random.random() >= Payment.FAILURE_CHANCE)

        if approved:
            self.payment_status = PaymentStatus.SUCCESSFUL
            print("\nPayment Successful")
            print(f"Amount : Rs {self.amount}")
            print(f"Method : {self.payment_method}")
            return True

        self.payment_status = PaymentStatus.FAILED
        print("\nPayment Failed")
        print(f"Amount : Rs {self.amount}")
        print(f"Method : {self.payment_method}")
        return False

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
