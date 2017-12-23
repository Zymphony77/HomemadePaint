package component.paintcanvas;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Vector;

import utility.*;

public class PaintCanvasHandler {
	public static void mousePressed(PaintCanvas pc, MouseEvent event) {
		int x = (int) Math.round(event.getX());
		int y = (int) Math.round(event.getY());
		
		if(event.getButton() == MouseButton.PRIMARY) {
			pc.addPaintPoint(pc.getFgColor(), x, y);
		} else if(event.getButton() == MouseButton.SECONDARY) {
			pc.addPaintPoint(pc.getBgColor(), x, y);
		}
	}
	
	public static void mouseDragged(PaintCanvas pc, MouseEvent event) {
		mousePressed(pc, event);
	}
	
	public static void mouseReleased(PaintCanvas pc) {
		if(pc.getHistory().size() == 20) {
			pc.getHistory().pollFirst();
		}
		
		pc.getHistory().add(new Vector<Pair<PosPair, ColorProfile>>());
		for(PosPair each: pc.getDrawn()) {
			pc.getHistory().getLast().add(new Pair<PosPair, ColorProfile>(each, 
					pc.getData().get(each.getFirst()).get(each.getSecond())));
		}
		
		pc.getDrawn().clear();
		pc.getPaintPoint().clear();
	}
}
