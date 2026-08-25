// Represents different items in an order, containing a product, quantity, and price.
public class OrderItem {

    private Product product;
    private int quantity;
    private double price;

    // Constructor
    public OrderItem(Product product, int quantity) {

        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {

        return product.getProductName()
                + " | Qty : "
                + quantity
                + " | Price : Rs "
                + price
                + " | Total : Rs "
                + getSubTotal();
    }

}