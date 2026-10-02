using System;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents a customer care (support) ticket. A buyer raises a ticket
    /// describing an issue, and an admin handles it by replying and resolving it.
    /// </summary>
    public class CustomerCare
    {
        // Static ID Generator
        private static int nextTicketId = 3001;

        private int ticketId;
        private string issue;
        private string response;
        private TicketStatus status;

        // Relationship: the user who raised this ticket
        private User raisedBy;
        private DateTime raisedDate;

        // Constructor: every new ticket starts as OPEN with no response.
        public CustomerCare(string issue, User raisedBy)
        {
            if (string.IsNullOrWhiteSpace(issue))
            {
                throw new ArgumentException("A ticket needs an issue description.");
            }
            if (raisedBy == null)
            {
                throw new ArgumentNullException("raisedBy", "A ticket must be raised by a user.");
            }

            this.ticketId = nextTicketId++;
            this.issue = issue.Trim();
            this.raisedBy = raisedBy;
            this.raisedDate = DateTime.Now;
            this.response = "";
            this.status = TicketStatus.OPEN;
        }

        // Setters
        public void SetStatus(TicketStatus status)
        {
            this.status = status;
        }

        public void SetResponse(string reply)
        {
            this.response = reply ?? "";
        }

        // Getters
        public int GetTicketId() { return ticketId; }
        public string GetIssue() { return issue; }
        public string GetResponse() { return response; }
        public TicketStatus GetStatus() { return status; }
        public User GetRaisedBy() { return raisedBy; }
        public DateTime GetRaisedDate() { return raisedDate; }

        // C# Properties
        public int TicketId { get { return ticketId; } }
        public string Issue { get { return issue; } }
        public string ResponseText { get { return response; } set { response = value ?? ""; } }
        public TicketStatus Status { get { return status; } set { status = value; } }
        public User RaisedBy { get { return raisedBy; } }
        public DateTime RaisedDate { get { return raisedDate; } }

        public bool IsResolved()
        {
            return status == TicketStatus.RESOLVED;
        }

        // One-line summary used when listing many tickets.
        public string ToSummary()
        {
            return string.Format("{0,-6} {1,-12} {2,-12} {3}",
                ticketId,
                Truncate(raisedBy.GetName(), 12),
                status,
                Truncate(issue, 40));
        }

        private static string Truncate(string text, int maxLength)
        {
            if (text == null)
            {
                return "";
            }
            if (text.Length <= maxLength)
            {
                return text;
            }
            return text.Substring(0, maxLength - 3) + "...";
        }

        public override string ToString()
        {
            return "\nTICKET" +
                   "\nTicket ID : " + ticketId +
                   "\nRaised By : " + raisedBy.GetName() +
                   "\nDate      : " + raisedDate.ToString("yyyy-MM-dd") +
                   "\nIssue     : " + issue +
                   "\nStatus    : " + status +
                   "\nResponse  : " + (string.IsNullOrEmpty(response) ? "Awaiting response" : response);
        }
    }
}
