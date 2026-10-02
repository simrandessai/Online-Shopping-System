from enum import Enum, auto


class TicketStatus(Enum):
    """Tracks where a customer care ticket is in its lifecycle."""
    OPEN = auto()
    IN_PROGRESS = auto()
    RESOLVED = auto()

    def __str__(self):
        return self.name
