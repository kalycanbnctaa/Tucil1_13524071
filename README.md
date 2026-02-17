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

Minimum requirement: Java JDK 17 or newer

Make sure Java is installed by running:

```bash
java -version
```

---

## Program Structure

```text
Tucil1_13524071/
├── bin/
│   ├── Board.class
│   ├── GUI.class
│   ├── GUI$1.class
│   ├── Main.class
│   ├── PermutationGenerator.class
│   ├── QueensSolver.class
│   ├── QueensSolver$UpdateListener.class
├── doc/
│   └── Tucil1_K1_13524071_Kalyca Nathania B. Manullang.pdf
├── src/
│   ├── Board.java
│   ├── GUI.java
│   ├── Main.java
│   ├── PermutationGenerator.java
│   └── QueensSolver.java
├── test/
│   ├── result
│   │     ├── result1.png
│   │     ├── result1.txt
│   │     ├── result9.png
│   │     ├── result9.txt
│   │     ├── result10.png
│   │     ├── result10.txt
│   ├── tc1.txt
│   ├── tc2.txt
│   ├── tc3.txt
│   ├── tc4.txt
│   ├── tc5.txt
│   ├── tc6.txt
│   ├── tc7.txt
│   ├── tc8.txt
│   ├── tc9.txt
│   ├── tc10.txt
└── README.md
```

---

## How to Compile

1. Clone the repository

```bash
git clone https://github.com/kalycanbnctaa/Tucil1_13524071
```

2. Navigate to the project root directory

```bash
cd Tucil1_13524071
```

3. Compile all Java source files

```bash
javac -d bin src/*.java
```

---

## How to Run

Run the program with:

```bash
java -cp bin Main
```

The program will launch the Graphical User Interface (GUI).

---

## How to Use

Steps to use the program:

1. Click the **Load & Solve** button

2. Select an input file in `.txt` format

3. The brute-force algorithm will start automatically

4. The board will update live during the search

5. Once a solution is found, it will be displayed

6. Click **Save Solution**

7. Choose the format:

   - Save as TXT  
   - Save as PNG  

---

## Input File Format

The input file represents an N × N board using characters A–Z to represent regions.

Example:

```text
AABB
AABB
CCDD
CCDD
```

Input constraints:

- The board must be square (N × N)
- Only characters A–Z are allowed
- The number of unique regions must equal N
- Maximum of 26 regions allowed
- No empty lines allowed

---

## Algorithm

This program uses a **Brute Force (Exhaustive Search)** algorithm.

Approach:

- The solution is represented as a permutation array
- All permutations are generated using nextPermutation
- Each permutation is checked for validity
- The search stops when a valid solution is found or all permutations have been checked

Time complexity:

```
O(N × N!)
```

Since all permutations are evaluated, the brute-force approach guarantees correctness, but becomes computationally expensive for large N.

---

## GUI Features

The GUI provides the following features:

- Load input file (.txt)
- Visual board visualization
- Colored regions display
- Live update during brute-force search
- Iteration counter display
- Execution time display
- Save solution as TXT
- Save solution as PNG

---

## Author

| NIM      | Name                          | Class |
|:--------:|-------------------------------|:-----:|
| 13524071 | Kalyca Nathania B. Manullang | 01    |

---

## Notes

This program was developed to fulfill the requirements of the Algorithm Strategy (IF2211) assignment at Institut Teknologi Bandung.