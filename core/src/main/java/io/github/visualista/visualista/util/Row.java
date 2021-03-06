package io.github.visualista.visualista.util;

import java.util.Arrays;

public class Row<E> {
    private final E[] row;

    @SuppressWarnings("unchecked")
    public Row(final int size) {
        row = (E[]) new Object[size];
    }

    private Row(final E[] row) {
        this.row = row;
    }

    public Row(final Row<E> row) {
        this(row.row.clone());
    }

    public int getLength() {
        return row.length;
    }

    public void setAt(final int i, final E object) {
        row[i] = object;
    }

    public E getAt(final int i) {
        return row[i];
    }

    @Override
    public String toString() {
        return "Row [row=" + Arrays.toString(row) + "]";
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(row);
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Row)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Row other = (Row) obj;
        if (!Arrays.deepEquals(row, other.row)) {
            return false;
        }
        return true;
    }
}
