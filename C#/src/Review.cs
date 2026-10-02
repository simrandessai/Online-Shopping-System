using System;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a review for a product, containing feedback from a buyer.
    /// </summary>
    public class Review
    {
        private int reviewId;
        private Buyer buyer;
        private Product product;
        private int rating;
        private string comment;
        private DateTime reviewDate;

        // Constructor
        public Review(int reviewId, Buyer buyer, Product product, int rating, string comment)
        {
            this.reviewId = reviewId;
            this.buyer = buyer;
            this.product = product;
            this.rating = rating;
            this.comment = comment;
            this.reviewDate = DateTime.Now;
        }

        // Getters
        public int GetReviewId() { return reviewId; }
        public Buyer GetBuyer() { return buyer; }
        public Product GetProduct() { return product; }
        public int GetRating() { return rating; }
        public string GetComment() { return comment; }
        public DateTime GetReviewDate() { return reviewDate; }

        // Setters
        public void SetRating(int rating)
        {
            if (rating >= 1 && rating <= 5)
            {
                this.rating = rating;
            }
        }

        public void SetComment(string comment)
        {
            this.comment = comment;
        }

        // C# Properties
        public int ReviewId { get { return reviewId; } }
        public Buyer Buyer { get { return buyer; } }
        public Product Product { get { return product; } }
        public int Rating { get { return rating; } set { SetRating(value); } }
        public string Comment { get { return comment; } set { comment = value; } }
        public DateTime ReviewDate { get { return reviewDate; } }

        // Display
        public override string ToString()
        {
            return "Review ID : " + reviewId +
                   "\nBuyer      : " + (buyer != null ? buyer.GetName() : "N/A") +
                   "\nRating     : " + rating + "/5" +
                   "\nComment    : " + comment +
                   "\nDate       : " + reviewDate.ToString("yyyy-MM-dd");
        }
    }
}
