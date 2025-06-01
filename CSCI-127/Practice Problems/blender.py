class Blender:
    # Blender class for modeling a blender object

    def __init__(self, color, brand, volume):
        self.color = color
        self.brand = brand
        self.volume = volume
        self.speed = 0
        self.time = 0.0

#    def blend(self, speed, time):
#        print("The " + self.color + " blender is ")
#        print("blending at a setting ", speed "for", time, "minutes")

    def set(self, s, t):
        self.speed = s
        self.time = t
        print("Set speed ", t, "for", t, "minutes.")

    def __str__(self):
        return "The " + self.color + " blender is " \
            + "blending at setting " + str(self.speed) + " for "\
                + str(self.time) + " minutes."
# Test the blender

my_blender = Blender("green", "Ninja", 48)
##my_blender.blend(3,2)
speed = input("Enter speed: ")
time = input("Enter time in minutes: ")
my_blender.set(speed, time)
print(my_blender)