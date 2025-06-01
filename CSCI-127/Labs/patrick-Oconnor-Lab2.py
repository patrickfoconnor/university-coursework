# ---------------------------------------
# CSCI 127, Joy and Beauty of Data
# Lab 2: Season Checker
# Patrick O'Connor
# Last Modified: January 30, 2020 
# ---------------------------------------
# Create a function that identifies the
# season or if the input is invalid.
# ---------------------------------------


# TODO: write a function called season_checker that returns
#  a string: Spring, Summer, Winter, Fall, or Invalid

def main():
    user_month = input("Enter month: ")
    user_day = int(input("Enter day: "))
    season = season_checker(user_month, user_day) 
    
    if season == ("false"):
        print("That is not a valid date.")
    else:
        print("That's a " + season + " Day.")


    # TODO: output the season for the user in a print statement,
    # or tell them if they input an invalid date

def season_checker(month, day):
# Create a season checker for Spring, Summer, Fall, Winter   
    if (month == ("March") and day > 19) or month == ("April") or month == ("May") or (month == ("June") and day < 21):
        season = ("Spring")
        return season

    elif (month == ("June") and day > 20) or month == ("July") or month == ("August") or (month == ("September") and day < 22):
        season = ("Summer")
        return season 

    elif month == ("October") or month == ("November") or (month == ("December") and day < 21):
        season = ("Fall")
        return season

    elif (month == ("December") and day > 20) or month == ("January") or month == ("Febuary") or (month == ("March") and day < 20):
        season = ("Winter")
        return season
    else:
        season = ("false")
        return season

main()
