# -----------------------------------------+
# CSCI 127, Joy and Beauty of Data         |
# Program 3: Classics CSV Library          |
# Patrick O'Connor                         |
# Last Modified: 3, 13, 2020               |
# -----------------------------------------+
# Using a csv file with book information:  |
# Check for most popular book              |
# Find the books in fk rating              |
# Find books by an author                  |
# Find books from your birthyear           |
# -----------------------------------------+
    
# -----------------------------------------+
# Option #1                                |
# -----------------------------------------+
# Identify the most popular book download. |
# -----------------------------------------+
import csv


# Look at metadata.download 
def most_popular(file):
    
    infile = open(file, "r", encoding="latin-1")
    first = infile.readline()
    first = first[:-1]

    titles = []
    downloads = []
    for line in infile:
        cells = line.split(",")
        titles.append(cells[3])
        downloads.append(cells[5])
    book_and_downloads = [(downloads[i],titles[i]) for i in range(0, len(titles))]
# Change the downloads from strings to ints
    for i in range(0, len(downloads)): 
        downloads[i] = int(downloads[i]) 

    sorted_Downloads = sorted(downloads, reverse=True)
    max_Downloads = sorted_Downloads[0]
    max_Downloads = str(max_Downloads)
# Go through each item in compiled books and downloads list searching for the download that 
# matches the most downloads. If it does assign the title to most_Popular_Book
    for item in book_and_downloads:
        if item[0] == max_Downloads:
            most_Popular_Book = item[1]

# Print out the book with the most downloads
    print("The most popular download is {}".format(most_Popular_Book))


# -----------------------------------------+
# Option #2                                |
# -----------------------------------------+
# List a range of books by difficulty      |
# rating on the Flesch-Kincaid Grade Level |
# Formula                                  |
# -----------------------------------------+

def fk_difficulty_range(file,bottom_Parameter ,top_Parameter):
    infile = open(file, "r", encoding="latin-1")
    first = infile.readline()
    first = first[:-1]
# Create two lists then join the list into one list with tuples
    flesch_Kinaid_Grading = []
    book_Title = []
# List one will store the FK Grade, List two will store the title
    for line in infile:
        cells = line.split(",")
        flesch_Kinaid_Grading.append(cells[23])
        book_Title.append(cells[3])
    for i in range(0, len(flesch_Kinaid_Grading)): 
        flesch_Kinaid_Grading[i] = float(flesch_Kinaid_Grading[i])
# Join the two list into list of tulple
    grading_and_Title = [(book_Title[i], flesch_Kinaid_Grading[i]) for i in range(0, len(book_Title))]
# Sort the list of tuples based on Alphabet
    grading_and_Title.sort(key = two_Element_Sort_First)

# Sort the list of tuples based on FK Grading    
    grading_and_Title.sort(key = two_Element_Sort_Second)

# Establish the print range and book number
    bottom_Parameter = int(bottom_Parameter)
    top_Parameter = int(top_Parameter)
    print_Range = (1 + top_Parameter) - bottom_Parameter

# Figure out how to print the right books from the list
    for i in range(print_Range):
        title = grading_and_Title[bottom_Parameter+i][0]
        grade = grading_and_Title[bottom_Parameter+i][1]
        book_Number = (bottom_Parameter+i)
        print("{} - {} - Grade Level: {}".format(book_Number, title, grade))


# -----------------------------------------+
# Option #3                                |
# -----------------------------------------+
# Identify all books by certain author.    |
# -----------------------------------------+

def all_books_by_author(file, input_author):
    infile = open(file, "r", encoding="latin-1")
    first = infile.readline()
    first = first[:-1]
    
    authors_book = []
    book_Title = []

    for line in infile:
        cells = line.split(",")
        authors_book.append(cells[11])
        book_Title.append(cells[3])
# Make the input into a title
    input_author = input_author.title()

    author_collection = []
    author_and_Title = []
    first_Last = []
# Create a list with authors and the title
    for i in range(len(authors_book)):
        author_and_Title.append((authors_book[i], book_Title[i]))
# Check to see if the author matches the input
    for author,title in author_and_Title:
        if author.count(input_author) == 1:
            author_collection.append([author, title])
# Split the authors name to reorder the first and last name
    for author, title in author_collection:
        first_Last = author.split(" ")

# Reverse order of author name
    correct_Author_Name = (first_Last[1] + " " + first_Last[0])
    
    for author, title in author_collection:
        print("{} by {}".format(title, correct_Author_Name))    
    

# -----------------------------------------+
# Option #4                                |
# -----------------------------------------+
# Based on input of birth year find books  |
# that were published during that year     |
# -----------------------------------------+

def books_From_Your_Birthyear(file, input_Year):
    infile = open(file, "r", encoding="latin-1")
    first = infile.readline()
    first = first[:-1]

# List of tuples [(Title, Author, Publishing Year)]
    book_Title_Month_Publishing_Year = []
    birth_Year_Matched_List = []

    for line in infile:
        cells = line.split(",")
        book_Title = (cells[3])
        month = (cells[14])
        publishing_Year = (cells[16])
        book_Title_Month_Publishing_Year.append([book_Title, month, publishing_Year])
    
# Check to see the year published matches the birth year
    for item in book_Title_Month_Publishing_Year:
        if int(item[2]) == input_Year:
            birth_Year_Matched_List.append((item[0],int(item[1])))
           
# Sort the list by first and then by second
    birth_Year_Matched_List.sort(key = two_Element_Sort_First)
    birth_Year_Matched_List.sort(key = two_Element_Sort_Second)

    january = []
    february = []
    march = []
    april = []
    may = []
    june = []
    july = []
    august = []
    september = []
    october = []
    november = []
    december = []
    undefined = []
    for title,month in birth_Year_Matched_List:
        if month == 1:
            january.append(title)
        elif month == 2:
            february.append(title)
        elif month == 3:
            march.append(title)
        elif month == 4:
            april.append(title)
        elif month == 5:
            may.append(title)
        elif month == 6:
            june.append(title)
        elif month == 7:
            july.append(title)
        elif month == 8:
            august.append(title)
        elif month == 9:
            september.append(title)
        elif month == 10:
            october.append(title)
        elif month == 11:
            november.append(title)
        elif month == 12:
            december.append(title)
        else:
            undefined.append(title)     

    input_Year = str(input_Year)

    print("The books below are from January " + input_Year)
    for item in january:
        print(item)
    print("")
    print("The books below are from February " + input_Year)
    for item in february:
        print(item)
    print("")
    print("The books below are from March " + input_Year)
    for item in march:
        print(item)
    print("")
    print("The books below are from April " + input_Year)
    for item in april:
        print(item)
    print("")
    print("The books below are from May " + input_Year)
    for item in may:
        print(item)
    print("")
    print("The books below are from June " + input_Year)
    for item in june:
        print(item)
    print("")
    print("The books below are from July " + input_Year)
    for item in july:
        print(item)
    print("")
    print("The books below are from August " + input_Year)
    for item in august:
        print(item)
    print("")
    print("The books below are from September " + input_Year)
    for item in september:
        print(item)
    print("")
    print("The books below are from October " + input_Year)
    for item in october:
        print(item)
    print("")
    print("The books below are from November " + input_Year)
    for item in november:
        print(item)
    print("")
    print("The books below are from December " + input_Year)
    for item in december:
        print(item)
    print("")
    print("These books are not defined")
    for item in undefined:
        print(item)


# Create two functions for sorting two element tuple list
def two_Element_Sort_First(elem):
    return elem[0]    


def two_Element_Sort_Second(elem):
    return elem[1] 


# -----------------------------------------+
# Do not change anything below this line   |
# with the exception of code related to    |
# option 4.                                |
# -----------------------------------------+

# -----------------------------------------+
# menu                                     |
# -----------------------------------------+
# Prints a menu of options for the user.   |
# -----------------------------------------+

def menu():
    print()
    print("1. Identify the most popular book download.")
    print("2. List a range of books by difficulty rating.")
    print("3. Identify all books by certain author.")
    print("4. Find out which books were published during your birthyear.")
    print("5. Quit.")
    print()

# -----------------------------------------+
# main                                     |
# -----------------------------------------+
# Repeatedly query the user for options.   |
# -----------------------------------------+

def main():
    input_file = "classics.csv"
    choice = 0
    while (choice != 5):
        menu()
        choice = int(input("Enter your choice: "))
        print()
        if (choice == 1):
            most_popular(input_file)
        elif (choice == 2):
            least = input("Enter least difficult out of 1000 (e.g. 250): ")
            most = input("Enter most difficult out of 1000 (e.g. 300): ")
            print("Using the Flesch–Kincaid grade level formula.")
            fk_difficulty_range(input_file, least, most)
        elif (choice == 3):
            author = input("Enter last name of author (e.g. Dickens): ")
            all_books_by_author(input_file, author)
        elif (choice == 4):
            birth_Year = int(input("Enter the year you were born(e.g. 1997): "))
            books_From_Your_Birthyear(input_file, birth_Year)

        elif (choice != 5):
            print("That is not a valid option.  Please try again.")
    print("Goodbye!")

# -----------------------------------------+

main()
