import java.util.ArrayList;

/**
 * Represents a Buyer user in the system. They can manage their cart,
 * wishlist, and place orders.
 */
public class Buyer extends User {

    // Relationships
    private Cart cart;
    private Wishlist wishlist;
    private ArrayList<Order> orderHistory;
    private ArrayList<CustomerCare> tickets;

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
        tickets = new ArrayList<>();
    }

    // Cart Methods

    public void addToCart(Product product, int quantity) {
        if (cart.addProduct(product, quantity)) {
            System.out.println(quantity + " x "
                    + product.getProductName()
                    + " added to cart.");
        }
    }

    public void removeFromCart(Product product) {
        if (cart.removeProduct(product)) {
            System.out.println(product.getProductName()
                    + " removed from cart.");
        } else {
            System.out.println("That product isn't in your cart.");
        }
    }

    public void viewCart() {
        cart.displayCart();
    }

    public Cart getCart() {
        return cart;
    }

    // Wishlist Methods
    public void addToWishlist(Product product) {
        if (wishlist.addProduct(product)) {
            System.out.println(product.getProductName()
                    + " added to wishlist.");
        } else {
            System.out.println(product.getProductName()
                    + " is already in your wishlist.");
        }
    }

    public void removeFromWishlist(Product product) {
        if (wishlist.removeProduct(product)) {
            System.out.println(product.getProductName()
                    + " removed from wishlist.");
        } else {
            System.out.println("That product isn't in your wishlist.");
        }
    }

    public void moveToWishlist(Product product) {
        if (!cart.removeProduct(product)) {
            System.out.println(product.getProductName() + " is not in your cart.");
            return;
        }
        wishlist.addProduct(product);
        System.out.println(product.getProductName() + " moved from cart to wishlist.");
    }

    public void viewWishlist() {
        wishlist.displayWishlist();
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void removeProductReferences(Product product) {
        if (product == null) {
            return;
        }
        cart.removeProduct(product);
        wishlist.removeProduct(product);
    }

    // Order Methods
    // Places the order directly from the cart.
    public Order placeOrder() {
        if (!isSignedIn()) {
            System.out.println("Please log in or register before placing an order.");
            return null;
        }
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return null;
        }
        // Check every item first so a half-built order is never created.
        for (CartItem item : cart.getItems()) {
            if (item.getQuantity() <= 0 || item.getQuantity() > item.getProduct().getStock()) {
                System.out.println("Insufficient stock for " + item.getProduct().getProductName()
                        + ". The order was not created.");
                return null;
            }
        }
        Order order = new Order(this);
        for (CartItem item : cart.getItems()) {
            order.addItem(
                    item.getProduct(),
                    item.getQuantity());
        }
        System.out.println("Order created (status: PENDING). Proceed to payment.");
        return order;
    }

    // Starts a direct order without using the cart.
    public Order startDirectOrder() {
        if (!isSignedIn()) {
            System.out.println("Please log in or register before placing an order.");
            return null;
        }
        return new Order(this);
    }

    public void addOrderToHistory(Order order) {
        if (order != null) {
            orderHistory.add(order);
        }
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // Displays every order the buyer has placed.
    public void viewOrderHistory() {
        System.out.println("\n ORDER HISTORY ");
        if (orderHistory.isEmpty()) {
            System.out.println("You haven't placed any orders yet.");
            return;
        }
        for (Order order : orderHistory) {
            order.displayOrder();
        }
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
        for (Review existingReview : product.getReviews()) {
            if (existingReview.getBuyer() == this) {
                System.out.println("You have already reviewed this product.");
                return;
            }
        }
        if (comment == null || comment.trim().isEmpty()) {
            System.out.println("Review comment cannot be empty.");
            return;
        }
        Review review = new Review(
                product.getReviews().size() + 1,
                this,
                product,
                rating,
                comment.trim());
        product.addReview(review);
        System.out.println("Review submitted successfully.");
    }

    // Customer Care Methods

    // Raises a new support ticket. The caller passes it on to an admin.
    public CustomerCare raiseTicket(String issue) {
        if (!isSignedIn()) {
            System.out.println("Please log in before raising a support ticket.");
            return null;
        }
        if (issue == null || issue.trim().isEmpty()) {
            System.out.println("Issue description cannot be empty.");
            return null;
        }
        CustomerCare ticket = new CustomerCare(issue.trim(), this);
        tickets.add(ticket);
        System.out.println("Support ticket raised. Your Ticket ID is "
                + ticket.getTicketId() + ".");
        return ticket;
    }

    // Shows the buyer's own tickets along with any admin replies.
    public void viewTickets() {
        System.out.println("\n MY SUPPORT TICKETS ");
        if (tickets.isEmpty()) {
            System.out.println("You haven't raised any support tickets.");
            return;
        }
        for (CustomerCare ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public ArrayList<CustomerCare> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {

        return "\n BUYER " +
                "\n" + super.toString();

    }

}