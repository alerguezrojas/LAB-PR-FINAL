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
                grid[i][j] = new Node(i, j);
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
}
