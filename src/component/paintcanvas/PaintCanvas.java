package component.paintcanvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;

import utility.*;

public class PaintCanvas extends Canvas {
	private Vector<Vector<ColorProfile>> data;
	private GraphicsContext gc;
	
	private int row;
	private int column;
	
	private Color fgColor;
	private Color bgColor;
	private double weight;
	
	private LinkedList<Vector<HistoryData>> history;
	private Vector<HistoryData> previousData;
	private int state;
	
	private LinkedList<PosPair> paintPoint;
	private TreeSet<PosPair> drawn;
	
	public PaintCanvas(int row, int column) {
		super(column, row);
		gc = getGraphicsContext2D();
		
		data = new Vector<Vector<ColorProfile>>();
		
		this.row = row;
		this.column = column;
		this.weight = 10;
		
		fgColor = Color.BLACK;
		bgColor = Color.WHITE;
		
		history = new LinkedList<Vector<HistoryData>>();
		previousData = new Vector<HistoryData>();
		state = 0;
		
		paintPoint = new LinkedList<PosPair>();
		drawn = new TreeSet<PosPair>();
		
		gc.setFill(bgColor);
		
		for(int i = 0; i < row; ++i) {
			data.add(new Vector<ColorProfile>());
			for(int j = 0; j < column; ++j) {
				data.get(i).add(new ColorProfile(bgColor, 1));
				gc.fillRect(j, i, 1, 1);
			}
		}
		
		setOnMousePressed(event -> PaintCanvasHandler.mousePressed(this, event));
		setOnMouseDragged(event -> PaintCanvasHandler.mouseDragged(this, event));
		setOnMouseReleased(event -> PaintCanvasHandler.mouseReleased(this, event));
		setOnKeyPressed(event -> PaintCanvasHandler.keyPressed(this, event));
		setOnKeyReleased(event -> PaintCanvasHandler.keyReleased(this, event));
	}
	
	/* 
	 * Draw Quadratic Bézier Curve (with 1 control point)
	 * through 3 points that cursor has already passed.
	 */
	public void addPaintPoint(Color color, int x, int y) {
		paintPoint.add(new PosPair(x, y));
		
		if(paintPoint.size() == 1) {
			paint(color, x, y);
		} else if(paintPoint.size() >= 3) {
			double t = 0.0;
			
			if(paintPoint.size() == 4) {
				paintPoint.remove();
				t = 0.5;
			}
			
			double xc = paintPoint.get(1).getFirst() - (paintPoint.get(2).getFirst() - paintPoint.get(0).getFirst()) / 4;
			double yc = paintPoint.get(1).getSecond() - (paintPoint.get(2).getSecond() - paintPoint.get(0).getSecond()) / 4;
			
			xc = 2 * xc - paintPoint.get(0).getFirst();
			yc = 2 * yc - paintPoint.get(0).getSecond();
			
			double x1, x2, y1, y2;
			int xx, yy;
			
			while(t <= 1) {
				x1 = (1 - t) * paintPoint.get(0).getFirst() + t * xc;
				y1 = (1 - t) * paintPoint.get(0).getSecond() + t * yc;
				
				x2 = (1 - t) * xc + t * paintPoint.get(2).getFirst();
				y2 = (1 - t) * yc + t * paintPoint.get(2).getSecond();
				
				xx = (int) Math.round((1 - t) * x1 + t * x2);
				yy = (int) Math.round((1 - t) * y1 + t * y2);
				
				paint(color, xx, yy);
				
				t += 0.001;
			}
		}
	}
	
	public void paint(Color color, int x, int y) {
		for(int i = Math.max(0, (int) Math.floor(y - weight)); i < Math.min(row - 1, (int) Math.ceil(y + weight)); ++i) {
			for(int j = Math.max(0, (int) Math.floor(x - weight)); j < Math.min(column - 1, (int) Math.ceil(x + weight)); ++j) {
				if(Math.pow(y - i, 2) + Math.pow(x - j, 2) < Math.pow(weight, 2) && !drawn.contains(new PosPair(i, j))) {
					drawn.add(new PosPair(i, j));
					
					previousData.add(new HistoryData(new PosPair(i, j), new ColorProfile(data.get(i).get(j)), 
							data.get(i).get(j).draw(new ColorProfile(color, 0.5))));
					
					
					gc.setFill(data.get(i).get(j).getColor());
					gc.fillRect(j, i, 1, 1);
				}
			}
		}
	}
	
	public void setPixel(int x, int y, ColorProfile cp) {
		data.get(x).set(y, new ColorProfile(cp));
		
		gc.setFill(data.get(x).get(y).getColor());
		gc.fillRect(y, x, 1, 1);
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public Vector<Vector<ColorProfile>> getData() {
		return data;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Color getFgColor() {
		return fgColor;
	}
	
	public Color getBgColor() {
		return bgColor;
	}
	
	public LinkedList<Vector<HistoryData>> getHistory() {
		return history;
	}
	
	public Vector<HistoryData> getPreviousData() {
		return previousData;
	}
	
	public int getState() {
		return state;
	}
	
	public LinkedList<PosPair> getPaintPoint() {
		return paintPoint;
	}
	
	public TreeSet<PosPair> getDrawn() {
		return drawn;
	}
}
