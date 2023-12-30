package main;
import java.lang.Thread.State;
import java.util.*;
public class AIPlayer {
	private Random rand = new Random();
	private int groupingVal;
	private int opGroupingVal;
	private static final int WIN_SCORE = 0;
	private int level;
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
		//temporary random color selction 
	//	return rand.nextInt(Tile.getArrColors().length);
		
		//_________________________________________________________________________________
		long start = System.currentTimeMillis();
		long end = start + 60 * getMillisForCurrentLevel();
		boolean opponent = !p1Turn;
		
		Tree tree = new Tree();
		MCTSNode rootNode = tree.getRoot();
		rootNode.getState().setGameBoard(gameBoard);
		rootNode.getState().setPlayer1Turn(p1Turn);
		
		while(System.currentTimeMillis() < end ) {
		
			//selection
			MCTSNode promisingNode = selectPromisingNode(rootNode);
			
			//expansion
			if(promisingNode.getState().getGameBoard().getStatus() == 0) {
				expandNode(promisingNode);
			}
			
			//simulation
			MCTSNode nodeToExplore = promisingNode;
			if(promisingNode.getChildArray().size() > 0) {
				nodeToExplore = promisingNode.getRandomChildNode();
			}
			int playoutResult = simPlayout(nodeToExplore);
			
			//update
			backProp(nodeToExplore, playoutResult);
			
		}
		
		MCTSNode winnerNode = rootNode.getChildWithMaxScore();
		tree.setRoot(winnerNode);
		if(p1Turn) {
			return winnerNode.getState().getGameBoard().getP1ColorI();
		}else {
			return winnerNode.getState().getGameBoard().getP2ColorI();
		}
		
	}
	
	
	
	
	private MCTSNode selectPromisingNode(MCTSNode rootNode) {
		MCTSNode node = rootNode;
		while (node.getChildArray().size() != 0) {
			node = bestUCTNode(node);
		}
		return node;
	}

	
	private void expandNode(MCTSNode node) {
		List<MCTSState> possibleStates = node.getState().getAllStates();
		possibleStates.forEach(state -> {
			MCTSNode newNode = new MCTSNode(state);
			newNode.setParent(node);
			newNode.getState().setPlayer1Turn(!node.getState().getPlayer1Turn());
			node.getChildArray().add(newNode);
		});
	}
	
	private void backProp(MCTSNode nodeToExplore, int groupVal) {
		MCTSNode tempNode = nodeToExplore;
		while(tempNode != null) {
			tempNode.getState().setVisitCount(tempNode.getState().getVisitCount() +1);
			if(tempNode.getState().getGroupingVal() != groupVal) {
				tempNode.getState().addScore(WIN_SCORE);
			}
			tempNode=tempNode.getParent();
		}
	}
	
	//-1 if p1, 0 if in progress, 1 if p2, 2 if draw, no clue what else this does. 
	private int simPlayout(MCTSNode node) {
		MCTSNode tempNode = new MCTSNode(node);
		MCTSState tempState = tempNode.getState();
		int boardStatus = tempState.getGameBoard().getStatus();
		
		if(boardStatus == this.opGroupingVal) {
			tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
			return boardStatus;
		}
		//while in progress
		while(boardStatus == 0) {
			tempState.setPlayer1Turn(!tempState.getPlayer1Turn());
			tempState.randomMove();
			boardStatus = tempState.getGameBoard().getStatus();
		}
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



