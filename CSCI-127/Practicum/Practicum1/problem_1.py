# Ask for cost

def cal_twent(cost):
    twenties_Counter = 0 
    while cost >= 20:
        if cost >= 20:
            cost -= 20
            twenties_Counter += 1
        else: 
            twenties_Counter += 1
            return twenties_Counter

def calc_twenties(cost):
    if cost <= 20:
        twenties = 1
        return twenties

    elif cost <= 40:
        twenties = 2
        return twenties
    
    elif cost <= 60:
        twenties = 3
        return twenties
    elif cost <= 80:
        twenties = 4
        return twenties
    elif cost <= 100:
        twenties = 5
        return twenties


def main():
    groceries_Cost = float(input("Enter the cost of groceries: "))
    
    twenties_Used = calc_twenties(groceries_Cost)
    twenties_Use = cal_twent(groceries_Cost)

    change = float(twenties_Used * 20) - groceries_Cost
    change_two = twenties_Use * 20) - groceries_Cost

    print("Pay with {} twenties, and get back {}".format(twenties_Used,change))
    print("Pay with {} twenties, and get back {}".format(twenties_Use,change_two))

main()