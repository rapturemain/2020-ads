package ru.mail.polis.ads.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HashTableImpl<K, V> implements HashTable<K, V> {
    private static class Entry<KE, VE> {
        final KE key;
        VE value;
        Entry<KE, VE> next;

        Entry(KE key, VE value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int MAX_SIZE = 1 << 30;
    private static final double FACTOR = 0.75;

    private Entry<K, V>[] hashTable;
    private int tableSize;
    private int threshold;
    private int size = 0;
    private boolean usingIdentityHashCode = false;

    public HashTableImpl(int initialTableSize) {
        if (initialTableSize < 1) {
            throw new IllegalArgumentException();
        }
        // Be sure we have tableSize equals 2^n
        if (initialTableSize > MAX_SIZE) {
            this.tableSize = MAX_SIZE;
        } else {
            int buffer = 1;
            for (int i = 0; i < 30; i++) {
                if (initialTableSize > buffer) {
                    buffer <<= 1;
                } else {
                    this.tableSize = buffer >>> 1;
                }
            }
        }
        this.threshold = (int) (tableSize * FACTOR);
        this.hashTable = new Entry[tableSize];
    }

    public HashTableImpl() {
        this(16);
    }

    @Override
    public @Nullable V get(@NotNull K k) {
        Entry<K, V> entry = hashTable[hashCode(k)];
        while (entry != null && !entry.key.equals(k)) {
            entry = entry.next;
        }
        return entry == null ? null : entry.value;
    }

    @Override
    public void put(@NotNull K k, @NotNull V v) {
        if (size > threshold) {
            resize();
        }

        int index = hashCode(k);
        Entry<K, V> entry = hashTable[index];

        // Empty bucket check
        if (entry == null) {
            hashTable[index] = new Entry<>(k, v);
            size++;
            return;
        }
        if (entry.key.equals(k)) {
            entry.value = v;
            return;
        }

        // Find entry in bucket and replace/add new value
        boolean cmp = false;
        while (entry.next != null && !(cmp = entry.next.key.equals(k))) {
            entry = entry.next;
        }
        if (cmp) {
            entry.next.value = v;
        } else {
            entry.next = new Entry<>(k ,v);
            size++;
        }
    }

    private void resize() {
        // Check if we can resize more, if we cannot check if we are using identityHashCode, if we are
        // we cannot resize more, if we are not we can try to use it
        if (tableSize >= MAX_SIZE) {
            threshold = Integer.MAX_VALUE;
            return;
        } else {
            tableSize <<= 1;
            threshold = (int) (tableSize * FACTOR);
        }

        Entry<K, V>[] newTable = new Entry[tableSize];

        for (int i = 0; i < hashTable.length; i++) {
            Entry<K, V> entry = hashTable[i];
            while (entry != null) {
                Entry<K, V> buffer = entry.next;
                entry.next = null;

                int index = hashCode(entry.key);
                Entry<K, V> newEntry = newTable[index];

                if (newEntry == null) {
                    newTable[index] = entry;
                } else {
                    while (newEntry.next != null) {
                        newEntry = newEntry.next;
                    }
                    newEntry.next = entry;
                }

                entry = buffer;
            }
        }

        hashTable = newTable;
    }

    @Override
    public @Nullable V remove(@NotNull K k) {
        int index = hashCode(k);
        Entry<K, V> entry = hashTable[index];

        // Check if first element
        if (entry == null) {
            return null;
        }
        if (entry.key.equals(k)) {
            hashTable[index] = entry.next;
            size--;
            return entry.value;
        }

        // Check other entries in this bucket
        while (entry.next != null && !entry.next.key.equals(k)) {
            entry = entry.next;
        }
        if (entry.next == null) {
            return null;
        } else {
            V v = entry.next.value;
            entry.next = entry.next.next;
            size--;
            return v;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int hashCode(K key) {
        return key.hashCode() & (tableSize - 1);
    }
}
