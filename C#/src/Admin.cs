using System;
using System.Collections.Generic;

namespace OnlineShoppingSystem
{
    /// <summary>
    /// Represents an Admin user in the system. They can manage users and products, 
    /// and handle customer care tickets raised by buyers.
    /// </summary>
    public class Admin : User
    {
        // Tickets received from buyers, waiting to be handled.
        private List<CustomerCare> tickets;

        // Constructor
        public Admin(int userId, string name, string email, string password, string phone, string address)
            : base(userId, name, email, password, phone, address, Role.ADMIN)
        {
            tickets = new List<CustomerCare>();
        }

        // User Management
        public void ManageUsers()
        {
            Console.WriteLine("Managing Users...");
        }

        // Product Management
        public void ManageProducts()
        {
            Console.WriteLine("Managing Products...");
        }

        // Removes a product from its seller and its category.
        public void RemoveProduct(Product product)
        {
            if (product == null)
            {
                Console.WriteLine("Product not found.");
                return;
            }
            if (product.GetSeller() != null)
            {
                product.GetSeller().RemoveProduct(product);
            }
            if (product.GetCategory() != null)
            {
                product.GetCategory().RemoveProduct(product);
            }
            Console.WriteLine(product.GetProductName() + " removed from the system.");
        }

        // Customer Care Ticket Handling

        // Receives a ticket raised by a buyer.
        public void ReceiveTicket(CustomerCare ticket)
        {
            if (ticket != null)
            {
                tickets.Add(ticket);
                Console.WriteLine("Ticket " + ticket.GetTicketId() + " received by " + name + ".");
            }
        }

        // Opens a ticket to read it. An OPEN ticket moves to IN_PROGRESS once viewed.
        public void ViewTicket(CustomerCare ticket)
        {
            if (ticket == null)
            {
                Console.WriteLine("Ticket not found.");
                return;
            }
            if (ticket.GetStatus() == TicketStatus.OPEN)
            {
                ticket.SetStatus(TicketStatus.IN_PROGRESS);
            }
            Console.WriteLine(ticket);
        }

        // Lists a one-line summary of every ticket.
        public void ViewAllTickets()
        {
            Console.WriteLine("\n SUPPORT TICKETS ");
            if (tickets.Count == 0)
            {
                Console.WriteLine("No support tickets.");
                return;
            }
            Console.WriteLine(string.Format("{0,-6} {1,-12} {2,-12} {3}", "ID", "Buyer", "Status", "Issue"));
            Console.WriteLine(new string('-', 75));
            foreach (CustomerCare ticket in tickets)
            {
                Console.WriteLine(ticket.ToSummary());
            }
        }

        // Replies to a ticket and marks it as RESOLVED.
        public void ResolveTicket(CustomerCare ticket, string reply)
        {
            if (ticket == null)
            {
                Console.WriteLine("Ticket not found.");
                return;
            }
            if (ticket.IsResolved())
            {
                Console.WriteLine("Ticket " + ticket.GetTicketId() + " is already resolved.");
                return;
            }
            if (string.IsNullOrWhiteSpace(reply))
            {
                Console.WriteLine("Reply cannot be empty. Ticket left unresolved.");
                return;
            }
            ticket.SetResponse(reply.Trim());
            ticket.SetStatus(TicketStatus.RESOLVED);
            Console.WriteLine("Ticket " + ticket.GetTicketId() + " resolved.");
        }

        // Finds a ticket by its ID, or returns null if it doesn't exist.
        public CustomerCare FindTicket(int ticketId)
        {
            foreach (CustomerCare ticket in tickets)
            {
                if (ticket.GetTicketId() == ticketId)
                {
                    return ticket;
                }
            }
            return null;
        }

        public List<CustomerCare> GetTickets() { return tickets; }
        public List<CustomerCare> Tickets { get { return tickets; } }
    }
}
