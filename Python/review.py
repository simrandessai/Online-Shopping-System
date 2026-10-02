"""Represents a review for a product, containing feedback from a buyer."""
from datetime import date


class Review:

    def __init__(self, review_id, buyer, product, rating, comment):
        self.review_id = review_id
        self.buyer = buyer
        self.product = product
        self.rating = rating
        self.comment = comment
        self.review_date = date.today()

    # GETTERS
    def get_review_id(self):
        return self.review_id

    def get_buyer(self):
        return self.buyer

    def get_product(self):
        return self.product

    def get_rating(self):
        return self.rating

    def get_comment(self):
        return self.comment

    def get_review_date(self):
        return self.review_date

    # SETTERS
    def set_rating(self, rating):
        if 1 <= rating <= 5:
            self.rating = rating

    def set_comment(self, comment):
        self.comment = comment

    # DISPLAY
    def __str__(self):
        return (f"Review ID : {self.review_id}"
                f"\nBuyer      : {self.buyer.get_name()}"
                f"\nRating     : {self.rating}/5"
                f"\nComment    : {self.comment}"
                f"\nDate       : {self.review_date}")
