# ----------------------------------------------------+
# CSCI 127, Lab 10                                    |
# April 9, 2019                                       |
# Patrick O'Connor                                    |
# ----------------------------------------------------+

class Stack:
    def __init__(self, name):
        self.name = name
        self.number_items = []
        self.number_string = ""

    def __iadd__(self, num):
        self.number_items.append(num)
        return self.__str__()

    def push(self, num):
        self.number_items.append(num)
    
    def pop(self):
        popped_number = self.number_items[-1]
        self.number_items.pop(-1)
        return popped_number
    
    def is_empty(self):
        if len(self.number_items) == 0:
            return True

    def __str__(self):
        return("Contents: " + " ".join(str(item) for item in self.number_items ))

# -----------------------------------------------------

def main():
    numbers = Stack("Numbers")

    print("Push 1, 2, 3, 4, 5")
    print("---------------------")
    for number in range(1, 6):
        numbers.push(number)
        print(numbers)

    print("\nPop one item")
    print("----------------")
    numbers.pop()
    print(numbers)

    print("\nPop all items")
    print("---------------")
    while not numbers.is_empty():
        print("Item popped:", numbers.pop())
        print(numbers)

    # Push 10, 11, 12, 13, 14
    for number in range(10, 15):
        numbers.push(number)

    # Push 15
    numbers += 15 # See: https://www.python-course.eu/python3_magic_methods.php
    print("\nPushed: 10, 11, 12, 13, 14, 15")
    print("-------------------------------")
    print(numbers)

# -----------------------------------------------------

main()