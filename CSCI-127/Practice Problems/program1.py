def userInput():
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
    salesTax = float(input("Enter sales tax (%): "))
    calculations(salesTax)


def calculations(tax):
# Add the tax to the total of items inputted above
    decimalTax = float(1 + (salesTax / 100))
    total = decimalTax * totalCost
    getAverage(total)


def getAverage():
# Find the average cost per item
    averagecost = total / itemTotal
    print("total cost (with tax): " + str(total))
    print("total number of items: " + str(itemTotal))
    print("average cost per item is {:.2f}".format(averagecost))
    item = averagecost


def get_rating(item, lo, hi):
    if item < lo:
        print("That store seems cheap")
    if item > hi:
        print("That store seems expensive")
    else:
        print("That store seems typical")  


def main():
    userInput()

main()