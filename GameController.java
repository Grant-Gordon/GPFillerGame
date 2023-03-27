package main;

public class GameController {
	private GameBoard board;
	private boolean playing;
	private boolean player1Turn;
	private int p1Score =0;
	private int p2Score =0;
	
	

	
	
	
	
	public GameController(){
		board = new GameBoard();
		playing = true;
		player1Turn = true;
	}
	public GameController(int size){
		board = new GameBoard(size);
		playing = true;
		player1Turn = true;

	}
	
	public void setGroupColor(int n) {
		//all CURRENTLY grouped tiles change color to selected color
		int matchingGroupVal;
		if(player1Turn) {
			matchingGroupVal = -1;
		}else {
			matchingGroupVal = 1;
		}
		
		
		for(int row = 0; row < this.board.getBoardSize(); row ++) {
			for (int col = 0; col < this.board.getBoardSize(); col++) {
				if(matchingGroupVal == this.board.getGroupedBoard()[row][col]) {
					this.board.getTileBoard()[row][col].setColorI(n);
				}
			}
		}
	}
	
	public void group() {

		
		/*
		 * iterate through each tile 
		 * check if group matches player 
		 * check surrounding tiles for unGrouped tiles 
		 * if unGrouped tile color matches selected color 
		 */
		int matchingGroupVal;
		if(player1Turn) {
			matchingGroupVal = -1;
		}else {
			matchingGroupVal = 1;
		}
		
		for(int row =0; row < this.board.getBoardSize(); row++) {
			for(int col = 0; col < this.board.getBoardSize(); col++) {
				//check if matches grouping val
				if(this.board.getGroupedBoard()[row][col] == matchingGroupVal) {
					//check surrounding tile 
					
					//check up
					if(row != 0) {
						//check unGrouped && same color 
						if(this.board.getGroupedBoard()[row - 1][col] == 0 && this.board.getTileBoard()[row][col].getColorI() == this.board.getTileBoard()[row -1][col].getColorI()) {
							this.board.getGroupedBoard()[row -1][col] = matchingGroupVal;
							this.board.getTileBoard()[row -1][col].setGrouped(true);

						}
					}
					
					//check down
					if(row != this.board.getBoardSize() -1) {
						//check unGrouped && same color 
						if(this.board.getGroupedBoard()[row +1][col] == 0 && this.board.getTileBoard()[row][col].getColorI() == this.board.getTileBoard()[row +1][col].getColorI()) {
							this.board.getGroupedBoard()[row+1][col] = matchingGroupVal;
							this.board.getTileBoard()[row +1][col].setGrouped(true);

						}
					}
					
					//check left
					if(col != 0) {
						//check unGrouped && same color 
						if(this.board.getGroupedBoard()[row][col -1] == 0 && this.board.getTileBoard()[row][col].getColorI() == this.board.getTileBoard()[row][col -1].getColorI()) {
							this.board.getGroupedBoard()[row][col -1] = matchingGroupVal;
							this.board.getTileBoard()[row][col -1].setGrouped(true);

						}
						
					}
					
					//check right
					if(col != this.board.getBoardSize() -1) {
						//check unGrouped && same color 
						if(this.board.getGroupedBoard()[row][col +1] == 0 && this.board.getTileBoard()[row][col].getColorI() == this.board.getTileBoard()[row][col +1].getColorI()) {
							this.board.getGroupedBoard()[row][col +1] = matchingGroupVal;
							this.board.getTileBoard()[row][col +1].setGrouped(true);
						}
						
					}
					
				}
			}
		}
	}
	public void updateScore() {
		this.p1Score = 0;
		this.p2Score = 0;
		for(int[] i : this.getGameBoard().getGroupedBoard()) {
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
	public void printScore() {
		System.out.println("|:| P1 Score: " + this.p1Score + " || P2 Score: " + this.p2Score + " |:|");
	}
	
	//public 
	public void updatePlaying() {
		
		//if board full 
		if(p1Score + p2Score == this.board.getBoardSize() * this.board.getBoardSize()) {
			playing = false;
		}
		
		//if mercy
		if (p1Score > (this.board.getBoardSize() * this.board.getBoardSize())/ 2 || p2Score > (this.board.getBoardSize() * this.board.getBoardSize())/ 2 ) {
			playing = false;
		}
		
		
		
		
	}
	
	public GameBoard getGameBoard() {
		return this.board;
	}
	public boolean getPlaying() {
		return this.playing;
	}
	public boolean getPlayer1Turn(){
		return this.player1Turn;
	}
	public void setPlayer1Turn(boolean b) {
		this.player1Turn = b;
	}
	

/*
 * idea for win check method
 * count number of contesteted squares if(contested + p1score < p2score || contested + p2score < p1score)
 * find contested by itterating through each square, recursivly see if you can make a path through
 *  ungrouped tiles connecting p1 group to p2 group prefered path (y = x) 
 *  if cannot connect ungrouped to p1 && p2 groups then tile is sealed off and not contested.  
 *  
 *  god i fucking love maze solving algorithms, i want to actually code one
 */
}
