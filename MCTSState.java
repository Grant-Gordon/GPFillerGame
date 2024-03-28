package main;

import java.util.ArrayList;
import java.util.List;
//References : https://github.com/eugenp/tutorials/blob/master/algorithms-modules/algorithms-searching/src/main/java/com/baeldung/algorithms/mcts/montecarlo/State.java
public class MCTSState {
	private GameBoard board;
	private boolean player1Turn;
	private int visitCount;
	private double winScore;
	private int groupingVal;
	 
	
	public static boolean compareTo(MCTSState A, MCTSState B) {
		if(!GameBoard.compareTo(A.getGameBoard(), B.getGameBoard())) {
			return false;
		}else if(A.getPlayer1Turn() != B.getPlayer1Turn()) {
			return false;
		}else if(A.getVisitCount() != B.getVisitCount()) {
			return false;
		}else if (A.getWinScore() != B.getWinScore()) {
			return false;
		}else if(A.getGroupingVal() != B.getGroupingVal()) {
			return false;
		}else {
			return true;
		}
	}
	
	public MCTSState() {
		//setGroupingVal();
	}
	//copy Constructor 
	public MCTSState(MCTSState s) {
		 this.board = new GameBoard(s.getGameBoard());
	        this.player1Turn = s.getPlayer1Turn();
	        this.visitCount = s.getVisitCount();
	        this.winScore = s.getWinScore();
	        this.setGroupingVal();
	}
	//creates state for current gameBoard
	public MCTSState(GameBoard g) {
		this.board = new GameBoard(g);
		this.setGroupingVal();
	}
	
	
	//-----------------------------------------------------------------------------
	public List<MCTSState> getAllStates(){
	//	return all possible states from current state
		List<MCTSState> possibleStates = new ArrayList<MCTSState>();
		for(int i : board.getPlayableMoves()){
			//for all playable moves
			MCTSState newState = new MCTSState(this.board);
			newState.setPlayer1Turn(this.getPlayer1Turn());
			newState.getGameBoard().playMove(i);
			possibleStates.add(newState);
		}
		return possibleStates;
		
	}
	
	public void randomMove() {
		//TODO random move only plays from playable moves not States playable moves???????
		//play a random move from playable moves on this/state.baord 
		List<Integer> playable = board.getPlayableMoves();
		int moves = playable.size();
		board.playMove(playable.get((int)(Math.random() * moves)));
	}
	
	//if score is not -INF/lost add score to state win score
	  public void addScore(double score) {
	        if (this.winScore != Integer.MIN_VALUE) {
	           this.winScore += score;
	        }
	    }
	
	
	
	
	//----------------------------------------------------------------------------------
	//Getters
	public GameBoard getGameBoard() {
		return this.board;
	}
	public boolean getPlayer1Turn() {
		return this.player1Turn;
		
	}
	public int getVisitCount() {
		return this.visitCount;
	}
	
	public double getWinScore() {
		return this.winScore;
	}
	public int getGroupingVal() {
		return this.groupingVal;
	}
	
	//Setters
	public void setGameBoard(GameBoard b){
		this.board =b;
	}
	public void setPlayer1Turn(boolean b) {
		this.player1Turn = b;
		this.board.setPlayer1Turn(b);
		setGroupingVal();
	}
	public void setVisitCount(int i) {
		this.visitCount = i;
	}
	public void setWinScore(double d) {
		this.winScore = d;
	}
	public void setGroupingVal(int i) {
		this.groupingVal = i;
	}
	public void setGroupingVal() {
		if(this.getPlayer1Turn()) {
			this.groupingVal = -1;
		}else {
			this.groupingVal = 1;
		}
	}
	
}

