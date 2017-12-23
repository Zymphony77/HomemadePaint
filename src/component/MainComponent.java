package component;

import javafx.scene.layout.BorderPane;

import component.paintcanvas.PaintCanvas;

public class MainComponent {
	private static final MainComponent instance = new MainComponent();
	
	private BorderPane container;
	private PaintCanvas paintCanvas;
	
	public MainComponent() {
		container = new BorderPane();
		
		paintCanvas = new PaintCanvas(750, 750);
		container.setCenter(paintCanvas);
	}
	
	public static MainComponent getInstance() {
		return instance;
	}
	
	public BorderPane getContainer() {
		return container;
	}
	
	public PaintCanvas getPaintCanvas() {
		return paintCanvas;
	}
}
