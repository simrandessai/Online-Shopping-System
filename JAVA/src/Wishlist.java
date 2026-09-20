import java.util.ArrayList;

// Wishlist class represents a user's wishlist, containing a list of products they are interested in. 
// It provides methods to add/remove products and display the wishlist.

public class Wishlist {

    private int wishlistId;
    private ArrayList<Product> products;

    // Constructor
    public Wishlist(int wishlistId) {
        this.wishlistId = wishlistId;
        products = new ArrayList<>();
    }

    // Add product to wishlist
    public void addProduct(Product product) {
        if (!products.contains(product))
            products.add(product);
    }

    // Remove product from wishlist
    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void displayWishlist() {
        System.out.println("\n WISHLIST ");
        if (products.isEmpty()) {
            System.out.println("Wishlist is empty.");
            return;
        }
        for (Product p : products)
            System.out.println(p.getProductName());
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}