package component.paintcanvas;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.util.Vector;
import java.util.HashSet;

import utility.*;

public class PaintCanvasHandler {
	private static HashSet<MouseButton> activeMouse;
	private static HashSet<KeyCode> activeKey;
	
	static {
		activeKey = new HashSet<KeyCode>();
		activeMouse = new HashSet<MouseButton>();
	}
	
	public static void keyPressed(PaintCanvas pc, KeyEvent event) {
		if(event.getCode() == KeyCode.CONTROL || event.getCode() == KeyCode.COMMAND) {
			activeKey.add(KeyCode.CONTROL);
		}
		
		if(event.getCode() == KeyCode.Z) {
			activeKey.add(KeyCode.Z);
		}
		
		if(activeKey.contains(KeyCode.Z) && activeKey.contains(KeyCode.CONTROL) && activeMouse.size() == 0) {
			if(pc.getHistory().size() == 0) {
				return;
			}
			
			Vector<Pair<PosPair, ColorProfile>> hist = pc.getHistory().pollLast();
			
			for(Pair<PosPair, ColorProfile> each: hist) {
				pc.setPixel(each.getKey().getFirst(), each.getKey().getSecond(), each.getValue());
			}
		}
	}
	
	public static void keyReleased(PaintCanvas pc, KeyEvent event) {
		if(event.getCode() == KeyCode.CONTROL || event.getCode() == KeyCode.COMMAND) {
			activeKey.remove(KeyCode.CONTROL);
		}
		
		if(event.getCode() == KeyCode.Z) {
			activeKey.remove(KeyCode.Z);
		}
	}
	
	public static void mousePressed(PaintCanvas pc, MouseEvent event) {
		activeMouse.add(event.getButton());
		
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
	
	public static void mouseReleased(PaintCanvas pc, MouseEvent event) {
		activeMouse.remove(event.getButton());
		
		if(pc.getHistory().size() > 20) {
			pc.getHistory().pollFirst();
		}
		
		pc.getHistory().add(new Vector<Pair<PosPair, ColorProfile>>(pc.getHist()));
		
		pc.getDrawn().clear();
		pc.getPaintPoint().clear();
		pc.getHist().clear();
	}
}
