namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents an item in an order, containing a product, quantity, and unit price at time of order.
    /// </summary>
    public class OrderItem
    {
        private Product product;
        private int quantity;
        private double price;

        // Constructor
        public OrderItem(Product product, int quantity)
        {
            this.product = product;
            this.quantity = quantity;
            this.price = product != null ? product.GetPrice() : 0.0;
        }

        public Product GetProduct() { return product; }

        public int GetQuantity() { return quantity; }

        public double GetPrice() { return price; }

        public double GetSubTotal()
        {
            return price * quantity;
        }

        // C# Properties
        public Product Product { get { return product; } }
        public int Quantity { get { return quantity; } }
        public double Price { get { return price; } }
        public double SubTotal { get { return GetSubTotal(); } }

        public override string ToString()
        {
            return (product != null ? product.GetProductName() : "N/A")
                    + " | Qty : "
                    + quantity
                    + " | Price : Rs "
                    + price
                    + " | Total : Rs "
                    + GetSubTotal();
        }
    }
}
