import pandas as pd
import matplotlib.pyplot as plt
import sys          # to determine Python version number
import matplotlib   # to determine Matplotlib version number

print('Python version ' + sys.version)
print('Pandas version ' + pd.__version__)
print('Matplotlib version ' + matplotlib.__version__)
print()

# Create Data --------------------------

# http://www.onthesnow.com/montana/bridger-bowl/historical-snowfall.html
years = [2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019]    # bridger bowl year
total_snowfall = [319, 346, 254, 316, 166, 228, 209, 271, 177, 186]   # inches
largest_snowfall = [16, 20, 25, 20, 14, 14, 21, 20, 15, 21]         # inches

BridgerDataSet = list(zip(years, total_snowfall, largest_snowfall))
print("BridgerDataSet:", BridgerDataSet, "\n")

data = pd.DataFrame(data = BridgerDataSet, columns=["Year", "Total", "Largest"])
print("Bridger DataFrame")
print("-----------------")
print(data)

data.to_csv('bridger.csv',index=False,header=False)

# Get Data -----------------------------

bridger = pd.read_csv('bridger.csv', names=['Year', 'Total', 'Largest'])
print("\nBridger DataFrame after reading csv file")
print("----------------------------------------")
print(bridger)

# Prepare Data -------------------------

if (bridger.Total.dtype == 'int64'):
    print("\nTotal snowfall is of type int64")
else:
    print("\nTotal Snowfall is of type", bridger.Total.dtype)

# Analyze Data -------------------------

sorted_data = bridger.sort_values(['Total'], ascending=False)
print("\nSorted Bridger Data Set")
print("-----------------------")
print(sorted_data)

print("\nThe least total snowfall was", bridger['Total'].min())

# Display Data -------------------------

bridger.plot(x="Year", y="Total", kind="bar", color="steelblue")
plt.xlabel("Year")
plt.ylabel("Total Snowfall")
plt.title("Bridger Bowl")
plt.subplot(131)
plt.bar(names, values)
plt.subplot(132)
plt.scatter(names, values)
plt.subplot(133)
plt.plot(names, values)
plt.suptitle('Categorical Plotting')
plt.show()

plt.show()