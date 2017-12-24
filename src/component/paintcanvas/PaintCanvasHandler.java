package component.paintcanvas;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
		
		if(event.getCode() == KeyCode.SHIFT) {
			activeKey.add(KeyCode.SHIFT);
		}
		
		if(event.getCode() == KeyCode.Z) {
			activeKey.add(KeyCode.Z);
		}
		
		if(activeKey.contains(KeyCode.Z) && activeKey.contains(KeyCode.CONTROL) && activeMouse.size() == 0) {
			if(activeKey.contains(KeyCode.SHIFT)) {
				if(pc.getState() == pc.getHistory().size()) {
					return;
				}
				
				pc.setState(pc.getState() + 1);
				
				Vector<HistoryData> hist = pc.getHistory().get(pc.getState() - 1);
				
				for(HistoryData each: hist) {
					pc.setPixel(each.getPosition().getFirst(), each.getPosition().getSecond(), each.getAfter());
				}
			} else {
				if(pc.getState() == 0) {
					return;
				}
				
				Vector<HistoryData> hist = pc.getHistory().get(pc.getState() - 1);
				
				for(HistoryData each: hist) {
					pc.setPixel(each.getPosition().getFirst(), each.getPosition().getSecond(), each.getBefore());
				}
				
				pc.setState(pc.getState() - 1);
			}
		}
	}
	
	public static void keyReleased(PaintCanvas pc, KeyEvent event) {
		if(event.getCode() == KeyCode.CONTROL || event.getCode() == KeyCode.COMMAND) {
			activeKey.remove(KeyCode.CONTROL);
		}
		
		if(event.getCode() == KeyCode.SHIFT) {
			activeKey.remove(KeyCode.SHIFT);
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
		
		while(pc.getHistory().size() > pc.getState()) {
			pc.getHistory().removeLast();
		}
		
		pc.getHistory().add(new Vector<HistoryData>(pc.getPreviousData()));
		pc.setState(pc.getState() + 1);
		
		if(pc.getHistory().size() > 20) {
			pc.getHistory().pollFirst();
			pc.setState(pc.getState() - 1);
		}
		
		pc.getDrawn().clear();
		pc.getPaintPoint().clear();
		pc.getPreviousData().clear();
	}
}
