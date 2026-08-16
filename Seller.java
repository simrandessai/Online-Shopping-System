import java.util.ArrayList;

// Represents a Seller user in the system. 
// They can manage their products and raise customer care tickets.
public class Seller extends User {

    private ArrayList<Product> products;

    // Constructor
    public Seller(int userId, String name,
            String email,
            String password,
            String phone,
            String address) {

        super(userId, name, email, password, phone, address, Role.SELLER);

        products = new ArrayList<>();
    }

    // Add Product
    public void addProduct(Product product) {
        products.add(product);
    }

    // Delete Product
    public void removeProduct(Product product) {
        products.remove(product);
    }

    // View Products
    public void viewProducts() {
        System.out.println("\nSeller Products");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        for (Product p : products)
            System.out.println(p);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public CustomerCare raiseTicket(String issue) {
        CustomerCare ticket = new CustomerCare(this, issue);
        System.out.println("Seller Ticket Created.");
        return ticket;
    }

    // View all reviews on this seller's products
    public void viewProductReviews() {
        System.out.println("\n------ REVIEWS ON YOUR PRODUCTS ------");
        
        boolean hasReviews = false;
        
        for (Product product : products) {
            ArrayList<Review> reviews = product.getReviews();
            
            if (!reviews.isEmpty()) {
                hasReviews = true;
                System.out.println("\n*** " + product.getProductName() + " ***");
                System.out.println("Average Rating: " + String.format("%.1f", product.getAverageRating()) + "/5");
                System.out.println("Total Reviews: " + reviews.size());
                System.out.println("---");
                
                for (Review review : reviews) {
                    System.out.println("Buyer: " + review.getBuyer().getName());
                    System.out.println("Rating: " + review.getRating() + "/5");
                    System.out.println("Comment: " + review.getComment());
                    System.out.println("Date: " + review.getReviewDate());
                    System.out.println("---");
                }
            }
        }
        
        if (!hasReviews) {
            System.out.println("You don't have any reviews yet.");
        }
    }
}