package kcore;

public class BigNode {
	private int number;
	private int c;
	private int eta;
	private boolean delete;
	//private int degree;
	
	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public BigNode() {
		this.number = 0;
		this.c = 0;
		this.eta = 0;
		//this.degree = 0;
	}
	
	public BigNode(int number, int c, int eta) {
		this.number = number;
		this.c = c;
		this.eta = eta;
		//this.degree = degree;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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

	@Override
	public String toString() {
		return "BigNode [node=" + number + ", c=" + c + ", eta=" + eta + "]";
	}

	/*public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}*/

	/*@Override
	public String toString() {
		return "BigNode [node=" + node + ", c=" + c + ", eta=" + eta + ", degree=" + degree + "]";
	}*/
	
	
}
