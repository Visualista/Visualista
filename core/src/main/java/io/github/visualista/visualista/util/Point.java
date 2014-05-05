package io.github.visualista.visualista.util;

public class Point {
	private final int x;
	private final int y;

	public Point(int width, int height) {
		this.x = width;
		this.y = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + y;
		result = prime * result + x;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;
		Point other = (Point) obj;
		if (y != other.y)
			return false;
		if (x != other.x)
			return false;
		return true;
	}

}
