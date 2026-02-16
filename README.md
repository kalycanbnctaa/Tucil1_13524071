# Queens Solver – Brute Force 

Queens Solver is a Java-based program designed to solve the **Queens LinkedIn Puzzle** using a **brute-force (exhaustive search) algorithm**.

The objective of the program is to place N queens on an N × N board while satisfying the following constraints:

- Each row contains exactly one queen  
- Each column contains exactly one queen  
- Each region contains exactly one queen  
- Queens must not be adjacent to each other (including horizontal, vertical, and diagonal adjacency)  

The program systematically generates and evaluates all possible configurations using a brute-force approach until a valid solution is found or all possibilities have been exhausted.

The program provides a **Graphical User Interface (GUI)** built with Java Swing and supports saving the solution in both **TXT and PNG formats**.

---

## Requirements

This program is written in Java and only uses the Java Standard Library.

Make sure Java is installed by running:

```bash
java -version

---

## Program Structure

```text
Tucil1_13524071/
├── bin/
├── doc/
│   └── Tucil1_K1_13524071_Kalyca Nathania B. Manullang.pdf
├── src/
│   ├── Board.java
│   ├── GUI.java
│   ├── Main.java
│   ├── PermutationGenerator.java
│   └── QueensSolver.java
├── test/
│   ├── tc1.txt
│   ├── tc2.txt
│   ├── tc3.txt
│   ├── tc4.txt
│   ├── tc5.txt
│   ├── tc6.txt
│   ├── tc7.txt
│   └── result/
│       ├── result1.txt
│       └── result1.png
└── README.md
```text

---

## How to Compile

1. Clone repository

```bash
git clone https://github.com/kalycanbnctaa/Tucil1_13524071
````

2. Navigate to the src folder

```bash
cd src
````

3. Compile all the Java files using the following command:

```bash
javac *.java
````

---

## How to Run


Run the program with:

```bash
java Main
````


The GUI window will appear.

---

## How to Use

Steps to use the program:

1. Click the Load & Solve button

2. Select an input file in .txt format

3. The brute-force algorithm will start automatically

4. The board will update live during the search

5. Once a solution is found, it will be displayed

6. Click Save Solution

7. Choose the format:

    - Save as TXT
    
    - Save as PNG

---

## Input File Format

The input file represents an N × N board using characters A–Z to represent regions.

Example:

AABB
AABB
CCDD
CCDD


Input constraints:

- The board must be square (N × N)

- Only characters A–Z are allowed

- The number of unique regions must equal N

- Maximum of 26 regions allowed

- No empty lines allowed

---

## Algorithm

This program uses a Brute Force (Exhaustive Search) algorithm.

Approach:

- The solution is represented as a permutation array

- All permutations are generated using nextPermutation

- Each permutation is checked for validity

- The search stops when a valid solution is found or all permutations have been checked

Time complexity:

O(N × N!)

---

## GUI Features

The GUI provides the following features:

- Load input file (.txt)

- Visual board visualization

- Live update during brute-force search

- Iteration counter display

- Execution time display

- Save solution as TXT

- Save solution as PNG

---

## Author

Name: Kalyca Nathania B. Manullang
NIM: 13524071
Class: 01

---

## Notes

This program was developed to fulfill the requirements of the Algorithm Strategy assignment.


