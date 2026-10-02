"""
An Admin user in the system. They can manage users and products,
and handle customer care tickets raised by buyers.
"""
from role import Role
from ticket_status import TicketStatus
from user import User


class Admin(User):

    def __init__(self, user_id, name, email, password, phone, address):
        super().__init__(user_id, name, email, password, phone, address, Role.ADMIN)
        # Tickets received from buyers, waiting to be handled.
        self.tickets = []

    # User Management
    def manage_users(self):
        print("Managing Users...")

    # Product Management
    def manage_products(self):
        print("Managing Products...")

    # Removes a product from its seller and its category.
    # (The caller is responsible for removing it from the global product list.)
    def remove_product(self, product):
        if product is None:
            print("Product not found.")
            return
        if product.get_seller() is not None:
            product.get_seller().remove_product(product)
        if product.get_category() is not None:
            product.get_category().remove_product(product)
        print(f"{product.get_product_name()} removed from the system.")

    # Customer Care Ticket Handling

    # Receives a ticket raised by a buyer.
    def receive_ticket(self, ticket):
        if ticket is not None:
            self.tickets.append(ticket)
            print(f"Ticket {ticket.get_ticket_id()} received by {self.name}.")

    # Opens a ticket to read it. An OPEN ticket moves to IN_PROGRESS once viewed.
    def view_ticket(self, ticket):
        if ticket is None:
            print("Ticket not found.")
            return
        if ticket.get_status() == TicketStatus.OPEN:
            ticket.set_status(TicketStatus.IN_PROGRESS)
        print(ticket)

    # Lists a one-line summary of every ticket.
    def view_all_tickets(self):
        print("\n SUPPORT TICKETS ")
        if not self.tickets:
            print("No support tickets.")
            return
        print(f"{'ID':<6} {'Buyer':<12} {'Status':<12} Issue")
        print("-" * 75)
        for ticket in self.tickets:
            print(ticket.to_summary())

    # Replies to a ticket and marks it as RESOLVED.
    def resolve_ticket(self, ticket, reply):
        if ticket is None:
            print("Ticket not found.")
            return
        if ticket.is_resolved():
            print(f"Ticket {ticket.get_ticket_id()} is already resolved.")
            return
        if reply is None or not reply.strip():
            print("Reply cannot be empty. Ticket left unresolved.")
            return
        ticket.set_response(reply.strip())
        ticket.set_status(TicketStatus.RESOLVED)
        print(f"Ticket {ticket.get_ticket_id()} resolved.")

    # Finds a ticket by its ID, or returns None if it doesn't exist.
    def find_ticket(self, ticket_id):
        for ticket in self.tickets:
            if ticket.get_ticket_id() == ticket_id:
                return ticket
        return None

    def get_tickets(self):
        return self.tickets
