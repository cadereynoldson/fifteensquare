
# Fifteen Square
A breif overview of the 15 square problem: 
- There are fifteen squares and one blank square in a 4x4 grid, in this implementation consisting of the characters:
1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F
- The goal of the game is to get the squares into this final shape: <br />
1  2  3  4          1  2  3  4 <br />
5  6  7  8    OR    5  6  7  8 <br />
9  A  B  C          9  A  B  C <br />
D  E  F             D  F  E  <br />

- Contains the following algorithms for solving the fifteen square problem: 
- The values in quotes correspond to their argument values. NOTE: These are case sensitive!
  - Depth First Search: "DFS"
  - Breadth First Search: "BFS"
  - Greedy Best First Search: "GBFS"
  - A Star: "AStar"
  - Iterative Deeping Search: "ID" 
  - Depth Limited Search: "DLS"

Arugments should be provided in the following format: 
[initialState], [searchMethod], [options]
Valid values for [options]:
- "h1" heuristic one, Total squares out of place.
- "h2" heuristic two, Total manhattan distance of all squares relative to their goal posion.
- Heuristics are ONLY taken into account for GBFS and AStar!
- "integerValue" the maximum depth or iteration depth per each iteration in ID or DLS.

