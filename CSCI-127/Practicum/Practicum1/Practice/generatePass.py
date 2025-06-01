# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 127, Practicum #1                   |
# Last Updated: 02, 20, 2020               |
# -----------------------------------------|
# Create a program that takes input of     |
# users password length and                |
# returns a random password to use         |
# -----------------------------------------+

import random
import string

def generate_password(length):
    password = []
    passwordString = ""
    for num in range(length):
        char = random.choice(string.ascii_letters)
        password.append(char)
# For each element in password add it to the string version
    for element in password:
        passwordString += element
    #print(password)
    return passwordString

def gen_password(length):
    password = ""
    for i in range(length):
        password += random.choice(string.ascii_letters)


    return password

# Create a 5 char password
print(generate_password(5))
print(generate_password(5))
# Class solution
print(gen_password(5))
print(gen_password(5))
# Create a 10 char password
print(generate_password(10))
print(generate_password(10))
# Class solution
print(gen_password(10))
print(gen_password(10))

