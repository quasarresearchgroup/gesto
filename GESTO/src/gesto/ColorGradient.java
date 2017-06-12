package gesto;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;


public class ColorGradient {

	private static Color[] gradient =
		{
			new Color(5,0,255),
			new Color(4,0,255),
			new Color(3,0,255),
			new Color(2,0,255),
			new Color(1,0,255),
			new Color(0,0,255),
			new Color(0,2,255),
			new Color(0,18,255),
			new Color(0,34,255),
			new Color(0,50,255),
			new Color(0,68,255),
			new Color(0,84,255),
			new Color(0,100,255),
			new Color(0,116,255),
			new Color(0,132,255),
			new Color(0,148,255),
			new Color(0,164,255),
			new Color(0,180,255),
			new Color(0,196,255),
			new Color(0,212,255),
			new Color(0,228,255),
			new Color(0,255,244),
			new Color(0,255,208),
			new Color(0,255,168),
			new Color(0,255,131),
			new Color(0,255,92),
			new Color(0,255,54),
			new Color(0,255,16),
			new Color(23,255,0),
			new Color(62,255,0),
			new Color(101,255,0),
			new Color(138,255,0),
			new Color(176,255,0),
			new Color(215,255,0),
			new Color(253,255,0),
			new Color(255,250,0),
			new Color(255,240,0),
			new Color(255,230,0),
			new Color(255,220,0),
			new Color(255,210,0),
			new Color(255,200,0),
			new Color(255,190,0),
			new Color(255,180,0),
			new Color(255,170,0),
			new Color(255,160,0),
			new Color(255,150,0),
			new Color(255,140,0),
			new Color(255,130,0),
			new Color(255,120,0),
			new Color(255,110,0),
			new Color(255,100,0),
			new Color(255,90,0),
			new Color(255,80,0),
			new Color(255,70,0),
			new Color(255,60,0),
			new Color(255,50,0),
			new Color(255,40,0),
			new Color(255,30,0),
			new Color(255,20,0),
			new Color(255,10,0),
			new Color(255,0,0),
			new Color(255,0,16),
			new Color(255,0,32),
			new Color(255,0,48),
			new Color(255,0,64),
			new Color(255,0,80),
			new Color(255,0,96),
			new Color(255,0,112),
			new Color(255,0,128),
			new Color(255,0,144),
			new Color(255,0,160),
			new Color(255,0,176),
			new Color(255,0,192),
			new Color(255,0,208),
			new Color(255,0,224),
			new Color(255,0,240),
			new Color(255,1,240),
			new Color(255,2,240),
			new Color(255,3,240),
			new Color(255,4,240),
			new Color(255,5,240),
			new Color(255,6,240),
			new Color(255,7,240),
			new Color(255,8,240),
			new Color(255,9,240),
			new Color(255,10,240),
			new Color(255,11,240),
			new Color(255,12,240),
			new Color(255,13,240),
			new Color(255,14,240)
		};
	
	private int current;
	
	public static final Color COOLEST = new Color(5,0,255);
	public static final Color WARMEST = new Color(255,14,240);
	
	public ColorGradient()
	{	
		this.current = gradient.length - 1;
		
//		for (Color c: gradient)
//			System.out.println(c + "\t -> " + quartile(c));
	}
	
	public ColorGradient(int initialValue)
	{	
		assert initialValue >= 0 && initialValue < gradient.length;
		
		this.current = initialValue;
	}

	public ColorGradient(Color actual)
	{
		List<Color> gradientList = Arrays.asList(gradient);
		int index = gradientList.indexOf(actual);
		current = (index == -1) ? 0 : index;
	}
	
	public Color currentColor()
	{
		return gradient[current];
	}
	
	public static boolean contains(Color given)
	{
		return Arrays.asList(gradient).contains(given);
	}
	
	public static int quartile(Color given)
	{
		return Arrays.asList(gradient).indexOf(given) / ((gradient.length-1) / 4) + 1;
	}
	
	
	public void warmUp(int value)
	{
		current = (current+value > gradient.length-1) ? gradient.length-1 : current + value;
	}
	
	public void coolDown(int value)
	{
		current = (current-value > 0) ? current-value : 0;
	}
	
//	public int hotestScore()
//	{
//		return gradient.length - 1;
//	}
	
	public Color relativeColor(double percentage)
	{
		return gradient[(int) ((gradient.length - 1) * percentage)];
	}
	
}
