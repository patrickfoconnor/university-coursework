<p align="center">
    Week 10 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    October 25th thru October 29th - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [October 25](#oct25): Variable Elimination
- [October 27](#oct27): Variable Elimination and Gibbs Sampling Introduction
- [October 29](#oct29): Bayes Decision Theory

## Date: Monday October 25th <a name="oct25"></a>

### Variable Elimination

- Note that we are imposing a random order in the summation
- Java Vectors?
- Have a point wise product
- Pseudo-code is in the slides from October 25th
- A hidden variable is not in evidence and not in query
  - Is only present in the given

## Date: Wednesday October 27th <a name="oct27"></a>

### Gibbs Sampling Introduction

- We should use forwardSampling which follows topology of the graph
- The size of T is essential to consider
  - As it drives complexity
  - Along with when T is large we can ignore the burn in period as it is deemed disproportionally small to entire sampling
- Possibly have a node class that holds parents and parents children

### For testing

- Find smaller network for testing

### For ordering

- Topological Sort

### VE vs Gibbs

- VE will always give the correct solution as its an exact
  - Let it if know that we are not stuck in a loop
- Gibbs is an approximation so sometimes can be off
- Design Decision for Gibbs is the amount of samples

### Utility Theory USEFUL FOR PROJECT 04

## Date: Friday October 29th <a name="oct29"></a>

### Bayes Decision Theory / Decision Networks

- Check slides when this is actually applied in Project 04
