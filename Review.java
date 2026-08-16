import java.time.LocalDate;

// Represents a review for a product, containing feedback from a buyer.
public class Review {
    private int reviewId;
    private Buyer buyer;
    private Product product;
    private int rating;
    private String comment;
    private LocalDate reviewDate;

    // Constructor
    public Review(int reviewId,
            Buyer buyer,
            Product product,
            int rating,
            String comment) {

        this.reviewId = reviewId;
        this.buyer = buyer;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDate.now();

    }

    // GETTERS
    public int getReviewId() {
        return reviewId;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    // SETTERS
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5)
            this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // DISPLAY

    @Override
    public String toString() {

        return "Review ID : " + reviewId +
                "\nBuyer      : " + buyer.getName() +
                "\nProduct    : " + product.getProductName() +
                "\nRating     : " + rating + "/5" +
                "\nComment    : " + comment +
                "\nDate       : " + reviewDate;

    }

}