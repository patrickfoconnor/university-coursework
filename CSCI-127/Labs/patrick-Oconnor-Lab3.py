# ------------------------------------------+
# CSCI 127, Lab 3                           |
# February 06, 2020                         |
# Patrick O'Connor                          |
# ------------------------------------------+
# Calculate how many bilabial consonenents  |
# are in a sentence using three techniques. |
# ------------------------------------------+

#Rewrite the body of the count_built_in function 
# to calculate and return the number of bilabials 
# (b, m, p)  in the parameter sentence using the 
# built-in count method.
def count_built_in(sentence):
    b = (sentence.count("b"))
    m = (sentence.count("m"))
    p = (sentence.count("p"))
    
# Add the total count together
    total = b + m + p
    return total
#-------------------------------------------------------
#Rewrite the body of the count_iterative function to 
# count the number of bilabials in sentence using a loop.
def count_iterative(sentence):
# Start count at 0 in order to have a runnining tally
    count = 0
# Using a for loop for to look at each character 
# individually adding 1 for every b, m, p
    for i in sentence: 
        if i == 'b': 
            count += 1
        elif i == "m":
            count += 1
        elif i == "p":
            count += 1
        else:
            count += 0
    return count

#-------------------------------------------------------
#Rewrite the body of the count_recursive function to 
# count the number of bilabials in sentence using 
# recursion.
def count_recursive(sentence):
    
    s_length = len(sentence) 
    if s_length == 0:
        return 0
    elif sentence[0] == 'b':
        return 1 + count_recursive(sentence[1:])
    elif sentence[0] == 'm':
        return 1 + count_recursive(sentence[1:])
    elif sentence[0] == 'p':
        return 1 + count_recursive(sentence[1:])
    else:
        return count_recursive(sentence[1:])

#-------------------------------------------------------

def main():
    answer = "yes"
    while (answer == "yes") or (answer == "y"):
        sentence = input("Please enter a sentence: ")
        sentence = sentence.lower()
        print()
        print("Counting bilabial consonents  using ...")
        print("---------------------------------------")
        print("Built-in function =", count_built_in(sentence))
        print("Iteration =", count_iterative(sentence))
        print("Recursion =", count_recursive(sentence))
        print()
        answer = input("Would you like to continue: ").lower()
        print()


main()
