package ru.mail.polis.ads.bst;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * LLRB implementation of binary search tree.
 */
public class RedBlackBst<Key extends Comparable<Key>, Value>
        implements Bst<Key, Value> {

    private static final boolean BLACK = false;
    private static final boolean RED = true;

    private Node root;

    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        boolean color;

        Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    @Nullable
    @Override
    public Value get(@NotNull Key key) {
        Node node = get(root, key);

        return node == null ? null : node.value;
    }

    private Node get(Node node, Key key) {
        if (node == null) return null;
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            return get(node.left, key);
        } else if (compareResult > 0) {
            return get(node.right, key);
        }
        return node;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, RED);
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            node.left = put(node.left, key, value);
        } else if (compareResult > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return fixUp(node);
    }

    private Node fixUp(Node node) {
        if (isRed(node.right) && isBlack(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        right.color = node.color;
        node.color = RED;
        return right;
    }

    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        left.color = node.color;
        node.color = RED;
        return left;
    }

    private void flipColors(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    @Nullable
    @Override
    public Value remove(@NotNull Key key) {
        if (root == null) {
            return null;
        }
        Value buffer = get(key);
        if (buffer != null) {
            root = remove(root, key);
        }
        return buffer;
    }

    private Node remove(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0 && node.left != null) {
            if (isBlack(node.left) && isBlack(node.left.left)) {
                node = moveRedToLeft(node);
            }
            node.left = remove(node.left, key);
        } else if (isRed(node.left)) {
            node = rotateRight(node);
            node.right = remove(node.right, key);
        } else if (compareResult == 0 && node.right == null) {
            return null;
        } else {
            if (node.right != null && isBlack(node.right) && isBlack(node.right.left)) {
                node = moveRedToRight(node);
            }
            if (key.compareTo(node.key) == 0) {
                Node min = min(node.right);
                node.key = min.key;
                node.value = min.value;
                node.right = deleteMin(node.right != null ? node.right : null);
            } else {
                node.right = remove(node.right, key);
            }
        }
        return fixUp(node);
    }

    private Node moveRedToLeft(Node node) {
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    private Node deleteMin(Node node) {
        if (node == null || node.left == null) {
            return null;
        }
        if (isBlack(node.left) && isBlack(node.left.left)) {
            node = moveRedToLeft(node);
        }
        node.left = deleteMin(node.left);
        return fixUp(node);
    }

    private Node moveRedToRight(Node node) {
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    @Nullable
    @Override
    public Key min() {
        return root == null ? null : min(root).key;
    }


    @Nullable
    @Override
    public Value minValue() {
        return root == null ? null : min(root).value;
    }

    private Node min(Node node) {
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    @Nullable
    @Override
    public Key max() {
        return root == null ? null : max(root).key;
    }

    @Nullable
    @Override
    public Value maxValue() {
        return root == null ? null : max(root).value;
    }

    private Node max(Node node) {
        Node max = node;
        while (max.right != null) {
            max = max.right;
        }
        return max;
    }

    @Nullable
    @Override
    public Key floor(@NotNull Key key) {
        if (root == null) {
            return null;
        }
        Node node = floor(root, key);
        return node == null ? null : node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult == 0) {
            return node;
        }
        if (compareResult < 0) {
            return floor(node.left, key);
        }
        Node right = floor(node.right, key);
        return right != null ? right : node;
    }

    @Nullable
    @Override
    public Key ceil(@NotNull Key key) {
        if (root == null) {
            return null;
        }
        Node node = ceil(root, key);
        return node == null ? null : node.key;
    }

    private Node ceil(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult == 0) {
            return node;
        }
        if (compareResult > 0) {
            return ceil(node.right, key);
        }
        Node left = ceil(node.left, key);
        return left != null ? left : node;
    }

    @Override
    public int size() {
        return root == null ? 0 : size(root);
    }

    private int size(Node node) {
        return node == null ? 0 : size(node.left) + size(node.right) + 1;
    }
}