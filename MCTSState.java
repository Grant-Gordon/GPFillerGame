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
	
	
	public MCTSState() {
		board = new GameBoard();
		setGroupingVal();
	}
	public MCTSState(MCTSState s) {
		 this.board = new GameBoard(s.getGameBoard());
	        this.player1Turn = s.getPlayer1Turn();
	        this.visitCount = s.getVisitCount();
	        this.winScore = s.getWinScore();
	        this.setGroupingVal();
	}
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
		//play a random move??
		List<Integer> playable = board.getPlayableMoves();
		int moves = playable.size();
		board.playMove(playable.get((int)(Math.random() * moves)));
	}
	
	  public void addScore(double score) {
	        if (this.winScore != Integer.MIN_VALUE)
	            this.winScore += score;
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

