
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {

    private final int width;
    private final int height;
    private final Node[][] grid;
    private final Random random = new Random();

    public MazeGenerator(int width, int height, Node[][] grid) {
        this.width = width;
        this.height = height;
        this.grid = grid;
    }

    public void generate() {
        // Initialize grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].setAsWall(); // Or a method that makes it a wall
            }
        }

        Stack<Node> stack = new Stack<>();
        Node startNode = grid[random.nextInt(width)][random.nextInt(height)];
        startNode.clearNode(); // Make it a path
        stack.push(startNode);

        while (!stack.isEmpty()) {
            Node currentNode = stack.peek();
            List<Node> neighbors = getUnvisitedNeighbors(currentNode);

            if (!neighbors.isEmpty()) {
                Node neighbor = neighbors.get(random.nextInt(neighbors.size()));
                stack.push(neighbor);
                removeWall(currentNode, neighbor);
                neighbor.clearNode();
            } else {
                stack.pop();
            }
        }
    }

    private List<Node> getUnvisitedNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();

        // Up
        if (y > 1 && grid[x][y - 2].isWall()) {
            neighbors.add(grid[x][y - 2]);
        }
        // Down
        if (y < height - 2 && grid[x][y + 2].isWall()) {
            neighbors.add(grid[x][y + 2]);
        }
        // Left
        if (x > 1 && grid[x - 2][y].isWall()) {
            neighbors.add(grid[x - 2][y]);
        }
        // Right
        if (x < width - 2 && grid[x + 2][y].isWall()) {
            neighbors.add(grid[x + 2][y]);
        }
        
        Collections.shuffle(neighbors);
        return neighbors;
    }

    private void removeWall(Node a, Node b) {
        int x = (a.getX() + b.getX()) / 2;
        int y = (a.getY() + b.getY()) / 2;
        grid[x][y].clearNode();
    }
}
