
/**
 * This is a class that represents the products in an online shopping system.
 * It contains details such as the product ID, name, description, price, stock quantity, category, seller, and reviews.
 * The class provides methods to manage stock, add and remove reviews, calculate average ratings, and display product details.
 */
import java.util.ArrayList;

public class Product {
    private int productId;
    private String productName;
    private String description;
    private double price;
    private int stock;

    // Relationships
    private Category category;
    private Seller seller;

    // One Product can have many Reviews
    private ArrayList<Review> reviews;

    // Constructor
    public Product(int productId,
            String productName,
            String description,
            double price,
            int stock,
            Category category,
            Seller seller) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.seller = seller;
        this.reviews = new ArrayList<>();
    }

    // Stock Methods

    // Adds (or, with a negative number, removes) stock. Stock can never go below
    // zero.
    public void updateStock(int quantity) {
        if (stock + quantity < 0) {
            System.out.println("Stock cannot go below zero.");
            return;
        }
        stock += quantity;
    }

    // Reduces stock for a sale. Returns true if the stock was reduced,
    // so the caller (e.g. Order) knows whether the sale can go ahead.
    public boolean reduceStock(int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return false;
        }
        if (stock < quantity) {
            System.out.println("Insufficient Stock.");
            return false;
        }
        stock -= quantity;
        return true;
    }

    // Review Methods
    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
        }
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void displayReviews() {
        System.out.println("\nREVIEWS");
        if (reviews.isEmpty()) {
            System.out.println("No Reviews Available.");
            return;
        }

        for (Review review : reviews) {
            System.out.println(review);
        }
    }

    // Calculate Average Rating
    public double getAverageRating() {
        if (reviews.isEmpty())
            return 0;
        double total = 0;
        for (Review review : reviews)
            total += review.getRating();
        return total / reviews.size();
    }

    // GETTERS & SETTERS
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            System.out.println("Stock cannot be negative.");
            return;
        }
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    // Display Product Details
    @Override
    public String toString() {

        return "\nPRODUCT" +
                "\nProduct ID : " + productId +
                "\nName       : " + productName +
                "\nDescription: " + description +
                "\nPrice      : Rs " + price +
                "\nStock      : " + stock +
                "\nCategory   : " + (category != null ? category.getCategoryName() : "N/A") +
                "\nSeller     : " + (seller != null ? seller.getName() : "N/A") +
                "\nRating     : " +
                String.format("%.1f", getAverageRating()) + "/5";
    }

}