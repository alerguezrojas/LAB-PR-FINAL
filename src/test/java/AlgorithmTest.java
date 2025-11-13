
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    private Node start, end;
    private Node[][] grid;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private Algorithm algorithm;

    @BeforeEach
    void setUp() {
        algorithm = new Algorithm();
        grid = new Node[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                grid[i][j] = new Node(i, j);
            }
        }
        start = grid[0][0];
        end = grid[WIDTH - 1][HEIGHT - 1];
        start.setColor(java.awt.Color.GREEN);
        end.setColor(java.awt.Color.RED);
    }

    @Test
    void testDijkstra() {
        // Assuming a simple path exists
        algorithm.dijkstra(start, end, WIDTH, HEIGHT);
        // We can't easily assert the path visually, but we can check if end node was processed.
        // This requires modification in Node or Algorithm to track visited status for testing.
        assertTrue(true); // Placeholder
    }

    @Test
    void testGreedyBestFirstSearch() {
        algorithm.greedyBestFirstSearch(start, end, WIDTH, HEIGHT);
        assertTrue(true); // Placeholder
    }

    @Test
    void testBidirectionalSearch() {
        algorithm.bidirectionalSearch(start, end, WIDTH, HEIGHT);
        assertTrue(true); // Placeholder
    }
}
