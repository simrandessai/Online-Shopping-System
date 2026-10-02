using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Places an order for a buyer, containing multiple items, payment details, and order status.
    /// </summary>
    public class Order
    {
        // Static ID Generator
        private static int nextOrderId = 1;

        private int orderId;
        private Buyer buyer;
        private DateTime orderDate;
        private List<OrderItem> items;
        private Payment payment;
        private OrderStatus orderStatus;

        // Constructor: every new order gets a unique ID and starts as PENDING.
        public Order(Buyer buyer)
        {
            this.orderId = nextOrderId++;
            this.buyer = buyer;
            this.orderDate = DateTime.Now;
            this.items = new List<OrderItem>();
            this.orderStatus = OrderStatus.PENDING;
        }

        // Add Item to Order.
        // Stock is only checked here; it is deducted once payment succeeds.
        public void AddItem(Product product, int quantity)
        {
            if (product == null || quantity <= 0)
            {
                Console.WriteLine("Product and quantity must be valid.");
                return;
            }
            int alreadyInOrder = 0;
            foreach (OrderItem item in items)
            {
                if (item.GetProduct().GetProductId() == product.GetProductId())
                {
                    alreadyInOrder += item.GetQuantity();
                }
            }
            if (product.GetStock() < alreadyInOrder + quantity)
            {
                Console.WriteLine("Insufficient stock for " + product.GetProductName());
                return;
            }
            items.Add(new OrderItem(product, quantity));
        }

        // Remove Item from Order
        public void RemoveItem(Product product)
        {
            if (product != null)
            {
                items.RemoveAll(item => item.GetProduct().GetProductId() == product.GetProductId());
            }
        }

        // Calculate Total Amount of the Order
        public double CalculateTotal()
        {
            double total = 0;
            foreach (OrderItem item in items)
            {
                total += item.GetSubTotal();
            }
            return total;
        }

        // Payment Method.
        // On success: stock is reduced and the order becomes CONFIRMED.
        // On failure: the order stays PENDING and no stock is deducted.
        public bool MakePayment(PaymentMethod method)
        {
            if (orderStatus == OrderStatus.CONFIRMED)
            {
                Console.WriteLine("This order has already been paid.");
                return false;
            }
            if (orderStatus == OrderStatus.CANCELLED)
            {
                Console.WriteLine("This order has been cancelled.");
                return false;
            }
            if (items.Count == 0)
            {
                Console.WriteLine("Cannot process payment for an empty order.");
                return false;
            }

            payment = new Payment(orderId, method, CalculateTotal());
            bool paid = payment.ProcessPayment();
            if (paid)
            {
                foreach (OrderItem item in items)
                {
                    item.GetProduct().ReduceStock(item.GetQuantity());
                }
                orderStatus = OrderStatus.CONFIRMED;
            }
            return paid;
        }

        // Update Order Status
        public void UpdateOrderStatus(OrderStatus status)
        {
            this.orderStatus = status;
        }

        // Display Order Details
        public void DisplayOrder()
        {
            Console.WriteLine("\n ORDER ");
            Console.WriteLine("Order ID   : " + orderId);
            Console.WriteLine("Buyer      : " + (buyer != null ? buyer.GetName() : "N/A"));
            Console.WriteLine("Order Date : " + orderDate.ToString("yyyy-MM-dd"));
            Console.WriteLine();
            foreach (OrderItem item in items)
            {
                Console.WriteLine(item);
            }
            Console.WriteLine("Total Amount : Rs " + CalculateTotal());
            Console.WriteLine("Status       : " + orderStatus);
            if (payment != null)
            {
                Console.WriteLine(payment);
            }
        }

        // Getters
        public int GetOrderId() { return orderId; }
        public Buyer GetBuyer() { return buyer; }
        public DateTime GetOrderDate() { return orderDate; }
        public List<OrderItem> GetItems() { return items; }
        public Payment GetPayment() { return payment; }
        public OrderStatus GetOrderStatus() { return orderStatus; }

        // C# Properties
        public int OrderId { get { return orderId; } }
        public Buyer Buyer { get { return buyer; } }
        public DateTime OrderDate { get { return orderDate; } }
        public List<OrderItem> Items { get { return items; } }
        public Payment Payment { get { return payment; } }
        public OrderStatus Status { get { return orderStatus; } set { orderStatus = value; } }
    }
}
