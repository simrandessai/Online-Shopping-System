
/**
 * The Wishlist class represents a user's wishlist, containing a list of products they are interested in.
 * It provides methods to add/remove products and display the wishlist.
 */
import java.util.ArrayList;

public class Wishlist {

    private int wishlistId;
    private ArrayList<Product> products;

    // Constructor
    public Wishlist(int wishlistId) {
        this.wishlistId = wishlistId;
        products = new ArrayList<>();
    }

    // Add product to wishlist. Returns false if it was null or already there.
    public boolean addProduct(Product product) {
        if (product == null || products.contains(product)) {
            return false;
        }
        products.add(product);
        return true;
    }

    // Remove product from wishlist. Returns true if it was actually there.
    public boolean removeProduct(Product product) {
        return products.remove(product);
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