import string

# --------------------------------------+
# CSCI 127, Joy and Beauty of Data      |
# Program 4: Pokedex                    |
# Your Name(, Your Partner's Name)      |
# Last Modified: April 3rd, 2019        |
# --------------------------------------+
# Create a program to look at a small   |
# sample of pokemon.                    |
# --------------------------------------+

class Pokemon:
    
    def __init__(self, name, number, combat_points, types):
        self.name = name.capitalize()
        self.number = number
        self.combat_points = combat_points
        self.types = types

    def get_number(self):
        return self.number

    def get_name(self):
        return self.name

    def get_combat_points(self):
        return self.combat_points

    def get_types(self):
        return self.types

            
    def __str__(self):
        return ("Number: {self.number}, ".format(self = self) + "Name: {self.name}, ".format(self = self) + "CP: {self.combat_points}, ".format(self = self) + "Type: " + " and ".join(map(str, self.types)))


def print_menu():
    print("1. Print Pokedex")
    print("2. Print Pokemon by Name")
    print("3. Print Pokemon by Number")
    print("4. Print Pokemon with Type")
    print("5. Print Average Pokemon Combat Points")
    print("6. Print Pokedex")


def print_pokedex(pokedex):
    print("The Pokedex \n-----------")
    for pokemon_object in pokedex:
        print(pokemon_object.__str__())
        
            
# Look up a pokemon by name and print its as below:
def lookup_by_name(pokedex, input_name):
    # Set up counter to have a fail safe for when input name is not present
    count = 0
    input_name = input_name.capitalize()
    # Iterate through the pokedex checking for the match for input number
    for pokemon_single in pokedex:
        pokemon_name = pokemon_single.get_name()
        if pokemon_name == input_name:
            count += 1
            print(pokemon_single.__str__())
    # if count is still 0 then print that there is no pokemon named that
    if count == 0:
        print("There is no Pokemon named " + input_name)

        
# Look up a pokemon by their associated numbers.
def lookup_by_number(pokedex, number_input):
    # Set up counter to have a fail safe for when input number is not present
    count = 0

    # Iterate through the pokedex checking for the match for input number
    for pokemon in pokedex:
        if number_input == pokemon.get_number() and number_input < 31:
            count += 1
            print(pokemon.__str__())
    # Since the last pokemon has a number of 810 create a different if statement to find it
        if number_input == pokemon.get_number() and number_input == 810:
            count += 1
            print(pokemon.__str__())
    # if count is still 0 then print that there is no pokemon named that
    if count == 0:
        print("There is no Pokemon number " + str(number_input))


# Iterate through and add to a counter if the type matches the inputted type
def total_by_type(pokedex, pokemon_type):
    type_counter = 0
    for pokemon in pokedex:
        specific_types = pokemon.get_types()
        if pokemon_type in specific_types:
            type_counter += 1
    print("Number of Pokemon that contain type " + pokemon_type + " = " + str(type_counter)) 


# Add the hit points and then average based on largest number associated to a pokemon
def average_hit_points(pokedex):
    total_hitpoints = 0
    total_pokemon = 0

    for pokemon in pokedex:
        specific_hitpoints = pokemon.get_combat_points()
        total_hitpoints += specific_hitpoints
        total_pokemon += 1
    # Find the average hitpoints by taking total and dividing by the amount of iterations
    average_hit_points = total_hitpoints / total_pokemon

    print("Average Pokemon combat points = {:0.2f}".format(average_hit_points))


# ---------------------------------------
# Do not change anything below this line
# ---------------------------------------



def create_pokedex(filename):
    pokedex = []
    file = open(filename, "r")
    
    for pokemon in file:
        pokelist = pokemon.strip().split(",")
        number = int(pokelist[0])               # number
        name = pokelist[1]                      # name
        combat_points = int(pokelist[2])        # hit points
        types = []
        for position in range(3, len(pokelist)):
            types += [pokelist[position]]       # type
        pokedex += [Pokemon(name, number, combat_points, types)]

    file.close()
    return pokedex

# ---------------------------------------

def get_choice(low, high, message):
    legal_choice = False
    while not legal_choice:
        legal_choice = True
        answer = input(message)
        for character in answer:
            if character not in string.digits:
                legal_choice = False
                print("That is not a number, try again.")
                break 
        if legal_choice:
            answer = int(answer)
            if (answer < low) or (answer > high):
                legal_choice = False
                print("That is not a valid choice, try again.")
    return answer

# ---------------------------------------

def main():
    pokedex = create_pokedex("pokedex.txt")
    choice = 0
    while choice != 6:
        print_menu()
        choice = get_choice(1, 6, "Enter a menu option: ")
        if choice == 1:    
            print_pokedex(pokedex)
        elif choice == 2:
            name = input("Enter a Pokemon name: ").lower()
            lookup_by_name(pokedex, name)
        elif choice == 3:
            number = get_choice(1, 1000, "Enter a Pokemon number: ")
            lookup_by_number(pokedex, number)
        elif choice == 4:
            pokemon_type = input("Enter a Pokemon type: ").lower()
            total_by_type(pokedex, pokemon_type)
        elif choice == 5:
            average_hit_points(pokedex)
        elif choice == 6:
            print("Thank you.  Goodbye!")
        print()

# ---------------------------------------

main()