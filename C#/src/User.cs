using System;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a user in the online shopping system.
    /// It contains details such as user ID, name, email, password, phone number, address, and role.
    /// </summary>
    public class User
    {
        protected int userId;
        protected string name = "";
        protected string email = "";
        protected string password = "";
        protected string phone = "";
        protected string address = "";
        protected Role? role;
        protected bool signedIn;

        // Constructors
        public User()
        {
        }

        public User(int userId, string name, string email, string password, string phone, string address)
            : this(userId, name, email, password, phone, address, null)
        {
        }

        public User(int userId, string name, string email, string password, string phone, string address, Role? role)
        {
            this.userId = userId;
            this.name = name;
            this.email = email;
            this.password = password;
            this.phone = phone;
            this.address = address;
            this.role = role;
        }

        // Register
        public virtual void Register()
        {
            Console.WriteLine(name + " registered successfully as "
                + (role.HasValue ? role.Value.ToString() : "USER") + ".");
        }

        // Login
        public virtual void Login()
        {
            signedIn = true;
            Console.WriteLine(name + " logged in.");
        }

        // Logout
        public virtual void Logout()
        {
            signedIn = false;
            Console.WriteLine(name + " logged out.");
        }

        // Checks whether the user is currently signed in.
        public bool IsSignedIn()
        {
            return signedIn;
        }

        // Update Profile
        public void UpdateProfile(string phone, string address)
        {
            this.phone = phone;
            this.address = address;
        }

        // Getters & Setters
        public int GetUserId() { return userId; }
        public void SetUserId(int userId) { this.userId = userId; }

        public string GetName() { return name; }
        public void SetName(string name) { this.name = name; }

        public string GetEmail() { return email; }
        public void SetEmail(string email) { this.email = email; }

        public string GetPassword() { return password; }
        public void SetPassword(string password) { this.password = password; }

        public string GetPhone() { return phone; }
        public void SetPhone(string phone) { this.phone = phone; }

        public string GetAddress() { return address; }
        public void SetAddress(string address) { this.address = address; }

        public Role? GetRole() { return role; }
        public void SetRole(Role? role) { this.role = role; }

        // C# Properties
        public int UserId { get { return userId; } set { userId = value; } }
        public string Name { get { return name; } set { name = value; } }
        public string Email { get { return email; } set { email = value; } }
        public string Password { get { return password; } set { password = value; } }
        public string Phone { get { return phone; } set { phone = value; } }
        public string Address { get { return address; } set { address = value; } }
        public Role? UserRole { get { return role; } set { role = value; } }
        public bool SignedIn { get { return signedIn; } set { signedIn = value; } }

        public override string ToString()
        {
            return "User ID : " + userId +
                   "\nName : " + name +
                   "\nEmail : " + email +
                   "\nPhone : " + phone +
                   "\nAddress : " + address +
                   "\nRole : " + (role.HasValue ? role.Value.ToString() : "N/A");
        }
    }
}
