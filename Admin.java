import java.util.ArrayList;

// Represents an Admin user in the system. They can manage users, products, and handle customer care tickets.
public class Admin extends User {

    // Tickets that the admin has received for customer care.
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

    // Ticket Methods

    public void receiveTicket(CustomerCare ticket) {
        tickets.add(ticket);
    }

    public void viewTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No Tickets.");
            return;
        }
        for (CustomerCare ticket : tickets)
            System.out.println(ticket);
    }

    public void resolveTicket(CustomerCare ticket,
            String reply) {
        ticket.setResponse(reply);
        ticket.setStatus(TicketStatus.RESOLVED);
        System.out.println(
                "Ticket "
                        + ticket.getTicketId()
                        + " Resolved.");
    }
}