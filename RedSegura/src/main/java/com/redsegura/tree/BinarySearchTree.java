package com.redsegura.tree;

import com.redsegura.model.Vertex;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    // Clase interna para el Nodo del ABB
    public static class Node {
        Vertex vertex;
        Node left;
        Node right;

        public Node(Vertex vertex) {
            this.vertex = vertex;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int searchSteps; // Contador de pasos para la Consulta Q1

    public BinarySearchTree() {
        this.root = null;
        this.searchSteps = 0;
    }

    public Node getRoot() {
        return root;
    }

    // 1. INSERCION EN EL ABB
    public void insert(Vertex vertex) {
        root = insertRec(root, vertex);
    }

    private Node insertRec(Node current, Vertex vertex) {
        if (current == null) {
            return new Node(vertex);
        }
        // Comparamos alfabeticamente por la etiqueta (ej. "Hospital Universitario")
        int cmp = vertex.getLabel().compareToIgnoreCase(current.vertex.getLabel());
        if (cmp < 0) {
            current.left = insertRec(current.left, vertex);
        } else if (cmp > 0) {
            current.right = insertRec(current.right, vertex);
        }
        return current;
    }

    // 2. BUSQUEDA EN EL ABB (Retorna el Vertice y cuenta los pasos para Q1)
    public Vertex search(String label) {
        searchSteps = 0;
        Node result = searchRec(root, label);
        return (result != null) ? result.vertex : null;
    }

    private Node searchRec(Node current, String label) {
        if (current == null) {
            return null;
        }
        searchSteps++; // Incrementamos pasos al visitar el nodo
        
        int cmp = label.compareToIgnoreCase(current.vertex.getLabel());
        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return searchRec(current.left, label);
        } else {
            return searchRec(current.right, label);
        }
    }

    public int getLastSearchSteps() {
        return searchSteps;
    }

    // 3. ELIMINACION EN EL ABB
    public void delete(String label) {
        root = deleteRec(root, label);
    }

    private Node deleteRec(Node current, String label) {
        if (current == null) {
            return null;
        }

        int cmp = label.compareToIgnoreCase(current.vertex.getLabel());
        if (cmp < 0) {
            current.left = deleteRec(current.left, label);
        } else if (cmp > 0) {
            current.right = deleteRec(current.right, label);
        } else {
            // Caso 1: Sin hijos o un solo hijo
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // Caso 2: Dos hijos -> Sucesor in-order (menor del subarbol derecho)
            current.vertex = findMin(current.right);
            current.right = deleteRec(current.right, current.vertex.getLabel());
        }
        return current;
    }

    private Vertex findMin(Node root) {
        Vertex minVal = root.vertex;
        while (root.left != null) {
            minVal = root.left.vertex;
            root = root.left;
        }
        return minVal;
    }

    // 4. ALTURA DEL ARBOL
    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node current) {
        if (current == null) {
            return -1;
        }
        int leftHeight = heightRec(current.left);
        int rightHeight = heightRec(current.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // 5. RECORRIDOS (InOrder, PreOrder, PostOrder)
    public List<Vertex> inOrder() {
        List<Vertex> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private void inOrderRec(Node node, List<Vertex> list) {
        if (node != null) {
            inOrderRec(node.left, list);
            list.add(node.vertex);
            inOrderRec(node.right, list);
        }
    }

    public List<Vertex> preOrder() {
        List<Vertex> list = new ArrayList<>();
        preOrderRec(root, list);
        return list;
    }

    private void preOrderRec(Node node, List<Vertex> list) {
        if (node != null) {
            list.add(node.vertex);
            preOrderRec(node.left, list);
            preOrderRec(node.right, list);
        }
    }

    public List<Vertex> postOrder() {
        List<Vertex> list = new ArrayList<>();
        postOrderRec(root, list);
        return list;
    }

    private void postOrderRec(Node node, List<Vertex> list) {
        if (node != null) {
            postOrderRec(node.left, list);
            postOrderRec(node.right, list);
            list.add(node.vertex);
        }
    }
}