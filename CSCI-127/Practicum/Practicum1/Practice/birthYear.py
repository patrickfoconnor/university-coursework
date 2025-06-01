# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 127, Practicum #1                   |
# Last Updated: 02, 20, 2020               |
# -----------------------------------------|
# Create a program that takes input of     |
# users birthday and prints the current age|
# of the user                              |
# -----------------------------------------+

def main():
    birthYear = int(input("Enter your birth year: "))
    age = 2020 - birthYear

    print("Happy Birthday! You are " + str(age) + " years old")

main()

