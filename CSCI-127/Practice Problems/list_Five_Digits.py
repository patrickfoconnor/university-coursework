# Count the amount of 5 letter words in a list

def count_5_letter_words(listitems):
    counter = 0
    for items in listitems:
        length = len(items)
        if length == 5:
            counter += 1
        else:
            counter += 0
    return counter


def main():
    wordList = ["space", "bike", "next", "fives"]
    print(count_5_letter_words(wordList))

main()