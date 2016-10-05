import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Sudoku extends JFrame{

static JFrame frame;
static JFrame frame2;
static JPanel mainPanel;
static JPanel topPanel;
static JPanel bottomPanel;
static JPanel centerPanel;
static JPanel boardPanel;
static JPanel tempPanel;
static JTextArea fileArea;
static JLabel label;
static JButton fileBtn;
static JButton regBtn;
static JButton xBtn;
static JButton yBtn;
static JButton xYBtn;
static JButton checkGame;
static JButton checkGameX;
static JButton checkGameY;
static JButton checkGameXY;
static JButton next,prev;
static JFileChooser chooser;
static JLabel label1;
static JLabel label2;
static JTextField[][] puzField;
static int[][] puzzle;
static String fileName;
static int subGrid;
static ArrayList<Puzzle> puzz;
static int currInd;
static int total;
	
//Implementation of UI
public static void Sudoku()
{
	frame = new JFrame ("SUDOKU PUZZLE");
	//topPanel
	topPanel = new JPanel();
	topPanel.setBackground(Color.WHITE);
	topPanel.setLayout(new FlowLayout());
	fileBtn = new JButton("Choose a File");
	topPanel.add(fileBtn);

	//boardPanel
	boardPanel = new JPanel();
	boardPanel.setLayout(new BorderLayout());
	
	//centerPanel
	centerPanel = new JPanel();
	centerPanel.setLayout(new BorderLayout());
	centerPanel.add(boardPanel,BorderLayout.CENTER);
	centerPanel.setVisible(false);

	//bottomPanel
	bottomPanel = new JPanel();
	bottomPanel.setBackground(Color.WHITE);
	bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,5));
	label1 = new JLabel("Solve:");
	label2 = new JLabel("Check Game:");
	regBtn = new JButton("R");
	xBtn = new JButton("X");
	yBtn = new JButton("Y");
	xYBtn = new JButton("XY");
	checkGame = new JButton("R");
	checkGameX = new JButton("X");
	checkGameY = new JButton("Y");
	checkGameXY = new JButton("XY");
	next = new JButton("NEXT");
	prev = new JButton("PREV");
	bottomPanel.add(label1);
	bottomPanel.add(regBtn);
	bottomPanel.add(xBtn);
	bottomPanel.add(yBtn);
	bottomPanel.add(xYBtn);
	bottomPanel.add(label2);
	bottomPanel.add(checkGame);
	bottomPanel.add(checkGameX);
	bottomPanel.add(checkGameY);
	bottomPanel.add(checkGameXY);
	label = new JLabel("Puzzle No:");
	topPanel.add(label);
	mainPanel = new JPanel();
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(topPanel,BorderLayout.NORTH);
	mainPanel.add(centerPanel,BorderLayout.CENTER);
	mainPanel.add(bottomPanel,BorderLayout.SOUTH);
	mainPanel.add(next,BorderLayout.EAST);
	mainPanel.add(prev,BorderLayout.WEST);
	frame.add(mainPanel);
	frame.pack();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	frame.setSize(720,500);

	//File Chooser
	chooser = new JFileChooser();

	next.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			nextPuzzle();
		}
	});
	prev.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			prevPuzzle();
		}
	});
	//When fileBtn clicks
	fileBtn.addActionListener(new ActionListener() 
  {
        public void actionPerformed(ActionEvent e)
        {
          int returnName = chooser.showOpenDialog(null);
			 		String path;
		        if (returnName == JFileChooser.APPROVE_OPTION) {
		            File f2 = chooser.getSelectedFile();
		            if (f2 != null) {
		                path = f2.getAbsolutePath();
		                File n = new File(path);
										//System.out.println(n);
										boardPanel.removeAll();
										readFile(n);
										boardPanel.revalidate();
		            }
		        }     
		    }        
  });
	regBtn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			solveSudoku(0);
		}
	});
	xBtn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			solveSudoku(1);
		}
	});
	yBtn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(subGrid%2 == 1)
				solveSudoku(2);
			else
				JOptionPane.showMessageDialog(frame,"There is no solution for even size Sudoku Y");
		}
	});
	xYBtn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(subGrid%2 == 1)
				solveSudoku(3);
			else
				JOptionPane.showMessageDialog(frame,"There is no solution for even size Sudoku XY");
		}
	});
	checkGame.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			checkFinish(0);
		}
	});
	checkGameX.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			checkFinish(1);
		}
	});
	checkGameY.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(subGrid%2 == 1)
				checkFinish(2);
			else
				JOptionPane.showMessageDialog(frame,"Even subgrids cannot be solved in Sudoku Y");
		}
	});
	checkGameXY.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(subGrid%2 == 1)
				checkFinish(3);
			else
				JOptionPane.showMessageDialog(frame,"Even subgrids cannot be solved in Sudoku XY");
		}
	});
}

//Update the state of the puzzle
public static void updatePuzzle(){
	boardPanel.removeAll();
	Puzzle p = puzz.get(currInd);
	int[][] a = p.getPuzzle();
	int b = p.getGrid();
	int size = b*b;
	subGrid = b;
	puzzle = new int[size][size];
	for(int x=0;x<b*b;x++){
		for(int y=0;y<b*b;y++){
			puzzle[x][y] = a[x][y];
		}
	}
	
	tempPanel = new JPanel();
	tempPanel.setLayout(new GridLayout(size,size));
	tempPanel.setBackground(Color.GRAY);
	puzField = new JTextField[size][size];
	for(int x=0; x<size; x++){			
		for(int y=0;y<size; y++){
			puzField[x][y] = new JTextField();
			puzField[x][y].setHorizontalAlignment(JTextField.CENTER);					
			if(puzzle[x][y] != 0){
				puzField[x][y].setText(puzzle[x][y]+"");
				puzField[x][y].setEditable(false);
			}
			tempPanel.add(puzField[x][y]);
		}
	}
	boardPanel.add(tempPanel, BorderLayout.CENTER);
	centerPanel.setVisible(true);
}
//Next Puzzle
public static void nextPuzzle(){
	if(currInd < total-1){
		currInd++;
		label.setText("Puzzle No:"+(currInd+1));
		updatePuzzle();
	}
}
//Previous Puzzle
public static void prevPuzzle(){
	if(currInd > 0){
		currInd--;
		label.setText("Puzzle No:"+(currInd+1));
		updatePuzzle();
	}
}
//Check the end of the game
public static boolean checkEndGame(int row,int col,int value, int type){
	int size = subGrid * subGrid;
	int gridsize = subGrid;
	int a,b,gridR = -1,gridC = -1;
	
	if(value > size || value < 1)
		return false;

	//check  columns
	for(a=0;a<size;a++){
		if(a != col){
			if(value == puzzle[row][a] || value == 0){
				return false;		
			}	
		}
		
	}
	//Check rows
	for(a=0;a<size;a++){
		if(a != row){
			if(value == puzzle[a][col] || value == 0){
				return false;		
			}
		}
	}

	//get start of grid
	for(a=0;a<gridsize;a++){
		if((row-a)%gridsize == 0){
			gridR = row-a;
		}
		if((col-a)%gridsize == 0){
			gridC = col-a;			
		}	
	}
	
	//check grid
	for(a=gridR;a<gridR+(gridsize);a++){
		for(b=gridC;b<gridC+(gridsize);b++){
			if(a != row && col != col){
				if(value == puzzle[a][b] || value == 0){
					return false;			
				}
			}
		}
	}

	//if type = 0 then none, type = 1 then X, type = 2 then Y, type = 3 XY 
	//Return false if X sudoku is not satisfied
	//Insert X sudoku validation for current input
	if(type == 1 || type == 3){
		if(row == col){
			for(a=0;a<size;a++){//left to right diagonal
				if(a != row && a != col){
					if(puzzle[a][a] == value){
						return false;
					}
				}
			}
		}
		else if(row+col == size -1){//right to left diagonal
			for(a=0;a<size;a++){
				if(a != row && col != size-1-a){
					if(puzzle[a][size-1-a] == value){
					return false;
					}	
				}
			}
		}
	}
	//Return false if Y sudoku is not satisfied
	//Insert Y sudoku validation for current input
	if(type == 2 || type == 3){
		if(row <= size/2){
			if(row == col){//If the current value is in the left Y diagonal
				for(a=0;a<=size/2;a++){
					if(row != a && col != a){
						if(puzzle[a][a] == value){
							return false;
						}
					}
				}
				for(a=size/2+1;a<size;a++){
						if(puzzle[a][size/2] == value){
							return false;
						}
				}
			}
			else if(row+col == size -1){//If the current value is in the right Y diagonal
				for(a=0;a<=size/2;a++){
					if(row != a && col != size-1-a){
						if(puzzle[a][size-1-a] == value){
							return false;
						}
					}
				}
				for(a=size/2+1;a<size;a++){
					if(puzzle[a][size/2] == value){
						return false;
					}
				}
			}
		}
		else if(row > size/2 && col == size/2){
			for(a=size/2+1;a<size;a++){//If the current value is in the Y tail
				if(row != a){
					if(puzzle[a][col] == value){
						return false;
					}
				}
			}
		}
	}
	return true;
}

public static void checkFinish(int type){
	int size = subGrid * subGrid;
	boolean c = false;

	//Recreate puzzle
	for(int x=0;x<size;x++){
		for(int y=0;y<size;y++){
			if(puzField[x][y].getText().isEmpty()){
				puzzle[x][y] = 0;
			}
			else{
				puzzle[x][y] = Integer.parseInt(puzField[x][y].getText());	
			}
		 	
		}
	}
	//Check initial inputs in X
	if(type == 1 || type == 3){
		for(int x=0;x<size;x++){
			for(int y=x+1;y<size;y++){
				if(puzzle[x][x] == puzzle[y][y]){
					c = true;
				}
				if(puzzle[x][size-1-x] == puzzle[y][size-1-y]){
					c = true;
				}
			}
		}
	}
	//Initial check of Y puzzle
	if(type == 2 || type == 3){
		for(int x=0;x<size;x++){
			for(int y=x+1;y<size;y++ ){
				if(x < size/2){
					if(y < size/2){
						if(puzzle[x][x] == puzzle[y][y]){
							c = true;
						}
						if(puzzle[x][size-1-x] == puzzle[y][size-1-y]){
							c = true;
						}
					}
					else{
						if(puzzle[x][x] == puzzle[y][size/2]){
							c = true;
						}
					}
				}
				else{
					if(puzzle[x][size/2] == puzzle[y][size/2]){
						c = true;
					}
				}
			}
		}
	}

	//Check each values in the board
	if(!c){
		for(int x=0;x<size;x++){
			for(int y=0;y<size;y++){
				int value = puzzle[x][y];//Integer.parseInt(puzField[x][y].getText());
				if(!checkEndGame(x,y,value,type)){
					c = true;
					break;
				}
			}
			if(c){
				break;
			}
		}
	}
	//If the answer is correct
	if(!c){
		if(type == 0)
			JOptionPane.showMessageDialog(frame,"You have solved Regular Sudoku");
		else if(type == 1)
			JOptionPane.showMessageDialog(frame,"You have solved X Sudoku");
		else if(type == 2)
			JOptionPane.showMessageDialog(frame,"You have solved Y Sudoku");
		else if(type == 3)
			JOptionPane.showMessageDialog(frame,"You have solved XY Sudoku");
	}
	//If the answer is wrong
	else{
		JOptionPane.showMessageDialog(frame,"Please try again!");	
	}
}

//Core of the program
//Solve the sudoku using backtracking
public static void solveSudoku(int type){
	int size = subGrid * subGrid;
	int ccount = size * size;
	
	int start,move;
	int[] nopts = new int[ccount+2];
	int[][] opts = new int[ccount+2][size+2];
	int[][] copy = new int[size][size];
	int i, candidate,row,col,a,b,counter = 0;
	int check;
	int val;
	boolean flag = true;
	ArrayList<int[][]> solList = new ArrayList<int[][]>();
	move = start = 0;
	nopts[start] = 1;
	
	//Copying of puzzle
	for(int x=0;x<size;x++){
		for(int y=0;y<size;y++){
			if(puzField[x][y].getText().isEmpty()){
				puzzle[x][y] = 0;
			}
			else{
				puzzle[x][y] = Integer.parseInt(puzField[x][y].getText());
				if(puzzle[x][y] > size || puzzle[x][y] < 1)
					puzzle[x][y] = 0;
			}
		}
	}
	
	//Copy puzzle!
	for(int x=0;x<size;x++){
		for(int y=0;y<size;y++){
			if(puzzle[x][y] != 0){
				copy[x][y] = -1;
			}
			else
				copy[x][y] = 0;
		}
	}
	//Initial check of X puzzle.
	if(type == 1 || type == 3){
		for(int x=0;x<size;x++){
			for(int y=x+1;y<size;y++){
				if(puzzle[x][x] == puzzle[y][y] && puzzle[x][x] != 0 && puzzle[y][y] != 0){
					flag = false;
				}
				if(puzzle[x][size-1-x] == puzzle[y][size-1-y] && puzzle[x][size-1-x] != 0 && puzzle[y][size-1-y] != 0){
					flag = false;
				}
			}
		}
	}
	//Initial check of Y puzzle
	if(type == 2 || type == 3){
		for(int x=0;x<size;x++){
			for(int y=x+1;y<size;y++ ){
				if(x < size/2){
					if(y < size/2){
						if(puzzle[x][x] == puzzle[y][y] && puzzle[x][x] != 0 && puzzle[y][y] != 0){
							flag = false;
						}
						if(puzzle[x][size-1-x] == puzzle[y][size-1-y] && puzzle[x][size-1-x] != 0 && puzzle[y][size-1-y] != 0){
							flag = false;
						}
					}
					else{
						if(puzzle[x][x] == puzzle[y][size/2] && puzzle[x][x] != 0 && puzzle[y][size/2] != 0){
							flag = false;
						}
					}
				}
				else{
					if(puzzle[x][size/2] == puzzle[y][size/2] && puzzle[x][size/2] != 0 && puzzle[y][size/2] != 0){
						flag = false;
					}
				}
			}
		}
	}
	while(nopts[start] > 0 && flag){
		if(nopts[move]>0)
		{
			move++;
			nopts[move] = 0;
			row = (move-1) / size;
			col = (move-1) % size;

			if(move == ccount+1){
				counter++;
				//Create copy of puzzle then save to arraylist
				int[][] nPuzzle = new int[size][size];
				for(int x=0;x<size;x++){
					for(int y=0;y<size;y++){
						nPuzzle[x][y] = puzzle[x][y];
					}
				}
				//Add one of the solution in arraylist of solutions
				solList.add(nPuzzle);
			}
			else{
				//If the puzzle needs candidates
				if(puzzle[row][col] == 0){
					for(candidate = size;candidate >= 1; candidate--){
						if(checkValid(row,col,candidate,type)){
							nopts[move]++;
							puzzle[row][col] = opts[move][nopts[move]] = candidate;						
						}					
					}		
				}
				//If the puzzle is already filled up
				else if(copy[row][col] == -1){
					nopts[move] = 1;
				}
			}
			//printTable(copy,gridsize);
		}
		else 
		{
			//backtrack
			move--;
			if(move == start)
				break;
			nopts[move]--;

			row = (move - 1)/size;
			col = (move - 1)%size;
			
			if(copy[row][col] == -1){
				nopts[move] = 0;
			}
			else{
				if(nopts[move] == 0)
					puzzle[row][col] = 0;
				else{
					if(checkValid(row,col,opts[move][nopts[move]],type))
						puzzle[row][col] = opts[move][nopts[move]];
					else{//Backtrack untils it reaches the valid option
						while(!checkValid(row,col,opts[move][nopts[move]],type)){
							nopts[move]--;
							if(nopts[move] == 0){								
								break;
							}			
						}
						if(nopts[move] == 0)
							puzzle[row][col] = 0;
						else
							puzzle[row][col] = opts[move][nopts[move]];
					}
				}
					
			}

		}
	}
	if(counter != 0){
		//System.out.println("Im here");
		new SolutionFrame(counter,solList,subGrid,type);
	}
	else{
		JOptionPane.showMessageDialog(frame,"There is no solution for the current state");
	}
}

/*
	puzzle - the current state of sudoku table
	gridsize - size of grid
	size - size of sudoku puzzle
*/
public static boolean checkValid(int row, int col,int value,int type){
	int size = subGrid * subGrid;
	int gridsize = subGrid;
	int a,b,gridR = -1,gridC = -1;
	
	//check  columns
	for(a=0;a<size;a++){
		if(value == puzzle[row][a]){
			return false;		
		}
	}
	//Check rows
	for(a=0;a<size;a++){
		if(value == puzzle[a][col]){
			return false;		
		}
	}

	//get start of grid
	for(a=0;a<gridsize;a++){
		if((row-a)%gridsize == 0){
			gridR = row-a;
		}
		if((col-a)%gridsize == 0){
			gridC = col-a;			
		}	
	}
	
	//check grid
	for(a=gridR;a<gridR+(gridsize);a++){
		for(b=gridC;b<gridC+(gridsize);b++){
			if(value == puzzle[a][b]){
				return false;			
			}
		}
	}

	//if type = 0 then none, type = 1 then X, type = 2 then Y, type = 3 XY 
	//Return false if X sudoku is not satisfied
	//Insert X sudoku validation for current input
	int c = size - 1;
	if(type == 1 || type == 3){
		//System.out.println("Entered X!");
		if(row == col){
			for(a=0;a<size;a++){
				if(puzzle[a][a] == value){
					return false;
				}
			}
		}
		else if(row+col == size -1){
			for(a=0;a<size;a++){
				if(puzzle[a][size-1-a] == value){
					return false;
				}
			}
		}
	}
	//Return false if Y sudoku is not satisfied
	//Insert Y sudoku validation for current input
	if(type == 2 || type == 3){
		//System.out.println("Entered Y!");
		if(row <= size/2){
			if(row == col){
				for(a=0;a<=size/2;a++){
					if(puzzle[a][a] == value){
						return false;
					}
				}
				for(a=size/2+1;a<size;a++){
					if(puzzle[a][size/2] == value){
						return false;
					}
				}
			}
			else if(row+col == size -1){
				for(a=0;a<=size/2;a++){
					if(puzzle[a][size-1-a] == value){
						return false;
					}
				}
				for(a=size/2+1;a<size;a++){
					if(puzzle[a][size/2] == value){
						return false;
					}
				}
			}
		}
		else if(row > size/2 && col == size/2){
			for(a=0;a<size/2;a++){
				if(puzzle[a][a] == value){
					return false;
				}
				if(puzzle[a][size-1-a] == value){
					return false;
				}
			}
			for(a=size/2+1;a<size;a++){
				if(puzzle[a][col] == value){
					return false;
				}
			}
		}
	}
	return true;
}

static void readFile(File file){
		int numOfpuz, i, j, c, a, b;

		try{
  		Scanner input = new Scanner(file);

			String[] line = input.nextLine().split("\n");
			numOfpuz = Integer.parseInt(line[0]);
			total = numOfpuz;
			puzz = new ArrayList<Puzzle>();
			currInd = 0;
			if(numOfpuz > 0)
				label.setText("Puzzle No:"+1);
			for(i=0; i<numOfpuz; i++){
				line = input.nextLine().split("\n");
				subGrid = Integer.parseInt(line[0]);
				int size = subGrid * subGrid;
				int[][] puzzie = new int[size][size];
				c = 0;
				while(c<size){
					line = input.nextLine().split("\n");
					for(j=0; j<size; j++){
							//System.out.println(line[0]);
							String[] x = line[0].split("\\s+");
							//System.out.println(x.length);
							for(int y = 0; y<x.length; y++){
								puzzie[c][y] = Integer.parseInt(x[y]);
							}
							//System.out.println(puzzle[c][j]);
					}
					c++;			
				}
				Puzzle p = new Puzzle(puzzie,subGrid);
				puzz.add(p);
				/*tempPanel = new JPanel();
				tempPanel.setLayout(new GridLayout(size,size));
				tempPanel.setBackground(Color.GRAY);
				puzField = new JTextField[size][size];
				//JButton[][] button = new JButton[size][size];				

				for(a=0; a<subGrid*subGrid; a++){
				//puzzle[a] = (int *)malloc(sizeof(int)*subGrid1*subGrid1);			
					for(b=0;b<subGrid*subGrid; b++){
						//System.out.println(puzzle[a][b]);
						
						String tmp = Integer.toString(puzzle[a][b]);
						puzField[a][b] = new JTextField();
						puzField[a][b].setHorizontalAlignment(JTextField.CENTER);					
						//puzField[a][b].set
						if(puzzle[a][b] != 0){
							puzField[a][b].setText(tmp);
							puzField[a][b].setEditable(false);
						}
						tempPanel.add(puzField[a][b]);
					}
				}
				boardPanel.add(tempPanel, BorderLayout.CENTER);
				centerPanel.setVisible(true);*/
				}
				updatePuzzle(); 
			}catch(Exception ex){
				System.out.println("Error in processing a file!");
			}
}


public static void main (String[] args){
	Sudoku ui = new Sudoku();
	SwingUtilities.invokeLater(new Runnable() 
		 {
		    public void run() {
		    	Sudoku();
		    } 
			 });
}

}
