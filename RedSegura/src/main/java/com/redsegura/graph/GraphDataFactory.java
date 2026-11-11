package com.redsegura.graph;

import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;

public class GraphDataFactory {

    public static Graph createSincelejoNetwork() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("S01", "UAJS Sede C", "Universidad");
        Vertex v2 = new Vertex("S02", "Hospital Universitario", "Salud");
        Vertex v3 = new Vertex("S03", "Clinica Maria Reina", "Salud");
        Vertex v4 = new Vertex("S04", "CECAR", "Universidad");
        Vertex v5 = new Vertex("S05", "Estacion Central SETP", "Transporte");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);

        graph.addEdge(new Edge("E01", "S01", "S02", false, 4.5, "Avenida Las Penitas"));
        graph.addEdge(new Edge("E02", "S02", "S03", false, 6.0, "Calle Principal"));
        graph.addEdge(new Edge("E03", "S01", "S04", false, 3.0, "Via Campestre"));
        graph.addEdge(new Edge("E04", "S04", "S05", false, 5.0, "Troncal de Occidente"));

        return graph;
    }
}