# Although Python provides us with many list methods, 
# it is good practice and very instructive to think about how they are implemented. 
# Implement a Python function that works like the following:
import random


#count
def count(list_Items):
    list_Length = len(list_Items)
    count_Patrick = list_Items.count("Patrick")
    return list_Length, count_Patrick
#in
def inTo(list_Items):
    pass
    #item list_Items.in[1:]
    #return list_Items
#reverse
def rev_list(list_Items):
    pass
    #list_Items.sort(reverse = True)
    #return list_Items
#index
def index_list_Items(list_Items):
    list_Items.index[1:]
    return list_Items

#insert
def insert_Items(list_Items):
    word = "Catherine"
    list_Items.insert([0] word)

def randomInt():
    numberList = []
    for i in range(10):
        number = random.randint(0,10)
        numberList.append(number)
    return numberList

def main():
    list = ["jill", "alex", "Patrick", "George"]
    numbers = randomInt()
    print(count(list))
    print(inTo(list))
    print(rev_list(numbers))
    print(index_list_Items(list))
    print(insert_Items(list))

main()

