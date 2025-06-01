<p align="center">
    Week 07 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    October 4th thru October 8th - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [October 4](#oct04): Converting to Clause Form
- [October 6](#oct06): Planning Problem
- [October 8](#oct08): Describing Planning Problem Formally

## Date: Monday October 4 <a name="oct04"></a>

### Converting to Clause Form

- No notes just discussion see slides for more specifics

#### Wumpus World

- percept and map
- ArrowCount = WumpusCount
- Cell is not always [1,1] but it is known

So if the gold is the goal state and we get teleported to safety after moving into goal state why would 
we need situation calculus

## Date: Wednesday October 6 <a name="oct06"></a>

### Planning in a known environment

#### Generic Logic-Based Agents

- Generic agent
  - Records current percept as fact
  - Infers best action using inference
  - Records fact that it will perform best action
  - Performs the action as function
- Predicates must indexed by time
- Percepts are tested using predicates at appropriate time

#### What is the planning problem?

- Given
  - A set of operators
  - An intial state description
  - A goal state

- Find
  - A sequence of operator instances such that performing them in order from the initial state will modify the state to achieve the goal

- Typical Assumptions
  - Each action is indivisible
  - No concurrent actions allowed
  - Actions are deterministic
  - Agent is sole cause of change to world
  - Agent is omniscient
  - Closed World Assumption - Everything known to be true is included in state description. Otherwise its false

## Date: Friday October 1st <a name="oct08"></a>

### Describing Planning Problem Formally

#### Opening States/Operators

- State/Situation representation is a conjunction of ground literals
- Goal is a state where all literals are positive
- "STRIPS" Operators have three parts:
  - Operator/action name
  - Preconditions: what needs to be true
  - Effects: what is now true (and false)

#### Planning as Search

- Generally, planning is regarded as a search problem due to the natural description
- Two main approaches suggest themselves
  - <b> Situation-Space Search </b> Search space is space of all possible states. Plan is sequence of operators on path from start to goal. Tends to be "total order"
  - <b> Plan-Space Search </b>

#### Situation-Space Planning

- Two Aprroaches
  - Progression Planning
    - Forward Chaining
    - Use standard search techniques (BFS, A*)
    - State-space search except with STRIPS operators
  - Regression Planning
    - Backward Chaining
    - Historically more efficient than progression planning
    - Must consider pre-conditions ad add-list of effects
- All search methods now benefit from heuristics

#### How to use STRIPS

- Uses a goal to support regression process
- Maintains current state throughout
- Approach
  - Pick an order for achievig each of the goals
  - When a goal is popped from stack, push operator that adds goal, followed by its preconditions
  - Repeat until all preconditions solved
