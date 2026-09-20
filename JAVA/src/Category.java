import java.util.ArrayList;

// Categorises products into different groups, allowing for better organization and filtering in the e-commerce system.
public class Category {

    private int categoryId;
    private String categoryName;

    // One Category contains many Products
    private ArrayList<Product> products;

    // Constructor
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.products = new ArrayList<>();
    }

    // Add Product
    public void addProduct(Product product) {
        products.add(product);
    }

    // Add multiple Products
    public void addProduct(Product... productsToAdd) {
        for (Product product : productsToAdd) {
            products.add(product);
        }
    }

    // Remove Product
    public void removeProduct(Product product) {
        products.remove(product);
    }

    // To Display Products
    public void displayProducts() {

        System.out.println("\nCategory : " + categoryName);

        for (Product p : products)
            System.out.println(p);
    }

    // Getters & Setters

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {

        return "Category ID : " + categoryId +
                "\nCategory : " + categoryName;
    }
}