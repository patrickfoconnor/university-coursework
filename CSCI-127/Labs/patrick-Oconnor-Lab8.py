# -----------------------------------------------------
# CSCI 127, Lab 8
# March 12, 2020
# Patrick O'Connor
# -----------------------------------------------------


class MSUContact:


    def __init__(self, first_name, last_name, phone_number):
        self.first_name = first_name
        self.last_name = last_name
        self.phone_number = phone_number
        self.line_number = phone_number[-4:]
        self.titles = ""

# Set the first name, last name, and the line number

    def set_first_name(self, first_name):
        self.first_name = first_name

    def set_last_name(self, last_name):
        self.last_name = last_name

    def set_phone_number(self, phone_number):
        self.phone_number = phone_number

    def set_title(self, titles):
        self.titles = titles
        
    
    def set_line_number(self, titles):
        self.line_number = line_number

# Create a get to return the first name, last name, and line number
    
    def get_first_name(self):
        return self.first_name

    def get_last_name(self):
        return self.last_name

    def get_phone_number(self):
        return self.phone_number

    def get_title(self):
        return self.titles
    
    def get_line_number(self):
        return self.line_number

# Create the print entry function that prints first directory
    def print_entry(self):
        if self.first_name == ("Champ"):
            print((self.first_name + " " + self.last_name).ljust(28) + str(self.phone_number))
        else:
            print(str(self.titles + " " + self.first_name + " " + self.last_name).ljust(28) + str(self.phone_number))
    
# -----------------------------------------------------
# Do not change anything below this line
# -----------------------------------------------------

def print_directory(contacts):
    print("MSU Contacts:")
    print("----------------------------------------")
    for person in contacts:
        person.print_entry()
    print("----------------------------------------\n")

# -----------------------------------------------------

def main():
    
    prof_892 = MSUContact("Daniel", "DeFrance", "406-994-1624")
    mascot = MSUContact("MSU", "Bobcat", "406-994-0000")
    director_CS = MSUContact("John", "Paxton", "406-994-5979")
    president = MSUContact("Waded", "Cruzado", "406-994-CATS")
    
    contacts = [prof_892, mascot, director_CS, president]
    print_directory(contacts)

    mascot.set_first_name("Champ")
    
    prof_892.set_title("Instuctor")
    director_CS.set_title("Director")
    president.set_title("President")

    print_directory(contacts)
    
    contact = prof_892
    print(contact.get_first_name() + "'s MSU phone line is", \
          contact.get_line_number())

# -----------------------------------------------------

main()
