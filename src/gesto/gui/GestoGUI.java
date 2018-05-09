package gesto.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
/***********************************************************
 * @author FBA 2009/04/29
 * 
 ***********************************************************/
public class GestoGUI extends JFrame
{
	private static int			FRAME_WIDTH		= 800;
	private static int			FRAME_HEIGHT	= 600;

	/**
	 * @return the texto
	 */
	public JTextArea getTexto()
	{
		return texto;
	}


	/**
	 * @param texto the texto to set
	 */
	public void setTexto(JTextArea texto)
	{
		this.texto = texto;
	}


	private JTextArea			texto;

	/***********************************************************
	 * 
	 ***********************************************************/
	public GestoGUI()
	{
		super("GESTO prototype / ISTAR SSE Group");

		final ImageIcon imageIcon = new ImageIcon("images/IstarLogo.png");
				
		texto = createTextArea(imageIcon);

		JScrollPane textoComScroll = new JScrollPane(texto);
		
		this.add(textoComScroll, BorderLayout.CENTER);	

		FRAME_WIDTH = imageIcon.getIconWidth() + 400;
		FRAME_HEIGHT = imageIcon.getIconHeight() + 500;

		// Get the size of the default screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		this.setLocation((dim.width - FRAME_WIDTH) / 2, (dim.height - FRAME_HEIGHT) / 2);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		this.setJMenuBar(new GestoMenuBar(this));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}


	/**
	 * @param imageIcon
	 */
	private JTextArea createTextArea(final ImageIcon imageIcon)
	{
		JTextArea textArea = new JTextArea()
		{
			Image	image	= imageIcon.getImage();

			{
				setOpaque(false);
			} // instance initializer

			public void paintComponent(Graphics g)
			{
				Point p = this.getLocation();
				g.drawImage(image, this.getWidth() - imageIcon.getIconWidth(), -p.y, this);
				super.paintComponent(g);
			}
		};

		textArea.setEditable(false);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Courier", 40, 14));
		// texto.setBackground(Color.YELLOW);
		// texto.setForeground(Color.BLUE);
		
		return textArea;
	}


}
