<p align="center">
    Week 04 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    September 13th thru 17th - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [September 13](#sept13): Genetic Algorithm Cont.
- [September 15](#sept15): Advanced Techniques Cont.
- [September 17](#sept17): CSP Advanced Topics

## Date: Monday September 13 <a name="sept13"></a>

### Sudoku Operators

#### Mutation

- Random: A cell is randomly changed to a new number
- Swap: Two cells are swapped in the puzzle
- Rotate: Can vary greatly, can be a selection of a row, sections, or a column.
- Crossover: One point, two point, rows, sections, column or uniform crossovers

## Date: Monday September 15 <a name="sept15"></a>

### Advanced Techniques

#### Informed Search

- Adds domain-specific knowledge to identify best path
- Defines a heuristic function, h(n), to estimate node goodness
  - h(n) = estimated cost from n to goal
- Characteristics of heauristics
  - h(n) = 0, goal
  - h(n) = infiniti, dead-end
  - h(n) = greater than or equal to 0, non-negative n

- Let f(n) be an estimate of the cost from the start to the goal
- Let h(n) be an estimate of the cost from the current node to the goal
- Let g(n) be the known cost from the start to the current node

##### Best-First Search

- Nodes queued in order of evaluation function f that includes the heuristic
  - Greedy Search
    - f(n) = h(n)
    - Nodes sorted in increasing order of f(upon expansion)
    - Not complete (if starts on infinite path)
    - Not admissible
  - Uniform Cost Search
    - f(n) = g(n)
    - Nodes sorted in incresing order of f (upon expansion)
    - Complete if all costs positive
    - Admissible - Basically makes BFS optimal
  - A^* Search
    - f(n) = g(n) + h(n)
      - g(n) = minimal cost from start to n
      - h-prime(n) = minimal cost from n to goal
      - h(n) = estimate of  h-prime(n)

###### Admissiablility of A^*

- THeorem: If  h-prime(n) is admissable

###### Heuristic for Sudoku

- g(S_i) = #{empty cells}
  - While  correct it does not good as for A* there must be a better "path"

## Date: Friday September 17 <a name="sept17"></a>

### CSP Advanced Topics

#### Intelligent Backtracking

##### Backjumping

- Define the conflict set to be the set of variables preceding a point requiring backtracking that is responsible for causing the failure
