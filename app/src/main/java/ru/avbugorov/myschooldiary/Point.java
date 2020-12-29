package ru.avbugorov.myschooldiary;

public class Point {
	public int x;
	public int y;


	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			return (this.x == p.x) && (this.y == p.y);
		}
		return false;
	}
}
