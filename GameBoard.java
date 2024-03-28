package main;
import java.util.*;

public class GameBoard {
	private final int defaultBoardSize = 8;
	private Tile[][] tileBoard;
	private int[][] groupedBoard; // -1 grouped player1, 0 ungrouped, 1 grouped player2
	private int boardSize;
	private int movesPlayed;
	private boolean player1Turn;
	private int p1ColorI;
	private int p2ColorI;
	private int p1Score =0;
	private int p2Score =0;
	 
	public static boolean compareTo(GameBoard A, GameBoard B) {
		if(A == null && B == null) {
			return true;
		}
		if(A.getBoardSize() != B.getBoardSize()) {
			return false;
		}else if(A.getP1ColorI() != B.getP1ColorI()) {
				return false;
		}else if(A.getP2ColorI() != B.getP2ColorI()) {
				return false;
		}else if(A.getP1Score() != B.getP1Score()) {
				return false;
		}else if (A.getP2Score() != B.getP2Score()) {
				return false;
		}else
			for(int i = 0; i < A.getBoardSize(); i++) {
				for (int j = 0; j< A.getBoardSize(); j++) {
					if(A.getTileBoard()[i][j].getColorI() != B.getTileBoard()[i][j].getColorI()) {
						return false;
					}else if(A.getTileBoard()[i][j].getGrouped() != B.getTileBoard()[i][j].getGrouped()) {
						return false;
					}else if(A.getGroupedBoard()[i][j] != B.getGroupedBoard()[i][j]) {
						return false;
					}	
				}
			}
		return true;
	}
	  

	//Defualt GameBoard Constructor 
	public GameBoard() {
		boardSize = defaultBoardSize;
		//Pre-populate board with default(64) empty tiles
		tileBoard = new Tile[defaultBoardSize][defaultBoardSize];
		//instantiates groupedBoard
		groupedBoard = new int[defaultBoardSize][defaultBoardSize];
		//populates tileBoard with default Tiles and assigns all groupings to 0
		for(int i = 0; i < defaultBoardSize; i++) {
			for(int j = 0; j < defaultBoardSize; j++) {
				tileBoard[i][j] = new Tile();
				groupedBoard[i][j] = 0;
			}
		} 
		//sets grouping Values
		groupedBoard[defaultBoardSize -1][0] = -1;
		groupedBoard[0][defaultBoardSize -1]= 1;
		//Tiles assinged colors via game Rules
		this.randomizeTileColors();
		this.setP2ColorI(this.getTileBoard()[0][this.getBoardSize() -1].getColorI());
		this.setP1ColorI(this.getTileBoard()[this.getBoardSize() -1][0].getColorI());
		//set p1 turn
		this.setPlayer1Turn(true);
	}
	//GameBoard Constructor of custom size
	public  GameBoard(int size){
		boardSize = size;
		tileBoard = new Tile[size][size];
		groupedBoard = new int[size][size];
	
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				tileBoard[i][j] = new Tile();
				groupedBoard[i][j] = 0;

			}
		}
		groupedBoard[size-1][0] = -1;
		groupedBoard[0][size-1] = 1;
		this.randomizeTileColors();
		this.setP2ColorI(this.getTileBoard()[0][this.getBoardSize() -1].getColorI());
		this.setP1ColorI(this.getTileBoard()[this.getBoardSize() -1][0].getColorI());
	
		this.setPlayer1Turn(true);

	}
	//copy constructor 
	public GameBoard(GameBoard b) {
		groupedBoard = new int[b.getBoardSize()][b.getBoardSize()];
		tileBoard = new Tile[b.getBoardSize()][b.getBoardSize()]; 
		//copy tileBoard
		for(int i = 0; i < b.getBoardSize(); i++) {
			for(int j =0; j < b.getBoardSize(); j++) {
				tileBoard[i][j] = new Tile(b.getTileBoard()[i][j]);
			}
		}
		//copy groupedBoard
		for(int i = 0; i < b.getBoardSize(); i++) {
			for( int j = 0; j < b.getBoardSize(); j++) {
				groupedBoard[i][j] = b.getGroupedBoard()[i][j];
			}
		}
		boardSize = b.getBoardSize();
		movesPlayed = b.getMovesplayed();
		player1Turn = b.getPlayer1Turn();
		p1ColorI = b.getP1ColorI();
		p2ColorI = b.getP2ColorI();
		p1Score = b.getP1Score();
		p2Score = b.getP2Score();
	}
	//checks if any tiles remain unclaimed by a player
	public boolean containsUnGrouped() {
		for(int[] i: this.groupedBoard) {
			for (int j: i) {
				if(j == 0) {
					return true;
				}
			}
		}
		return false;
	}
	//Checks if tiles have been unassigned a color
	public boolean containsBlanks() {
		for (Tile[] r: this.tileBoard) {
			for (Tile t: r) {
				if(t.getColorI() == -1 || t.getColorS() == "") {
					return true;
				}
			}
		}
		return false;
	}
	
	// Assign colors to tiles ensuring that colors do not match cardinal neighbors color. 
	//call on gameBoard object
	public void randomizeTileColors() {
		/*
		 * randomizes colors of each tile.
		 * starts at [0][0]
		 * Like colors cannot touch sides (next to each other on same row or on same column)
		 * Like colors can share corners/diagonals
		 */
		
		Random random = new Random();
		//check left check top random 
		for(int row = 0; row < this.boardSize; row++) {
			for(int col =0; col < this.boardSize; col++) {
				//if [0][0] assign random color
				if(row == 0 && col == 0) {
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					
					//if top row in column
				}else if(row ==0) {
					//start off setting random color
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					//only check left because iterate left to right
						//meaning tile to the right is unassigned, eliminating unnecessary checks
					//while tiles color == tiles to the left color, set tile color to new color 
					while (this.tileBoard[row][col].getColorI() == this.tileBoard[row][col-1].getColorI()) {
						this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					}
				
			
					//if first in row
				}else if(col == 0) {
					//start off setting random color
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					//only check top because iteration is top to bottom
						//meaning tile bellow is unassigned, eliminating unnecessary checks
					//white tile color = tile above color, set tile color to new color
					while(this.tileBoard[row][col].getColorI() == this.tileBoard[row-1][col].getColorI()) {
						this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					}
				
				}else {
				//not first in row and not top row
					//start off setting random color
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					// while tile color = tile to the left or above color, set to random 
					while (this.tileBoard[row][col].getColorI() == this.tileBoard[row-1][col].getColorI() || this.tileBoard[row][col].getColorI() == this.tileBoard[row][col-1].getColorI()) {
						this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					}
				}

						
			}
		}
		
		//make starting position fair
		//players cant start same color
		//while p1 = p2 randomize
		while(this.tileBoard[0][this.getBoardSize() -1].getColorI() ==  this.tileBoard[this.getBoardSize()-1][0].getColorI()) {
			this.tileBoard[this.getBoardSize()-1][0].setColorI(random.nextInt(Tile.getArrColors().length));
		}
		//players can't be surrounded by opponent color 
			//check player 1 surrounded
		//while p2 == p1 surroundings,  randomize 1 of p1 surroundings
		while((this.tileBoard[0][this.getBoardSize() -1].getColorI() == this.tileBoard[this.getBoardSize() -2 ][0].getColorI() ) &&  (this.tileBoard[0][this.getBoardSize() -1].getColorI() == this.tileBoard[this.getBoardSize() -1 ][1].getColorI())) {
			//while [2nd to bottom][0] == surrounding tiles randomize [2nd to bottom][0]
			while((this.tileBoard[this.getBoardSize() -2 ][0].getColorI() == this.tileBoard[this.getBoardSize() -1 ][0].getColorI()) || (this.tileBoard[this.getBoardSize() -2 ][0].getColorI() == this.tileBoard[this.getBoardSize() -3 ][0].getColorI()) || (this.tileBoard[this.getBoardSize() -2 ][0].getColorI() == this.tileBoard[this.getBoardSize() -2 ][1].getColorI())) {
				this.tileBoard[this.getBoardSize() -2][0].setColorI(random.nextInt(Tile.getArrColors().length));
			}
		}
		
		//check p2 surrounded
		//while p1 == p2 surroundings, randomize 1 of p2 surroundings
		while((this.tileBoard[this.getBoardSize() -1][0].getColorI() == this.tileBoard[0][this.getBoardSize() -2].getColorI() ) &&  (this.tileBoard[this.getBoardSize() -1][0].getColorI() == this.tileBoard[1][this.getBoardSize() -1].getColorI() )) {
			//while [1][right most] == surrounding tiles, randomize [1][right most]
			while((this.tileBoard[1][this.getBoardSize() -1].getColorI() == this.tileBoard[0][this.getBoardSize() -1].getColorI()) || (this.tileBoard[1][this.getBoardSize() -1].getColorI() == this.tileBoard[1][this.getBoardSize() - 2].getColorI()) || (this.tileBoard[1][this.getBoardSize() -1 ].getColorI() == this.tileBoard[2][this.getBoardSize() -1 ].getColorI())) {
				this.tileBoard[1][this.getBoardSize() - 1].setColorI(random.nextInt(Tile.getArrColors().length));
			}
		}		//players shouldn't?? be surrounded by the same color period??? check gamepidgeon logic 
	}
	
	//Assign colors to tiles ensuring that colors do not match cardinal neighbors color. 
	//Call statically and pass gameBoard Argument 
	public static void randomizeTileColors(GameBoard gameBoard) {
		
		
		/*
		 * randomizes colors of each tile.
		 * starts at [0][0]
		 * Like colors cannot touch sides (next to each other on same row or on same column)
		 * Like colors can share corners/diagonals
		 */ 
		Random random = new Random();
				
		
		//check left check top random 
		for(int row = 0; row < gameBoard.boardSize; row++) {
			for(int col =0; col < gameBoard.boardSize; col++) {
				//if very [0][0] assign random color
				if(row == 0 && col == 0) {
					gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					
					//if top in column
				}else if(row ==0) {
					//start off setting random color
					gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					//only check left 
					//while tiles color = tiles to the left color, set tile color to new color 
					while (gameBoard.tileBoard[row][col].getColorI() == gameBoard.tileBoard[row][col-1].getColorI()) {
						gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					}
				
			
					//if  first in row
				}else if(col == 0) {
					//start off setting random color
					gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					//only check top
					//white tile color = tile above color, set tile color to new color
					while(gameBoard.tileBoard[row][col].getColorI() == gameBoard.tileBoard[row-1][col].getColorI()) {
						gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					}
				
				}else {
					//start off setting random color
					gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					// while tile color = tile to the left or above color, set to random 
					while (gameBoard.tileBoard[row][col].getColorI() == gameBoard.tileBoard[row-1][col].getColorI() || gameBoard.tileBoard[row][col].getColorI() == gameBoard.tileBoard[row][col-1].getColorI()) {
						gameBoard.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					}
				}

						
			}
		}
	}

	//print Tile Colors as either integers or Strings
	//pass n=0 for integers, pass n=1 for Strings
	public void printTileColor(int n) {
	//pass n=0 for integers, pass n=1 for Strings	
		
	//print board colors as integers
		if(n == 0) {
			for(int i = 0; i < defaultBoardSize; i++) {
				for(int j = 0; j < defaultBoardSize; j++) {
					if(this.tileBoard[i][j].getColorI() == -1){
						System.out.print("BLANK ");
					}else {
					System.out.print(this.tileBoard[i][j].getColorI() + " ");
					}
				}
				System.out.println("\n");

			} 
		}
		
		//print board colors as string
		if(n == 1) {
			for(int i = 0; i < defaultBoardSize; i++) {
				
				for(int j = 0; j < defaultBoardSize; j++) {
					
					if(this.tileBoard[i][j].getColorS().equals("")) {
						System.out.print("BLANK ");
					}else {
					System.out.print(this.tileBoard[i][j].getColorS() + " ");
					}
				} 
				System.out.println("\n");

			}
		}
	}
	
	//print board values of groupedBoard. -1:p1, 0:un-grouped, 1:p2
	public void printGroupedBoard() {
		for(int[] i: this.groupedBoard) {
			for(int j : i) {
				System.out.print(j + " ");
			}
			System.out.println("\n");

		}
	}
	
	public void playMove(int colorSelected) {
		if(player1Turn) {
			this.setP1ColorI(colorSelected);
		}else {
			this.setP2ColorI(colorSelected);
		}
		
		//actions called for turn
		this.setGroupColor(colorSelected);
		this.group();
		this.updateScore();
	}
	
	//all CURRENTLY grouped tiles change color to selected color
	//selected color being argument n
	public void setGroupColor(int n) {
		int matchingGroupVal;
		//assigns the grouping value based of player turn
		if(player1Turn) {
			matchingGroupVal = -1;
		}else {
			matchingGroupVal = 1;
		}
		
		//iterates through board
		for(int row = 0; row < this.getBoardSize(); row ++) {
			for (int col = 0; col < this.getBoardSize(); col++) {
				
				//if tile grouping values match, set tile Color to selected color(n)
				if(matchingGroupVal == this.getGroupedBoard()[row][col]) {
					this.getTileBoard()[row][col].setColorI(n);
				}
			}
		}
	}
	
	
	//Assigns neighbors of group, of groupColor, to group
		public void group() {
			//??add grouping value for entirely encased tiles to eliminate future checks.

			int matchingGroupVal;
			//Assigns grouping value based off of player turn 
			if(player1Turn) {
				matchingGroupVal = -1;
			}else {
				matchingGroupVal = 1;
			}
			//iterate through board
			for(int row =0; row < this.getBoardSize(); row++) {
				for(int col = 0; col < this.getBoardSize(); col++) {
					//check if matches grouping val
					if(this.getGroupedBoard()[row][col] == matchingGroupVal) {
						//check surrounding tile 
						
						//check up if not the first row
						if(row != 0) {
							//check unGrouped && same color 
							if(this.getGroupedBoard()[row - 1][col] == 0 && this.getTileBoard()[row][col].getColorI() == this.getTileBoard()[row -1][col].getColorI()) {
								this.getGroupedBoard()[row -1][col] = matchingGroupVal;
								this.getTileBoard()[row -1][col].setGrouped(true);

							}
						}
						
						//check down if not bottom row
						if(row != this.getBoardSize() -1) {
							//check unGrouped && same color 
							if(this.getGroupedBoard()[row +1][col] == 0 && this.getTileBoard()[row][col].getColorI() == this.getTileBoard()[row +1][col].getColorI()) {
								this.getGroupedBoard()[row+1][col] = matchingGroupVal;
								this.getTileBoard()[row +1][col].setGrouped(true);

							}
						}
						
						//check left if not first column
						if(col != 0) {
							//check unGrouped && same color 
							if(this.getGroupedBoard()[row][col -1] == 0 && this.getTileBoard()[row][col].getColorI() == this.getTileBoard()[row][col -1].getColorI()) {
								this.getGroupedBoard()[row][col -1] = matchingGroupVal;
								this.getTileBoard()[row][col -1].setGrouped(true);

							}
							
						}
						
						//check right if not last column 
						if(col != this.getBoardSize() -1) {
							//check unGrouped && same color 
							if(this.getGroupedBoard()[row][col +1] == 0 && this.getTileBoard()[row][col].getColorI() == this.getTileBoard()[row][col +1].getColorI()) {
								this.getGroupedBoard()[row][col +1] = matchingGroupVal;
								this.getTileBoard()[row][col +1].setGrouped(true);
							}
							
						}
						
					}
				}
			}
		}
	
		//summates grouped tiles of each player
		public void updateScore() {
			//counts elements of grouped board. pertaining to p1 and p2
			this.p1Score = 0;
			this.p2Score = 0;
			for(int[] i : this.getGroupedBoard()) {
				for(int j : i) {
					if(j == -1) {
						this.p1Score ++;
					}
					else if(j == 1) {
						this.p2Score ++;
						
					}
				}
			}
		}
	
		//print summations of each grouping
		public void printScore() {
			System.out.println("|:| P1 Score: " + this.p1Score + " || P2 Score: " + this.p2Score + " |:|");
		}
	
		// -1 p1, 0 onGoing, 1 p2, 2 draw
		public int getStatus() {
			int numTiles = (int) Math.pow(this.getBoardSize(),2);
			if(this.getP1Score() > numTiles /2 ){
				return -1;
			}else if(this.getP2Score() > numTiles /2 ) {
				return 1;
			}else if(this.getP2Score() == numTiles /2 && this.getP1Score() == numTiles/2) {
				return 2;
			}else {
				return 0;
			}
		}
	
	
	
	
	
	
	
	//Getters

	public int getMovesplayed() {
		return this.movesPlayed;
	}
	public boolean getPlayer1Turn() {
		return this.player1Turn;
	}
	public int getP1ColorI() {
		return this.p1ColorI;
	}
	public int getP2ColorI() {
		return this.p2ColorI;
	}
	public int getP1Score() {
		return this.p1Score;
	}
	public int getP2Score() {
		return this.p2Score;
	}
	public int getBoardSize() {
		return this.boardSize;
	}
	public Tile[][] getTileBoard(){
		return this.tileBoard;
	}
	public int[][] getGroupedBoard(){
		return this.groupedBoard;
	}
	public GameBoard getGameBoard() {
		return this;
	}
	public List<Integer> getPlayableMoves(){
		List<Integer> out = new ArrayList<Integer>();
		for(int i=0; i < Tile.getArrColors().length; i++) {
			if (i!= p1ColorI && i != p2ColorI) {
				out.add(i);
			}
		}
		return out;
	}

	//Setters
	
	
	public void setMovesplayed(int i) {
		 this.movesPlayed = i;
	}
	public void setPlayer1Turn(boolean b) {
		 this.player1Turn = b;
	}
	public void setP1ColorI(int i) {
		 this.p1ColorI  = i;
	}
	public void setP2ColorI(int i) {
		 this.p2ColorI = i;
	}
	public void setP1Score(int i) {
		 this.p1Score = i;
	}
	public void setP2Score(int i) {
		 this.p2Score = i;
	}


}
