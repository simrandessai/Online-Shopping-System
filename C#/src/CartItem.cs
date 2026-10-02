namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents an individual item in the shopping cart, linking a product with its quantity.
    /// </summary>
    public class CartItem
    {
        private Product product;
        private int quantity;

        // Constructor
        public CartItem(Product product, int quantity)
        {
            this.product = product;
            this.quantity = quantity;
        }

        public Product GetProduct() { return product; }

        public int GetQuantity() { return quantity; }

        public void SetQuantity(int quantity)
        {
            this.quantity = quantity;
        }

        public double GetTotalPrice()
        {
            return product != null ? product.GetPrice() * quantity : 0.0;
        }

        // C# Properties
        public Product Product { get { return product; } }
        public int Quantity { get { return quantity; } set { quantity = value; } }
        public double TotalPrice { get { return GetTotalPrice(); } }

        public override string ToString()
        {
            return "ID: " + (product != null ? product.GetProductId().ToString() : "N/A") +
                   " | " + (product != null ? product.GetProductName() : "N/A") +
                   " | Qty : " + quantity +
                   " | Total : Rs " + GetTotalPrice();
        }
    }
}
