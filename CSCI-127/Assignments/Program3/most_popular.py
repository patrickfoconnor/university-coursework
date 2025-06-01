import csv
import pandas as pd


# Location -- []
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
    for i in range(0, len(downloads)): 
        downloads[i] = int(downloads[i]) 
    book_and_downloads = [(titles[i],downloads[i]) for i in range(0, len(titles))]
    book_and_downloads_Dict = dict(book_and_downloads)
    Keymax = max(book_and_downloads_Dict, key=book_and_downloads_Dict.get)
    print(Keymax)
    
    #for item in downloads:
    #    item = item.replace("\'", " ")
    

    #sorted_Downloads = sorted(downloads, reverse=True)
    #max_Downloads = sorted_Downloads[0]
    #max_Downloads = str(max_Downloads)
# Go through each item in compiled books and downloads list searching for the download that 
# matches the most downloads. If it does assign the title to most_Popular_Book
    #for item in book_and_downloads:
    #    if item[0] == max_Downloads:
    #        most_Popular_Book = item[1]

    #print(most_Popular_Book)

        

def main():
    input_file = "classics.csv"
    most_popular(input_file)


main()