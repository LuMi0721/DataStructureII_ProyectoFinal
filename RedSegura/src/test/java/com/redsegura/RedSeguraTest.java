package com.redsegura;

import com.redsegura.exception.InvalidGraphDataException;
import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;
import com.redsegura.tree.BinarySearchTree;
import com.redsegura.tree.AvlTree;
import com.redsegura.graph.Graph;
import com.redsegura.graph.GraphDataFactory;
import com.redsegura.graph.DijkstraRouter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class RedSeguraTest {

    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = GraphDataFactory.createSincelejoNetwork();
    }

    @Test
    @DisplayName("Prueba 1: Validacion de peso negativo en arista")
    public void testInvalidEdgeWeight() {
        assertThrows(InvalidGraphDataException.class, () -> {
            new Edge("E_ERR", "S01", "S02", false, -3.0, "Ruta Invalida");
        });
    }

    @Test
    @DisplayName("Prueba 2: Busqueda en Arbol Binario (ABB)")
    public void testBinarySearchTree() {
        BinarySearchTree bst = new BinarySearchTree();
        Vertex v1 = new Vertex("S01", "UAJS Sede C", "Universidad");
        Vertex v2 = new Vertex("S02", "Hospital Universitario", "Salud");

        bst.insert(v1);
        bst.insert(v2);

        Vertex found = bst.search("Hospital Universitario");
        assertNotNull(found);
        assertEquals("S02", found.getId());
    }

    @Test
    @DisplayName("Prueba 3: Ordenamiento e insercion en AVL")
    public void testAvlTree() {
        AvlTree avl = new AvlTree();
        avl.insert(new Vertex("S01", "UAJS Sede C", "Universidad"));
        avl.insert(new Vertex("S02", "Hospital Universitario", "Salud"));
        avl.insert(new Vertex("S04", "CECAR", "Universidad"));

        assertNotNull(avl.getRoot());
        assertEquals(3, avl.inOrder().size());
    }

    @Test
    @DisplayName("Prueba 4: Algoritmo de Dijkstra para la ruta mas corta")
    public void testDijkstraShortestPath() {
        DijkstraRouter.PathResult result = DijkstraRouter.findShortestPath(graph, "S01", "S03");

        assertNotNull(result);
        assertTrue(result.getTotalWeight() > 0);
        assertEquals(3, result.getPath().size());
    }
}
