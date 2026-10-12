using System;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a payment made for an order. It contains details such as the 
    /// payment ID, payment method, amount, and payment status.
    /// </summary>
    public class Payment
    {
        private int paymentId;
        private PaymentMethod paymentMethod;
        private double amount;
        private PaymentStatus paymentStatus;

        // Constructor
        public Payment(int paymentId, PaymentMethod paymentMethod, double amount)
        {
            this.paymentId = paymentId;
            this.paymentMethod = paymentMethod;
            this.amount = amount;
            this.paymentStatus = PaymentStatus.PENDING;
        }

        // Processes the payment. Returns true and marks it SUCCESSFUL.
        public bool ProcessPayment()
        {
            paymentStatus = PaymentStatus.SUCCESSFUL;
            Console.WriteLine("\nPayment Successful");
            Console.WriteLine("Amount : Rs " + amount);
            Console.WriteLine("Method : " + paymentMethod);
            return true;
        }

        public int GetPaymentId() { return paymentId; }

        public PaymentMethod GetPaymentMethod() { return paymentMethod; }

        public double GetAmount() { return amount; }

        public PaymentStatus GetPaymentStatus() { return paymentStatus; }

        // C# Properties
        public int PaymentId { get { return paymentId; } }
        public PaymentMethod Method { get { return paymentMethod; } }
        public double Amount { get { return amount; } }
        public PaymentStatus Status { get { return paymentStatus; } }

        public override string ToString()
        {
            return "\nPayment ID : " + paymentId +
                   "\nMethod : " + paymentMethod +
                   "\nAmount : Rs " + amount +
                   "\nStatus : " + paymentStatus;
        }
    }
}
