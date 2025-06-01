<p align="center">
    Week 09 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    October 18th thru October 22th - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [October 18](#oct18): See Slides
- [October 20](#oct20): Wumpus World (Again)
- [October 22](#oct22): Not sure

## Date: Monday October 18th <a name="oct11"></a>

### See slides for the information

## Date: Wednesday October 20th <a name="oct20"></a>

### Wumpus World (Again)

- See slides for more info but nothing very essential taken

### Bayesian Networks

- Belief network is a graph in which
  - Nodes correspond to random variables
  - Directed links connect pairs of nodes and indicate a causal link or influence
  - Each node has a conditional probability table that quantifies the effects parents of the node have on the node
  - Needs to be acyclic

    family_out      has_fleas
    /        \      /
    |        |     |
    V        V     V
light_on     dog_out
               |
               V
            hear_bark

#### Topology

- The topology of the network captures independence assumptions
  - A node, given its parents, is conditionally independent of its non-descendants
  - A node, given its parents, children, and children's parents, is conditionally independent of the rest of the network
- Probability distributions are defined using
  - Prior probability for a root nodes(i.e. no parents)
- Conditional Independence

## Date: Friday October 22th <a name="oct22"></a>

### Conditional Independence

- Many diagrams and graphs See presentation

#### Direction-Dependent Seperation

- We can define conditional independence further using the concept of d-seperation
- If every undirected path from a node in a set of nodes X to a node in a set of nodes Y is d-seperated by a set of evidence E, then X and Y are conditionally independent given E
- A set of evidence nodes, E, d-seperates two sets of nodes X and Y if every undirected path from a node in X to a node in Y is blocked given E

Path Blocking

- A path is blocked given a set of nodes E if there exists a node Z on the path for which one of the following holds
  - Z is in E and Z has one arrow on the path leading in and one arrow leading out
  - Z is in E and Z has both path arrows leading out
  - Z is not in E and Z has both paths leading in and both paths separately leading out

#### General Exact Inference

Inference on Bayesian Networks

- Diagnostic Inference: Infer from effect to cause, thus determining the causes
- Causal Inference: Infer from cause to effect, thus determining how a cause is manifest
- Intercausal Inference:
- Mixed Inference:

A new network
       Burglary    Earthquake
             \      /
             |     |
             V     V
              Alarm
             /     \
            |       |
            V       V
      JohnCalls    MaryCalls
