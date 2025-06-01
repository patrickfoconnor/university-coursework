class Pet:

    def __init__(self, n, a, s): # instance variables
        self.name = n
        self.age = a
        self.is_sleeping = s
# Methods 
    def set_name(self, n):
        self.name = n

    def set_age(self, a):
        self.age = a

    def set_is_sleeping(self, s):
        self.is_sleeping = s

    def __str__(self):
        return self.name + " is " + str(self.age) + " years old."


class Dog(Pet):

    def __init__(self, name, age, sleep, b):
        Pet.__init__(self, name, age, sleep)
        self.breed = b

    def brush(self):
        print("Arf! Thanks for the dog brushing!")

class Fish(Pet):

    def __init__(self, name, age, sleep, sw):
        Pet.__init__(self, name, age, sleep)
        self.salt_water = sw

    def change_filter(self):
        print("Glug! Thanks for changing the filter.")

class Rock(Pet):

    def __init__(self, name, age, sleep, c):
        Pet.__init__(self, name, age, sleep)
        self.color = c

    def set_paint(self, c):
        self.color = c

    def __str__(self):
        return Pet.__str__(self) + "It's " + self.color

def main():
    my_pet = Pet("Dan's Pet", 0, False)
    print(my_pet)

    my_dog = Dog("Chuki", 2, True, "Giant Chiuaua")
    print(my_dog)
    my_dog.brush()

    #my_pet.brush()

    my_fish = Fish("Steve", 14, True, False)
    print(my_fish)
    my_fish.change_filter()

    my_rock = Rock("Rocky", 1000000000, True, "gray")
    print(my_rock)
    my_rock.set_paint("red")
    print(my_rock)

main()
            
