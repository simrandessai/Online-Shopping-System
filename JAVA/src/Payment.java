
/**
 * Represents a payment made for an order. It contains details such as the 
 * payment ID, payment method, amount, and payment status.
 */
import java.util.Random;

public class Payment {

    // Simulated chance that an online payment is declined (Cash on Delivery never
    // fails).
    // Set to 0 to make every payment succeed.
    private static final double FAILURE_CHANCE = 0.10;
    private static final Random random = new Random();

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

    // Processes the payment. Returns true and marks it SUCCESSFUL if it went
    // through, otherwise marks it FAILED and returns false.
    public boolean processPayment() {
        boolean approved = paymentMethod == PaymentMethod.CASH_ON_DELIVERY
                || random.nextDouble() >= FAILURE_CHANCE;

        if (approved) {
            paymentStatus = PaymentStatus.SUCCESSFUL;
            System.out.println("\nPayment Successful");
            System.out.println("Amount : Rs " + amount);
            System.out.println("Method : " + paymentMethod);
            return true;
        }

        paymentStatus = PaymentStatus.FAILED;
        System.out.println("\nPayment Failed");
        System.out.println("Amount : Rs " + amount);
        System.out.println("Method : " + paymentMethod);
        return false;
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