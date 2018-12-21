package bfs;

public class Graph {
	private int[][] matrix;
	private int n;
	private int first;
	private int last;
	
	public Graph() {
		
	}
	
	public Graph(int n, int first, int last) {
		this.n = n;
		this.first = first;
		this.last = last;
		this.matrix = new int[n][n];
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++) {
				this.matrix[i][j] = 0;
			}
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
	
	public void printMatrix() {
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print(this.matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
