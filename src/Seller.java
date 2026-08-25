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
}