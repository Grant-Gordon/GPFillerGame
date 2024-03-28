package main;
import java.lang.Thread.State;
import java.util.*;
public class AIPlayer {
	private Random rand = new Random();
	private int groupingVal;
	private int opGroupingVal;
	private static int winScore = 0;
	private int level;
	private int counter = 0;
//	private boolean player1Turn;
	
	
	
	public AIPlayer() {
		groupingVal = 0;
		opGroupingVal = 0;
		level = 3;
	}
	public AIPlayer(int gv) {
		groupingVal = gv;
		opGroupingVal = (-1) * gv;
		level =3;
	}

	public int pickColorI(GameBoard gameBoard, boolean p1Turn) {
		//temporary random color selection 
		//return rand.nextInt(Tile.getArrColors().length);
		//_________________________________________________________________________________
		winScore = (int) Math.pow(gameBoard.getBoardSize(),2);
		GameBoard CPGameBoard = new GameBoard(gameBoard);
		//how long AI should search 
		long start = System.currentTimeMillis();
		long end = start + 60 * getMillisForCurrentLevel();
		
		boolean opponent = !p1Turn;
		 //TODO  blank constructors may cause problems 
		Tree tree = new Tree();
		MCTSNode rootNode = tree.getRoot();
		rootNode.getState().setGameBoard(CPGameBoard);
		rootNode.getState().setPlayer1Turn(p1Turn);	
		
		while(System.currentTimeMillis() < end || counter  == 0  ) {
			
		//selection---------------------------------------------------
			//promising node = highest UCT node in tree
			MCTSNode promisingNode = selectPromisingNode(rootNode);
			
		//expansion----------------------------------------------------
			//if game ongoing: 
				//add all possible game states from node as children nodes
			if(promisingNode.getState().getGameBoard().getStatus() == 0) {
				expandNode(promisingNode);
			}
			
		//simulation--------------------------------------------------
			
			//explore highest UCT node
			MCTSNode nodeToExplore = promisingNode;
			if(promisingNode.getChildArray().size() > 0) {
				//explore random child node
				nodeToExplore = promisingNode.getRandomChildNode();
			}
			//return winner of random moves played from nodeToExplore
			int playoutResult = simPlayout(nodeToExplore);
			
		//update------------------------------------------------------
			//update node.state.winscore for all nodes 
			backProp(nodeToExplore, playoutResult);
			counter ++;
		}
		
		
		System.out.println( "\nTimes nodes updated: "+counter + "\n");
		counter = 0;
		
		
		//TODO WinnerNode = node with most Visits?? 
		//Shouldn't childWithMaxScore Return highest state WINscore???
		MCTSNode winnerNode = rootNode.getChildWithMaxScore();
		tree.setRoot(winnerNode);
		if(p1Turn) {
			return winnerNode.getState().getGameBoard().getP1ColorI();
		}else {
			return winnerNode.getState().getGameBoard().getP2ColorI();
		}
		
	}
	
	
	
	//return node of highest UCT starting at root node
	private MCTSNode selectPromisingNode(MCTSNode rootNode) {
		MCTSNode node = rootNode;
		while (node.getChildArray().size() != 0) {
			node = bestUCTNode(node);
		}
		return node;
	}

	//creates new nodes for all possible new states from node. 
	private void expandNode(MCTSNode node) {
		List<MCTSState> possibleStates = node.getState().getAllStates();
		possibleStates.forEach(state -> {
			//create child node for all possible states from node
			MCTSNode newNode = new MCTSNode(state);
			newNode.setParent(node);
			//switch turns 
			newNode.getState().setPlayer1Turn(!node.getState().getPlayer1Turn());
			node.getChildArray().add(newNode);
		});
	}
	
	//grouping val is winner of simPlayout
	private void backProp(MCTSNode nodeToExplore, int playoutStatus) {
		MCTSNode tempNode = nodeToExplore;
		//while tempNode != rootNode in Tree
		while(tempNode != null) {
			//Increment visit Count
			tempNode.getState().setVisitCount(tempNode.getState().getVisitCount() +1);
			//if grouping val = simPlauout winner. nodeValue = winScore
			if(tempNode.getState().getGroupingVal() == playoutStatus) {
				
				tempNode.getState().addScore(winScore);
			}else if (tempNode.getState().getGroupingVal() * -1 == playoutStatus) { // if simPlayout Winner = opponent
				tempNode.getState().addScore(Integer.MIN_VALUE);
				//if simulation ongoing
			}else if (playoutStatus == 0) {
				//if state is P1 turn 
				if(tempNode.getState().getPlayer1Turn() ) {
					//if AI is P1
					if(this.groupingVal == -1) {
						//add P1 score to nodeVal
						tempNode.getState().addScore(tempNode.getState().getGameBoard().getP1Score());
					}
					//if AI is P2
					if(this.groupingVal == 1) {
						//subtract P1 score to nodeVal
						tempNode.getState().addScore(-1 * tempNode.getState().getGameBoard().getP1Score());
					}
				}else { //if State is P2
					//if AI is P1
					if(this.groupingVal == -1) {
						//subtract P2 score from nodeVal
						tempNode.getState().addScore(-1 * tempNode.getState().getGameBoard().getP2Score());
					}
					//if AI is P2
					if(this.groupingVal == 1) {
						//add P2 score to node val
						tempNode.getState().addScore(tempNode.getState().getGameBoard().getP2Score());
					}
					//tempNode.getState().addScore(tempNode.getState().getGameBoard().getP1Score());
				}
				
			}
			tempNode=tempNode.getParent();
		}
	}
	
	//returns winner of game after random moves starting at node. 
	private int simPlayout(MCTSNode node) {
		MCTSNode tempNode = new MCTSNode(node);
		//TODO
		MCTSState tempState =  tempNode.getState();
		int boardStatus = tempState.getGameBoard().getStatus();
		
		//if state.board has opponent winning state's winScore = MIN_VALUE
		if(boardStatus == this.opGroupingVal) {
			tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
			//return this node loses
			return boardStatus;
		}
		//while in progress
		while(boardStatus == 0) {
			tempState.setPlayer1Turn(!tempState.getPlayer1Turn());
			//plays move on state.board
			tempState.randomMove();
			//update status if random move results in a win. 
			boardStatus = tempState.getGameBoard().getStatus();
		}
		//return winner of this node from playing random moves
		return boardStatus;
	}
	
	
	
	
	public static double uctValue(int totalVisist, double nodeWinScore, int nodeVisit) {
		if(nodeVisit==0) {
			return Integer.MAX_VALUE;
		}
		return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisist) / (double) nodeVisit);
	}
	
	
	public static MCTSNode bestUCTNode(MCTSNode node) {
		int parentVisit = node.getState().getVisitCount();
		return Collections.max(node.getChildArray(), Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
	}
	
	
	//________________________________________________________________________________________
	//getters
	public int getGroupingVal() {
		return this.groupingVal;
	}
	public int getLevel() {
	    return level;
	}

	private int getMillisForCurrentLevel() {
	    return 2 * (this.level - 1) + 1;
	}
	
	//setters 
	public void setGroupingVal(int i) {
		this.groupingVal = i;
	}
	public void setLevel(int level) {
	    this.level = level;
	}
}



