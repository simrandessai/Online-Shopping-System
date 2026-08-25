// This is the Payment class, which represents a payment made for an order. 
// It contains details such as the payment ID, payment method, amount, and payment status.
public class Payment {

    private int paymentId;
    private PaymentMethod paymentMethod;
    private double amount;
    private PaymentStatus paymentStatus;

    // Constructor
    public Payment(int paymentId,
            PaymentMethod paymentMethod,
            double amount) {

        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PENDING;

    }

    // Processes the payment, updating the payment status to successful and
    // displaying the payment details.
    public void processPayment() {
        paymentStatus = PaymentStatus.SUCCESSFUL;
        System.out.println("\nPayment Successful");
        System.out.println("Amount : Rs " + amount);
        System.out.println("Method : " + paymentMethod);
    }

    public int getPaymentId() {
        return paymentId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {

        return "\nPayment ID : " + paymentId +
                "\nMethod : " + paymentMethod +
                "\nAmount : Rs " + amount +
                "\nStatus : " + paymentStatus;

    }

}