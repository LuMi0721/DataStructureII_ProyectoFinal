package com.redsegura.graph;

import com.redsegura.model.Edge;
import com.redsegura.model.Vertex;
import java.util.*;

public class DijkstraRouter {

    // Clase auxiliar para manejar la distancia acumulada hacia un vertice
    public static class PathResult {
        private List<Vertex> path;
        private double totalWeight;

        public PathResult(List<Vertex> path, double totalWeight) {
            this.path = path;
            this.totalWeight = totalWeight;
        }

        public List<Vertex> getPath() {
            return path;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }

    // Metodo principal que ejecuta Dijkstra desde un origen hasta un destino
    public static PathResult findShortestPath(Graph graph, String startId, String targetId) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::getDistance));

        Map<String, Vertex> vertices = graph.getVertices();

        if (!vertices.containsKey(startId) || !vertices.containsKey(targetId)) {
            return new PathResult(new ArrayList<>(), -1.0);
        }

        for (String vertexId : vertices.keySet()) {
            distances.put(vertexId, Double.MAX_VALUE);
        }

        distances.put(startId, 0.0);
        pq.add(new NodeDistance(startId, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            String currentId = current.getVertexId();

            if (currentId.equals(targetId)) {
                break; // Llegamos al destino mas corto
            }

            if (current.getDistance() > distances.get(currentId)) {
                continue;
            }

            for (Edge edge : graph.getEdgesFrom(currentId)) {
                String neighborId = edge.getDestinationId();
                double newDist = distances.get(currentId) + edge.getWeight();

                if (newDist < distances.get(neighborId)) {
                    distances.put(neighborId, newDist);
                    previousNodes.put(neighborId, currentId);
                    pq.add(new NodeDistance(neighborId, newDist));
                }
            }
        }

        // Reconstruir el camino desde el destino hasta el origen
        List<Vertex> path = new ArrayList<>();
        String step = targetId;

        if (!previousNodes.containsKey(step) && !startId.equals(targetId)) {
            return new PathResult(new ArrayList<>(), -1.0); // No hay ruta posible
        }

        path.add(vertices.get(targetId));
        while (previousNodes.containsKey(step)) {
            step = previousNodes.get(step);
            path.add(0, vertices.get(step));
        }

        return new PathResult(path, distances.get(targetId));
    }

    // Clase auxiliar interna para la cola de prioridad
    private static class NodeDistance {
        private String vertexId;
        private double distance;

        public NodeDistance(String vertexId, double distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }

        public String getVertexId() {
            return vertexId;
        }

        public double getDistance() {
            return distance;
        }
    }
}