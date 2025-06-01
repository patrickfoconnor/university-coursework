# Create a data frame that displays
# and analyzes a farm
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

nums = (45, 3, 850, 12)
sounds = {"cow": "moo", "dog":"woof", "pig":"oink", "cat":"meow"}
prices = np.array([2000, 50, 300, 1.25], dtype=int)

farm_data_set = list(zip(nums, sounds.values(), prices))
rows = ["Cows", "Dogs", "Pigs", "Cats"]
cols = ["Nums", "Sounds", "Prices"]
df = pd.DataFrame(data=farm_data_set, index=rows, columns= cols)

max_value = df["Nums"].max()
min_value = df["Nums"].min()

# Use grid = True to show a grid on graph
graph1 = df.plot(y = "Nums", kind = "pie")
graph2 = df.plot(y = "Prices", kind = "pie"); plt.show()

