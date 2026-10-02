using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a Buyer user in the system. They can manage their cart,
    /// wishlist, and place orders.
    /// </summary>
    public class Buyer : User
    {
        // Relationships
        private Cart cart;
        private Wishlist wishlist;
        private List<Order> orderHistory;
        private List<CustomerCare> tickets;

        // Constructor
        public Buyer(int userId, string name, string email, string password, string phone, string address)
            : base(userId, name, email, password, phone, address, Role.BUYER)
        {
            cart = new Cart(userId);
            wishlist = new Wishlist(userId);
            orderHistory = new List<Order>();
            tickets = new List<CustomerCare>();
        }

        // Cart Methods
        public void AddToCart(Product product, int quantity)
        {
            if (cart.AddProduct(product, quantity))
            {
                Console.WriteLine(quantity + " x "
                        + product.GetProductName()
                        + " added to cart.");
            }
        }

        public void RemoveFromCart(Product product)
        {
            if (cart.RemoveProduct(product))
            {
                Console.WriteLine(product.GetProductName()
                        + " removed from cart.");
            }
            else
            {
                Console.WriteLine("That product isn't in your cart.");
            }
        }

        public void ViewCart()
        {
            cart.DisplayCart();
        }

        public Cart GetCart() { return cart; }
        public Cart Cart { get { return cart; } }

        // Wishlist Methods
        public void AddToWishlist(Product product)
        {
            if (wishlist.AddProduct(product))
            {
                Console.WriteLine(product.GetProductName()
                        + " added to wishlist.");
            }
            else
            {
                Console.WriteLine(product.GetProductName()
                        + " is already in your wishlist.");
            }
        }

        public void RemoveFromWishlist(Product product)
        {
            if (wishlist.RemoveProduct(product))
            {
                Console.WriteLine(product.GetProductName()
                        + " removed from wishlist.");
            }
            else
            {
                Console.WriteLine("That product isn't in your wishlist.");
            }
        }

        public void MoveToWishlist(Product product)
        {
            if (!cart.RemoveProduct(product))
            {
                Console.WriteLine(product.GetProductName() + " is not in your cart.");
                return;
            }
            wishlist.AddProduct(product);
            Console.WriteLine(product.GetProductName() + " moved from cart to wishlist.");
        }

        public void ViewWishlist()
        {
            wishlist.DisplayWishlist();
        }

        public Wishlist GetWishlist() { return wishlist; }
        public Wishlist Wishlist { get { return wishlist; } }

        public void RemoveProductReferences(Product product)
        {
            if (product == null)
            {
                return;
            }
            cart.RemoveProduct(product);
            wishlist.RemoveProduct(product);
        }

        // Order Methods
        // Places the order directly from the cart.
        public Order PlaceOrder()
        {
            if (!IsSignedIn())
            {
                Console.WriteLine("Please log in or register before placing an order.");
                return null;
            }
            if (cart.GetItems().Count == 0)
            {
                Console.WriteLine("Cart is empty.");
                return null;
            }
            // Check every item first so a half-built order is never created.
            foreach (CartItem item in cart.GetItems())
            {
                if (item.GetQuantity() <= 0 || item.GetQuantity() > item.GetProduct().GetStock())
                {
                    Console.WriteLine("Insufficient stock for " + item.GetProduct().GetProductName()
                            + ". The order was not created.");
                    return null;
                }
            }
            Order order = new Order(this);
            foreach (CartItem item in cart.GetItems())
            {
                order.AddItem(item.GetProduct(), item.GetQuantity());
            }
            Console.WriteLine("Order created (status: PENDING). Proceed to payment.");
            return order;
        }

        // Starts a direct order without using the cart.
        public Order StartDirectOrder()
        {
            if (!IsSignedIn())
            {
                Console.WriteLine("Please log in or register before placing an order.");
                return null;
            }
            return new Order(this);
        }

        public void AddOrderToHistory(Order order)
        {
            if (order != null)
            {
                orderHistory.Add(order);
            }
        }

        public List<Order> GetOrderHistory() { return orderHistory; }
        public List<Order> OrderHistory { get { return orderHistory; } }

        // Displays every order the buyer has placed.
        public void ViewOrderHistory()
        {
            Console.WriteLine("\n ORDER HISTORY ");
            if (orderHistory.Count == 0)
            {
                Console.WriteLine("You haven't placed any orders yet.");
                return;
            }
            foreach (Order order in orderHistory)
            {
                order.DisplayOrder();
            }
        }

        // To check if the buyer has purchased a specific product.
        public bool HasPurchased(Product product)
        {
            if (product == null)
            {
                return false;
            }
            foreach (Order order in orderHistory)
            {
                if (order.GetOrderStatus() == OrderStatus.CANCELLED)
                {
                    continue;
                }
                foreach (OrderItem item in order.GetItems())
                {
                    if (item.GetProduct() != null && item.GetProduct().GetProductId() == product.GetProductId())
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        /// <summary>
        /// Retrieves a list of all products the buyer has purchased, excluding cancelled
        /// orders and ensuring no duplicates.
        /// </summary>
        public List<Product> GetPurchasedProducts()
        {
            List<Product> purchased = new List<Product>();
            foreach (Order order in orderHistory)
            {
                if (order.GetOrderStatus() == OrderStatus.CANCELLED)
                {
                    continue;
                }
                foreach (OrderItem item in order.GetItems())
                {
                    Product product = item.GetProduct();
                    if (product == null)
                    {
                        continue;
                    }
                    bool alreadyAdded = false;
                    foreach (Product existing in purchased)
                    {
                        if (existing.GetProductId() == product.GetProductId())
                        {
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if (!alreadyAdded)
                    {
                        purchased.Add(product);
                    }
                }
            }
            return purchased;
        }

        // Gives a review for a purchased product.
        public void GiveReview(Product product, int rating, string comment)
        {
            if (product == null)
            {
                Console.WriteLine("Product not found.");
                return;
            }
            if (!HasPurchased(product))
            {
                Console.WriteLine("You can only review products you have purchased.");
                return;
            }
            if (rating < 1 || rating > 5)
            {
                Console.WriteLine("Rating should be between 1 and 5.");
                return;
            }
            foreach (Review existingReview in product.GetReviews())
            {
                if (existingReview.GetBuyer() == this)
                {
                    Console.WriteLine("You have already reviewed this product.");
                    return;
                }
            }
            if (string.IsNullOrWhiteSpace(comment))
            {
                Console.WriteLine("Review comment cannot be empty.");
                return;
            }
            Review review = new Review(
                    product.GetReviews().Count + 1,
                    this,
                    product,
                    rating,
                    comment.Trim());
            product.AddReview(review);
            Console.WriteLine("Review submitted successfully.");
        }

        // Customer Care Methods

        // Raises a new support ticket. The caller passes it on to an admin.
        public CustomerCare RaiseTicket(string issue)
        {
            if (!IsSignedIn())
            {
                Console.WriteLine("Please log in before raising a support ticket.");
                return null;
            }
            if (string.IsNullOrWhiteSpace(issue))
            {
                Console.WriteLine("Issue description cannot be empty.");
                return null;
            }
            CustomerCare ticket = new CustomerCare(issue.Trim(), this);
            tickets.Add(ticket);
            Console.WriteLine("Support ticket raised. Your Ticket ID is "
                    + ticket.GetTicketId() + ".");
            return ticket;
        }

        // Shows the buyer's own tickets along with any admin replies.
        public void ViewTickets()
        {
            Console.WriteLine("\n MY SUPPORT TICKETS ");
            if (tickets.Count == 0)
            {
                Console.WriteLine("You haven't raised any support tickets.");
                return;
            }
            foreach (CustomerCare ticket in tickets)
            {
                Console.WriteLine(ticket);
            }
        }

        public List<CustomerCare> GetTickets() { return tickets; }
        public List<CustomerCare> Tickets { get { return tickets; } }

        public override string ToString()
        {
            return "\n BUYER " +
                    "\n" + base.ToString();
        }
    }
}
