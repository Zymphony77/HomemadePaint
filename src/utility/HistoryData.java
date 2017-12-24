package utility;

public class HistoryData {
	private PosPair position;
	private ColorProfile before;
	private ColorProfile after;
	
	public HistoryData(PosPair position, ColorProfile before, ColorProfile after) {
		this.position = new PosPair(position);
		this.before = new ColorProfile(before);
		this.after = new ColorProfile(after);
	}
	
	public HistoryData(HistoryData other) {
		this.position = new PosPair(other.position);
		this.before = new ColorProfile(other.before);
		this.after = new ColorProfile(other.after);
	}
	
	public PosPair getPosition() {
		return position;
	}
	
	public ColorProfile getBefore() {
		return before;
	}
	
	public ColorProfile getAfter() {
		return after;
	}
}
