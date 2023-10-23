# Project 2 (Nondeterministic Finite Automata)

* Author: Joe Lathrop, Clara Arnold
* Class: CS361 001
* Semester: Fall 2023

## Overview

This program can create an NFA by adding states and transition, including epsilon
transitions. It can check if the created NFA is actually a DFA, it can create the
E-Closure for each of the system's states, it can check if strings are accepted
or rejected by the system, and can trace the system given a string it accepts
or rejects.

## Reflection

The first part of this project, getting an NFA to successfully be created by the test
class, was simple due to the use of our DFA implementation from P1. We only had to go through
and fix a few of the methods to work correctly for an NFA system. Next, we worked on `isDFA()`
where we checked each state to see if they had only one transition per sigma character by looping
through each state's transitions. We then worked on `eClosure()` which was also fairly simple 
to implement by following the DFS algorithm and searching for the transitions with an `e` character.

Finally, we began our work on `maxCopies()` and `accepts()` which gave us some trouble as expected
by the project description. We first set out to understand what `maxCopies()` was doing, essentially
a trace tree for an NFA, and then tried implementing it. The part we struggled with most was

## Compiling and Using

To compile the program:
- $ javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java

To run NFATest:
- $ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar
  org.junit.runner.JUnitCore test.nfa.NFATest

## Sources used

- Lecture notes
- P2 project description
- java.util.Set interface
- java.util.Map interface
- Our P1 implementation

----------
