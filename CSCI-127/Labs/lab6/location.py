import csv



def earthquake_locations(earthquake_records):
    
    earthquake_records.sort()
    #print(earthquake_records)
    locations = []
    for state in earthquake_records:
        if locations.count(state) == 0:
            locations.append(state)
    locations.remove("name")        
    print(locations) 
        

def main():
    with open("earthquakes.csv", "r") as myfile:
        mag_list = []
        location_list = []
        reader = csv.reader(myfile)
        data = list(reader)
    
        #i.remove("'")
        for line in data:
            location_list.append(line[-5])
        


        for line in data:
            mag_list.append(line[-8])

        earthquake_locations(location_list)

main()