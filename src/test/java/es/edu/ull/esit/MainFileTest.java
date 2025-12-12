package es.edu.ull.esit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MainFileTest {

    @TempDir
    Path tempDir;

    @Test
    void testSaveAndOpenMaze() throws IOException, NoSuchFieldException, IllegalAccessException {
        Main mainApp = new Main();
        
        // Initialize nodeList
        Field nodeListField = Main.class.getDeclaredField("nodeList");
        nodeListField.setAccessible(true);
        Node[][] nodes = new Node[28][19]; // Use correct dimensions
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 19; j++) {
                nodes[i][j] = new Node(i, j);
            }
        }
        nodeListField.set(mainApp, nodes);
        
        // Set some nodes
        nodes[0][0].setColor(Color.GREEN); // Start (2)
        nodes[1][1].setColor(Color.RED);   // End (3)
        nodes[2][2].setColor(Color.BLACK); // Wall (1)
        
        // Test Save
        File saveFile = tempDir.resolve("saved.maze").toFile();
        mainApp.saveMazeToFile(saveFile);
        
        assertTrue(saveFile.exists());
        assertTrue(saveFile.length() > 0);
        
        // Test Open
        // Reset nodes to verify loading
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 19; j++) {
                nodes[i][j].clearNode();
            }
        }
        
        mainApp.openMazeFromFile(saveFile);
        
        // Verify loaded state
        // Note: openMaze sets start/target fields, and colors.
        // We check colors as proxy for type.
        assertEquals(Color.GREEN, nodes[0][0].getColor());
        assertEquals(Color.RED, nodes[1][1].getColor());
        assertEquals(Color.BLACK, nodes[2][2].getColor());
    }
}
