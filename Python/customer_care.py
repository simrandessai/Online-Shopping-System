"""
Represents a customer care (support) ticket. A buyer raises a ticket
describing an issue, and an admin handles it by replying and resolving it.
"""
from datetime import date

from ticket_status import TicketStatus


def _truncate(text, max_length):
    if text is None:
        return ""
    if len(text) <= max_length:
        return text
    return text[:max_length - 3] + "..."


class CustomerCare:

    # Static ID Generator
    _next_ticket_id = 3001

    # Constructor: every new ticket starts as OPEN with no response.
    def __init__(self, issue, raised_by):
        if issue is None or not issue.strip():
            raise ValueError("A ticket needs an issue description.")
        if raised_by is None:
            raise ValueError("A ticket must be raised by a user.")
        self.ticket_id = CustomerCare._next_ticket_id
        CustomerCare._next_ticket_id += 1
        self.issue = issue.strip()
        self.raised_by = raised_by
        self.raised_date = date.today()
        self.response = ""
        self.status = TicketStatus.OPEN

    # Setters
    def set_status(self, status):
        if status is not None:
            self.status = status

    def set_response(self, reply):
        self.response = "" if reply is None else reply

    # Getters
    def get_ticket_id(self):
        return self.ticket_id

    def get_issue(self):
        return self.issue

    def get_response(self):
        return self.response

    def get_status(self):
        return self.status

    def get_raised_by(self):
        return self.raised_by

    def get_raised_date(self):
        return self.raised_date

    def is_resolved(self):
        return self.status == TicketStatus.RESOLVED

    # One-line summary used when listing many tickets.
    def to_summary(self):
        return (f"{self.ticket_id:<6} "
                f"{_truncate(self.raised_by.get_name(), 12):<12} "
                f"{str(self.status):<12} "
                f"{_truncate(self.issue, 40)}")

    def __str__(self):
        return ("\nTICKET"
                f"\nTicket ID : {self.ticket_id}"
                f"\nRaised By : {self.raised_by.get_name()}"
                f"\nDate      : {self.raised_date}"
                f"\nIssue     : {self.issue}"
                f"\nStatus    : {self.status}"
                f"\nResponse  : {'Awaiting response' if not self.response else self.response}")
