# Question #1
#def count_lines(file):
#    file = open(file, "r")
#    line_count = 0

 #   for line in file:
 #       line_count += 1

#    return line_count

#file_name = "question_one.txt"
#how_many = count_lines(file_name)
#print("There are " + str(how_many) + " lines in the file " + file_name)
#-----------------------------------------------------------------------
# Question #2

class Vehicle:
    def __init__(self,type_of_body):
        self.type_of_body = type_of_body
        self.runs_on = "gas"

    def __str__(self):
        return ("A "+ self.type_of_body + " runs on " + self.runs_on)

class Bicycle(Vehicle):
    def __init__(self):
        Vehicle.__init__(self, "Bicycle") 
        self.runs_on = "pedal power"




def main():
    car = Vehicle("Car")
    print(car)
    truck = Vehicle("Truck")
    print(truck)
    bicylce = Bicycle()
    print(bicylce)

main()

#-----------------------------------------------------------------------