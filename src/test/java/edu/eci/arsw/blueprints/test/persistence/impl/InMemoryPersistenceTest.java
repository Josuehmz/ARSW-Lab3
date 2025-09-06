/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }

    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        // Agregar varios blueprints del mismo autor
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "blueprint1", pts1);
        
        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("john", "blueprint2", pts2);
        
        Point[] pts3 = new Point[]{new Point(40, 40), new Point(50, 50)};
        Blueprint bp3 = new Blueprint("alice", "blueprint3", pts3);
        
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        ibpp.saveBlueprint(bp3);
        
        // Obtener blueprints de john
        Set<Blueprint> johnBlueprints = ibpp.getBlueprintsByAuthor("john");
        assertEquals("Should return 2 blueprints for john", 2, johnBlueprints.size());
        assertTrue("Should contain blueprint1", johnBlueprints.contains(bp1));
        assertTrue("Should contain blueprint2", johnBlueprints.contains(bp2));
        assertFalse("Should not contain alice's blueprint", johnBlueprints.contains(bp3));
        
        // Obtener blueprints de alice
        Set<Blueprint> aliceBlueprints = ibpp.getBlueprintsByAuthor("alice");
        assertEquals("Should return 1 blueprint for alice", 1, aliceBlueprints.size());
        assertTrue("Should contain blueprint3", aliceBlueprints.contains(bp3));
    }

    @Test
    public void getBlueprintsByNonExistentAuthorTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        try {
            ibpp.getBlueprintsByAuthor("nonexistent");
            fail("Should throw BlueprintNotFoundException for non-existent author");
        } catch (BlueprintNotFoundException ex) {
            // Expected behavior
        }
    }

    @Test
    public void getAllBlueprintsTest() throws BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        
        // Verificar que ya existe un blueprint por defecto
        Set<Blueprint> allBlueprints = ibpp.getAllBlueprints();
        int initialSize = allBlueprints.size();
        assertTrue("Should have at least 1 blueprint initially", initialSize >= 1);
        
        // Agregar más blueprints
        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "blueprint1", pts1);
        
        Point[] pts2 = new Point[]{new Point(20, 20), new Point(30, 30)};
        Blueprint bp2 = new Blueprint("alice", "blueprint2", pts2);
        
        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);
        
        allBlueprints = ibpp.getAllBlueprints();
        assertEquals("Should have initial + 2 blueprints", initialSize + 2, allBlueprints.size());
    }
    
}
