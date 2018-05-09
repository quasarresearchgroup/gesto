package gesto.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gesto.SubjectType;

public abstract class Configuration
{
	private static boolean	
													trajectoriesOn = true,
													stoppingPointsOn = true,
													thinStrokeOn = false,
													randomColorsOn = false,
													arrowsOn = true,
													gridOn = false, 
													speedOn = false,
													demoOn = false,
													delayOn = false,
													meanTrajectoryOn = false, 
													dissimilarTrajectoryOn = false;

	private static Set<SubjectType> selectedTypes = new HashSet<SubjectType>(Arrays.asList(SubjectType.values()));
	
	public static boolean isTypeSelected(SubjectType type)
	{
		return selectedTypes.contains(type);
	}

	public static void toggleSelected(SubjectType type)
	{
		if (selectedTypes.contains(type))
			selectedTypes.remove(type);
		else
			selectedTypes.add(type);
	}

	public static boolean isTrajectoriesSelected()
	{
		return trajectoriesOn;
	}

	public static void toggleTrajectories()
	{
		trajectoriesOn = !trajectoriesOn;
	}
	
	public static boolean isStoppingPointsSelected()
	{
		return stoppingPointsOn;
	}

	public static void toggleStoppingPoints()
	{
		stoppingPointsOn = !stoppingPointsOn;
	}
	
	public static boolean isThinStrokeSelected()
	{
		return thinStrokeOn;
	}

	public static void toggleThinStroke()
	{
		thinStrokeOn = !thinStrokeOn;
	}

	public static boolean isRandomColorsSelected()
	{
		return randomColorsOn;
	}

	public static void toggleRandomColors()
	{
		randomColorsOn = !randomColorsOn;
	}
	
	public static boolean isArrowsSelected()
	{
		return arrowsOn;
	}

	public static void toggleArrows()
	{
		arrowsOn = !arrowsOn;
	}
	
	public static boolean isGridSelected()
	{
		return gridOn;
	}

	public static void toggleGrid()
	{
		gridOn = !gridOn;
	}

	public static boolean isSpeedSelected()
	{
		return speedOn;
	}

	public static void toggleSpeed()
	{
		speedOn = !speedOn;
	}

	public static boolean isDemoSelected()
	{
		return demoOn;
	}

	public static void toggleDemo()
	{
		demoOn = !demoOn;
	}
	
	public static boolean isDelaySelected()
	{
		return delayOn;
	}

	public static void toggleDelay()
	{
		delayOn = !delayOn;
	}
	
	public static boolean isMeanTrajectorySelected()
	{
		return meanTrajectoryOn;
	}

	public static void toggleMeanTrajectory()
	{
		meanTrajectoryOn = !meanTrajectoryOn;
	}

	public static boolean isDissimilarTrajectorySelected()
	{
		return dissimilarTrajectoryOn;
	}

	public static void toggleDissimilarTrajectory()
	{
		dissimilarTrajectoryOn = !dissimilarTrajectoryOn;
	}

}
