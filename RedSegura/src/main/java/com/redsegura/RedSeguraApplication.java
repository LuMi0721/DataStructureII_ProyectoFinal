package com.redsegura;

import com.redsegura.exception.InvalidGraphDataException;
import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;
import com.redsegura.tree.BinarySearchTree;
import com.redsegura.tree.AvlTree;
import com.redsegura.graph.Graph;
import com.redsegura.graph.GraphDataFactory;
import com.redsegura.graph.DijkstraRouter;

public class RedSeguraApplication {

    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("       SISTEMA DE ATENCION DE EMERGENCIAS - REDSEGURA           ");
        System.out.println("================================================================");

        // 1. Validaciones y Control de Errores del Modelo
        System.out.println("\n[VALIDACION] Probando control de errores en datos de entrada...");
        try {
            Vertex sedeC = new Vertex("S01", "UAJS Sede C", "Universidad");
            System.out.println(" - Vertice creado correctamente: " + sedeC.getLabel());
            
            // Intento de crear arista con peso invalido para forzar la excepcion
            Edge errorEdge = new Edge("R99", "S01", "S07", true, -5.0, "Ruta erronea");
        } catch (InvalidGraphDataException e) {
            System.out.println(" -> Excepcion atrapada exitosamente: " + e.getMessage());
        }

        // 2. Consulta Q1: Busqueda de profundidad en el ABB
        System.out.println("\n[CONSULTA Q1] Busqueda de ubicacion medica en el Arbol Binario (ABB)...");
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(new Vertex("S01", "UAJS Sede C", "Universidad"));
        bst.insert(new Vertex("S07", "Estacion Central SETP", "Transporte"));
        bst.insert(new Vertex("S02", "Hospital Universitario", "Salud"));
        bst.insert(new Vertex("S03", "Clinica Maria Reina", "Salud"));

        String objetivoQ1 = "Hospital Universitario";
        Vertex resultadoQ1 = bst.search(objetivoQ1);
        if (resultadoQ1 != null) {
            System.out.println(" -> Ubicacion encontrada: " + resultadoQ1.getLabel());
            System.out.println(" -> Pasos de profundidad necesarios (Q1): " + bst.getLastSearchSteps());
        }

        // 3. Consulta Q2: Demostracion de rotaciones y auto-balanceo en el Arbol AVL
        System.out.println("\n[CONSULTA Q2] Demostracion de balanceo automatico y rotaciones en Arbol AVL...");
        AvlTree avl = new AvlTree();
        avl.insert(new Vertex("S01", "UAJS Sede C", "Universidad"));
        avl.insert(new Vertex("S02", "Hospital Universitario", "Salud"));
        avl.insert(new Vertex("S04", "CECAR", "Universidad")); // Dispara la rotacion necesaria

        System.out.println(" -> Arbol AVL balanceado correctamente. Recorrido alfabetico (InOrder):");
        for (Vertex v : avl.inOrder()) {
            System.out.println("    * " + v.getLabel() + " (" + v.getType() + ")");
        }

        // 4. Calculo de Ruta Optima de Emergencia (Dijkstra en el Grafo de Sincelejo)
        System.out.println("\n[ENRUTAMIENTO] Calculando la ruta de emergencia mas rapida (Dijkstra)...");
        Graph redSincelejo = GraphDataFactory.createSincelejoNetwork();
        System.out.println(" -> Red de Sincelejo cargada con " + redSincelejo.getVertices().size() + " puntos estrategicos.");

        DijkstraRouter.PathResult rutaOptima = DijkstraRouter.findShortestPath(redSincelejo, "S01", "S03");
        if (rutaOptima.getTotalWeight() > 0) {
            System.out.println(" -> Ruta mas rapida calculada desde UAJS Sede C hasta Clinica Maria Reina:");
            System.out.println(" -> Tiempo total estimado: " + rutaOptima.getTotalWeight() + " minutos.");
            System.out.println(" -> Secuencia de trayecto:");
            for (Vertex paso : rutaOptima.getPath()) {
                System.out.println("    [Punto] " + paso.getLabel() + " - Tipo: " + paso.getType());
            }
        } else {
            System.out.println(" -> No se encontro una ruta disponible.");
        }

        System.out.println("\n================================================================");
        System.out.println("     EJECUCION DE CONSULTAS FUNCIONALES FINALIZADA CON EXITO    ");
        System.out.println("================================================================");
    }
}