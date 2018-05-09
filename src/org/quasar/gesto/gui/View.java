package org.quasar.gesto.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.quasar.gesto.Cluster;
import org.quasar.gesto.ColorGradient;
import org.quasar.gesto.KMeansBasic;
import org.quasar.gesto.Model;
import org.quasar.gesto.Point;
import org.quasar.gesto.Trajectory;
import org.quasar.voronoi2.GraphEdge;
import org.quasar.voronoi2.Voronoi;

public class View
{

	private static final int ARROW_WIDTH = 5;
	private static final int ARROW_HEIGHT = 15;

	private static final String MONASTERY_IMAGE_FILE = "data/MosteiroAlcobaca(rotated).JPG";

	private static BufferedImage monasteryImage = getMonasteryImage();

	private static Graphics2D g2d = monasteryImage.createGraphics();

	private static ImageFrame frame = new ImageFrame("Mosteiro de Alcobaça", monasteryImage);

	/***********************************************************
	 * @return
	 ***********************************************************/
	private static BufferedImage getMonasteryImage()
	{
		BufferedImage monasteryImage = null;
		try
		{
			monasteryImage = ImageIO.read(new File(MONASTERY_IMAGE_FILE));
		} catch (IOException e)
		{
		}
		return monasteryImage;
	}

	/***********************************************************
	 *
	 ***********************************************************/
	private static void resetMonasteryImage()
	{
		monasteryImage = getMonasteryImage();
		frame.updateImage(monasteryImage);
		g2d = monasteryImage.createGraphics();
	}

	/***********************************************************
	 *
	 ***********************************************************/
	private static void causeDelay()
	{
		try
		{
			Thread.sleep(200); // 200 milliseconds
		} catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}

	/***********************************************************
	 * @param width
	 * @param dashed
	 * @return
	 ***********************************************************/
	private static BasicStroke currentStroke(float width, boolean dashed)
	{
		if (dashed)
		{
			float dash1[] = { 10.0f };
			return new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, dash1, 0.0f);
		} else
			return new BasicStroke(width, // Line width
					BasicStroke.CAP_ROUND, // End-cap style
					BasicStroke.JOIN_ROUND);
	}

	/***********************************************************
	 *
	 ***********************************************************/
	public static void showSelectedTrajectory()
	{
		Point.setCanvas(monasteryImage.getWidth(), monasteryImage.getHeight());

		JComboBox<Trajectory> trajectoriesChoice = new JComboBox<Trajectory>();
		for (Trajectory t : Model.getAllTrajectories())
			trajectoriesChoice.addItem(t);

		frame.add(trajectoriesChoice, BorderLayout.SOUTH);

		frame.setSize(frame.getWidth(), frame.getHeight() + trajectoriesChoice.getHeight());
		frame.setVisible(true);

		resetMonasteryImage();

		showGrid(g2d);

		showTrajectory(g2d, (Trajectory) trajectoriesChoice.getSelectedItem());

		showStoppingPoints(g2d, (Trajectory) trajectoriesChoice.getSelectedItem());

		showInterestingRoutes();

		class ActionHandler implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				resetMonasteryImage();

				showGrid(g2d);

				showTrajectory(g2d, (Trajectory) trajectoriesChoice.getSelectedItem());

				showStoppingPoints(g2d, (Trajectory) trajectoriesChoice.getSelectedItem());

				showInterestingRoutes();
			}
		}

		// add an action listener
		ActionListener actionListener = new ActionHandler();

		trajectoriesChoice.addActionListener(actionListener);
	}

	/***********************************************************
	 *
	 ***********************************************************/
	public static void showAllTrajectories()
	{
		Point.setCanvas(monasteryImage.getWidth(), monasteryImage.getHeight());

		resetMonasteryImage();

		showGrid(g2d);

		for (Trajectory t : Model.getAllTrajectories())
			showTrajectory(g2d, t);

		for (Trajectory t : Model.getAllTrajectories())
			showStoppingPoints(g2d, t);

		showInterestingRoutes();
	}

	/************************************************************
	 *
	 ***********************************************************/
	public static void showHeatMap()
	{
		Point.setCanvas(monasteryImage.getWidth(), monasteryImage.getHeight());

		resetMonasteryImage();

		g2d.setBackground(Color.WHITE);

		showGrid(g2d);

		for (Trajectory t : Model.getAllTrajectories())
		{
			BufferedImage image = new BufferedImage(monasteryImage.getWidth(), monasteryImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d_aux = image.createGraphics();

			showTrajectory(g2d_aux, t);

			showStoppingPoints(g2d_aux, t);

			for (int x = 0; x < image.getWidth(); x++)
				for (int y = 0; y < image.getHeight(); y++)

					// If it is a point belonging to the trajectory
					if (ColorGradient.contains(new Color(image.getRGB(x, y))))
					{
						ColorGradient cg = new ColorGradient(new Color(monasteryImage.getRGB(x, y)));
						cg.warmUp(ColorGradient.quartile(new Color(image.getRGB(x, y))) + 1);
						monasteryImage.setRGB(x, y, cg.currentColor().getRGB());
					}
		}
		showInterestingRoutes();
	}
	
	/************************************************************
	 *
	 ***********************************************************/
	public static void drawVoronoi()
	{
		Point.setCanvas(monasteryImage.getWidth(), monasteryImage.getHeight());

		resetMonasteryImage();

		g2d.setBackground(Color.WHITE);

		showGrid(g2d);
		
		double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;

		List<Point> pointsList = new ArrayList<Point>();

		for (Trajectory t : Model.getAllTrajectories())
		{
			showStoppingPoints(g2d, t);
			for (Point p : t.getPoints())
				if (p.getTimeStopped() > 0)
				{
					pointsList.add(p);
					if (p.getX() < minX)
						minX = p.getX();
					if (p.getX() > maxX)
						maxX = p.getX();
					if (p.getY() < minY)
						minY = p.getY();
					if (p.getY() > maxY)
						maxY = p.getY();
				}
		}

		KMeansBasic.setPoints(pointsList);

		List<Point> centroids = KMeansBasic.clusterCentroids(minX, maxX, minY, maxY);

		for (Point c: centroids)
			drawCircle(g2d, Color.BLACK, c.getCanvasX(), c.getCanvasY(), Configuration.isThinStrokeSelected() ? 3 : 5, true);
		
		List<GraphEdge> voronoiEdges = KMeansBasic.voronoiEdges(centroids, minX, maxX, minY, maxY);

		Point begin, end;
		for (GraphEdge edge : voronoiEdges)
		{
//			System.out.println(edge);
			begin = new Point(edge.x1, edge.y1);
			end = new Point(edge.x2, edge.y2);
			g2d.setColor(Color.BLACK);
			g2d.drawLine(begin.getCanvasX(), begin.getCanvasY(), end.getCanvasX(), end.getCanvasY());
		}
		
		showInterestingRoutes();
	}

	/************************************************************
	 *
	 ***********************************************************/
	private static void showInterestingRoutes()
	{
		if (Configuration.isMeanTrajectorySelected())
			showTrajectory(g2d, Model.mostCommon(), true, Color.GREEN, 3, true);

		if (Configuration.isDissimilarTrajectorySelected())
			showTrajectory(g2d, Model.mostDissimilar(), true, Color.RED, 3, true);
	}

	/***********************************************************
	 * @param g
	 * @param monasteryImage
	 ***********************************************************/
	private static void showGrid(Graphics2D g)
	{
		if (Configuration.isGridSelected())
		{
			g.setBackground(Color.WHITE);
			g.setColor(Color.LIGHT_GRAY);
			g.setStroke(new BasicStroke(1));

			int width = monasteryImage.getWidth();
			int height = monasteryImage.getHeight();

			for (int i = 0; i < 21; i++)
			{
				g.drawLine((width + 2) / 20 * i, 0, (width + 2) / 20 * i, height - 1);
				g.drawLine(0, (height + 2) / 20 * i, width - 1, (height + 2) / 20 * i);
			}
		}
	}

	/***********************************************************
	 * @param g
	 * @param t
	 * @param arrows
	 * @param specifiedColor
	 * @param strokeSize
	 ***********************************************************/
	private static void showTrajectory(Graphics2D g, Trajectory t, boolean arrows, Color specifiedColor, int strokeSize,
			boolean dashed)
	{
		g.setStroke(currentStroke(strokeSize, dashed));

		g.setColor(specifiedColor);

		Point start = null;
		int segment = 0;

		for (Point p : t.getPoints())
		{
			segment++;
			if (start != null)
				if (arrows && segment % 5 == 2)
					drawArrowLine(g, start.getCanvasX(), start.getCanvasY(), p.getCanvasX(), p.getCanvasY());
				else
					g.drawLine(start.getCanvasX(), start.getCanvasY(), p.getCanvasX(), p.getCanvasY());

			// g.drawOval((int) (p.getX() + p.getTimeStopped() / 1000), (int)
			// (p.getY() + p.getTimeStopped() / 1000),
			// 2 * p.getTimeStopped() / 1000, 2 * p.getTimeStopped() / 1000);
			start = p;
		}
		frame.repaint();
	}

	/***********************************************************
	 * @param g
	 * @param t
	 ***********************************************************/
	private static void showTrajectory(Graphics2D g, Trajectory t)
	{
		if (Configuration.isTrajectoriesSelected())
		{
			g.setStroke(currentStroke(Configuration.isThinStrokeSelected() ? 2 : 5, false));

			Random rand = new Random();

			Color c;

			if (Configuration.isRandomColorsSelected())
			{
				float red = (float) (rand.nextFloat() / 2f + 0.5);
				float green = (float) (rand.nextFloat() / 2f + 0.5);
				float blue = (float) (rand.nextFloat() / 2f + 0.5);

				c = new Color(red, green, blue);
			} else
				c = Color.BLUE;

			g.setColor(c);

			ColorGradient speedColor = new ColorGradient();
			boolean warming = true;
			Point start = null;
			int segment = 0;
			for (Point p : t.getPoints())
			{
				if (Configuration.isSpeedSelected())
				{
					if (Configuration.isDemoSelected())
					{
						g.setColor(speedColor.currentColor());
						if (warming)
						{
							speedColor.warmUp(1);
							if (speedColor.currentColor().equals(ColorGradient.WARMEST))
								warming = !warming;
						} else
						{
							speedColor.coolDown(1);
							if (speedColor.currentColor().equals(ColorGradient.COOLEST))
								warming = !warming;
						}
					} else
					{
						g.setColor(speedColor.relativeColor((Model.HIGHEST_SPEED - p.getSpeedFromPrevious()) / Model.HIGHEST_SPEED));
					}
				}

				segment++;
				if (start != null)
					if (Configuration.isArrowsSelected() && segment % 5 == 2)
						drawArrowLine(g, start.getCanvasX(), start.getCanvasY(), p.getCanvasX(), p.getCanvasY());
					else
						g.drawLine(start.getCanvasX(), start.getCanvasY(), p.getCanvasX(), p.getCanvasY());

				start = p;
			}

			frame.repaint();
		}
	}

	/**
	 * @param g
	 * @param t
	 */
	private static void showStoppingPoints(Graphics2D g, Trajectory t)
	{
		if (Configuration.isStoppingPointsSelected())
		{
			ColorGradient stopColor = new ColorGradient();
			for (Point p : t.getPoints())
				if (p.getTimeStopped() > 0)
				{
					// System.out.println("STOPPED>"+p.getTimeStopped() + " [" + (1 -
					// (Model.LONGEST_STOP - p.getTimeStopped()) / Model.LONGEST_STOP) +
					// "]");
					Color sc = null;
					if (p.getTimeStopped() > Model.AVERAGE_STOP)
						sc = stopColor.relativeColor(
								0.5 + (Math.log10(p.getTimeStopped() / Model.AVERAGE_STOP) / (Math.log10(Model.LONGEST_STOP / Model.AVERAGE_STOP)))
										/ 2);
					else
						sc = stopColor.relativeColor(p.getTimeStopped() / (2 * Model.AVERAGE_STOP));

					drawCircle(g, sc, p.getCanvasX(), p.getCanvasY(), Configuration.isThinStrokeSelected() ? 2 : 4, true);
				}
			// System.out.println("LONGEST>" + Model.LONGEST_STOP);
		}
	}

	/**
	 * @param g
	 * @param centerX
	 * @param centerY
	 * @param radius
	 */
	private static void drawCircle(Graphics2D g, Color color, int centerX, int centerY, int radius, boolean filled)
	{
		Color currentColor = g.getColor();

		g.setColor(color);
		
		if (filled)
			g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
		else
			g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
		
		g.setColor(currentColor);
	}

	/***********************************************************
	 * Draw an arrow line between two points
	 * 
	 * @param g
	 *          the graphic component
	 * @param x1
	 *          x-position of first point
	 * @param y1
	 *          y-position of first point
	 * @param x2
	 *          x-position of second point
	 * @param y2
	 *          y-position of second point
	 ***********************************************************/
	private static void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2)
	{

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - ARROW_HEIGHT, xn = xm, ym = ARROW_WIDTH, yn = -ARROW_WIDTH, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);
	}

}
