package gesto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fernando Brito e Abreu (fba@iscte-iul.pt)
 * @version 1.1 (July 2015)
 */
public class Trajectory
{

	private SubjectType type;
	private String subject;
	private List<Point> points = new ArrayList<Point>();

	/**
	 * @param subject
	 * @param points
	 */
	public Trajectory(SubjectType type, String subject, List<Point> points)
	{
		this.type = type;
		this.subject = subject;
		for (Point p : points)
			this.points.add(p);
	}

	public SubjectType getType()
	{
		return type;
	}

	public String getSubject()
	{
		return subject;
	}

	public List<Point> getPoints()
	{
		return points;
	}

	/**
	 * @return the time walking in miliseconds
	 */
	public int timeWalking()
	{
		int walking = 0;
		for (Point p : this.points)
			walking += p.getTimeFromPrevious();
		return walking;
	}
	
	/**
	 * @return the total time stopped in miliseconds
	 */
	public int timeStopped()
	{
		int stopped = 0;
		for (Point p : this.points)
			stopped += p.getTimeStopped();
		return stopped;
	}
	
	/**
	 * @return the total duration in seconds
	 */
	public int totalDuration() 
	{
		return (timeWalking()+ timeStopped()) / 1000;
	}
	
	/**
	 * @return the total distance walked in meters
	 */
	public int distanceWalked()
	{
		int distance = 0;
		for (Point p : this.points)
			distance += p.getDistanceFromPrevious();
		return distance;
	}

	/**
	 * @return the average speed in m/s (meters per second)
	 */
	public double averageSpeed()
	{
		return distanceWalked() * 1000.0 / (timeWalking()+ timeStopped());
	}
	
	/**
	 * @param another trajectory
	 * @return the Dynamic Time Warping (DTW) distance to the named trajectory
	 */
	public double distanceTo(Trajectory another)
	{

		int n = this.points.size();
		int m = another.points.size();

		double[][] DTW = new double[n + 1][m + 1];

		for (int i = 1; i <= n; i++)
			DTW[i][0] = Double.MAX_VALUE;

		for (int j = 1; j <= m; j++)
			DTW[0][j] = Double.MAX_VALUE;

		DTW[0][0] = 0;

		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++)
			{
				double cost = this.points.get(i - 1).distanceTo(another.points.get(j - 1));
				DTW[i][j] = cost + Math.min(
						Math.min(DTW[i - 1][j], // insertion
								DTW[i][j - 1]), // deletion
						DTW[i - 1][j - 1]); // match
			}
		return DTW[n][m];
	}

	/**
	 * @param another trajectory
	 * @param w window size
	 * @return the Dynamic Time Warping (DTW) distance to the named trajectory
	 */
//	public double distanceTo(Trajectory another, int w)
//	{
//
//		int n = this.points.size();
//		int m = another.points.size();
//
//		double[][] DTW = new double[n + 1][m + 1];
//
//		w = Math.max(w, Math.abs(n - m)); // adapt window size (*)
//
//		for (int i = 0; i <= n; i++)
//			for (int j = 0; j <= m; j++)
//				DTW[i][j] = Double.MAX_VALUE;
//
//		DTW[0][0] = 0;
//
//		for (int i = 1; i <= n; i++)
//			for (int j = Math.max(1, i - w); j < Math.min(m, i + w); j++)
//			{
//				double cost = this.points.get(i - 1).distanceTo(another.points.get(j - 1));
//				DTW[i][j] = cost + Math.min(
//						Math.min(DTW[i - 1][j], // insertion
//								DTW[i][j - 1]), // deletion
//						DTW[i - 1][j - 1]); // match
//			}
//		return DTW[n][m];
//	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Trajectory " + subject + " (" + points.size() + " points) ["+ type +"] Distance walked->" + distanceWalked() + " meters\n"
		+ "Time walking->" + timeWalking()/1000 + " s [" + (timeWalking() * 100) / (timeWalking()+ timeStopped()) +"%] " 
	  + "Time stopped->" + timeStopped()/1000 + " s [" + (timeStopped() * 100) / (timeWalking()+ timeStopped()) +"%] Total duration->" 
	  + totalDuration() + " s\n"
	  + "Average speed (including stops)->" + String.format("%.3f", averageSpeed()) + " m/s "
	  + (this==Model.mostCommon() ? "[MOST COMMON]" : (this==Model.mostDissimilar() ? "[MOST UNCOMMON]" : ""))
	  + (this==Model.longer() ? "[LONGER]" : (this==Model.shorter() ? "[SHORTER]" : ""))
	  + (this==Model.faster() ? "[FASTER]" : (this==Model.slower() ? "[SLOWER]" : ""));
	}
}
