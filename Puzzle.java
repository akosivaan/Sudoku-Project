
public class Puzzle{
	private int[][] puzzle;
	private int subgrid;

	public Puzzle(int[][] a, int b){
		puzzle = a;
		subgrid = b;
	}

	public void setPuzzle(int[][] a){
		puzzle = a;
	}

	public void setGrid(int b){
		subgrid = b;
	}

	public int getGrid(){
		return subgrid;
	}

	public int[][] getPuzzle(){
		return puzzle;
	}
}