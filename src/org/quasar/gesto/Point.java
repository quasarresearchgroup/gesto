package org.quasar.gesto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point
{

	private static int canvasWidth;
	private static int canvasHeight;

	public static void setCanvas(int width, int height)
	{
		canvasWidth = width;
		canvasHeight = height;
	}

	private int order;
	private double x;
	private double y;
	private double distanceFromPrevious;
	private int timeStopped;
	private int timeFromPrevious;
	private int cluster_number = 0;

	public Point(int order, double x, double y, double distanceFromPrevious, int timeStopped, int timeFromPrevious)
	{
		this.order = order;
		this.x = x;
		this.y = y;
		this.distanceFromPrevious = distanceFromPrevious;
		this.timeStopped = timeStopped;
		this.timeFromPrevious = timeFromPrevious;
	}

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public int getAction()
	{
		return order;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public void setCluster(int n)
	{
		this.cluster_number = n;
	}

	public int getCluster()
	{
		return this.cluster_number;
	}

	public int getCanvasX()
	{
		return (int) Math.round(y * 8.4) + 230;
	}

	public int getCanvasY()
	{
		return (int) Math.round(x * 8.3) + canvasHeight / 2 + 20;
	}

	public double distanceTo(Point other)
	{
		double deltaX = x - other.x;
		double deltaY = y - other.y;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	/**
	 * @return the distanceFromPrevious
	 */
	public double getDistanceFromPrevious()
	{
		return distanceFromPrevious;
	}

	/**
	 * @return the timeStopped
	 */
	public int getTimeStopped()
	{
		return timeStopped;
	}

	/**
	 * @return the timeFromPrevious
	 */
	public int getTimeFromPrevious()
	{
		return timeFromPrevious;
	}

	public double getSpeedFromPrevious()
	{
		// return distanceFromPrevious / ((timeFromPrevious+timeStopped)/1000.0);
		return distanceFromPrevious / (timeFromPrevious / 1000.0);
	}

	public static Point createRandomPoint(double minX, double maxX, double minY, double maxY)
	{
		Random r = new Random();
		double x = minX + (maxX - minX) * r.nextDouble();
		double y = minY + (maxY - minY) * r.nextDouble();
		return new Point(x, y);
	}
	

	// Creates random point
	public static Point createRandomPoint(int min, int max)
	{
		Random r = new Random();
		double x = min + (max - min) * r.nextDouble();
		double y = min + (max - min) * r.nextDouble();
		return new Point(x, y);
	}

	public static List<Point> createRandomPoints(int min, int max, int number)
	{
		List<Point> points = new ArrayList<Point>(number);
		for (int i = 0; i < number; i++)
		{
			points.add(createRandomPoint(min, max));
		}
		return points;
	}

	public String toString()
	{
		return order + "\t(" + x + ", " + y + ")\t" + getDistanceFromPrevious() + "\t" + getTimeStopped() + "\t" + getTimeFromPrevious()
				+ "\t" + String.format("%.3f", getSpeedFromPrevious());
	}


}