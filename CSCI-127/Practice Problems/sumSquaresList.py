# Write a function sum_of_squares(xs) that 
# computes the sum of the squares of the numbers in the list xs.

def sum_of_squares(xs):
    total = 0
    for item in xs:
        item = item**2
        total += item

    return total



print(sum_of_squares([2, 3, 4]))