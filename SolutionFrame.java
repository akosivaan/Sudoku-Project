import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SolutionFrame extends JFrame implements ActionListener{

	private int solcount;
	private ArrayList<int[][]> puzzle;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JLabel solutions;
	private JLabel current;
	private JLabel type;
	private JTextField[][] board;
	private JButton next;
	private JButton prev;
	private int ind;
	private int gridsize;
	private int size;

	public SolutionFrame(int solcount,ArrayList<int[][]> puzzle,int gridsize,int type){
		super("Sudoku Solutions");
		this.puzzle = puzzle;
		this.solcount = solcount;
		this.gridsize = gridsize;
		this.size = gridsize*gridsize;
		ind = 0;
		board = new JTextField[size][size];
		if(type == 0)
			this.type = new JLabel("Sudoku Regular!");
		else if(type == 1)
			this.type = new JLabel("Sudoku X!");
		else if(type == 2)
			this.type = new JLabel("Sudoku Y!");
		else if(type == 3)
			this.type = new JLabel("Sudoku XY!");
		solutions = new JLabel("Number of Solutions:"+solcount);
		current = new JLabel("Solution:"+(ind+1));
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT,50,10));
		panel3.add(this.type);
		panel3.add(solutions);
		panel3.add(current);
		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel2 = new JPanel();
		next = new JButton("Next");
		prev = new JButton("Previous");
		next.addActionListener(this);
		prev.addActionListener(this);
		panel1.setLayout(new GridLayout(size,size,1,1));
		for(int x=0;x<size;x++){
			for(int y=0;y<size;y++){
				board[x][y] = new JTextField();
				board[x][y].setEditable(false);
				board[x][y].setHorizontalAlignment(JTextField.CENTER);
				//board[x][y].setVerticalAlignment(JTextField.CENTER);
				panel1.add(board[x][y]);
			}
		}
		updatePuzzle();
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER,50,10));
		panel2.add(prev);
		panel2.add(next);

		this.setLayout(new BorderLayout());
		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
		this.add(panel3,BorderLayout.NORTH);
		this.setVisible(true);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
	}

	public void updatePuzzle(){
		current.setText("Solution:"+(ind+1));
		int[][] puz = puzzle.get(ind);
		for(int x=0;x<size;x++){
			for(int y=0;y<size;y++){
				board[x][y].setText(puz[x][y]+"");
			}
		}
			
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == next){
			if(ind < solcount){
					ind++;
					if(ind != solcount)
						updatePuzzle();
			}
		}
		else if(e.getSource() == prev){
			if(ind >= 1){
					ind--;
					updatePuzzle();	
			}
		}
	}
	
}