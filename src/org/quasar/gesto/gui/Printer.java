package org.quasar.gesto.gui;

import javax.swing.JTextArea;

import org.quasar.gesto.Model;
import org.quasar.gesto.Point;
import org.quasar.gesto.Trajectory;

public abstract class Printer
{
	private static JTextArea target;

	/***********************************************************
	*
	***********************************************************/
	public static void setTargetTextArea(JTextArea target)
	{
		Printer.target = target;
	}

	/***********************************************************
	*
	***********************************************************/
	public static void clean()
	{
		target.setText("");
	}

	/***********************************************************
	*
	***********************************************************/
	public static void separator()
	{
		target.append("______________________________________________________________________\n");
	}

	/***********************************************************
	*
	***********************************************************/
	public static void printTrajectories()
	{
		clean();
		for (Trajectory t : Model.getAllTrajectories())
		{
			target.append(t.toString() + "\n");
			if (Configuration.isStoppingPointsSelected())
			{
				for (Point p : t.getPoints())
					target.append("\t" + p + "\n");
			}
			separator();
		}
		target.repaint();
	}

	/***********************************************************
	*
	***********************************************************/
	public static void printMatrix()
	{
		clean();
		target.append("\t|\t");
		for (Trajectory t : Model.getAllTrajectories())
			target.append(t.getSubject() + "\t");
		target.append("\n");
		separator();

		for (int i = 0; i < Model.getAdjacencyMatrix().length; i++)
		{
			target.append(Model.getAllTrajectories().get(i).getSubject() + "\t|\t");

			for (int j = 0; j < Model.getAdjacencyMatrix().length; j++)
				// if (i <= j)
				target.append(String.format("%.2f", Model.getAdjacencyMatrix()[i][j]) + "\t");
			// else
			// target.append("\t");
			target.append("\n");
		}
		target.repaint();
	}

	/***********************************************************
	*
	***********************************************************/
	public static void printAverageDistancesVector()
	{
		int matrixSize = Model.getAdjacencyMatrix().length;

		double[] totalDistance = new double[matrixSize];

		clean();
		for (int i = 0; i < matrixSize; i++)
		{
			totalDistance[i] = 0;
			for (int j = 0; j < matrixSize; j++)
				totalDistance[i] += Model.getAdjacencyMatrix()[i][j];
		}

		target.append("Route\tDistance\tPoints\tType\n");
		separator();

		Trajectory t;
		for (int i = 0; i < matrixSize; i++)
		{
			t = Model.getAllTrajectories().get(i);

			target.append(String.format("%s%.2f%s%14d%s\n", t.getSubject() + "\t", totalDistance[i] / matrixSize, "\t",
					t.getPoints().size(), "\t" + t.getType() + "\t"
							+ (t == Model.mostCommon() ? "[MOST COMMON]" : (t == Model.mostDissimilar() ? "[MOST UNCOMMON]" : ""))));
		}
		target.repaint();
	}

	/***********************************************************
	*
	***********************************************************/
	public static void printStatistics()
	{
		clean();
		target.append("LOADED " + Model.getAllTrajectories().size() + " trajectories\n");
		separator();
		target.append(Model.mostCommon() + "\n");
		separator();
		target.append(Model.mostDissimilar() + "\n");
		separator();
		target.append(Model.shorter() + "\n");
		separator();
		target.append(Model.longer() + "\n");
		separator();
		target.append("LENGTH [Min: " + Model.shorter().distanceWalked() + ", Max: " + Model.longer().distanceWalked() + "] meters\n");
		separator();
		target.append(Model.slower() + "\n");
		separator();
		target.append(Model.faster() + "\n");
		separator();
		target.append("SPEED (excluding stops) [Min: " + String.format("%.3f", Model.LOWEST_SPEED) + ", Max: "
				+ String.format("%.3f", Model.HIGHEST_SPEED) + "] meters/second\n");
		separator();
		target.append(
				"STOPPING TIMES [Average: " + Model.AVERAGE_STOP / 1000 + ", Longest: " + Model.LONGEST_STOP / 1000 + "] seconds\n");
		separator();
	}

}
