using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a Seller user in the system.
    /// They can manage their products.
    /// </summary>
    public class Seller : User
    {
        private List<Product> products;

        // Constructor
        public Seller(int userId, string name, string email, string password, string phone, string address)
            : base(userId, name, email, password, phone, address, Role.SELLER)
        {
            products = new List<Product>();
        }

        // Add Product
        public void AddProduct(Product product)
        {
            if (product != null && !products.Contains(product))
            {
                products.Add(product);
            }
        }

        // Delete Product
        public void RemoveProduct(Product product)
        {
            products.Remove(product);
        }

        // View Products
        public void ViewProducts()
        {
            Console.WriteLine("\nSeller Products");
            if (products.Count == 0)
            {
                Console.WriteLine("No products available.");
                return;
            }
            foreach (Product p in products)
            {
                Console.WriteLine(p);
            }
        }

        public List<Product> GetProducts() { return products; }

        public List<Product> Products { get { return products; } }
    }
}
