package utility;

public class PosPair implements Comparable<PosPair> {
	private int first;
	private int second;
	
	public PosPair(int first, int second) {
		this.first = first;
		this.second = second;
	}
	
	public PosPair(PosPair other) {
		this.first = other.first;
		this.second = other.second;
	}
	
	@Override
	public int compareTo(PosPair other) {
		if(this.first < other.first) {
			return -1;
		} else if(this.first > other.first) {
			return 1;
		} else if(this.second < other.second) {
			return -1;
		} else if(this.second > other.second) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getSecond() {
		return second;
	}
}
