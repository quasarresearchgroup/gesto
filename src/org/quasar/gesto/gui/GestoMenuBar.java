package org.quasar.gesto.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.quasar.gesto.Model;
import org.quasar.gesto.SubjectType;

public class GestoMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;


	/**
	 * @param container
	 */
	public GestoMenuBar(GestoGUI container)
	{
		Printer.setTargetTextArea(container.getTexto());
		
		this.add(createConfigurationMenu());
		this.add(createGraphicsMenu());
		this.add(createTextualMenu());
		this.add(createHelpMenu());
	}

	/**
	 * Creates the File menu.
	 * 
	 * @return the menu
	 */
	public JMenu createConfigurationMenu()
	{
		JMenu menu = new JMenu("Configuration");
		menu.add(createSubjectsMenu());
		menu.add(createTrajectoriesMenu());
		menu.add(createShowMenu());
		menu.add(createOptionsMenu());
		menu.add(createProgramExitItem());
		return menu;
	}
	
	
	/**
	 * Creates the Subjects menu.
	 * 
	 * @return the menu
	 */
	public JMenu createSubjectsMenu()
	{
		JMenu menu = new JMenu("Subjects");
		menu.add(createFreshmenItem());
		menu.add(createFinalistsItem());
		return menu;
	}


	/**
	 * Creates a check box menu item to allow selecting FRESHMEN subjects and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createFreshmenItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("FRESHMEN", Configuration.isTypeSelected(SubjectType.FRESHMAN));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleSelected(SubjectType.FRESHMAN);
				Model.readTrajectories();
			}
		});
		return item;
	}

	/**
	 * Creates a check box menu item to allow selecting FINALISTS subjects and
	 * sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createFinalistsItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("FINALISTS", Configuration.isTypeSelected(SubjectType.FINALIST));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleSelected(SubjectType.FINALIST);
				Model.readTrajectories();
			}
		});
		return item;
	}

	/**
	 * Creates the Trajectories Color and Stroke menu.
	 * 
	 * @return the menu
	 */
	public JMenu createTrajectoriesMenu()
	{
		JMenu menu = new JMenu("Trajectories");
		menu.add(createRandomColorsItem());
		menu.add(createThinStrokeItem());
		menu.add(createArrowsItem());
		return menu;
	}
	
	/**
	 * Creates a check box menu item to paint trajectories in random colors or in monochrome
	 * and sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createRandomColorsItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Random colors", Configuration.isRandomColorsSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleRandomColors();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to select a thin (or thick) stroke and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createThinStrokeItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Thin stroke", Configuration.isThinStrokeSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleThinStroke();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to show/hide arrows and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createArrowsItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Arrows", Configuration.isArrowsSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleArrows();
			}
		});
		return item;
	}
	
	/**
	 * Creates the Show menu.
	 * 
	 * @return the menu
	 */
	public JMenu createShowMenu()
	{
		JMenu menu = new JMenu("Show");
		menu.add(createGridItem());
		menu.add(createSelectTrajectoriesItem());
		menu.add(createStoppingPointsItem());
		menu.add(createMeanTrajectoryItem());
		menu.add(createDissimilarTrajectoryItem());
		return menu;
	}
	
	/**
	 * Creates a check box menu item to show/hide the trajectories and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createSelectTrajectoriesItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Trajectories", Configuration.isTrajectoriesSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleTrajectories();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to show/hide the stopping points and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createStoppingPointsItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Stopping Points", Configuration.isStoppingPointsSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleStoppingPoints();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to show/hide the grid and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createGridItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Grid", Configuration.isGridSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleGrid();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to show/hide the mean trajectory and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createMeanTrajectoryItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Mean (most common) trajectory", Configuration.isMeanTrajectorySelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleMeanTrajectory();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to show/hide the mean trajectory and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createDissimilarTrajectoryItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Dissimilar (most uncommon) trajectory", Configuration.isDissimilarTrajectorySelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleDissimilarTrajectory();
			}
		});
		return item;
	}
	
	/**
	 * Creates the Options menu.
	 * 
	 * @return the menu
	 */
	public JMenu createOptionsMenu()
	{
		JMenu menu = new JMenu("Options");
		menu.add(createSpeedItem());
		menu.add(createDemoItem());
		menu.add(createDelayItem());

		return menu;
	}
	
	/**
	 * Creates a check box menu item to turn on/off speed representation and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createSpeedItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Speed", Configuration.isSpeedSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleSpeed();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to turn on/off speed demo representation and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createDemoItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Speed demo", Configuration.isDemoSelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleDemo();
			}
		});
		return item;
	}
	
	/**
	 * Creates a check box menu item to turn on/off a delay and sets
	 * its action listener.
	 * 
	 * @return the menu item
	 */
	public JCheckBoxMenuItem createDelayItem()
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Delay", Configuration.isDelaySelected());

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Configuration.toggleDelay();
			}
		});
		return item;
	}
	
	/**
	 * Creates the Program->Exit menu item and sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JMenuItem createProgramExitItem()
	{
		JMenuItem item = new JMenuItem("Exit", new ImageIcon("images/exitIcon.png"));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		return item;
	}

	/**
	 * Creates the Graphics menu.
	 * 
	 * @return the menu
	 */
	public JMenu createGraphicsMenu()
	{
		JMenu menu = new JMenu("Graphical outputs");
		menu.add(createSelectedTrajectoryItem());
		menu.add(createAllTrajectoriesItem());
		menu.add(createHeatMapItem());
		menu.add(createVoronoiItem());
		return menu;
	}

	/**
	 * Creates the "Show the selected trajectory (on a drop-down)" menu item and
	 * sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JMenuItem createSelectedTrajectoryItem()
	{
		JMenuItem item = new JMenuItem("Show the selected trajectory (on a drop-down)", new ImageIcon("images/onePathIcon.png"));
		
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				View.showSelectedTrajectory();
			}
		});
		return item;
	}

	
	/**
	 * Creates the "Show all trajectories" menu item and sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JMenuItem createAllTrajectoriesItem()
	{
		JMenuItem item = new JMenuItem("All trajectories", new ImageIcon("images/allPathsIcon.png"));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				View.showAllTrajectories();
			}
		});
		return item;
	}

	/**
	 * Creates the "Show the Heat Map" menu item and sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JMenuItem createHeatMapItem()
	{
		JMenuItem item = new JMenuItem("Heat Map", new ImageIcon("images/heatMapIcon.png"));
		
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				View.showHeatMap();
			}
		});
		return item;
	}

	
	/**
	 * Creates the "Show the Voronoi diagram" menu item and sets its action listener.
	 * 
	 * @return the menu item
	 */
	public JMenuItem createVoronoiItem()
	{
		JMenuItem item = new JMenuItem("Voronoi Diagram", new ImageIcon("images/voronoiIcon.png"));
		
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				View.drawVoronoi();
			}
		});
		return item;
	}

	
	/**
	 * Creates the Textual menu.
	 * 
	 * @return the menu
	 */
	public JMenu createTextualMenu()
	{
		JMenu menu = new JMenu("Textual outputs");
		menu.add(createTrajectoriesItem());
		menu.add(createTrajectoryMatrixItem());
		menu.add(createAverageDistancesVectorItem());
		menu.add(createStatisticsItem());
		menu.add(createCleanItem());
		return menu;
	}

	/**
	 * @param name
	 *          - the name of the menu item
	 * @return the menu item
	 */
	public JMenuItem createTrajectoryMatrixItem()
	{
		JMenuItem item = new JMenuItem("Trajectory Matrix", new ImageIcon("images/trajectoryMatrixIcon.png"));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Printer.printMatrix();
			}
		});
		return item;
	}

	/**
	 * @param name
	 *          - the name of the menu item
	 * @return the menu item
	 */
	public JMenuItem createTrajectoriesItem()
	{
		JMenuItem item = new JMenuItem("Trajectories", new ImageIcon("images/trajectoriesIcon.png"));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Printer.printTrajectories();
			}
		});
		return item;
	}

	/**
	 * @param name
	 *          - the name of the menu item
	 * @return the menu item
	 */
	public JMenuItem createAverageDistancesVectorItem()
	{
		JMenuItem item = new JMenuItem("Average Distances Vector", new ImageIcon("images/averageIcon.png"));
		
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Printer.printAverageDistancesVector();
				
//				JOptionPane.showMessageDialog(container, "Aqui irá aparecer ...", "Opção não implementada!",
//						JOptionPane.WARNING_MESSAGE);
			}
		});
		return item;
	}

	
	/**
	 * @param name
	 *          - the name of the menu item
	 * @return the menu item
	 */
	public JMenuItem createStatisticsItem()
	{
		JMenuItem item = new JMenuItem("Statistics", new ImageIcon("images/statisticsIcon.png"));
		
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Printer.printStatistics();
			}
		});
		return item;
	}

	
	/**
	 * @param name
	 *          - the name of the menu item
	 * @return the menu item
	 */
	public JMenuItem createCleanItem()
	{
		JMenuItem item = new JMenuItem("Clean screen", new ImageIcon("images/cleanIcon.png"));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Printer.clean();
			}
		});
		return item;
	}

	/**
	 * Creates the Help menu.
	 * 
	 * @return the menu
	 */
	public JMenu createHelpMenu()
	{
		JMenu menu = new JMenu("Help");
		menu.add(createAboutItem());
		return menu;
	}

	/***********************************************************
	 * @param name
	 *          the name of the menu item
	 * @return the menu item
	 ***********************************************************/
	public JMenuItem createAboutItem()
	{
		JMenuItem item = new JMenuItem("About GESTO", new ImageIcon("images/aboutIcon.png"));

		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				Icon icon = new ImageIcon("images/AlcobacaLIDAR.png");
				JOptionPane.showMessageDialog(null, "This prototype was produced\n" + "by the ISTAR-IUL SSE Team\n" + "\n"
						+ "Icons designed by Freepik\n" + "from www.flaticon.com", "GESTP Project (FCT EXPL/ATP-AQI/1142/2013)\n",
						JOptionPane.PLAIN_MESSAGE, icon);
			}
		});
		return item;
	}

}
