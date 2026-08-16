public class CustomerCare {

    private static int nextTicketId = 1;

    private int ticketId;
    private User user; // Buyer or Seller
    private String issue;
    private String response;
    private TicketStatus status;

    public CustomerCare(User user, String issue) {

        this.ticketId = nextTicketId++;
        this.user = user;
        this.issue = issue;
        this.response = "";
        this.status = TicketStatus.OPEN;

    }

    // Getters
    public int getTicketId() {
        return ticketId;
    }

    public User getUser() {
        return user;
    }

    public String getIssue() {
        return issue;
    }

    public String getResponse() {
        return response;
    }

    public TicketStatus getStatus() {
        return status;
    }

    // Update Status
    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    // Admin Reply
    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {

        return "\n CUSTOMER CARE " +
                "\nTicket ID : " + ticketId +
                "\nUser      : " + user.getName() +
                "\nRole      : " + user.getRole() +
                "\nIssue     : " + issue +
                "\nResponse  : " + response +
                "\nStatus    : " + status;

    }
}