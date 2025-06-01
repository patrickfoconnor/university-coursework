<p align="center">
    Week 02 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    Aug 30th thru Sept 3rd - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [August 30](#aug30): Agents/Architecture
- [September 01](#sept01):
- [September 03](#sept03):

## Date: Wed Aug 25 <a name="aug30"></a>

## Agents and Architecture?

### Agents

- An agent is anything that can percieve its environment throuh a set of sensors and act upon that environment through a set of actuators
- Key Elements of a Rational Agent
  - Performance measure (To be maximized)
  - Environment (and knowledge about the environment)
  - Available actions (determined by actuators)
  - Percept Sequence (sensor information)

- PEAS Model
  - Performance Measure
    - Distance traveled, patient outcome, experience points
  - Environment
    - City streets, hospita, Oribos
  - Actuators
    - Velocity, turn angle, diagnostic tests, pyroblast/ice block
  - Sensors
    - Distance to lead vehicle, biopsy image, proximity alarm

#### Properties of an Environment

- Acccessible vs Inaccessible
- Deterministic vs Nondeterministic
  - Are successor states fully determined by the current state ans te action taken by the agent or can successors be selected "at random" from a set of possible next states
- Episodic vs Nonepisodic
  - Is the utility of the agent's actions dependent only upon the action taken in the current state or is look-ahead required to determine utility?
  - Immediate vs delayed "reward"
- Static vs Dynamic
  - Does the environment's state remain constant while the agent is deliberating, or can the environment's state change "on its own"?
  - If the environment is static but the utility changes with time, then the environment is semidynamic
- Discrete vs Continuous
  - Are the number of percepts and clearly enumerable, or do either percepts or actions lie within a range of pssible values

## Date: Wed Aug 27 <a name="sept01"></a>

## State Space Search

- A goal predicate determines if a goal node has been reach
- A solution is a sequence of operations associated with a path from start to goal
- The cost of the solution is the sum of the costs on the traversed edges
- State space search is the process of exploring the graph to find a solution

### 15 - Puzzle

Each vertice is a state
Each edge of the graph a movement of the empty space

Initial State (S_0)
              / move the empty space left one
            (S_1)
            /
          (S_g) Continues until goal state

### Formulating State-Space Search

- A state-space is a graph, G= (V,E)
  - Each node is a state descrition
  - Each edge is a transition resulting from application of an operator
- Edges have fixed costs associated with
- One or more nodes is designated start
- One or more nodes is designated goal

### State-Space Problems

- What is the initial state?
- What are the applicable operators?
- what is the goal test?
- What is the cost funtion?

#### Breadth First Search & Depth First Search is not a good option

- Not optimal

### Informed Search

- Adds domain-specific knowledge to identify best path
- Defines a heuristic function, h(n) to estimate node goodness
  - h(n)  = estimated cost from n to goal
- Characteristics of heuristics
  - h(n) = 0, goal
  - h(n) = infinite, dead-end
  - h(n) geq 0

#### Heuristicc Function

- Let f(n) be an estimate of  the cost from the start ot the goal
- Let h(n) be an estimate of the cost from the current node to the goal
- Let g(n) be the known cost from the start to the current node

## Date: Wed Aug 27 <a name="sept03"></a>

## Constraint Satisfaction Problems

- A constraint satisfaction problem consists of three components
  - X = {X_1, ..., X_n}
  - D = {D_1, ..., D_n}
  - C = a set of constraints that specify allowable combinations of variables
- A somain D_i consists of a set of allowable values {v_1,...v_k} for variable X_1
- Each constraint C_j = <scope, rel> defines the set of variables participating in the constraint (scope) and a relation (rel)
- The task is to final a value assignment for the variables that satify the constraints

### CSPS as Search Problems

- Initial State: The empty assignment {}, in wich all variables are unassigned
- Successor FunctionL A value is assigned to any unassigned variable, provied it does not conflct with previously assigned varibales
- Goal Test: The current assignment is complete (and violates no constraints)
- Path Cost: A constant cost (e.g. 1) for each step

### Types of CSPs

- Discrete variables with finite domains
  - Boolean CSPs are a special subclass
- Discrete variables with infinite domains
- Continuous variables
- Linear constraints
  - Linear programming is the most famous example
- Nonlinear constraints

### Types of constraints

- Unary constraints: Constraints on a single variable. Note that every unary constraint can be eliminated through preprocessing - remove values that violate the constraint 
- Binary constraints: Constraints relating two variables. Note that binary CSPc only have binary constraints. These should not be confused with Booelean CSPs
- Higher-order constraints: Constraints involving three or more variables. Note that all higher-order constraints can be re-written as a set of binary constraints with auxilary constraints
