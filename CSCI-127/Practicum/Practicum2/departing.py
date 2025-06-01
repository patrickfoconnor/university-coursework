
def departing(flight_dictionary):
# Set up for loop and look at each item in dictionary
    for location, information in flight_dictionary.items():
# If the information is == to departing then return the location of flight
        if information == ("Departing"):
            print(location)
        

def main():
    flights = {"BZN": "On Time", "SEA": "Delayed", "PDX":"Departing", "SLC":"Arriving", "DEN":"Departing"}
    departing(flights)
    
main()

