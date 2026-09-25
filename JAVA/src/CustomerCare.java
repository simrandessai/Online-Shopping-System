
/**
 * Represents a customer care (support) ticket. A buyer raises a ticket
 * describing an issue, and an admin handles it by replying and resolving it.
 */
import java.time.LocalDate;

public class CustomerCare {

    // Static ID Generator
    private static int nextTicketId = 3001;

    private int ticketId;
    private String issue;
    private String response;
    private TicketStatus status;

    // Relationship: the user who raised this ticket
    private User raisedBy;
    private LocalDate raisedDate;

    // Constructor: every new ticket starts as OPEN with no response.
    public CustomerCare(String issue, User raisedBy) {
        if (issue == null || issue.trim().isEmpty()) {
            throw new IllegalArgumentException("A ticket needs an issue description.");
        }
        if (raisedBy == null) {
            throw new IllegalArgumentException("A ticket must be raised by a user.");
        }
        this.ticketId = nextTicketId++;
        this.issue = issue.trim();
        this.raisedBy = raisedBy;
        this.raisedDate = LocalDate.now();
        this.response = "";
        this.status = TicketStatus.OPEN;
    }

    // Setters (as per UML)
    public void setStatus(TicketStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public void setResponse(String reply) {
        this.response = (reply == null) ? "" : reply;
    }

    // Getters
    public int getTicketId() {
        return ticketId;
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

    public User getRaisedBy() {
        return raisedBy;
    }

    public LocalDate getRaisedDate() {
        return raisedDate;
    }

    public boolean isResolved() {
        return status == TicketStatus.RESOLVED;
    }

    // One-line summary used when listing many tickets.
    public String toSummary() {
        return String.format("%-6d %-12s %-12s %s",
                ticketId,
                truncate(raisedBy.getName(), 12),
                status,
                truncate(issue, 40));
    }

    private static String truncate(String text, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    @Override
    public String toString() {
        return "\nTICKET" +
                "\nTicket ID : " + ticketId +
                "\nRaised By : " + raisedBy.getName() +
                "\nDate      : " + raisedDate +
                "\nIssue     : " + issue +
                "\nStatus    : " + status +
                "\nResponse  : " + (response.isEmpty() ? "Awaiting response" : response);
    }
}