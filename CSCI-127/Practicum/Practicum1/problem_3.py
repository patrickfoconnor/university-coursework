
def print_progression(list_Numbers):
    str_nums = " -> ".join(list_Numbers)
    print(str_nums)


        
    
    #print(progression_list)

def main():
    user_inputs = input("Enter numbers seperated by spaces: ")

    nums = user_inputs.split()

    print_progression(nums)

main()