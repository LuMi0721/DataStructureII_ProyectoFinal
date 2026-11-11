package com.redsegura.model;

import com.redsegura.exception.InvalidGraphDataException;
import java.util.Objects;

public class Edge {
    private String id;          
    private String sourceId;    
    private String destinationId; 
    private boolean isDirected; 
    private double weight;      
    private String label;       

    public Edge(String id, String sourceId, String destinationId, boolean isDirected, double weight, String label) {
        setId(id);
        setSourceId(sourceId);
        setDestinationId(destinationId);
        this.isDirected = isDirected;
        setWeight(weight);
        setLabel(label);
    }

    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidGraphDataException("El ID de la conexion no puede ser nulo o vacio.");
        }
        this.id = id;
    }

    public String getSourceId() { return sourceId; }
    public void setSourceId(String sourceId) {
        if (sourceId == null || sourceId.trim().isEmpty()) {
            throw new InvalidGraphDataException("El ID de origen no puede ser nulo o vacio.");
        }
        this.sourceId = sourceId;
    }

    public String getDestinationId() { return destinationId; }
    public void setDestinationId(String destinationId) {
        if (destinationId == null || destinationId.trim().isEmpty()) {
            throw new InvalidGraphDataException("El ID de destino no puede ser nulo o vacio.");
        }
        this.destinationId = destinationId;
    }

    public boolean isIsDirected() { return isDirected; }
    public void setIsDirected(boolean isDirected) { this.isDirected = isDirected; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new InvalidGraphDataException("El peso (minutos) de la conexion debe ser mayor a cero.");
        }
        this.weight = weight;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new InvalidGraphDataException("La etiqueta de la conexion no puede ser nula o vacia.");
        }
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(id, edge.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Edge{id=" + id + ", source=" + sourceId + ", dest=" + destinationId + 
               ", directed=" + isDirected + ", weight=" + weight + "min, label=" + label + "}";
    }
}