import java.time.LocalDate;
import java.util.ArrayList;

// Places an order for a buyer, containing multiple items, payment details, and order status.
public class Order {

    // Static ID Generator
    private static int nextOrderId = 1;

    private int orderId;
    private Buyer buyer;
    private LocalDate orderDate;
    private ArrayList<OrderItem> items;
    private Payment payment;
    private OrderStatus orderStatus;

    // Constructor
    public Order(Buyer buyer) {

        this.orderId = nextOrderId++;
        this.buyer = buyer;
        this.orderDate = LocalDate.now();
        this.items = new ArrayList<>();
        this.orderStatus = OrderStatus.PENDING;

    }

    // Optional Constructor (Manual ID)
    public Order(int orderId, Buyer buyer) {

        this.orderId = orderId;
        this.buyer = buyer;
        this.orderDate = LocalDate.now();
        this.items = new ArrayList<>();
        this.orderStatus = OrderStatus.PENDING;

    }

    // Add Item to Order
    public void addItem(Product product, int quantity) {
        if (product.getStock() < quantity) {
            System.out.println("Insufficient stock for "
                    + product.getProductName());
            return;
        }
        items.add(new OrderItem(product, quantity));
        product.reduceStock(quantity);
    }

    // Remove Item from Order
    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().getProductId() == product.getProductId());
    }

    // Calculate Total Amount of the Order
    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }

    // Payment Method.
    public void makePayment(PaymentMethod method) {
        payment = new Payment(
                orderId,
                method,
                calculateTotal());
        payment.processPayment();
        orderStatus = OrderStatus.CONFIRMED;
    }

    // Update Order Status
    public void updateOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    // Display Order Details
    public void displayOrder() {
        System.out.println("\n ORDER ");
        System.out.println("Order ID   : " + orderId);
        System.out.println("Buyer      : " + buyer.getName());
        System.out.println("Order Date : " + orderDate);
        System.out.println();
        for (OrderItem item : items) {
            System.out.println(item);
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Total Amount : Rs " + calculateTotal());
        System.out.println("Status       : " + orderStatus);
        if (payment != null) {
            System.out.println(payment);
        }
    }

    // GETTERS
    public int getOrderId() {
        return orderId;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public Payment getPayment() {
        return payment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

}