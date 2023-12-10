# Bridge Crossing Problem Solver

## Overview

This Java project provides a solution to the classic Bridge Crossing Problem using the A* search algorithm with a closed set. The problem involves transporting a group of family members with different crossing times across a bridge with a lantern, ensuring that no family member is left alone on either side of the bridge in the presence of the lantern.

## Project Structure

- **FamilyMember.java**: Represents an individual family member with a unique ID and crossing time.

- **State.java**: Defines the State class, which represents the current state of the problem. It includes methods for generating child states, applying heuristics, and evaluating the state.

- **Search.java**: Implements the A* search algorithm to find the optimal solution to the problem. It maintains a frontier of states and explores possible solutions.

- **Main.java**: The entry point for the project. It creates the initial state with family members and starts the A* search.

## Configuration

- **Heuristic Selection**: The project allows you to choose different heuristics for the A* search algorithm. The `heuristic` variable in the `Main` class can be adjusted to use a specific heuristic (1, 2, or 3).

- **Family Members**: The crossing times of family members are defined in the `Main` class using the `familyMembers` HashMap. You can modify the IDs and crossing times to represent different scenarios.
Output

## Output

The program outputs the following information:

- **Time Elapsed**: Total time elapsed during the A* search.
- **Nodes Expanded**: Number of nodes expanded during the search.
- **Nodes Explored**: Number of nodes explored during the search.
- **Search Time**: Total time taken for the search process.