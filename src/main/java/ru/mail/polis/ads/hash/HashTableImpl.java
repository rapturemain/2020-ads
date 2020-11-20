package ru.mail.polis.ads.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


/**
 * Hashtable realization for technopolis ads course
 * Table and buckets are based on the array of objects for greater performance (using other collections in java.util
 * will cause less performance due to additional checks (e.g. range check))
 * Bucket can be used as an abstract class for tree-like realization in case of big bucketSize value (still using
 * this array based realization for small bucketSize values)
 * @param <K> key type
 * @param <V> value type
 */
public class HashTableImpl<K, V> implements HashTable<K, V> {
    private class Entry {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * @param o - object to check, can be either Entry or Key
         * @return true, if keys are identical
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (key.getClass() == o.getClass()) {
                return key.equals((K) o);
            } else if (getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            return Objects.equals(key, entry.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode() * 31 + value.hashCode();
        }
    }

    private class Bucket {
        static final int EXISTED = 1;
        static final int NEW_ENTRY = 2;

        Object[] bucket;
        int size;
        private int iterator;

        Bucket(int size) {
            this.size = 0;
            this.bucket = new Object[size];
        }

        int add(K k, V v) {
            Entry e = getEntry(k);
            if (e != null) {
                e.value = v;
                return EXISTED;
            } else {
                for (int i = 0; i < bucket.length; i++) {
                    if (bucket[i] == null) {
                        bucket[i] = new Entry(k, v);
                        break;
                    }
                }
                size++;
                return NEW_ENTRY;
            }
        }

        Entry remove(K k) {
            int index = getEntryIndex(k);
            if (index == -1) {
                return null;
            }
            Entry buffer = cast(index);
            bucket[index] = null;
            return buffer;
        }

        Entry getEntry(@NotNull K k) {
            for (int i = 0; i < size; i++) {
                if (bucket[i] != null && bucket[i].equals(k)) {
                    return cast(i);
                }
            }
            return null;
        }

        int getEntryIndex(@NotNull K k) {
            for (int i = 0; i < size; i++) {
                if (bucket[i] != null && bucket[i].equals(k)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Lightweight functions that provide more performance than the classical one by reduced functionality
         * Used for resize operation
         */
        Entry next() {
            while (iterator < size && bucket[iterator] == null) {
                iterator++;
            }
            return iterator >= size ? null : cast(iterator++);
        }

        void resetIterator() {
            iterator = 0;
        }

        void add(Entry entry) {
            bucket[size++] = entry;
        }

        private Entry cast(int index) {
            return (Entry) bucket[index];
        }
    }

    private static final int MAX_SIZE = 1 << 30;

    private Object[] hashTable;
    private int tableSize;
    private int bucketSize;
    private int size = 0;
    private boolean usingIdentityHashCode = false;

    public HashTableImpl(int initialTableSize, int initialBucketSize) {
        if (initialTableSize < 1 || initialBucketSize < 1) {
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
        this.bucketSize = initialBucketSize;
        this.hashTable = new Object[tableSize];
        for (int i = 0; i < tableSize; i++) {
            hashTable[i] = new Bucket(bucketSize);
        }
    }

    public HashTableImpl() {
        this(16, 12);
    }

    @Override
    public @Nullable V get(@NotNull K k) {
        Entry entry = getBucket(hashCode(k)).getEntry(k);
        return entry == null ? null : entry.value;
    }

    @Override
    public void put(@NotNull K k, @NotNull V v) {
        Bucket bucket = getBucket(hashCode(k));
        int result = bucket.add(k, v);
        if (result == Bucket.NEW_ENTRY) {
            size++;
            if (bucket.size == bucketSize) {
                usingIdentityHashCode = false;
                resize();
            }
        }
    }

    /**
     * Tries to increase table capacity saving initial proportions of tableSize and bucketSize
     * If increasing in not possible, tries to use identityHashCode,
     * if even this is not helps, than throws OutOfMemoryError
     */
    private void resize() {
        if (tableSize >= MAX_SIZE || bucketSize >= MAX_SIZE) {
            throw new OutOfMemoryError();
        }
        int newSize = tableSize;
        int newBucketSize = bucketSize;
        boolean success = false;
        while (!success) {
            // If we cannot increase table even more, try to increase bucketSize
            if (newSize >= MAX_SIZE) {
                while (!success) {
                    if (newBucketSize >= MAX_SIZE) {
                        resizeIdentityCheck();
                        return;
                    }
                    newBucketSize <<= 1;
                    success = resize(newSize, newBucketSize);
                }
                continue;
            }
            // If we cannot increase bucketSize even more, try to increase tableSize
            if (newBucketSize >= MAX_SIZE) {
                while (!success) {
                    if (newSize >= MAX_SIZE) {
                        resizeIdentityCheck();
                        return;
                    }
                    newSize <<= 1;
                    success = resize(newSize, newBucketSize);
                }
                continue;
            }
            // Else try to increase both saving initial proportions
            if ((double) newSize / newBucketSize <= (double) tableSize / bucketSize) {
                newSize <<= 1;
            } else {
                newBucketSize <<= 1;
            }
            success = resize(newSize, newBucketSize);
        }
        tableSize = newSize;
        bucketSize = newBucketSize;
    }

    private void resizeIdentityCheck() {
        if (!usingIdentityHashCode) {
            usingIdentityHashCode = true;
            resize();
        } else {
            throw new OutOfMemoryError();
        }
    }

    /**
     * Tries to copy old table into new table.
     * If bucketSize requirement do not meet, returns false with rejecting copying result.
     * @return if copy was successful
     */
    private boolean resize(int newSize, int newBucketSize) {
        for (int i = 0; i < tableSize; i++) {
            ((Bucket) hashTable[i]).resetIterator();
        }

        Object[] newTable = new Object[newSize];
        for (int i = 0; i < newSize; i++) {
            newTable[i] = new Bucket(newBucketSize);
        }

        for (int i = 0; i < tableSize; i++) {
            Bucket bucket = getBucket(i);
            Entry entry;
            while ((entry = bucket.next()) != null) {
                Bucket newBucket = (Bucket) newTable[hashCode(entry.key, newSize)];
                newBucket.add(entry);
                if (newBucket.size == newBucketSize) {
                    return false;
                }
            }
        }

        hashTable = newTable;
        return true;
    }

    @Override
    public @Nullable V remove(@NotNull K k) {
        Entry entry = getBucket(hashCode(k)).remove(k);
        if (entry == null) {
            return null;
        }
        size--;
        return entry.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Bucket getBucket(int index) {
        return (Bucket) hashTable[index];
    }

    private int hashCode(K key) {
        return hashCode(key, tableSize);
    }

    private int hashCode(K key, int base) {
        return (usingIdentityHashCode ? System.identityHashCode(key) : key.hashCode()) & (base - 1);
    }
}
