# ------------------------------------------------+
# CSCI 127, Lab 11                                |
# April 16, 2020                                  |
# Patrick OConnnor                                |
# ------------------------------------------------+

import numpy as np
import csv
import matplotlib.pyplot as plt


def read_file(file_name):
    infile = open(file_name, "r")
# Skip past the first line that has a single value of 7 as 
# it is not incorporated into graph
    first = infile.readline()
    first = first[:-1]
# Create empty list for appending that happens in lines (23-30)
    college_names = []
    college_enrollments = []
    stripped_enrollments = []
# Go through each of the following lines adding names of college and the enrollment information
    for line in infile:
        cells = line.split(",")
        college_names.append(cells[0])
        college_enrollments.append(cells[1])
# Remove the new line char from each of the enrollment strings
    for item in college_enrollments:
        strip = item.rstrip("\n")
        stripped_enrollments.append(int(strip))

# Convert list to array
    college_names_array = np.array(college_names)
    college_enrollments_array = np.array(stripped_enrollments)
# Close file and return the names and enrollments arrays
    infile.close()
    return college_names_array, college_enrollments_array

# -------------------------------------------------


def main(file_name):
    college_names, college_enrollments = read_file(file_name)
# Insert function to create 
# Create a list of labels for y-axis
    y_labels = [1000, 2000, 3000, 4000, 5000]
# Plot the bar graph with names on x-axis and enrollments on y
# Assign every other bar to be a different color by listing two options
    plt.bar(college_names, college_enrollments, color = ["gold", "dodgerblue"])
# Assign the graph to go up by 1000 and end at 5000
    plt.yticks(y_labels)
# Set the labels and title according to information
    plt.xlabel("College", fontsize = 10)
    plt.ylabel("Enrollment", fontsize = 10)
    plt.title("Spring 2020", fontsize = 13)
# Set the Figure title to MSU Enrollments
    fig = plt.gcf()
    fig.canvas.set_window_title("MSU Enrollments")
# Show the created graph
    plt.show()
    
# -------------------------------------------------

main("spring-2020.csv")

