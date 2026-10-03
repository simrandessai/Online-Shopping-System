#include "Review.h"
#include "Buyer.h"
#include "Utils.h"

Review::Review(int reviewId, BuyerPtr buyer, ProductPtr product, int rating,
               const std::string& comment)
    : reviewId(reviewId), buyer(std::move(buyer)), product(std::move(product)),
      rating(rating), comment(comment), reviewDate(util::today()) {}

std::string Review::toString() const {
    return "Review ID : " + std::to_string(reviewId) +
           "\nBuyer      : " + buyer->getName() +
           "\nRating     : " + std::to_string(rating) + "/5" +
           "\nComment    : " + comment +
           "\nDate       : " + reviewDate;
}
