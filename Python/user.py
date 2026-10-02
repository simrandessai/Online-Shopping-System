"""
This is the User class, which represents a user in the online shopping
system. It contains details such as user ID, name, email, password,
phone number, address.
"""


class User:

    def __init__(self, user_id=0, name=None, email=None, password=None,
                 phone=None, address=None, role=None):
        self.user_id = user_id
        self.name = name
        self.email = email
        self.password = password
        self.phone = phone
        self.address = address
        self.role = role
        self.signed_in = False

    # Register
    def register(self):
        print(f"{self.name} registered successfully as "
              f"{self.role if self.role is not None else 'USER'}.")

    # Login
    def login(self):
        self.signed_in = True
        print(f"{self.name} logged in.")

    # Logout
    def logout(self):
        self.signed_in = False
        print(f"{self.name} logged out.")

    # Checks whether the user is currently signed in.
    def is_signed_in(self):
        return self.signed_in

    # Update Profile
    def update_profile(self, phone, address):
        self.phone = phone
        self.address = address

    # Getters & Setters
    def get_user_id(self):
        return self.user_id

    def set_user_id(self, user_id):
        self.user_id = user_id

    def get_name(self):
        return self.name

    def set_name(self, name):
        self.name = name

    def get_email(self):
        return self.email

    def set_email(self, email):
        self.email = email

    def get_password(self):
        return self.password

    def set_password(self, password):
        self.password = password

    def get_phone(self):
        return self.phone

    def set_phone(self, phone):
        self.phone = phone

    def get_address(self):
        return self.address

    def set_address(self, address):
        self.address = address

    def get_role(self):
        return self.role

    def set_role(self, role):
        self.role = role

    def __str__(self):
        return (f"User ID : {self.user_id}"
                f"\nName : {self.name}"
                f"\nEmail : {self.email}"
                f"\nPhone : {self.phone}"
                f"\nAddress : {self.address}"
                f"\nRole : {self.role if self.role is not None else 'N/A'}")
