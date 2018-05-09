package gesto.gui;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ImageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel imageLabel;
	
	public ImageFrame(String title, BufferedImage image) {
		super(title);

		imageLabel = new JLabel(new ImageIcon(image));

		setLayout(new FlowLayout());
		add(imageLabel);
		setSize(image.getWidth(), image.getHeight() + 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateImage(BufferedImage image)
	{
		imageLabel.setIcon(new ImageIcon(image));
	}

}
