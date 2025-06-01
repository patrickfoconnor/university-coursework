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

- [October 11](#oct11): Planning Continued
- [October 13](#oct13): Partial Ordering Planning
- [October 15](#oct15): Probability

## Date: Monday October 11th <a name="oct11"></a>

### Heuristics for Planning

- Both progression and regression planning benefit from good heuristics

#### Relaxation

- See presentation

#### Subgoal Independence

- Assume pure divide an conquer will work
- Estimate cost of solving as sum of cost of solving each sub-goal independently
- Note that this can be either optimistic or pessimistic
  - Optimistic when there are negative interactions between sub-plans that must be corrected

### Partial Order Planning: Preferred in modern day

A planner that only represents partial-order constraints on steps

- Also known as a non-linear planner

#### Principle of Least Commitment

- Never make a choice unless it is needed

#### Components

- Each partial order plan has the following components
  - A set of actions that make up the plan
  - A set of ordering constraints of the form "A before B" where A and B are actions. Note this doe not require A to be immediately before B
  - A set of causal links of the form "A achieves p for B" where p is a predicate that is now satisfied by performing action A. Note that p is also a precondition for B that is now satisfied
    - Note: Once p is satisfied, a new action C cannot be added that conflicts with this causal lin

#### Approach

- Search in plan-space
- Start node in plan-space consists of a plan with two connected "pseudo-nodes"
  - Start
    - P: None
    - E: All positive literals defining initial state
  - Finish
    - P: literals defining the conjunctive goal
    - E: None
- There are two main reasons why a plan may not be a solution
  - Unsatisfied goal
  - Possible threat: Undoing of needed goal through other step
- Define plan modification operators to detect and fix these problems

## Date: Wednesday October 13th <a name="oct13"></a>

### Partial Ordering Planning

- Nothing to add from yesterday [October 11](#oct11)

### Graph Planning

- Planning graphs are constructed as a sequence of "level"
- Each level contains a set of literals and a set of actions
  - The literals are those that could be true at that time step
  - The actions are those that could have their preconditions satisfied at that time step
- Only works for propositional planning problems

Given a planning graph, it is possible to extract a plan directly from the graph

## Date: Friday October 15th <a name="oct15"></a>

### Numerical Agents

#### Reason Probabilistic Agents

- We take a bayesian view on the world
- Agents have degrees of belief about facts
- Rather than reasoning about truth or falsity of a fact

P)(a|b) = P(a and b)/P(b)
P(a given b) = P( a and b at the same time) over Prob(b)

Bayes rule will be very important

1 : 15,000 people

never wrong is not negative

1 : 200 false positive

Probablity of Disease given Positive

P(Dis) = 1:15,000
P(Pos) = 1:200

Dis = x
Pos = y
P(Dis|Pos) = ((199/200)(1/15000))/(1:200)
