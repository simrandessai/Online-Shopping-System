
/**
 * Represents a shopping cart, holding items before they're checked out into
 * an order. Buyers have their own cart; visitors who haven't signed in use a
 * temporary guest cart that is merged into their cart when they sign in.
 */
import java.util.ArrayList;

public class Cart {
    private int cartId;
    private ArrayList<CartItem> items;

    // Constructor
    public Cart(int cartId) {
        this.cartId = cartId;
        items = new ArrayList<>();
    }

    // Add product to cart. If the product already exists, increase its quantity.
    public boolean addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            return false;
        }
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                if (item.getQuantity() + quantity > product.getStock()) {
                    System.out.println("Cannot add more than the available stock.");
                    return false;
                }
                item.setQuantity(item.getQuantity() + quantity);
                return true;
            }
        }
        if (quantity > product.getStock()) {
            System.out.println("Cannot add more than the available stock.");
            return false;
        }
        items.add(new CartItem(product, quantity));
        return true;
    }

    // Removes a product from the cart based on its product ID.
    // Returns true if something was actually removed.
    public boolean removeProduct(Product product) {
        if (product == null) {
            return false;
        }
        return items.removeIf(item -> item.getProduct().getProductId() == product.getProductId());
    }

    // Calculates the total price of everything in the cart.
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items)
            total += item.getTotalPrice();
        return total;
    }

    // Function to display the contents of the cart, including each item's details
    // and the grand total.
    public void displayCart() {
        System.out.println("\n CART ");
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (CartItem item : items)
                System.out.println(item);
            System.out.println("Grand Total : Rs " + calculateTotal());
        }
    }

    // Clears all items from the cart.
    public void clearCart() {
        items.clear();
    }

    // Getter for cart items.
    public ArrayList<CartItem> getItems() {
        return items;
    }
}