/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.main;

import edu.eci.arsw.blueprints.config.BlueprintsConfiguration;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Set;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BlueprintsConfiguration.class)) {
            BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
            
            System.out.println("=== DEMO DE GESTIÓN DE BLUEPRINTS ===");
            System.out.println();
            System.out.flush();
            // Crear y registrar blueprints con puntos redundantes para demostrar el filtrado
            Point[] pointsWithRedundancy = new Point[]{
                new Point(10, 10),
                new Point(10, 10), // Punto duplicado consecutivo
                new Point(20, 20),
                new Point(30, 30),
                new Point(30, 30), // Otro punto duplicado consecutivo
                new Point(40, 40),
                new Point(50, 50),
                new Point(60, 60)
            };
            
            Blueprint bp1 = new Blueprint("Juan", "Casa Moderna", pointsWithRedundancy);
            
            Point[] morePoints = new Point[]{
                new Point(0, 0),
                new Point(10, 0),
                new Point(20, 0),
                new Point(30, 0),
                new Point(40, 0),
                new Point(50, 0)
            };
            
            Blueprint bp2 = new Blueprint("Juan", "Edificio Comercial", morePoints);
            Blueprint bp3 = new Blueprint("Maria", "Residencia Familiar", new Point[]{
                new Point(100, 100),
                new Point(200, 200),
                new Point(300, 300),
                new Point(400, 400)
            });
            
            // Registrar blueprints
            System.out.println("1. REGISTRANDO BLUEPRINTS:");
            bps.addNewBlueprint(bp1);
            System.out.println("   - Registrado: " + bp1.getName() + " de " + bp1.getAuthor());
            
            bps.addNewBlueprint(bp2);
            System.out.println("   - Registrado: " + bp2.getName() + " de " + bp2.getAuthor());
            
            bps.addNewBlueprint(bp3);
            System.out.println("   - Registrado: " + bp3.getName() + " de " + bp3.getAuthor());
            System.out.println();
            
            // Consultar blueprint específico
            System.out.println("2. CONSULTANDO BLUEPRINT ESPECÍFICO:");
            Blueprint retrieved = bps.getBlueprint("Juan", "Casa Moderna");
            System.out.println("   Blueprint: " + retrieved.getName());
            System.out.println("   Autor: " + retrieved.getAuthor());
            System.out.println("   Puntos originales: 8, Puntos después del filtro: " + retrieved.getPoints().size());
            System.out.println("   Puntos filtrados: ");
            for (Point p : retrieved.getPoints()) {
                System.out.println("      " + p);
            }
            System.out.println();
            
            // Consultar blueprints por autor
            System.out.println("3. CONSULTANDO BLUEPRINTS POR AUTOR:");
            Set<Blueprint> juanBlueprints = bps.getBlueprintsByAuthor("Juan");
            System.out.println("   Blueprints de Juan: " + juanBlueprints.size());
            for (Blueprint bp : juanBlueprints) {
                System.out.println("      - " + bp.getName() + " (" + bp.getPoints().size() + " puntos filtrados)");
            }
            System.out.println();
            
            Set<Blueprint> mariaBlueprints = bps.getBlueprintsByAuthor("Maria");
            System.out.println("   Blueprints de Maria: " + mariaBlueprints.size());
            for (Blueprint bp : mariaBlueprints) {
                System.out.println("      - " + bp.getName() + " (" + bp.getPoints().size() + " puntos filtrados)");
            }
            System.out.println();
            
            // Consultar todos los blueprints
            System.out.println("4. CONSULTANDO TODOS LOS BLUEPRINTS:");
            Set<Blueprint> allBlueprints = bps.getAllBlueprints();
            System.out.println("   Total de blueprints en el sistema: " + allBlueprints.size());
            for (Blueprint bp : allBlueprints) {
                System.out.println("      - " + bp.getName() + " de " + bp.getAuthor() + 
                                 " (" + bp.getPoints().size() + " puntos filtrados)");
            }
            System.out.println();
            
            System.out.println("=== FILTRO ACTUAL: RedundancyBlueprintFilter (Filtro de redundancias) ===");
            System.out.println("Para cambiar al filtro de submuestreo, intercambie las anotaciones @Primary");
            System.out.println("entre RedundancyBlueprintFilter y SubsamplingBlueprintFilter");
            System.out.println();
            System.out.flush();
            
        } catch (BlueprintPersistenceException | BlueprintNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
