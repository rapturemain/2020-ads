package ru.mail.polis.ads.bst;

import org.jetbrains.annotations.NotNull;

/**
 * AVL implementation of binary search tree.
 */
public class AvlBst<Key extends Comparable<Key>, Value> implements Bst<Key, Value> {

    private Node root;

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        int height;

        Node(Key key, Value value, int height) {
            this.key = key;
            this.value = value;
            this.height = height;
        }
    }

    @Override
    public Value get(@NotNull Key key) {
        Node node = getNode(key);
        return node != null ? node.value : null;
    }

    public Node getNode(Key key) {
        Node node = root;
        while (node != null && !node.key.equals(key)) {
            node = node.key.compareTo(key) > 0 ? node.left : node.right;
        }
        return node;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value){
        if (node == null) {
            return new Node(key, value, 1);
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            node.left = put(node.left, key, value);
        } else if (compareResult > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.height = 1 + nodeMaxChildrenHeight(node);
        node = balance(node);
        return node;
    }

    private Node balance(Node node) {
        int unbalancedValue = unbalancedValue(node);
        if (unbalancedValue > 1) {
            while (unbalancedValue(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (unbalancedValue < -1){
            while (unbalancedValue(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private int nodeMaxChildrenHeight(Node node) {
        return Math.max(nullProtectedHeight(node.left), nullProtectedHeight(node.right));
    }

    private int unbalancedValue(Node node) {
        return nullProtectedHeight(node.left) - nullProtectedHeight(node.right);
    }

    private int nullProtectedHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private Node rotateRight(Node node){
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        node.height = 1 + nodeMaxChildrenHeight(node);
        left.height = 1 + nodeMaxChildrenHeight(left);
        return left;
    }

    private Node rotateLeft(Node node){
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        node.height = 1 + nodeMaxChildrenHeight(node);
        right.height = 1 + nodeMaxChildrenHeight(right);
        return right;
    }

    @Override
    public Value remove(@NotNull Key key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        root = remove(root, key);
        return node.value;
    }

    private Node remove(Node node, Key key){
        if (node == null) {
            return null;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            node.left = remove(node.left, key);
        } else if (compareResult > 0) {
            node.right = remove(node.right, key);
        } else {
            node = removeActual(node);
        }
        return node;
    }

    private Node removeActual(Node node){
        if (node.right == null) {
            return node.left;
        }
        if (node.left == null) {
            return node.right;
        }

        Node buffer = node;
        node = min(buffer.right);
        node.right = removeMin(buffer.right);
        node.left = buffer.left;
        return node;
    }

    private Node removeMin(Node node) {
        if (node.left == null)
            return node.right;
        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public Key min() {
        return root == null ? null : min(root).key;
    }

    @Override
    public Value minValue() {
        return root == null ? null : min(root).value;
    }

    private Node min(Node node) {
        Node min = node;
        while (min.left != null){
            min = min.left;
        }
        return min;
    }

    @Override
    public Key max() {
        return  root == null ? null : max(root).key;
    }

    @Override
    public Value maxValue() {
        return  root == null ? null : max(root).value;
    }

    private Node max(Node node){
        Node max = node;
        while (max.right != null){
            max = max.right;
        }
        return max;
    }

    @Override
    public Key floor(@NotNull Key key) {
        Node node = floor(root, key);
        return node == null ? null : node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        if (key == node.key){
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return floor(node.left, key);
        }
        Node right = floor(node.right, key);
        return right == null ? node : right;
    }

    @Override
    public Key ceil(@NotNull Key key) {
        Node node = ceil(root, key);
        return node == null ? null : node.key;
    }

    private Node ceil(Node node, Key key) {
        if (node == null) {
            return null;
        }
        if (key == node.key){
            return node;
        }
        if (key.compareTo(node.key) > 0) {
            return ceil(node.right, key);
        }
        Node left = ceil(node.left, key);
        return left == null ? node : left;
    }

    @Override
    public int size() {
        return root == null ? 0 : size(root);
    }

    private int size(Node node) {
        return node == null ? 0 : 1 + size(node.left) + size(node.right);
    }

    @Override
    public int height() {
        return nullProtectedHeight(root);
    }
}
