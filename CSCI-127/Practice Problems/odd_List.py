# Write a function to count how many odd numbers are in a list.

def create_List():
    numberList = []
    for num in range(1,101):
        numberList.append(num)
    return numberList

def is_Odd(listItems):
    odd_List = []
    for num in listItems:
        if num % 2 != 0:
            odd_List.append(num)
    return odd_List
        

def sum_Of_Even(listItems):
    sumOfEvens = 0
    for num in listItems:
        if num % 2 == 0:
            sumOfEvens += num

    return sumOfEvens

def main():
    num_List = create_List()

    print(is_Odd(num_List))
    print(sum_Of_Even(num_List))

main()
