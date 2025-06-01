<p align="center">
    Week 06 - CSCI 446 <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    September 27th thru October 1st - Fall 2021 - <br/>
</p>

# Table of Contents
- [ Class Topics](#topics)

<a name="topics"></a>

# Topics

- [September 27](#sept27): Genetic Algorithm Cont.
- [September 29](#sept29): Advanced Techniques Cont.
- [October 1](#oct01): CSP Advanced Topics

## Date: Monday September 27 <a name="sept27"></a>

### Wumpus World Continued

#### Determining Rules for the World

- Will be one of the most complex components

#### First-Order Logic

##### Limitations of Propositional Logic

- Propositional logic is not sufficiently powerful for general inference systems.
  - Cannot represent
    - Quantifiers
    - Variables
    - Information
    - Uncertainty
- Next we will consider a more expressive logic formalism - first order logic

##### Reason to choose FO Logic

- Extend language beyond propositions
  - Constants
  - Functions
  - Predicates
  - Variables
  - Connectives
  - Quantifiers
  - Equality

##### Truth in F-O Logic

- Sentences are true with respect to a model and interpretation
- Models contain objects and relations among them
- Interpretation specifies referents for:
  - GET FROM NOTES

## Date: Wednesday September 29 <a name="sept29"></a>

### First-Order Logic Continued

#### Universal Quantification

- See notes for any clarification but most is just simple discrete math

### First Order Inference

#### Inference Rules in FOL

- PL inference rules apply in FOL as well
  - Universal Elim: If for all x P(x) is true, then P(c) is true for any constant c in the domeain
  - Existential Intro: If P(c) is true, then there exist x where P(x) is true
  - Extistential Elim: If for there exist x where P(x) is true, then P(c) is true for some constant c not appearing in any other sentence (Skolem constant)

## Date: Friday October 1st <a name="oct01"></a>

### Chaining

#### Logical Inference

- Given a set of axioms (i.e., a set of sentences assumed to be true)
- Generate a set of theorems (i.e., a set of of sentences inferred to be true from the axioms.)
- Two approaches
  - Forward Chaining
  - Backward Chaining

##### Forward Chaining

- From known facts, infer new facts by matching facts to l.h.s. of rules and inferring r.h.s.
- This approach makes use of Modus Ponens
- Inference process continues by chaining through rules until desired conclusions are reached

##### Backward Chaining

- Start with goal conclusion and state as hypothesis (i.e. something assumed to be true)
- Match goal to r.h.s. of rules and take l.h.s. as new sub-goal
- Chain back through rules until known facts are found
- Similar to a depth-first search

### Resolution

- An application of a special form of Modus Ponens
- Can also be considered a form of backward chaining
- Provides a method for automated theorem proving
- Assumes all sentences are in conjuctive normal form (a.k.a. clause form)
  - See slide for example

#### Tips for Converting to clause form

- Eliminate implications
- Reduce scope of negation symbols
- Standardize variables
- Eliminate existential quantifiers
- See notes for other 5 although not all will be used

#### For Wumpus World

- Unification and Resolution(Backward Chaining)
  - Attempt to have as simple of rules as possible
- Possible use some sort of hashing for mapping facts to rules
- Keep in clause form for resolution
