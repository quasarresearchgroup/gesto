package gesto;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import gesto.gui.GestoGUI;


/***********************************************************
 * @author FBA
 * 
 ***********************************************************/
public class Main
{
	@SuppressWarnings("unused")

	/***********************************************************
	 * @param args
	 ***********************************************************/
	public static void main(String[] args)
	{
		Model.readTrajectories();
			
		initializeLookAndFeel();

		JFrame iface = new GestoGUI();
	}

	/***********************************************************
	* 
	***********************************************************/
	public static void initializeLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// Guarantees the native look and feel (e.g. Windows, Linux, Mac)
		} catch (Exception e)
		{
			System.err.println("Falling back to the plain Java look and feel ...");
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de configuração!", JOptionPane.WARNING_MESSAGE);
		}
	}

}
