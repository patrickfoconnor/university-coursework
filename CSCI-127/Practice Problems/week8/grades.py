infile = open("grades.csv", "r")
outfile = open("grades2.csv", "w")


first = first[:-1]

first += (", Average \n")
outfile.write(first)

for line in infile:
    cells = line.split(",")
    #print(cells[1], cells[2])
    ave = (int(cells[1]) + int(cells[2])) / 2
    #print(ave)

    outline = line[:-1]
    outline += (",")
    outline += str(ave)
    outline += ("\n")
    
    outfile.write(outline)

print("Calculations Complete")

infile.close
outfile.close
