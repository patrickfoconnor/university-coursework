# -------------------------------------+
# CSCI 127, Lab 6                      |
# February 27, 2020                    |
# Patrick O'Connor                     |
# -------------------------------------+
import csv

def average_magnitude(earthquake_records):
# Calculate the sum of magnitudes
    sum_mag = sum(earthquake_records)
# Divide by length of list
    denom = len(earthquake_records)
# Find average 
    average_mag = sum_mag / denom

    return average_mag


def earthquake_locations(earthquake_records):
# Sort the earthquakes alphabetically
    earthquake_records.sort()
# Create a list of locations only including the location once once
    locations = []
    for place in earthquake_records:
        if locations.count(place) == 0:
            locations.append(place)
    locations.remove("name")        
    return locations



def count_earthquakes(file, lower, upper):
# Sort the earthquakes based on magnitudes 
    file.sort()
# Start a counter and if the mag is between upper and lower 
# Count goes up one
    counter = 0
    for mag in file:
        if mag >= lower and mag <= upper:
            counter += 1
    return counter




# --------------------------------------

def main(file_name):
    
    with open(file_name, "r") as myfile:
        mag_list = []
        location_list = []
        reader = csv.reader(myfile)
        data = list(reader)
# Create a list with only the magnitudes
        for line in data[1:]:
            mag_list.append(float(line[-8]))
        magnitude = average_magnitude(mag_list)
        print("The average earthquake magnitude is {:.2f}\n".format(magnitude))

# Create a list with only the locations
        for line in data:
            location_list.append(line[-5])
        single_Locations = earthquake_locations(location_list)
# Print according to the example code
        print("Alphabetical Order of Earthquake Locations")
        print("------------------------------------------")
        for number, letter in enumerate(single_Locations, start=1):
            print(str(number) + ". " + letter)
    
        lower_bound = float(input("Enter a lower bound for the magnitude: "))
        upper_bound = float(input("Enter an upper bound for the magnitude: "))
        how_many = count_earthquakes(mag_list, lower_bound, upper_bound)
        print("Number of recorded earthquakes between {:.2f} and {:.2f} = {:d}".format(lower_bound, upper_bound, how_many))
    
# --------------------------------------

main("earthquakes.csv")

