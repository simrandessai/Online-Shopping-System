using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a user's wishlist, containing a list of products they are interested in.
    /// Provides methods to add/remove products and display the wishlist.
    /// </summary>
    public class Wishlist
    {
        private int wishlistId;
        private List<Product> products;

        // Constructor
        public Wishlist(int wishlistId)
        {
            this.wishlistId = wishlistId;
            products = new List<Product>();
        }

        // Add product to wishlist. Returns false if it was null or already there.
        public bool AddProduct(Product product)
        {
            if (product == null || products.Contains(product))
            {
                return false;
            }
            products.Add(product);
            return true;
        }

        // Remove product from wishlist. Returns true if it was actually there.
        public bool RemoveProduct(Product product)
        {
            return products.Remove(product);
        }

        public void DisplayWishlist()
        {
            Console.WriteLine("\n WISHLIST ");
            if (products.Count == 0)
            {
                Console.WriteLine("Wishlist is empty.");
                return;
            }
            foreach (Product p in products)
            {
                Console.WriteLine(p.GetProductName());
            }
        }

        public int GetWishlistId() { return wishlistId; }

        public List<Product> GetProducts() { return products; }

        // C# Properties
        public int WishlistId { get { return wishlistId; } }
        public List<Product> Products { get { return products; } }
    }
}
