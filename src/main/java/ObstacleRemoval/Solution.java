package ObstacleRemoval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
    private int gridRows, gridCols;

    public int minimumObstacles(int[][] grid) {
        if (grid == null || grid.length == 0)
            return -1;
        int sol = 0;
        gridRows = grid.length;
        gridCols = grid[0].length;
        if (gridRows == 1) {
            for (int i = 0; i < gridCols; i++)
                sol += (grid[0][i] == 0 ? 0 : 1);
            return sol;
        }
        Graph graph = new Graph(gridRows * gridCols);
        graph.make(grid);
        sol = graph.findShortestPath(0, (gridRows - 1) * gridCols + (gridCols - 1), gridRows, gridCols);
        graph.printGraph();
        System.out.println(graph.vertices[(gridRows - 1) * gridCols].getPath());
        return sol;
    }

    /**
     * Represents a graph of int cells
     */
    public class Graph {
        private Vertex[] vertices;
        private int numVertices;
        private int maxNum;

        /**
         * Creates a graph with a given maximum number of vertices
         * 
         * @param maximum The desired maximum number of vertices
         */
        public Graph(int maximum) {
            vertices = new Vertex[maximum];
            numVertices = 0;
            maxNum = maximum;
        }

        /**
         * Adds a node to the graph
         * 
         * @param id The int id of the node to add
         * @return The index of the Vertex in the Graph's array
         * @throws IllegalStateException If graph is full
         */
        public int addNode(int id) throws IllegalStateException {
            // Check if there are too many vertices in the graph
            if (numVertices >= maxNum)
                throw new IllegalStateException("Graph is full.");
            // Create a new vertex with the given id and add it to the graph
            Vertex newVertex = new Vertex(id);
            vertices[numVertices] = newVertex;
            return numVertices++;
        }

        /**
         * Adds an edge between two vertices with a given cost
         * 
         * @param i    The starting index of the vertex of the edge
         * @param j    The ending vertex of the vertex of the edge
         * @param cost The desired cost of the edge
         * @throws IllegalAccessException If vertex indices are invalid
         */
        public void addEdge(int i, int j, int cost) throws IllegalArgumentException {
            // Check if the vertices are out of bounds and disallow self-loops
            if (i < 0 || i >= numVertices || j < 0 || j >= numVertices || i == j)
                throw new IllegalArgumentException("Invalid vertex indices");
            // Check for duplicate edges
            for (Edge edge : vertices[i].edges)
                if (edge.endNode == j)
                    throw new IllegalArgumentException("Edge already exists");
            // Create an edge from vertex i to j with given cost
            vertices[i].edges.add(new Edge(j, cost));
            // vertices[j].edges.add(new Edge(i, cost));
        }

        /**
         * Creates a graph out of a matrix array of ints where each cell has either a
         * value of 0 or 1. Edges are adjacent cells, and have a cost of 0 if the
         * adjacent cell if 0, or 1 if the adjacent cell is 1.
         * 
         * @param matrix The 2D int array to turn into a graph
         */
        public void make(int[][] matrix) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            // Create a vertex for each cell with a unique id
            Vertex[][] cellVertices = new Vertex[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int id = i * cols + j;
                    cellVertices[i][j] = new Vertex(id);
                    this.addNode(id);
                }
            }
            // Add edges for adjacent cells
            int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int currentId = i * cols + j;
                    for (int[] direction : directions) {
                        int neighbor_i = i + direction[0];
                        int neighbor_j = j + direction[1];
                        boolean ni = neighbor_i >= 0 && neighbor_i < rows;
                        boolean nj = neighbor_j >= 0 && neighbor_j < cols;
                        if (ni && nj) {
                            int neighborId = neighbor_i * cols + neighbor_j;
                            int cost = matrix[neighbor_i][neighbor_j] == 0 ? 0 : 1;
                            this.addEdge(currentId, neighborId, cost);
                        }
                    }
                }
            }
        }

        /**
         * Finds the shortest path from the top left corner of a matrix to the bottom
         * right corner using dijkstra's algorithm.
         *
         * @param from The index of the starting vertex
         * @param to The index of the ending vertex
         * @param rows The number of rows in the graph
         * @param cols The number of columns in the graph
         * @return The cost of the bottom-left vertex
         * @throws IllegalArgumentException If vertex indices are invalid
         */
        public int findShortestPath(int from, int to, int rows, int cols) throws IllegalArgumentException {
            // Check if from or to are invalid
            if (from < 0 || from >= vertices.length || to < 0|| to >= vertices.length || from == to)
                throw new IllegalArgumentException("Vertex indices out of bounds or equal.");
            // Priority que to store the vertices by cost
            PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.cost));
            vertices[from].cost = 0;
            pq.add(vertices[from]);
            while (!pq.isEmpty()) {
                Vertex current = pq.poll();
                // If the vertex is already processed then skip
                if (!current.done) {
                    current.done = true;
                    for (Edge edge : current.edges) {
                        Vertex neighbor = vertices[edge.endNode];
                        int newCost = current.cost + edge.cost;
                        // Update cost if new path is cheaper
                        if (newCost < neighbor.cost) {
                            neighbor.cost = newCost;
                            neighbor.parent = current;
                            pq.add(neighbor);
                        }
                    }
                }
            }
            return vertices[to].cost;
        }

        /**
         * Uses Prim's algorithm to compute the minimum spanning tree for the graph.
         * @return A list of edges where each entry is in the form {source, destination, cost}
         */
        public List<Edge> minimumSpanningTree() {
            List<Edge> mst = new ArrayList<>();
            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
            // Arbitrarily choose to start at vertex 0
            Vertex start = vertices[0];
            start.encountered = true;
            pq.addAll(start.edges);
            // Grow the MST until it contains all of the edges
            while (mst.size() < vertices.length - 1) {
                // Check if the graph is disconnected
                if (pq.isEmpty())
                    return null;
                Edge edge = pq.poll();
                Vertex neighbor = vertices[edge.endNode];
                // Only add to mst if edge has not yet been encountered
                if (!neighbor.encountered) {
                    neighbor.encountered = true;
                    mst.add(edge);
                    // Add all edges of the neighbor to the priority queue
                    for (Edge next : neighbor.edges)
                        if (!vertices[next.endNode].encountered)
                            pq.add(next);
                }
            }
            return mst;
        }

        /**
         * Prints the vertices of the graph and their edges
         */
        public void printGraph() {
            for (int i = 0; i < numVertices; i++) {
                Vertex vertex = vertices[i];
                System.out.print("Vertex " + vertex.id + " -> ");
                vertex.edges.sort(Comparator.comparingInt(edge -> edge.endNode));
                for (Edge edge : vertex.edges) {
                    System.out.print("[" + edge.endNode + ", cost: " + edge.cost + "] ");
                }
                System.out.println(); // Move to the next line for the next vertex
            }
        }

        /**
         * A class to represent a vertex in a Graph
         */
        public class Vertex {
            private int id;
            private LinkedList<Edge> edges = new LinkedList<>();
            private boolean encountered = false;
            private boolean done = false;
            private Vertex parent = null;
            private int cost = Integer.MAX_VALUE;

            public Vertex(int id) {
                this.id = id;
            }

            /**
             * Returns the path that traces back from the bottom-right cell to the top-left
             * cell.
             * 
             * @return A list of integers reconstructing the path
             */
            public List<Integer> getPath() {
                List<Integer> path = new ArrayList<>();
                Vertex current = this;
                while (current != null) {
                    path.add(current.id);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }
        }

        /**
         * A class to represent an edge in a Graph
         */
        public class Edge {
            private int endNode;
            private int cost;

            public Edge(int endNode, int cost) {
                this.endNode = endNode;
                this.cost = cost;
            }
        }
    }

    public static void main(String[] args) {
        int[][] testOne = { { 0, 1, 1 },
                { 1, 1, 0 },
                { 1, 1, 0 }
        };
        int[][] testTwo = { { 0, 1, 0, 0, 0 },
                { 0, 1, 0, 1, 0 },
                { 0, 0, 0, 1, 0 }
        };
        int[][] singleRow = new int[][] { { 0, 1, 1, 1, 0 } };
        int[][] testFail = { { 0, 1, 1 },
                { 0, 1, 1 },
                { 1, 1, 0 }
        };
        System.out.println(new Solution().minimumObstacles(testOne));
        System.out.println(new Solution().minimumObstacles(testTwo));
        System.out.println(new Solution().minimumObstacles(singleRow));
        System.out.println(new Solution().minimumObstacles(testFail));
    }
}
