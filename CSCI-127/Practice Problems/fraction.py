def gcd(m, n): # algorithm to find greatest common denominator
    while m% n != 0:
        oldm = m
        oldn = n

        m = oldn
        n = oldm % oldn

    return n

class Fraction:

    def __init__(self, top, bottom):

        self.num = top # Numerator is on top
        self.den = bottom # Denominator is on bottom

    def __str__(self): # Use a string to return the fraction
        return str(self.num) + "/" + str(self.den)

    def simplify(self):
        common = gcd(self.num, self.den)

        self.num = self.num // common
        self.den = self.den // common

my_fraction = Fraction(12,16)

print(my_fraction)
my_fraction.simplify()
print(my_fraction)