package gesto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import gesto.gui.Configuration;

public abstract class Model
{

	private static final String TRAJECTORIES_POINTS_FILE = "data/PercursosTemporizados.txt";

	private static List<Trajectory> allTrajectories = new ArrayList<Trajectory>();

	private static double[][] adjacencyMatrix;

	public static double HIGHEST_SPEED = 0;
	public static double LOWEST_SPEED = Double.POSITIVE_INFINITY;

	public static double LONGEST_STOP = 0;
	public static double AVERAGE_STOP = 0;
	
	/**
	 * @return the allTrajectories
	 */
	public static List<Trajectory> getAllTrajectories()
	{
		return allTrajectories;
	}

	/**
	 * @return the adjacencyMatrix
	 */
	public static double[][] getAdjacencyMatrix()
	{
		return adjacencyMatrix;
	}

	/***********************************************************
	 * @param filename
	 * @return
	 ***********************************************************/
	public static void readTrajectories()
	{
		allTrajectories.clear();
		
		List<Point> points = new ArrayList<Point>();
		try
		{
			String previousSubject = "";
			SubjectType previousType = null;

			// [0] 1stYearStudent (Yes-1; No-0)
			// [1] SubjectCode
			// [2] PointOrder
			// [3] CoordX (m)
			// [4] CoordY (m)
			// [5] DistanceBetweenPoints (m)
			// [6] TimeStopped (msec)
			// [7] WalkingTimeEstimate (msec)

			String[] parts;
			SubjectType type = null;
			Point p;
			
			for (String line : Files.readAllLines(Paths.get(TRAJECTORIES_POINTS_FILE)))
			{
				parts = line.split("\t");

				type = SubjectType.convertCode(parts[0].charAt(0));

				if (!previousSubject.isEmpty() && !parts[1].equals(previousSubject)) 
				{
					if (Configuration.isTypeSelected(type))
						allTrajectories.add(new Trajectory(type, previousSubject, points));
					points.clear();
				}

				p = new Point(Integer.valueOf(parts[2]), Double.valueOf(parts[3]), Double.valueOf(parts[4]), Double.valueOf(parts[5]),
						Integer.valueOf(parts[6]), Integer.valueOf(parts[7]));
				points.add(p);
				previousType = SubjectType.convertCode(parts[0].charAt(0));
				previousSubject = parts[1];

			}
			if (type != null && Configuration.isTypeSelected(type))
				allTrajectories.add(new Trajectory(previousType, previousSubject, points));

			HIGHEST_SPEED = highestSpeed();
			LOWEST_SPEED = lowestSpeed();
			
			LONGEST_STOP = longestStop();
			AVERAGE_STOP = averageStop();

			fillMatrix();
			
		} catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}

	/***********************************************************
	  *
	  ***********************************************************/
	private static void fillMatrix()
	{
		int matrixSize = allTrajectories.size();

		adjacencyMatrix = new double[matrixSize][matrixSize];

		for (int i = 0; i < matrixSize; i++)
			for (int j = 0; j < matrixSize; j++)
				if (i < j)
					adjacencyMatrix[i][j] = allTrajectories.get(i).distanceTo(allTrajectories.get(j));
				else if (i == j)
					adjacencyMatrix[i][j] = 0;
				else
					adjacencyMatrix[i][j] = adjacencyMatrix[j][i];
	}

	/**
	 * @return the mean trajectory
	 */
	public static Trajectory mostCommon()
	{

		int matrixSize = adjacencyMatrix.length;

		double[] totalDistance = new double[matrixSize];

		double minDistance = Double.MAX_VALUE;

		Trajectory mean = null;
		for (int i = 0; i < matrixSize; i++)
		{
			totalDistance[i] = 0;
			for (int j = 0; j < matrixSize; j++)
				totalDistance[i] += adjacencyMatrix[i][j];
			if (totalDistance[i] < minDistance)
			{
				minDistance = totalDistance[i];
				mean = allTrajectories.get(i);
			}
		}
		return mean;
	}

	/**
	 * @return the most dissimilar trajectory
	 */
	public static Trajectory mostDissimilar()
	{

		int matrixSize = adjacencyMatrix.length;

		double[] totalDistance = new double[matrixSize];

		double maxDistance = 0;

		Trajectory dissimilar = null;
		for (int i = 0; i < matrixSize; i++)
		{
			totalDistance[i] = 0;
			for (int j = 0; j < matrixSize; j++)
				totalDistance[i] += adjacencyMatrix[i][j];
			if (totalDistance[i] > maxDistance)
			{
				maxDistance = totalDistance[i];
				dissimilar = allTrajectories.get(i);
			}
		}
		return dissimilar;
	}

	/***********************************************************
	*
	***********************************************************/
	private static int longestStop()
	{
		int longest = 0;
		for (Trajectory t : allTrajectories)
		{
			for (Point p : t.getPoints())
			{
				if (p.getTimeStopped() > longest)
					longest = p.getTimeStopped();
			}
		}
		return longest;
	}
	
	/***********************************************************
	*
	***********************************************************/
	private static int averageStop()
	{
		int totalTime = 0, totalStops = 0;
		for (Trajectory t : allTrajectories)
		{
			for (Point p : t.getPoints())
			{
				totalTime += p.getTimeStopped();
				totalStops++;
			}
		}
		return totalTime / totalStops;
	}
	
	/***********************************************************
	*
	***********************************************************/
	private static double highestSpeed()
	{
		double highest = 0;
		for (Trajectory t : allTrajectories)
		{
			for (Point p : t.getPoints())
			{
				if (p.getSpeedFromPrevious() != 0 && p.getSpeedFromPrevious() > highest)
					highest = p.getSpeedFromPrevious();
			}
		}
		return highest;
	}

	/***********************************************************
	*
	***********************************************************/
	private static double lowestSpeed()
	{
		double lowest = Double.POSITIVE_INFINITY;
		for (Trajectory t : allTrajectories)
		{
			for (Point p : t.getPoints())
			{
				if (p.getSpeedFromPrevious() != 0 && p.getSpeedFromPrevious() < lowest)
					lowest = p.getSpeedFromPrevious();
			}
		}
		return lowest;
	}

	
	/***********************************************************
	*
	***********************************************************/
	public static Trajectory shorter()
	{
		int shorterLength = Integer.MAX_VALUE;
		Trajectory theShorter = null;
		for (Trajectory t : allTrajectories)
		{
			int distance = t.distanceWalked();
				if (distance < shorterLength)
				{
					shorterLength = distance;
					theShorter = t;
				}
		}
		return theShorter;
	}
	
	/***********************************************************
	*
	***********************************************************/
	public static Trajectory longer()
	{
		int longerLength = 0;
		Trajectory theLonger = null;
		for (Trajectory t : allTrajectories)
		{
			int distance = t.distanceWalked();
				if (distance > longerLength)
				{
					longerLength = distance;
					theLonger = t;
				}
		}
		return theLonger;
	}
	
	/***********************************************************
	*
	***********************************************************/
	public static Trajectory slower()
	{
		double slowerSpeed = Double.POSITIVE_INFINITY;
		Trajectory theSlower = null;
		for (Trajectory t : allTrajectories)
		{
			double trajectorySpeed = t.averageSpeed();
				if (trajectorySpeed < slowerSpeed)
				{
					slowerSpeed = trajectorySpeed;
					theSlower = t;
				}
		}
		return theSlower;
	}
	

	/***********************************************************
	*
	***********************************************************/
	public static Trajectory faster()
	{
		double fasterSpeed = 0;
		Trajectory theFaster = null;
		for (Trajectory t : allTrajectories)
		{
			double trajectorySpeed = t.averageSpeed();
				if (trajectorySpeed > fasterSpeed)
				{
					fasterSpeed = trajectorySpeed;
					theFaster = t;
				}
		}
		return theFaster;
	}
}
