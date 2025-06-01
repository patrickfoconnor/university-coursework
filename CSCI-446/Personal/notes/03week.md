<p align="center">
    Week 03 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    Aug 30th thru Sept 3rd - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [September 08](#sept08): CSP Improvements
- [September 10](#sept10): CSP Local Search

## Date: Wed Aug 25 <a name="sept08"></a>

### Suggestion for Sudoku Solver

Add a counter

- State space search
- Local search

Hypothesis for experiment

- Is arc efficiency more efficient for harder problem? The book says not.

For Local Search

- Read in the game
- From there create non-changeable places that are the original game
- random assignment to all other spots


### CSP Improvements

#### General-Purpose Improvements

- We can improve on simple backtracking (DFS) by addressing the following:
  - Which variables should be assigned next, and in what order should the values be tried?
  - What are the implications of the current variable assignments for the other unassigned variables?
  - When a path fails, can the search avoid repeating this failure in subsequent paths?

#### Forward Checking

- Whenever a variable X is assigned
  - Look at each unassigned variable Y connected to X by a constraint
  - Delete from Y's domain any value that is inconsistent with the value chosen for X.
  
#### Constraint Propagation

- Constraint propagation is the general process of propagating the implications of a constraint on variable forward onto other variables
- The goal is to perform this propagation efficiently

##### K-Consistency

- A CSP is said to be k-consistent if, for any set of k-1 variables, and for any consistent assignment to those variables, a consistent value can be assigned to any kth variable
- 1-consistency: Also called node consistency, refers to each individual variable being self-consistent
- 2-consistency: Also called arc consistency, refers to the case when for every value of some variable there exists a consistent value for each of the neighboring variables
- 3-consistency: ALso called path consistency, refers to the case when any pair of adjacent consistent variables can be etened to a third neighboring variable
- A graph is strongly k-consistent if it j-consistent for j=1,..., k

#### Variable Assignment

- Recall the line in simple backtracking:
  - f
- Careful selection of which variables to assign can improve the performance of backtracking search
- Heuristics:
  - Minimum remaining values: choose the variable with the fewest "legal" values
  - Degree heuristic: choose the variable involved in the largest number of constraints on other unassigned variabels
  - Least constraining value: prefer the value that rules out the fewest choices for neighboring unassigned variables

### CSP Local Search

Local search begins by assigning values to all variables whether the resulting assignment is consistent or not

Then the variable assignments are varied until a solution is found

#### Min-Conflict Variation

- Local search depends upon selecting and changing variable assignments
- In choosing a new value for a variable, the most obvious heuristic is to select the value that results in the minimum number of conflicts with other variables

### CSP Local Search: Randomized Search Variations

- Hillclimbing with multiple random restarts
- Simulated Annealing
- Genetic Algorithm

#### Multiple Random Restarts

- Simplest randomized approach
- Requires multiple hill climbs in fitness space
- Each hill climb begins from different location
- Select starting location
  - Uniform random pick
  - Systematic coverage of space

## Date: Wed Aug 25 <a name="sept10"></a>

### CSP Local Search Continued

#### Simulated Annealing

- Minor variation on hill climbing
- Permits transition to point of lesser value with some probability
- Probability of accepting poor positions changes with time

Should use the approach that in order to stop the program a performance tracking device has hit a ridge or top

#### Genetic Algorithm

- Based on biological principle of "survival of the fittest"
- Encodes solution "Problem" as a "chromosomes"
- Maintains population of potential solutions
- Allows individuals in population to reproduce and generate new individuals
- Search driven by fitness function, selection mechanism, and reproduction operators

##### Other Representations Besides Binary

- Real-valued Strings
- Symbolic Strings
- Trees
- Graphs
- Programs
  - Linear
  - Tree

#### Fitness Function

- Selecting an individual is probabilistic
- Selection is with replacement (thus individuals can be picked more than once)
- Probabilities are determined in proportion to fitness

#### Tournament Selection

- Selecting an individual is probabilistic but not with replacement
- Define a tournament size of q and select q individuals using uniform distribution
- Select the individual from tournament with maximum fitness

#### Rank-Order Selection

- Selecting an individual is probabilistic and with replacement
- Probabilities based solely on rank (based on fitness) of individual in ordered list

#### Replacement

- Generational: Construct whole new population with new generation
- Steady-state: Select small number of replacements (e.g. 2) for next generation
- Elitist: Retain n best members of population
- Endogenous: Let resource availability

#### Mutation

- Replacement: One bit is picked and flipped
- Crossover:

#### Design Decisions

- How large is your population?
- Does each generation die after "reproduction"
- How large is the tournament? (Usually tournament size is 2)
- Do not implement a full elitism selection in tournaments
