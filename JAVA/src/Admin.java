
/**
 * Represents an Admin user in the system. They can manage users and products, 
 * and handle customer care tickets raised by buyers.
 */
import java.util.ArrayList;

public class Admin extends User {

    // Tickets received from buyers, waiting to be handled.
    private ArrayList<CustomerCare> tickets;

    // Constructor
    public Admin(int userId,
            String name,
            String email,
            String password,
            String phone,
            String address) {
        super(userId,
                name,
                email,
                password,
                phone,
                address,
                Role.ADMIN);

        tickets = new ArrayList<>();
    }

    // User Management
    public void manageUsers() {
        System.out.println("Managing Users...");
    }

    // Product Management
    public void manageProducts() {
        System.out.println("Managing Products...");
    }

    // Removes a product from its seller and its category.
    // (The caller is responsible for removing it from the global product list.)
    public void removeProduct(Product product) {
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        if (product.getSeller() != null) {
            product.getSeller().removeProduct(product);
        }
        if (product.getCategory() != null) {
            product.getCategory().removeProduct(product);
        }
        System.out.println(product.getProductName() + " removed from the system.");
    }

    // Customer Care Ticket Handling

    // Receives a ticket raised by a buyer.
    public void receiveTicket(CustomerCare ticket) {
        if (ticket != null) {
            tickets.add(ticket);
            System.out.println("Ticket " + ticket.getTicketId() + " received by " + name + ".");
        }
    }

    // Opens a ticket to read it. An OPEN ticket moves to IN_PROGRESS once viewed.
    public void viewTicket(CustomerCare ticket) {
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }
        if (ticket.getStatus() == TicketStatus.OPEN) {
            ticket.setStatus(TicketStatus.IN_PROGRESS);
        }
        System.out.println(ticket);
    }

    // Lists a one-line summary of every ticket.
    public void viewAllTickets() {
        System.out.println("\n SUPPORT TICKETS ");
        if (tickets.isEmpty()) {
            System.out.println("No support tickets.");
            return;
        }
        System.out.printf("%-6s %-12s %-12s %s%n", "ID", "Buyer", "Status", "Issue");
        System.out.println("-".repeat(75));
        for (CustomerCare ticket : tickets) {
            System.out.println(ticket.toSummary());
        }
    }

    // Replies to a ticket and marks it as RESOLVED.
    public void resolveTicket(CustomerCare ticket, String reply) {
        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }
        if (ticket.isResolved()) {
            System.out.println("Ticket " + ticket.getTicketId() + " is already resolved.");
            return;
        }
        if (reply == null || reply.trim().isEmpty()) {
            System.out.println("Reply cannot be empty. Ticket left unresolved.");
            return;
        }
        ticket.setResponse(reply.trim());
        ticket.setStatus(TicketStatus.RESOLVED);
        System.out.println("Ticket " + ticket.getTicketId() + " resolved.");
    }

    // Finds a ticket by its ID, or returns null if it doesn't exist.
    public CustomerCare findTicket(int ticketId) {
        for (CustomerCare ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }
        return null;
    }

    public ArrayList<CustomerCare> getTickets() {
        return tickets;
    }

}