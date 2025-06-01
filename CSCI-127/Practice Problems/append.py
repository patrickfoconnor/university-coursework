# Create a list called myList with the following six items:
# 76, 92.3, “hello”, True, 4, 76. Do it with both append and with concatenation, one item at a time.

def appendstring(listItems):
    myList = []
    for item in listItems:
        myList.append(item)
    return myList

def concatenateString(listItems):
    myList = []
    myList.insert(0, 76)
    myList.insert(1, 92.3)
    myList.insert(2, "hello")
    myList.insert(3, True)
    myList.insert(4, 4) 
    myList.insert(5, 76)

    return myList



tup = (76, 92.3, "hello", True, 4, 76)

print(appendstring(tup))
print(concatenateString(tup))



