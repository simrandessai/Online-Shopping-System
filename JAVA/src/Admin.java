// Represents an Admin user in the system. They can manage users and products.
public class Admin extends User {

    // Constructor
    public Admin(int userId,
            String name,
            String email,
            String password,
            String phone,
            String address) {
        super(userId,
                name,
                email,
                password,
                phone,
                address,
                Role.ADMIN);

    }

    // User Management
    public void manageUsers() {
        System.out.println("Managing Users...");
    }

    // Product Management
    public void manageProducts() {
        System.out.println("Managing Products...");
    }

}