import matplotlib.pyplot as plt
import pandas as pd
import os

# Initial set of baby names and birth rates
names = ["Bob", "Jessica", "Mary", "John","Mel" ]
births = [968, 155, 77, 578, 973]

# Create a list from the two values above
babyDataSet = list(zip(names, births))

df = pd.DataFrame(data = babyDataSet, columns=["Names", "Births"])

# Export to csv file
    # index = False turns off extra column
df.to_csv("births1880.csv", index=False, header=False)

location = r"/Users/patrickoconnor/births1880.csv"

# Read in the csv file from the location
df = pd.read_csv(location, header = None)

# Create a header with names and births
df = pd.read_csv(location, names=["Names", "Births"], header = None)

# Find the max value for births
Sorted = df.sort_values(['Births'], ascending=False)
Sorted.head(1)

max_births = df["Births"].max()


# Create graph
df['Births'].plot()

# Maximum value in the data set
MaxValue = df['Births'].max()

# Name associated with the maximum value
MaxName = df['Names'][df['Births'] == df['Births'].max()].values

# Text to display on graph
Text = str(MaxValue) + " - " + MaxName

# Add text to graph
plt.annotate(Text, xy=(1, MaxValue), xytext=(8, 0), xycoords=('axes fraction', 'data'), textcoords='offset points')
plt.show()

print("The most popular name")
df[df['Births'] == df['Births'].max()]
#Sorted.head(1) can also be used

