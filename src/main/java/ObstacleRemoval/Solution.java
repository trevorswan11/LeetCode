package ObstacleRemoval;

import java.util.LinkedList;

public class Solution {
    public int minimumObstacles(int[][] grid) {
        Graph graph = new Graph(grid.length * grid[0].length);
        return 0;
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
         * @param i The starting index of the vertex of the edge
         * @param j The ending vertex of the vertex of the edge
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
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
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
         * A class to represent a vertex in a Graph
         */
        public class Vertex {
            private int id;
            private LinkedList<Edge> edges = new LinkedList<>();
            private boolean encountered = false;
            private boolean done = false;
            private Vertex parent;
            private int cost;

            public Vertex(int id) {
                this.id = id;
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
        int[][] testOne = { { 0, 1, 1 }, { 1, 1, 0 }, { 1, 1, 0 } };
        int[][] testTwo = { { 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0 } };
        System.out.println(new Solution().minimumObstacles(testOne));
        System.out.println(new Solution().minimumObstacles(testTwo));
    }
}
