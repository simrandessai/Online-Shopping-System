#pragma once
// A review for a product, containing feedback from a buyer.
#include <string>
#include "Fwd.h"

class Review {
private:
    int reviewId;
    BuyerPtr buyer;
    ProductPtr product;
    int rating;
    std::string comment;
    std::string reviewDate;

public:
    Review(int reviewId, BuyerPtr buyer, ProductPtr product, int rating,
           const std::string& comment);

    int getReviewId() const { return reviewId; }
    BuyerPtr getBuyer() const { return buyer; }
    ProductPtr getProduct() const { return product; }
    int getRating() const { return rating; }
    const std::string& getComment() const { return comment; }
    const std::string& getReviewDate() const { return reviewDate; }

    void setRating(int r) { if (r >= 1 && r <= 5) rating = r; }
    void setComment(const std::string& c) { comment = c; }

    std::string toString() const;
};
