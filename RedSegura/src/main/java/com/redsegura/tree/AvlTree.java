package com.redsegura.tree;

import com.redsegura.model.Vertex;
import java.util.ArrayList;
import java.util.List;

public class AvlTree {

    public static class Node {
        Vertex vertex;
        int height;
        Node left;
        Node right;

        public Node(Vertex vertex) {
            this.vertex = vertex;
            this.height = 1; // Altura inicial de un nodo hoja es 1
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public AvlTree() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    // Obtener la altura de un nodo
    private int height(Node n) {
        if (n == null) return 0;
        return n.height;
    }

    // Obtener el factor de balance de un nodo
    private int getBalance(Node n) {
        if (n == null) return 0;
        return height(n.left) - height(n.right);
    }

    // 1. ROTACION SIMPLE A LA DERECHA (Caso LL)
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotacion
        x.right = y;
        y.left = T2;

        // Actualizar alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // Nueva raiz
    }

    // 2. ROTACION SIMPLE A LA IZQUIERDA (Caso RR)
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotacion
        y.left = x;
        x.right = T2;

        // Actualizar alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y; // Nueva raiz
    }

    // INSERCION AVL CON BALANCEO AUTOMATICO
    public void insert(Vertex vertex) {
        root = insertRec(root, vertex);
    }

    private Node insertRec(Node node, Vertex vertex) {
        // 1. Insercion normal de ABB
        if (node == null) {
            return new Node(vertex);
        }

        int cmp = vertex.getLabel().compareToIgnoreCase(node.vertex.getLabel());
        if (cmp < 0) {
            node.left = insertRec(node.left, vertex);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, vertex);
        } else {
            return node; // No se permiten duplicados exactos
        }

        // 2. Actualizar altura del nodo actual
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Obtener factor de balance
        int balance = getBalance(node);

        // --- APLICACION DE LAS 4 ROTACIONES OBLIGATORIAS ---

        // Caso LL (Left Left)
        if (balance > 1 && vertex.getLabel().compareToIgnoreCase(node.left.vertex.getLabel()) < 0) {
            return rightRotate(node);
        }

        // Caso RR (Right Right)
        if (balance < -1 && vertex.getLabel().compareToIgnoreCase(node.right.vertex.getLabel()) > 0) {
            return leftRotate(node);
        }

        // Caso LR (Left Right - Rotacion doble)
        if (balance > 1 && vertex.getLabel().compareToIgnoreCase(node.left.vertex.getLabel()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso RL (Right Left - Rotacion doble)
        if (balance < -1 && vertex.getLabel().compareToIgnoreCase(node.right.vertex.getLabel()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // ELIMINACION AVL
    public void delete(String label) {
        root = deleteRec(root, label);
    }

    private Node deleteRec(Node root, String label) {
        if (root == null) {
            return null;
        }

        int cmp = label.compareToIgnoreCase(root.vertex.getLabel());
        if (cmp < 0) {
            root.left = deleteRec(root.left, label);
        } else if (cmp > 0) {
            root.right = deleteRec(root.right, label);
        } else {
            // Nodo con un solo hijo o sin hijos
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left == null) ? root.right : root.left;
                if (temp == null) {
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                // Nodo con dos hijos: obtener el menor del subarbol derecho
                Node temp = minValueNode(root.right);
                root.vertex = temp.vertex;
                root.right = deleteRec(root.right, temp.vertex.getLabel());
            }
        }

        if (root == null) {
            return null;
        }

        // Actualizar altura
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // Obtener balance y rebalancear
        int balance = getBalance(root);

        // LL
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }
        // LR
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        // RR
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }
        // RL
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Recorrido InOrder para verificar ordenamiento
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
}