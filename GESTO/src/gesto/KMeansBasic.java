package gesto;

import java.util.ArrayList;
import java.util.List;

import voronoi2.GraphEdge;
import voronoi2.Voronoi;

public abstract class KMeansBasic
{
	// Number of Clusters. This metric should be related to the number of points
	private static int NUM_CLUSTERS = 6;

	private static List<Point> points = new ArrayList<Point>();
	private static List<Cluster> clusters = new ArrayList<Cluster>();

	public static void setPoints(List<Point> list)
	{
		points = list;
		clusters.clear();
	}
	
	public static List<Point> clusterCentroids(double minX, double maxX, double minY, double maxY)
	{
		// Create Clusters
		// Set Random Centroids
		for (int i = 0; i < NUM_CLUSTERS; i++)
		{
			Cluster cluster = new Cluster(i);
			Point centroid = Point.createRandomPoint(minX, maxX, minY, maxY);
			cluster.setCentroid(centroid);
			clusters.add(cluster);
		}

		// Print Initial state
		plotClusters();

		return calculate();
	}
		
	public static List<GraphEdge> voronoiEdges(List<Point> centroids, double minX, double maxX, double minY, double maxY)
	{
		double[] xValuesIn = new double[centroids.size()];
		double[] yValuesIn = new double[centroids.size()];

		int i = 0;
		for (Point p : centroids)
		{
			xValuesIn[i] = p.getX();
			yValuesIn[i] = p.getY();
			i++;
		};

		Voronoi v = new Voronoi(5);

		return v.generateVoronoi(xValuesIn, yValuesIn, minX, maxX, minY, maxY);
	}

	
	private static void plotClusters()
	{
		for (int i = 0; i < NUM_CLUSTERS; i++)
		{
			Cluster c = clusters.get(i);
			//c.plotCluster();
		}
	}

	// The process to calculate the K Means, with iterating method.
	public static List<Point> calculate()
	{
		boolean finish = false;
		int iteration = 0;
		List<Point> currentCentroids = null;
		List<Point> lastCentroids = null;

		// Add in new data, one at a time, recalculating centroids with each new one.
		while (!finish)
		{
			// Clear cluster state
			clearClusters();

			lastCentroids = getCentroids();

			// Assign points to the closer cluster
			assignCluster();

			// Calculate new centroids.
			calculateCentroids();

			iteration++;

			currentCentroids = getCentroids();

			// Calculates total distance between new and old Centroids
			double distance = 0;
			for (int i = 0; i < lastCentroids.size(); i++)
			{
				distance += lastCentroids.get(i).distanceTo(currentCentroids.get(i));
			}
//			System.out.println("#################");
//			System.out.println("Iteration: " + iteration);
//			System.out.println("Centroid distances: " + distance);
			plotClusters();

			if (distance == 0)
			{
				finish = true;
			}
		}
		return currentCentroids;
	}

	private static void clearClusters()
	{
		for (Cluster cluster : clusters)
		{
			cluster.clear();
		}
	}

	private static List<Point> getCentroids()
	{
		List<Point> centroids = new ArrayList<Point>(NUM_CLUSTERS);
		for (Cluster cluster : clusters)
		{
			Point aux = cluster.getCentroid();
			Point point = new Point(aux.getX(), aux.getY());
			centroids.add(point);
		}
		return centroids;
	}

	private static void assignCluster()
	{
		double max = Double.MAX_VALUE;
		double min = max;
		int cluster = 0;
		double distance = 0.0;

		for (Point point : points)
		{
			min = max;
			for (int i = 0; i < NUM_CLUSTERS; i++)
			{
				Cluster c = clusters.get(i);
				distance = point.distanceTo(c.getCentroid());
				if (distance < min)
				{
					min = distance;
					cluster = i;
				}
			}
			point.setCluster(cluster);
			clusters.get(cluster).addPoint(point);
		}
	}

	private static void calculateCentroids()
	{
		for (Cluster cluster : clusters)
		{
			double sumX = 0;
			double sumY = 0;
			List<Point> list = cluster.getPoints();
			int n_points = list.size();

			for (Point point : list)
			{
				sumX += point.getX();
				sumY += point.getY();
			}

			Point centroid = cluster.getCentroid();
			if (n_points > 0)
			{
				double newX = sumX / n_points;
				double newY = sumY / n_points;
				centroid.setX(newX);
				centroid.setY(newY);
			}
		}
	}
}
