package es.edu.ull.esit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MazeGenerator.
 * Tests the maze generation algorithm to ensure it creates valid mazes.
 */
class MazeGeneratorTest {

    private MazeGenerator mazeGenerator;
    private Node[][] grid;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    /**
     * Sets up the test environment before each test.
     * Creates a grid of nodes and initializes the maze generator.
     */
    @BeforeEach
    void setUp() {
        grid = new Node[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                // Initialize with pixel coordinates as Main.java does
                // Node.getX() expects (Xpos - 15) / 35
                grid[i][j] = new Node(i, j).setX(15 + i * 35).setY(15 + j * 35);
            }
        }
        mazeGenerator = new MazeGenerator(WIDTH, HEIGHT, grid);
    }

    /**
     * Tests that the maze generator creates a valid maze.
     * Verifies that the generated maze contains at least some path nodes (not all walls).
     */
    @Test
    void testGenerate() {
        mazeGenerator.generate();
        
        int wallCount = 0;
        int pathCount = 0;
        
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (grid[i][j].isWall()) {
                    wallCount++;
                } else {
                    pathCount++;
                }
            }
        }
        
        assertTrue(wallCount > 0, "Maze should contain walls");
        assertTrue(pathCount > 0, "Maze should contain paths");
    }

    @Test
    void testMazeIsSolvable() {
        mazeGenerator.generate();
        
        // Find a start and end node that are paths
        Node start = null;
        Node end = null;
        
        // Find first path node
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (!grid[i][j].isWall()) {
                    start = grid[i][j];
                    break;
                }
            }
            if (start != null) break;
        }
        
        // Find last path node
        for (int i = WIDTH - 1; i >= 0; i--) {
            for (int j = HEIGHT - 1; j >= 0; j--) {
                if (!grid[i][j].isWall() && grid[i][j] != start) {
                    end = grid[i][j];
                    break;
                }
            }
            if (end != null) break;
        }
        
        assertNotNull(start, "Should have at least one path node");
        assertNotNull(end, "Should have at least two path nodes");
        
        start.setColor(java.awt.Color.GREEN);
        end.setColor(java.awt.Color.RED);
        
        // Use BFS to check if end is reachable from start
        Algorithm algorithm = new Algorithm();
        algorithm.setSearchTime(0);
        algorithm.setStrategy(new es.edu.ull.esit.algorithm.BfsAlgorithm());
        
        // We need to set directions because MazeGenerator might not set them?
        // MazeGenerator modifies the grid. Main sets directions.
        // We need to set directions for the algorithm to work.
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Node up = null, down = null, left = null, right = null;
                if (j > 0) up = grid[i][j - 1];
                if (j < HEIGHT - 1) down = grid[i][j + 1];
                if (i > 0) left = grid[i - 1][j];
                if (i < WIDTH - 1) right = grid[i + 1][j];
                grid[i][j].setDirections(left, right, up, down);
            }
        }
        
        algorithm.performSearch(start, end, WIDTH, HEIGHT);
        
        // If solvable, end should be colored (MAGENTA usually, or at least searched)
        // BfsAlgorithm sets end color to MAGENTA if found.
        assertEquals(java.awt.Color.MAGENTA, end.getColor(), "Maze should be solvable");
    }
}
