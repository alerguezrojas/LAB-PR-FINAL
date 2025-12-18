package es.edu.ull.esit.algorithm;

import es.edu.ull.esit.Node;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Dijkstra's pathfinding algorithm.
 * Guarantees the shortest path by exploring nodes in order of their distance from start.
 */
public class DijkstraAlgorithm extends AbstractSearchAlgorithm {

    @Override
    public void search(Node start, Node end, int graphWidth, int graphHeight, int searchTime) {
        List<Node> openList = new ArrayList<>();
        Node[][] prev = new Node[graphWidth][graphHeight];
        openList.add(start);

        start.setgCost(0);

        while (!openList.isEmpty()) {
            Collections.sort(openList, Comparator.comparingDouble(node -> node.getgCost()));

            Node curNode = openList.remove(0);

            if (curNode.isEnd()) {
                curNode.setColor(Color.MAGENTA);
                shortpath(prev, end, searchTime);
                return;
            }

            curNode.setColor(Color.ORANGE);
            try {
                Thread.sleep(searchTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            curNode.setColor(Color.BLUE);

            for (Node adjacent : curNode.getNeighbours()) {
                if (adjacent.isSearched()) {
                    continue;
                }

                double newDist = curNode.getgCost() + Node.distance(curNode, adjacent);
                if (newDist < adjacent.getgCost()) {
                    adjacent.setgCost(newDist);
                    prev[adjacent.getX()][adjacent.getY()] = curNode;
                    if (!openList.contains(adjacent)) {
                        openList.add(adjacent);
                    }
                }
            }
        }
    }
}
