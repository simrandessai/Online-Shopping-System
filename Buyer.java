import java.util.ArrayList;

/**
 * Represents a Buyer user in the system. They can manage their cart,
 * wishlist, place orders, and raise customer care tickets.
 */
public class Buyer extends User {

    // Relationships
    private Cart cart;
    private Wishlist wishlist;
    private ArrayList<Order> orderHistory;

    // Constructor
    public Buyer(int userId,
            String name,
            String email,
            String password,
            String phone,
            String address) {

        super(userId, name, email, password, phone, address, Role.BUYER);

        cart = new Cart(userId);
        wishlist = new Wishlist(userId);
        orderHistory = new ArrayList<>();
    }

    // Cart Methods

    public void addToCart(Product product, int quantity) {
        cart.addProduct(product, quantity);
        System.out.println(quantity + " x "
                + product.getProductName()
                + " added to cart.");
    }

    public void removeFromCart(Product product) {
        cart.removeProduct(product);
        System.out.println(product.getProductName()
                + " removed from cart.");
    }

    public void viewCart() {
        cart.displayCart();
    }

    public Cart getCart() {
        return cart;
    }

    // Wishlist Methods
    public void addToWishlist(Product product) {
        wishlist.addProduct(product);
        System.out.println(product.getProductName()
                + " added to wishlist.");
    }

    public void removeFromWishlist(Product product) {
        wishlist.removeProduct(product);
        System.out.println(product.getProductName()
                + " removed from wishlist.");
    }

    public void moveToWishlist(Product product) {
        boolean inCart = false;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                inCart = true;
                break;
            }
        }
        if (!inCart) {
            System.out.println(product.getProductName() + " is not in your cart.");
            return;
        }
        cart.removeProduct(product);
        wishlist.addProduct(product);
        System.out.println(product.getProductName() + " moved from cart to wishlist.");
    }

    public void viewWishlist() {
        wishlist.displayWishlist();
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    // Order Methods
    // Places the order directly from the cart.
    public Order placeOrder(int orderId) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return null;
        }
        Order order = new Order(orderId, this);
        for (CartItem item : cart.getItems()) {
            order.addItem(
                    item.getProduct(),
                    item.getQuantity());
        }
        System.out.println("Order placed successfully.");
        return order;
    }

    // Starts a direct order without using the cart.
    public Order startDirectOrder(int orderId) {
        return new Order(orderId, this);
    }

    public void addOrderToHistory(Order order) {
        if (order != null) {
            orderHistory.add(order);
        }
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // To check if the buyer has purchased a specific product.
    public boolean hasPurchased(Product product) {
        for (Order order : orderHistory) {
            if (order.getOrderStatus() == OrderStatus.CANCELLED) {
                continue;
            }
            for (OrderItem item : order.getItems()) {
                if (item.getProduct().getProductId() == product.getProductId()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves a list of all products the buyer has purchased, excluding cancelled
     * orders and ensuring no duplicates.
     */
    public ArrayList<Product> getPurchasedProducts() {
        ArrayList<Product> purchased = new ArrayList<>();
        for (Order order : orderHistory) {
            if (order.getOrderStatus() == OrderStatus.CANCELLED) {
                continue;
            }
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                boolean alreadyAdded = false;
                for (Product existing : purchased) {
                    if (existing.getProductId() == product.getProductId()) {
                        alreadyAdded = true;
                        break;
                    }
                }
                if (!alreadyAdded) {
                    purchased.add(product);
                }
            }
        }
        return purchased;
    }

    // Raises a customer care ticket with the specified issue.
    public CustomerCare raiseTicket(String issue) {
        CustomerCare ticket = new CustomerCare(this, issue);
        System.out.println("Customer Care Ticket Created.");
        return ticket;
    }

    // Gives a review for a purchased product.
    public void giveReview(Product product,
            int rating,
            String comment) {
        if (!hasPurchased(product)) {
            System.out.println("You can only review products you have purchased.");
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("Rating should be between 1 and 5.");
            return;
        }
        Review review = new Review(
                product.getReviews().size() + 1,
                this,
                product,
                rating,
                comment);
        product.addReview(review);
        System.out.println("Review submitted successfully.");
    }

    @Override
    public String toString() {

        return "\n BUYER " +
                "\n" + super.toString();

    }

}