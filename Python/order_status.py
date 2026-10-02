from enum import Enum, auto


class OrderStatus(Enum):
    PENDING = auto()
    CONFIRMED = auto()
    SHIPPED = auto()
    OUT_FOR_DELIVERY = auto()
    DELIVERED = auto()
    CANCELLED = auto()

    def __str__(self):
        return self.name
