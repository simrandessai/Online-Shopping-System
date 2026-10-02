from enum import Enum, auto


class Role(Enum):
    ADMIN = auto()
    SELLER = auto()
    BUYER = auto()

    def __str__(self):
        return self.name
