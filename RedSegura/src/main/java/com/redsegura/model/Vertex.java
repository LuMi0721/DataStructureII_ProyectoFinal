package com.redsegura.model;

import com.redsegura.exception.InvalidGraphDataException;
import java.util.Objects;

public class Vertex {
    private String id;     
    private String label;  
    private String type;   

    public Vertex(String id, String label, String type) {
        setId(id);
        setLabel(label);
        setType(type);
    }

    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidGraphDataException("El ID del vertice no puede ser nulo o vacio.");
        }
        this.id = id;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new InvalidGraphDataException("La etiqueta del vertice no puede ser nula o vacia.");
        }
        this.label = label;
    }

    public String getType() { return type; }
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new InvalidGraphDataException("El tipo del vertice no puede ser nulo o vacio.");
        }
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Vertex{id=" + id + ", label=" + label + ", type=" + type + "}";
    }
}