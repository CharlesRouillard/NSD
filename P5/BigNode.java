
public class BigNode {
	private int node;
	private int c;
	private int eta;
	private int degree;
	
	public BigNode() {
		this.node = 0;
		this.c = 0;
		this.eta = 0;
		this.degree = 0;
	}
	
	public BigNode(int number, int c, int eta, int degree) {
		this.node = number;
		this.c = c;
		this.eta = eta;
		this.degree = degree;
	}

	public int getNumber() {
		return node;
	}

	public void setNumber(int number) {
		this.node = number;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "BigNode [node=" + node + ", c=" + c + ", eta=" + eta + ", degree=" + degree + "]";
	}
}
