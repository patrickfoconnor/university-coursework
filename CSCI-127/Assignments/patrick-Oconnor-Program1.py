# -----------------------------------------+
# Patrick O'Connor                         |
# CSCI 127, Program #1: Reciept Cruncher   |
# Last Updated: January 27 , 2019          |
#------------------------------------------|
# Generalize a grocery store into one of   |
# three categories: typical, cheap, or     |
# expensive. Go through each line of a     |
# receipt, and calculate the average cost  |
# of a single item including tax.          |
# -----------------------------------------|
# Cheap Average Cost:    Less than $3      |
# Typical Average Cost:  $3 to $5          |
# Expensive Average Cost:More than $5      |
# -----------------------------------------|
# Low Tax      : tax < 2%                  |      
# Average Tax  : tax > 2% or < 5%          |
# High Tax     : tax > 5%                  |
#------------------------------------------+


def calculations():
# Establish how many entries that the user needs to input
    howMany = input("How many entries are listed on the reciept? ")

# Create a running total for the entries to be added to
    itemTotal = 0
    totalCost = 0

# Using a for loop have user input appropriate info
    for n in range(1, int(howMany) + 1):
        howManyItems = (input("Enter number of items in entry " + str(n) + ": "))
        itemTotal += int(howManyItems)
        totalItemCost = (input("Enter that entry's total cost: "))
        totalCost += float(totalItemCost)
        costPerItem = float(totalItemCost) / float(howManyItems)
        print("Cost per item of entry " + str(n) + " is: {:.2f}".format(costPerItem))

# Add the tax to the total of items inputted above
    salesTax = float(input("Enter sales tax (%): "))
    decimalTax = float(1 + (salesTax / 100))
    total = decimalTax * totalCost
    
# Find the average cost per item
    averagecost = total / itemTotal
    print("total cost (with tax): {:.2f}".format(total))
    print("total number of items: " + str(itemTotal))
    print("average cost per item is {:.2f}".format(averagecost))

# Set low and high levels for rating
    lowprice = 3
    highprice = 5

    get_rating(averagecost,lowprice, highprice, salesTax)


# Decide whether the item is priced "cheap", "expensive",
#  or "typical"
def get_rating(item, lo, hi, tax):
# Establish low and high tax
    lowTax = 2
    highTax = 5
    if item < lo:
        if tax < lowTax:
            print("cheap, but it helps that the sales tax is " + str(tax) + "% tax!")
        else:
            print("That store seems cheap")
    elif item > hi:
        if tax > highTax:
            print("That store seems expensive, but you could blame that on the " + str(tax) + "% tax.")
        else:  
            print("That store seems expensive")
        
    else:
        print("That store seems typical")        


def main():
    calculations()

main()

