package utility;

import javafx.scene.paint.Color;

public class ColorProfile {
	private Color color;
	private double currentOpacity;
	private double maxOpacity;
	
	public ColorProfile(Color color, double maxOpacity) {
		this.color = Color.rgb(toInteger(color.getRed()), toInteger(color.getGreen()), toInteger(color.getBlue()));
		
		this.maxOpacity = maxOpacity;
		this.currentOpacity = maxOpacity;
	}
	
	public ColorProfile(int red, int green, int blue, double maxOpacity) {
		this.color = Color.rgb(red, green, blue);
		this.maxOpacity = maxOpacity;
		this.currentOpacity = maxOpacity;
	}
	
	public ColorProfile(ColorProfile other) {
		this.color = other.color;
		this.currentOpacity = other.currentOpacity;
		this.maxOpacity = other.maxOpacity;
	}
	
	public ColorProfile draw(ColorProfile other) {
		int red = toInteger((color.getRed() * maxOpacity + other.color.getRed() * other.maxOpacity) / (maxOpacity + other.maxOpacity));
		int green = toInteger((color.getGreen() * maxOpacity + other.color.getGreen() * other.maxOpacity) / (maxOpacity + other.maxOpacity));
		int blue = toInteger((color.getBlue() * maxOpacity + other.color.getBlue() * other.maxOpacity) / (maxOpacity + other.maxOpacity));
		
		color = Color.rgb(red, green, blue);
		maxOpacity = Math.max(maxOpacity, other.maxOpacity);
		
		return new ColorProfile(Color.rgb(red, green, blue), maxOpacity);
	}
	
	public Color getColor() {
		return Color.rgb(toInteger(color.getRed()), toInteger(color.getGreen()), toInteger(color.getBlue()), currentOpacity);
	}
	
	public double getMaxOpacity() {
		return maxOpacity;
	}
	
	private int toInteger(double x) {
		return (int) (255 * x);
	}
}
