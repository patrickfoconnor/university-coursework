import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import interactive

# ------------------------------------------------+
# CSCI 127, Lab 13                                |
# April 23, 2019                                  |
# Patrick O'Connor                                |
# ------------------------------------------------+

def process(file_name):
# Read the file_name into a pandas dataframe
    df_of_file = pd.read_csv(file_name)
# sort and plot the dataframe using arguments 
# Create a list of the header values for accessing x-axis and y-axis
    columns = list(df_of_file.columns)
# Access the first column to find x-axis
    x_axis = columns[0]
# Access the first column to find y-axis
    y_axis = columns[1]
# Set x and y for df.plot use
    x = x_axis
    y = y_axis
# Sort the df based on the value of y-axis 
    sorted_df_of_file = df_of_file.sort_values(by = columns[1], ascending = False)
# Set the type of graph to bar
    kind = "bar"
# Set title of plot to the file name with a period on the end 
    title = file_name[:-3]
# Set legend to false so it does not appear on plot
    legend = False
# Plot the sorted df from the inputed file
    sorted_df_of_file.plot(x = x, y = y, kind = kind, title = title, legend = legend)

    # The only statements that may use the matplotlib 
    # library appear next.
    # Do not modify them.
    plt.xlabel(x_axis)      # Note: x-axis should be determined above
    plt.ylabel(y_axis)      # Note: y-axis should be determined above
    plt.show()

# -------------------------------------------------

process("MSU College Enrollments.csv")
process("CS Faculty.csv")

