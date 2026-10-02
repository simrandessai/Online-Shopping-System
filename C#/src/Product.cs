using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a product in the online shopping system.
    /// It contains details such as the product ID, name, description, price, stock quantity, category, seller, and reviews.
    /// </summary>
    public class Product
    {
        private int productId;
        private string productName;
        private string description;
        private double price;
        private int stock;

        // Relationships
        private Category category;
        private Seller seller;

        // One Product can have many Reviews
        private List<Review> reviews;

        // Constructor
        public Product(int productId, string productName, string description, double price, int stock, Category category, Seller seller)
        {
            this.productId = productId;
            this.productName = productName;
            this.description = description;
            this.price = price;
            this.stock = stock;
            this.category = category;
            this.seller = seller;
            this.reviews = new List<Review>();
        }

        // Stock Methods
        public void UpdateStock(int quantity)
        {
            if (stock + quantity < 0)
            {
                Console.WriteLine("Stock cannot go below zero.");
                return;
            }
            stock += quantity;
        }

        public bool ReduceStock(int quantity)
        {
            if (quantity <= 0)
            {
                Console.WriteLine("Quantity must be greater than zero.");
                return false;
            }
            if (stock < quantity)
            {
                Console.WriteLine("Insufficient Stock.");
                return false;
            }
            stock -= quantity;
            return true;
        }

        // Review Methods
        public void AddReview(Review review)
        {
            if (review != null)
            {
                reviews.Add(review);
            }
        }

        public void RemoveReview(Review review)
        {
            reviews.Remove(review);
        }

        public List<Review> GetReviews() { return reviews; }

        public void DisplayReviews()
        {
            Console.WriteLine("\nREVIEWS");
            if (reviews.Count == 0)
            {
                Console.WriteLine("No Reviews Available.");
                return;
            }

            foreach (Review review in reviews)
            {
                Console.WriteLine(review);
            }
        }

        public double GetAverageRating()
        {
            if (reviews.Count == 0)
            {
                return 0.0;
            }
            double total = 0;
            foreach (Review review in reviews)
            {
                total += review.GetRating();
            }
            return total / reviews.Count;
        }

        // Getters & Setters
        public int GetProductId() { return productId; }
        public void SetProductId(int productId) { this.productId = productId; }

        public string GetProductName() { return productName; }
        public void SetProductName(string productName) { this.productName = productName; }

        public string GetDescription() { return description; }
        public void SetDescription(string description) { this.description = description; }

        public double GetPrice() { return price; }
        public void SetPrice(double price)
        {
            if (price < 0)
            {
                Console.WriteLine("Price cannot be negative.");
                return;
            }
            this.price = price;
        }

        public int GetStock() { return stock; }
        public void SetStock(int stock)
        {
            if (stock < 0)
            {
                Console.WriteLine("Stock cannot be negative.");
                return;
            }
            this.stock = stock;
        }

        public Category GetCategory() { return category; }
        public void SetCategory(Category category) { this.category = category; }

        public Seller GetSeller() { return seller; }
        public void SetSeller(Seller seller) { this.seller = seller; }

        // C# Properties
        public int ProductId { get { return productId; } set { productId = value; } }
        public string ProductName { get { return productName; } set { productName = value; } }
        public string Description { get { return description; } set { description = value; } }
        public double Price { get { return price; } set { SetPrice(value); } }
        public int Stock { get { return stock; } set { SetStock(value); } }
        public Category Category { get { return category; } set { category = value; } }
        public Seller Seller { get { return seller; } set { seller = value; } }
        public List<Review> Reviews { get { return reviews; } }

        // Display Product Details
        public override string ToString()
        {
            return "\nPRODUCT" +
                   "\nProduct ID : " + productId +
                   "\nName       : " + productName +
                   "\nDescription: " + description +
                   "\nPrice      : Rs " + price +
                   "\nStock      : " + stock +
                   "\nCategory   : " + (category != null ? category.GetCategoryName() : "N/A") +
                   "\nSeller     : " + (seller != null ? seller.GetName() : "N/A") +
                   "\nRating     : " + string.Format("{0:0.0}", GetAverageRating()) + "/5";
        }
    }
}
