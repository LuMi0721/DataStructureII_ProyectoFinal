package com.redsegura.graph;

import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;
import com.redsegura.exception.InvalidGraphDataException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Graph {
    private Map<String, Vertex> vertices;
    private Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.vertices = new HashMap<>();
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new InvalidGraphDataException("El vertice no puede ser nulo.");
        }
        if (vertices.containsKey(vertex.getId())) {
            throw new InvalidGraphDataException("Ya existe un vertice con el ID: " + vertex.getId());
        }
        vertices.put(vertex.getId(), vertex);
        adjacencyList.put(vertex.getId(), new ArrayList<>());
    }

    public void addEdge(Edge edge) {
        if (edge == null) {
            throw new InvalidGraphDataException("La conexion no puede ser nula.");
        }
        if (!vertices.containsKey(edge.getSourceId()) || !vertices.containsKey(edge.getDestinationId())) {
            throw new InvalidGraphDataException("Los vertices de origen y destino deben existir en el grafo.");
        }

        adjacencyList.get(edge.getSourceId()).add(edge);

        if (!edge.isIsDirected()) {
            Edge reverseEdge = new Edge(
                edge.getId() + "_rev",
                edge.getDestinationId(),
                edge.getSourceId(),
                false,
                edge.getWeight(),
                edge.getLabel()
            );
            adjacencyList.get(edge.getDestinationId()).add(reverseEdge);
        }
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdgesFrom(String vertexId) {
        return adjacencyList.getOrDefault(vertexId, new ArrayList<>());
    }
}