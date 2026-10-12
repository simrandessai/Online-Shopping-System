using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a shopping cart, holding items before they're checked out into
    /// an order. Buyers have their own cart; visitors who haven't signed in use a
    /// temporary guest cart that is merged into their cart when they sign in.
    /// </summary>
    public class Cart
    {
        private int cartId;
        private List<CartItem> items;

        // Constructor
        public Cart(int cartId)
        {
            this.cartId = cartId;
            items = new List<CartItem>();
        }

        // Add product to cart. If the product already exists, increase its quantity.
        public bool AddProduct(Product product, int quantity)
        {
            if (product == null || quantity <= 0)
            {
                return false;
            }
            foreach (CartItem item in items)
            {
                if (item.GetProduct().GetProductId() == product.GetProductId())
                {
                    if (item.GetQuantity() + quantity > product.GetStock())
                    {
                        Console.WriteLine("Cannot add more than the available stock.");
                        return false;
                    }
                    item.SetQuantity(item.GetQuantity() + quantity);
                    return true;
                }
            }
            if (quantity > product.GetStock())
            {
                Console.WriteLine("Cannot add more than the available stock.");
                return false;
            }
            items.Add(new CartItem(product, quantity));
            return true;
        }

        // Returns the CartItem for the given product, or null if not found.
        public CartItem GetItem(Product product)
        {
            if (product == null)
            {
                return null;
            }
            return items.Find(item => item.GetProduct().GetProductId() == product.GetProductId());
        }

        // Removes a product or deducts a specific quantity from the cart.
        // If quantity is null or >= existing quantity, the product is completely removed.
        // If 0 < quantity < existing quantity, the item quantity is reduced.
        // Returns true if an item was removed or its quantity was deducted, false otherwise.
        public bool RemoveProduct(Product product, int? quantity = null)
        {
            if (product == null)
            {
                return false;
            }
            CartItem item = GetItem(product);
            if (item == null)
            {
                return false;
            }
            if (!quantity.HasValue || quantity.Value >= item.GetQuantity())
            {
                items.Remove(item);
                return true;
            }
            if (quantity.Value > 0)
            {
                item.SetQuantity(item.GetQuantity() - quantity.Value);
                return true;
            }
            return false;
        }

        // Calculates the total price of everything in the cart.
        public double CalculateTotal()
        {
            double total = 0;
            foreach (CartItem item in items)
            {
                total += item.GetTotalPrice();
            }
            return total;
        }

        // Displays the contents of the cart, including each item's details and the grand total.
        public void DisplayCart()
        {
            Console.WriteLine("\n CART ");
            if (items.Count == 0)
            {
                Console.WriteLine("Cart is empty.");
            }
            else
            {
                foreach (CartItem item in items)
                {
                    Console.WriteLine(item);
                }
                Console.WriteLine("Grand Total : Rs " + CalculateTotal());
            }
        }

        // Clears all items from the cart.
        public void ClearCart()
        {
            items.Clear();
        }

        public int GetCartId() { return cartId; }

        // Getter for cart items.
        public List<CartItem> GetItems() { return items; }

        // C# Properties
        public int CartId { get { return cartId; } }
        public List<CartItem> Items { get { return items; } }
    }
}
