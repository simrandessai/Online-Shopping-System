import java.util.ArrayList;

// This  is a class that represents the products in an online shopping system. 
// It contains details such as the product ID, name, description, price, stock quantity, category, seller, and reviews. 
// The class provides methods to manage stock, add and remove reviews, calculate average ratings, and display product details.
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
        reviews = new ArrayList<>();
    }

    // Stock Methods
    public void updateStock(int quantity) {
        stock += quantity;
    }

    public void reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        } else {
            System.out.println("Insufficient Stock.");
        }
    }

    // Review Methods
    public void addReview(Review review) {
        reviews.add(review);
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
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
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

        return "\n------ PRODUCT ------" +
                "\nProduct ID : " + productId +
                "\nName       : " + productName +
                "\nDescription: " + description +
                "\nPrice      : Rs " + price +
                "\nStock      : " + stock +
                "\nCategory   : " + category.getCategoryName() +
                "\nSeller     : " + seller.getName() +
                "\nRating     : " +
                String.format("%.1f", getAverageRating()) +
                "/5";

    }

}