
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeGeneratorTest {

    private MazeGenerator mazeGenerator;
    private Node[][] grid;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

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

    @Test
    void testGenerate() {
        mazeGenerator.generate();
        // Check that the maze is not all walls
        boolean hasPath = false;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (!grid[i][j].isWall()) {
                    hasPath = true;
                    break;
                }
            }
            if(hasPath) break;
        }
        assertTrue(hasPath);
    }
}
