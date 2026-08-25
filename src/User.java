// This is the User class, which represents a user in the online shopping system. 
// It contains details such as user ID, name, email, password, phone number, address
public class User {

    protected int userId;
    protected String name;
    protected String email;
    protected String password;
    protected String phone;
    protected String address;
    protected Role role;

    // Constructors
    public User() {
    }

    public User(int userId, String name, String email, String password,
            String phone, String address) {
        this(userId, name, email, password, phone, address, null);
    }

    public User(int userId, String name, String email, String password,
            String phone, String address, Role role) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    // Login
    public void login() {
        System.out.println(name + " logged in.");
    }

    // Logout
    public void logout() {
        System.out.println(name + " logged out.");
    }

    // Update Profile
    public void updateProfile(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    // Getters & Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User ID : " + userId +
                "\nName : " + name +
                "\nEmail : " + email +
                "\nPhone : " + phone +
                "\nAddress : " + address +
                "\nRole : " + (role != null ? role : "N/A");
    }
}