
public class LabelNode implements Comparable<LabelNode> {
	private int number;
	private int label;
	
	public LabelNode(int number, int label) {
		this.number = number;
		this.label = label;
	}
	
	public int compareTo(LabelNode ln){
		return this.label - ln.label;
	}

	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getLabel() {
		return this.label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "LabelNode [number=" + number + ", label=" + label + "]";
	}
}
