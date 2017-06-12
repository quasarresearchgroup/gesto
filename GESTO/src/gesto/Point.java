package gesto;

public class Point {

	private static int canvasWidth;
	private static int canvasHeight;

	public static void setCanvas(int width, int height) {
		canvasWidth = width;
		canvasHeight = height;
	}

	private int order;
	private double x;
	private double y;
	private double distanceFromPrevious;
	private int timeStopped;
	private int timeFromPrevious;

	public Point(int order, double x, double y, double distanceFromPrevious, int timeStopped, int timeFromPrevious) {
		this.order = order;
		this.x = x;
		this.y = y;
		this.distanceFromPrevious = distanceFromPrevious;
		this.timeStopped = timeStopped;
		this.timeFromPrevious = timeFromPrevious;
	}

	public int getAction() {
		return order;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getCanvasX() {
		return (int) Math.round(y * 8.4) + 230;
	}

	public int getCanvasY() {
		return (int) Math.round(x * 8.3) + canvasHeight / 2 + 20;
	}

	public double distanceTo(Point other) {
		double deltaX = x - other.x;
		double deltaY = y - other.y;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	/**
	 * @return the distanceFromPrevious
	 */
	public double getDistanceFromPrevious() {
		return distanceFromPrevious;
	}

	/**
	 * @return the timeStopped
	 */
	public int getTimeStopped() {
		return timeStopped;
	}

	/**
	 * @return the timeFromPrevious
	 */
	public int getTimeFromPrevious() {
		return timeFromPrevious;
	}

	public double getSpeedFromPrevious() {
//		return distanceFromPrevious / ((timeFromPrevious+timeStopped)/1000.0);
		return distanceFromPrevious / (timeFromPrevious/1000.0);
		}
	
	public String toString() {
		return order + "\t(" + x + ", " + y + ")\t" + 
				getDistanceFromPrevious() + "\t" +
				getTimeStopped() + "\t" +
				getTimeFromPrevious() + "\t" +
				String.format("%.3f", getSpeedFromPrevious());
	}
}