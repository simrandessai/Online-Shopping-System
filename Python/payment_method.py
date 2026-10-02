from enum import Enum, auto


class PaymentMethod(Enum):
    CREDIT_CARD = auto()
    DEBIT_CARD = auto()
    UPI = auto()
    NET_BANKING = auto()
    CASH_ON_DELIVERY = auto()
    WALLET = auto()

    def __str__(self):
        return self.name
