import java.util.ArrayList;

/**
 * Represents a buyer's shopping cart, holding items before they're
 * checked out into an order.
 */
public class Cart {
    private int cartId;
    private ArrayList<CartItem> items;

    // Constructor
    public Cart(int cartId) {
        this.cartId = cartId;
        items = new ArrayList<>();
    }

    // Add product to cart. If the product already exists, increase its quantity.
    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // To remove a product from the cart based on its product ID.
    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().getProductId() == product.getProductId());
    }

    // To update the quantity of a specific product in the cart.
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items)
            total += item.getTotalPrice();
        return total;
    }

    /**
     * Function to display the contents of the cart, including each item's details
     * and the grand total.
     */
    public void displayCart() {
        System.out.println("\n CART ");
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (CartItem item : items)
                System.out.println(item);

            System.out.println("-------------------------------------------");
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