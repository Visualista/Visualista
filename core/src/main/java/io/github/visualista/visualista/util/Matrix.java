package io.github.visualista.visualista.util;

import java.util.Arrays;

public class Matrix<E> implements IMatrixSet<E> {
    private final Row<E>[] matrix;

    @SuppressWarnings("unchecked")
    public Matrix(final Dimension size) {
        if (size.getHeight() < 1) {
            throw new IllegalArgumentException(
                    "Matrix height <1 is not allowed. Sent in was: "
                            + size.getHeight());
        }
        if (size.getWidth() < 1) {
            throw new IllegalArgumentException(
                    "Matrix width <1 is not allowed. Sent in was: "
                            + size.getWidth());
        }
        matrix = new Row[size.getHeight()];
        for (int i = 0; i < matrix.length; ++i) {
            matrix[i] = new Row<E>(size.getWidth());
        }
    }

    @Override
    public Dimension getSize() {
        return new Dimension(matrix[0].getLength(), matrix.length);
    }

    @Override
    public void fillWith(final IObjectCreator<E> creator) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].getLength(); ++j) {
                matrix[i].setAt(j, creator.createObject());
            }
        }

    }

    @Override
    public E getAt(final Point from) {
        return matrix[from.getY()].getAt(from.getX());
    }

    @Override
    public void setAt(final Point where, final E object) {
        matrix[where.getY()].setAt(where.getX(), object);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(matrix);
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
        if (!(obj instanceof Matrix)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Matrix other = (Matrix) obj;
        if (!Arrays.deepEquals(matrix, other.matrix)) {
            return false;
        }
        return true;
    }

}
