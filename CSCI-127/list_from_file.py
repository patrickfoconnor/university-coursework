def create_list(input_file):
    file = open(input_file, "r")

    my_list = file.readlines()
    print(my_list)
    
file_name = "practicum.txt"

create_list(file_name)
