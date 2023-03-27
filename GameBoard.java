package main;
import java.util.*;

public class GameBoard {
	private final int defaultBoardSize = 8;
	private Tile[][] tileBoard;
	private int[][] groupedBoard; // -1 grouped player1, 0 ungrouped, 1 grouped player2
	private int boardSize;
	 


	public GameBoard() {
		boardSize = defaultBoardSize;
		//Pre-populate board with default(64) empty tiles
			tileBoard = new Tile[defaultBoardSize][defaultBoardSize];
			groupedBoard = new int[defaultBoardSize][defaultBoardSize];
		for(int i = 0; i < defaultBoardSize; i++) {
			for(int j = 0; j < defaultBoardSize; j++) {
				tileBoard[i][j] = new Tile();
				groupedBoard[i][j] = 0;
			}
		}
		groupedBoard[defaultBoardSize -1][0] = -1;
		groupedBoard[0][defaultBoardSize -1]= 1;
		this.randomizeTileColors();
	}
	
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

	}
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
	public boolean containsBlanks() {
		for (Tile[] r: this.tileBoard) {
			for (Tile t: r) {
				if(t.getColorI() == -1) {
					return true;
				}
			}
		}
		return false;
	}
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
				//if very [0][0] assign random color
				if(row == 0 && col == 0) {
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					
					//if top in column
				}else if(row ==0) {
					//start off setting random color
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					//only check left 
					//while tiles color = tiles to the left color, set tile color to new color 
					while (this.tileBoard[row][col].getColorI() == this.tileBoard[row][col-1].getColorI()) {
						this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					}
				
			
					//if  first in row
				}else if(col == 0) {
					//start off setting random color
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					//only check top
					//white tile color = tile above color, set tile color to new color
					while(this.tileBoard[row][col].getColorI() == this.tileBoard[row-1][col].getColorI()) {
						this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));
					}
				
				}else {
					//start off setting random color
					this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					// while tile color = tile to the left or above color, set to random 
					while (this.tileBoard[row][col].getColorI() == this.tileBoard[row-1][col].getColorI() || this.tileBoard[row][col].getColorI() == this.tileBoard[row][col-1].getColorI()) {
						this.tileBoard[row][col].setColorI(random.nextInt(Tile.getArrColors().length));

					}
				}

						
			}
		}
	}
	
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

	public void printTileColor(int n) {
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
	public void printGroupedBoard() {
		for(int[] i: this.groupedBoard) {
			for(int j : i) {
				System.out.print(j + " ");
			}
			System.out.println("\n");

		}
	}
		
	//Getters
	public int getBoardSize() {
		return this.boardSize;
	}
	public Tile[][] getTileBoard(){
		return this.tileBoard;
	}
	public int[][] getGroupedBoard(){
		return this.groupedBoard;
	}

	//Setters
}
