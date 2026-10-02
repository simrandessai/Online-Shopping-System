using System;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a payment made for an order. It contains details such as the 
    /// payment ID, payment method, amount, and payment status.
    /// </summary>
    public class Payment
    {
        // Simulated chance that an online payment is declined (Cash on Delivery never fails).
        // Set to 0 to make every payment succeed.
        private static readonly double FAILURE_CHANCE = 0.10;
        private static readonly Random random = new Random();

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

        // Processes the payment. Returns true and marks it SUCCESSFUL if it went
        // through, otherwise marks it FAILED and returns false.
        public bool ProcessPayment()
        {
            bool approved = paymentMethod == PaymentMethod.CASH_ON_DELIVERY
                    || random.NextDouble() >= FAILURE_CHANCE;

            if (approved)
            {
                paymentStatus = PaymentStatus.SUCCESSFUL;
                Console.WriteLine("\nPayment Successful");
                Console.WriteLine("Amount : Rs " + amount);
                Console.WriteLine("Method : " + paymentMethod);
                return true;
            }

            paymentStatus = PaymentStatus.FAILED;
            Console.WriteLine("\nPayment Failed");
            Console.WriteLine("Amount : Rs " + amount);
            Console.WriteLine("Method : " + paymentMethod);
            return false;
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
