package es.edu.ull.esit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main mainApp;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        mainApp = new Main();
        
        // Initialize nodeList manually to avoid AWT init() issues
        Field nodeListField = Main.class.getDeclaredField("nodeList");
        nodeListField.setAccessible(true);
        nodeListField.set(mainApp, new Node[28][19]); // NODES_WIDTH=28, NODES_HEIGHT=19
        
        // Populate nodes for tests that expect them
        mainApp.createNodes(false);
    }

    @Test
    void testCreateNodes() throws NoSuchFieldException, IllegalAccessException {
        // Access private nodeList
        Field nodeListField = Main.class.getDeclaredField("nodeList");
        nodeListField.setAccessible(true);
        Node[][] nodeList = (Node[][]) nodeListField.get(mainApp);

        assertNotNull(nodeList);
        assertTrue(nodeList.length > 0);
        assertTrue(nodeList[0].length > 0);
        assertNotNull(nodeList[0][0]);
        
        // Test resetting
        mainApp.createNodes(true);
        assertNotNull(nodeList[0][0]);
    }

    @Test
    void testGetNodeAt() {
        // Based on logic: x -= 15; x /= 35; y -= 15; y /= 35;
        // Node at 0,0 corresponds to pixel 15, 15
        Node node = mainApp.getNodeAt(15, 15);
        assertNotNull(node);
        assertEquals(0, node.getX());
        assertEquals(0, node.getY());

        // Test out of bounds
        // 0,0 maps to index 0,0 due to integer division (-15/35 = 0)
        // We need a value that results in < 0 index, e.g., -20 -> -35/35 = -1
        assertNull(mainApp.getNodeAt(-20, -20));
    }

    @Test
    void testIsMazeValid() throws NoSuchFieldException, IllegalAccessException {
        // Access private static start and target
        Field startField = Main.class.getDeclaredField("start");
        startField.setAccessible(true);
        Field targetField = Main.class.getDeclaredField("target");
        targetField.setAccessible(true);

        // Initially null (or whatever state previous tests left it in)
        startField.set(null, null);
        targetField.set(null, null);
        assertFalse(mainApp.isMazeValid());

        // Set start
        startField.set(null, new Node(0, 0));
        assertFalse(mainApp.isMazeValid());

        // Set target
        targetField.set(null, new Node(1, 1));
        assertTrue(mainApp.isMazeValid());
    }
    
    @Test
    void testResetCosts() throws NoSuchFieldException, IllegalAccessException {
        Field nodeListField = Main.class.getDeclaredField("nodeList");
        nodeListField.setAccessible(true);
        Node[][] nodeList = (Node[][]) nodeListField.get(mainApp);
        
        nodeList[0][0].setgCost(10.0);
        mainApp.resetCosts();
        assertEquals(Double.MAX_VALUE, nodeList[0][0].getgCost());
    }
    
    @Test
    void testClearSearchResults() throws NoSuchFieldException, IllegalAccessException {
        Field nodeListField = Main.class.getDeclaredField("nodeList");
        nodeListField.setAccessible(true);
        Node[][] nodeList = (Node[][]) nodeListField.get(mainApp);
        
        // Simulate a searched node
        nodeList[0][0].setColor(Color.BLUE); 
        assertTrue(nodeList[0][0].isSearched());
        
        mainApp.clearSearchResults();
        assertFalse(nodeList[0][0].isSearched());
        assertEquals(Color.LIGHT_GRAY, nodeList[0][0].getColor());
    }

    @Test
    void testMousePressed() throws NoSuchFieldException, IllegalAccessException {
        // Simulate mouse click at 15, 15 (Node 0,0)
        // Button 1: Wall
        MouseEvent e1 = new MouseEvent(mainApp, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 15, 15, 1, false, MouseEvent.BUTTON1);
        mainApp.mousePressed(e1);
        
        Node node = mainApp.getNodeAt(15, 15);
        assertTrue(node.isWall());
        
        // Click again to clear
        mainApp.mousePressed(e1);
        assertFalse(node.isWall());
        
        // Button 2: Start
        MouseEvent e2 = new MouseEvent(mainApp, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 15, 15, 1, false, MouseEvent.BUTTON2);
        mainApp.mousePressed(e2);
        assertTrue(node.isStart());
        
        // Button 3: End
        MouseEvent e3 = new MouseEvent(mainApp, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 50, 15, 1, false, MouseEvent.BUTTON3); // Node 1,0
        mainApp.mousePressed(e3);
        Node node2 = mainApp.getNodeAt(50, 15);
        assertTrue(node2.isEnd());
    }
}
