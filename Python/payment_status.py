from enum import Enum, auto


class PaymentStatus(Enum):
    PENDING = auto()
    SUCCESSFUL = auto()
    FAILED = auto()
    REFUNDED = auto()

    def __str__(self):
        return self.name
