#Write a Python function that will take a the list of 100 random integers 
# between 0 and 1000 and return the maximum value.

import random

def randomInt():
    numberList = []
    for i in range(100):
        number = random.randint(0,1000)
        numberList.append(number)
    numberList.sort(reverse=True)
    print(numberList)
    return numberList[-1]

# If you want to use reverse = True then select the 0 position in return statement
    

print(randomInt())