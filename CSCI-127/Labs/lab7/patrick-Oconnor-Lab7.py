# -------------------------------------+
# CSCI 127, Lab 7                      |
# March 5, 2020                        |
# Patrick O'Connor                     |
# -------------------------------------+

# Comment regarding why comma character and 
# the space character are treated differently in ascii-codes.csv
# The comma is a way to seperate values while keeping them together in key: value form when 
# changing the csv file to a dictionary. While the space is use to let python know that 
# a new item is to be created in the dictionary


# Import Necessary modules
import csv

# ----------------------------------------------------------------------------
def translate(sentence_Input, bionary_Dictionary, file_Name):
    output_file = open(file_Name, "w")
# Go through each char in the sentence
    for char in sentence_Input:
# Check to see if the char is in dictionary else move on to UNDEFINED
        if char in bionary_Dictionary.keys():
# Go through each of the items and find the corresponding items
            for char_Binary, binary in bionary_Dictionary.items():
                if char == char_Binary:
                    binary_code = binary
# Output / write the converted info to new file
                    output_file.write(binary_code + "	" + char + "\n")
# Return UNDEFINED for char not found in ascii list
        else:
            binary = "UNDEFINED"
            binary_code = binary
# Output / write the converted info to new file
            output_file.write(binary_code + "  " + char + "\n")
    output_file.close() 

# ----------------------------------------------------------------------------
def create_dictionary(f): # define the function
    # open the file for reading and assign to a file handle variable*
    with open(f, "r") as myfile:
        reader = csv.reader(myfile)
 # make the line into a list, splitting it up at the commas
        ascii_binaray_Tuples = list(reader)

# Create a dictionary from the tuples created on line 38
        binary_to_ascii_Dict = dict(ascii_binaray_Tuples)

# fix the comma, space, and quote mark
        for binary, char in binary_to_ascii_Dict.items():
            if char == "space":
                binary_to_ascii_Dict[binary] = " "
            if char == "comma":
                binary_to_ascii_Dict[binary] = ","
            if char == "quote":
                binary_to_ascii_Dict[binary] = "\""

# Convert the list from binary : ascii values to ascii keys : binary values
        ascii_to_binary_Dict = dict([(value, key) for key, value in binary_to_ascii_Dict.items()])

# return the dictionary to where it was called from
        return ascii_to_binary_Dict
    myfile.close


# ----------------------------------------------------------------------------
def main():
    dictionary = create_dictionary("ascii-codes.csv")
    sentence = "A long time ago in a galaxy far, far away..."
    translate(sentence, dictionary, "output-1.txt")
    sentence = "Montana State University (406) 994-0211"
    translate(sentence, dictionary, "output-2.txt")
    sentence = "“True friends stab you in the front.” —Wilde"
    translate(sentence, dictionary, "output-3.txt")

# ----------------------------------------------------------------------------

main()
