/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.filters.impl;

import edu.eci.arsw.blueprints.filters.impl.SubsamplingBlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class SubsamplingBlueprintFilterTest {
    
    @Test
    public void testSubsamplingFilter() {
        SubsamplingBlueprintFilter filter = new SubsamplingBlueprintFilter();
        
        // Crear blueprint con 6 puntos
        Point[] points = new Point[]{
            new Point(10, 10), // Índice 0 - se mantiene
            new Point(20, 20), // Índice 1 - se elimina
            new Point(30, 30), // Índice 2 - se mantiene
            new Point(40, 40), // Índice 3 - se elimina
            new Point(50, 50), // Índice 4 - se mantiene
            new Point(60, 60)  // Índice 5 - se elimina
        };
        
        Blueprint originalBlueprint = new Blueprint("author", "test", points);
        Blueprint filteredBlueprint = filter.filterBlueprint(originalBlueprint);
        
        // Verificar que se mantienen solo los puntos en índices pares
        assertEquals("Should have 3 points after subsampling", 3, filteredBlueprint.getPoints().size());
        assertEquals(new Point(10, 10), filteredBlueprint.getPoints().get(0));
        assertEquals(new Point(30, 30), filteredBlueprint.getPoints().get(1));
        assertEquals(new Point(50, 50), filteredBlueprint.getPoints().get(2));
    }
    
    @Test
    public void testEmptyBlueprint() {
        SubsamplingBlueprintFilter filter = new SubsamplingBlueprintFilter();
        Blueprint emptyBlueprint = new Blueprint("author", "empty");
        Blueprint filteredBlueprint = filter.filterBlueprint(emptyBlueprint);
        
        assertEquals("Empty blueprint should remain empty", 0, filteredBlueprint.getPoints().size());
    }
    
    @Test
    public void testSinglePoint() {
        SubsamplingBlueprintFilter filter = new SubsamplingBlueprintFilter();
        
        Point[] points = new Point[]{new Point(10, 10)};
        Blueprint originalBlueprint = new Blueprint("author", "test", points);
        Blueprint filteredBlueprint = filter.filterBlueprint(originalBlueprint);
        
        assertEquals("Should keep the single point", 1, filteredBlueprint.getPoints().size());
        assertEquals(new Point(10, 10), filteredBlueprint.getPoints().get(0));
    }
    
    @Test
    public void testOddNumberOfPoints() {
        SubsamplingBlueprintFilter filter = new SubsamplingBlueprintFilter();
        
        Point[] points = new Point[]{
            new Point(10, 10), // Índice 0 - se mantiene
            new Point(20, 20), // Índice 1 - se elimina
            new Point(30, 30), // Índice 2 - se mantiene
            new Point(40, 40), // Índice 3 - se elimina
            new Point(50, 50)  // Índice 4 - se mantiene
        };
        
        Blueprint originalBlueprint = new Blueprint("author", "test", points);
        Blueprint filteredBlueprint = filter.filterBlueprint(originalBlueprint);
        
        assertEquals("Should have 3 points after subsampling", 3, filteredBlueprint.getPoints().size());
        assertEquals(new Point(10, 10), filteredBlueprint.getPoints().get(0));
        assertEquals(new Point(30, 30), filteredBlueprint.getPoints().get(1));
        assertEquals(new Point(50, 50), filteredBlueprint.getPoints().get(2));
    }
}
