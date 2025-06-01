# ------------------------------------------+
# CSCI 127, Lab 4                           |
# February 13, 2020                         |
# Patrick O'Connor                          |
# ------------------------------------------+
# Prompt the user for a list of words       |
# separated by spaces.                      |
# Call a function to output those words     |
# and their frequencies.                    |
# ------------------------------------------+

from string import punctuation

def stripPunc(newlines):
# get rid of punctuation and return line
    lines = ["".join(c for c in s if c not in punctuation) for s in newlines]
    return lines


def count_frequencies(listItems):
    listItems = str(listItems)
    listItems.replace("'", "  ")
    listItems = list(listItems)
    counts = dict()
# Count the number of times each word is seen in sentence
    for word in listItems:
        if word in (counts):
            counts[word] += 1
        else:
            counts[word] = 1
    


# print each word and their corresponding count
    for x in counts:
        print(repr(x), counts[x])


def main():
# Recieve input for the sentence
    sentence = input("Enter words: ")
# Make all of the words lowercase
    sentence = sentence.lower()
# Create a list with inputted sentence
    wordlist = list(sentence.split())
# Strip the punctuation
    lines = stripPunc(wordlist)
# Call the count function
    count_frequencies(lines)


main()