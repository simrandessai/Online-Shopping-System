using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Categorises products into different groups, allowing for better organization and filtering in the e-commerce system.
    /// </summary>
    public class Category
    {
        private int categoryId;
        private string categoryName;
        private List<Product> products;

        // Constructor
        public Category(int categoryId, string categoryName)
        {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.products = new List<Product>();
        }

        // Add Product
        public void AddProduct(Product product)
        {
            if (product != null && !products.Contains(product))
            {
                products.Add(product);
            }
        }

        // Add multiple Products
        public void AddProduct(params Product[] productsToAdd)
        {
            foreach (Product product in productsToAdd)
            {
                if (product != null && !products.Contains(product))
                {
                    products.Add(product);
                }
            }
        }

        // Remove Product
        public void RemoveProduct(Product product)
        {
            products.Remove(product);
        }

        // Display Products
        public void DisplayProducts()
        {
            Console.WriteLine("\nCategory : " + categoryName);
            foreach (Product p in products)
            {
                Console.WriteLine(p);
            }
        }

        // Getters & Setters
        public int GetCategoryId() { return categoryId; }
        public void SetCategoryId(int categoryId) { this.categoryId = categoryId; }

        public string GetCategoryName() { return categoryName; }
        public void SetCategoryName(string categoryName) { this.categoryName = categoryName; }

        public List<Product> GetProducts() { return products; }

        // C# Properties
        public int CategoryId { get { return categoryId; } set { categoryId = value; } }
        public string CategoryName { get { return categoryName; } set { categoryName = value; } }
        public List<Product> Products { get { return products; } }

        public override string ToString()
        {
            return "Category ID : " + categoryId +
                   "\nCategory : " + categoryName;
        }
    }
}
