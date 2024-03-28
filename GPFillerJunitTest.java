package main;

import static org.junit.Assert.*;
//import static org.junit.Test.*;
import java.util.*;

import org.junit.Test;

public class GPFillerJunitTest {
//assert expected,actual
	static final double DELTA = 1e-15;
	
//	@Test
	public void testPrint() {
		GameBoard g = new GameBoard();
		System.out.println("***************____g_____*******************");
		g.printTileColor(0);
		g.printGroupedBoard();
		/*
		GPFillerJunitTest.setOneMoveWin(g);
		System.out.println("***********______g.setOneWintMove______**************");
		g.printTileColor(0);
		g.printGroupedBoard();
		 */
		GPFillerJunitTest.setRainbow(g);
		System.out.println("***********______g.setRainbow________**************");
		g.printTileColor(0);
		g.printGroupedBoard();
		/*
		GPFillerJunitTest.setP1Lost(g);;
		System.out.println("***********______g.setP1Lost________**************");
		g.printTileColor(0);
		g.printGroupedBoard();
		*/
		
		/*
		GPFillerJunitTest.setP2Lost(g);;
		System.out.println("***********______g.setP2Lost________**************");
		g.printTileColor(0);
		g.printGroupedBoard();
		*/
	
	}
	
	//vertical stripes 
	//   0 1 2 3 4 5 0 1 
	//  -1 0 0 0 0 0 0 1 

	private static void setRainbow(GameBoard g) {
		for(int i =0; i < g.getBoardSize(); i++) {
			for(int j = 0; j < g.getBoardSize(); j++) {
				g.getTileBoard()[i][j].setColorI(j % Tile.getArrColors().length);
				
			}
		}
		for(int i =0; i < g.getBoardSize();i++) {
			g.getTileBoard()[i][0].setGrouped(true);
			g.getGroupedBoard()[i][0] = -1;
			g.getGroupedBoard()[i][g.getBoardSize() -1] =1;
			g.getTileBoard()[i][g.getBoardSize() -1].setGrouped(true);
	
		}
		g.setP1ColorI(g.getTileBoard()[0][0].getColorI());
		g.setP2ColorI(g.getTileBoard()[0][g.getBoardSize() -1].getColorI());
	}
	
	
	//splits the board in half with one row of ungrouped, color: 2. down the middle 
	private static void setOneMoveWin(GameBoard g) {
		g.setP1ColorI(0);
		g.setP2ColorI(1);
		for ( int i = 0; i < g.getBoardSize(); i ++) {
			for(int j = 0; j < g.getBoardSize(); j ++){
				if(i < g.getBoardSize() / 2) {
					g.getTileBoard()[i][j].setColorI(0);
					g.getTileBoard()[i][j].setGrouped(true);
					g.getGroupedBoard()[i][j] = -1;
				}else {
					g.setP1ColorI(1);
					g.getTileBoard()[i][j].setColorI(1);
					g.getTileBoard()[i][j].setGrouped(true);
					g.getGroupedBoard()[i][j] = 1;
				}
				if(i == g.getBoardSize()/2) {
					g.getTileBoard()[i][j].setColorI(2);
					g.getTileBoard()[i][j].setGrouped(false);
					g.getGroupedBoard()[i][j] = 0;
				}
			
				
			}
			
		}
	}
//gives p1 1/3 of tiles
	private static void setP2Lost(GameBoard g) {
		if(g.getP2ColorI() !=0) {
			g.setP1ColorI(0);

			for ( int i = 0; i < g.getBoardSize(); i ++) {
				for(int j = 0; j < g.getBoardSize(); j ++){
					if(i > 2 * (g.getBoardSize()/3)) {
						g.getTileBoard()[i][j].setColorI(0);
						g.getTileBoard()[i][j].setGrouped(true);
						g.getGroupedBoard()[i][j] = -1;
					}
				}
			}
		}else {
			g.setP1ColorI(1);
			for ( int i = 0; i < g.getBoardSize(); i ++) {
				for(int j = 0; j < g.getBoardSize(); j ++){
					if(i >  2 * (g.getBoardSize()/3)) {
						g.getTileBoard()[i][j].setColorI(1);
						g.getTileBoard()[i][j].setGrouped(true);
						g.getGroupedBoard()[i][j] = -1;
					}
				}
			}
		}
	}
	//gives p2 1/3 of tiles
	private static void setP1Lost(GameBoard g) {
		if(g.getP1ColorI() !=0) {
			g.setP2ColorI(0);
			for(int i = 0; i < g.getBoardSize(); i ++) {
				for(int j = 0; j < g.getBoardSize(); j ++){
					if(i <= (g.getBoardSize()/3)) {
						g.getTileBoard()[i][j].setColorI(0);
						g.getTileBoard()[i][j].setGrouped(true);
						g.getGroupedBoard()[i][j] = 1;
					}
				}
			}
		}else {
			g.setP2ColorI(1);
			for ( int i = 0; i < g.getBoardSize(); i ++) {
				for(int j = 0; j < g.getBoardSize(); j ++){
					if(i <= (g.getBoardSize()/3)) {
						g.getTileBoard()[i][j].setColorI(1);
						g.getTileBoard()[i][j].setGrouped(true);
						g.getGroupedBoard()[i][j] = 1;
					}
				}
			}
		}
	}
	//Test AIPlayer.java-------------------------------------------------------------------------------------
	@Test
	public void testAIPlayerConstructor() {
		
	}
	@Test
	public void testPickColorI() {
		GameBoard g = new GameBoard();
		GPFillerJunitTest.setOneMoveWin(g);
		//ai player 1 
		AIPlayer ai = new AIPlayer(-1);
		assertEquals(2,ai.pickColorI(g, true));
		
		
	}
	@Test
	public void testSelectPromisingNode() {
		
	}
	@Test
	public void testExpandNode() {
		
	}
	@Test
	public void testBackProp() {
		
	}
	@Test
	public void testSimPlayout() {
		
	}
	@Test
	public void testUctValue() {
		
	}
	@Test
	public void testBestUctValue() {
		
	}
	
		//Getters/Setters Tests
	@Test
	public void testGetGroupingValue() {
		
	}
	@Test
	public void testGetLevel() {
		
	}
	@Test
	public void testGetMilisForCurrentLevel() {
		
	}
	@Test
	public void testSetGroupingValue() {
		
	}
	@Test
	public void testSetLevel() {
		
	}
	
	//Test ConsoleTester.java-------------------------------------------------------------------------------------
	@Test
	public void testToString() {
		
	}
	@Test
	public void testConsoleTester() {
		
	}
	@Test
	public void testUpdatePLaying() {
		
	}
	@Test
	public void testRun() {
		
	}
	@Test
	public void testMain() {
		
	}
	//Test GameBoard.java-------------------------------------------------------------------------------------
	@Test
	public void testGameBoard() {
		//Default Constructor
		GameBoard g = new GameBoard();
		//defualt baordsize
		assertEquals(8,g.getBoardSize());
		//assume P1 and P2 are not the same color 
		assertTrue(g.getTileBoard()[g.getBoardSize()-1][0].getColorI() != g.getTileBoard()[0][g.getBoardSize()-1].getColorI());
		//row
		for(int i = 0; i < g.getBoardSize(); i++) {
			//col
			for( int j = 0; j < g.getBoardSize();j ++){
				//assume prepopulated with tiles
				assertNotNull(g.getTileBoard()[i][j]);
				//assert all tiles are legal colors 
				assertTrue(g.getTileBoard()[i][j].getColorI() >= 0 && g.getTileBoard()[i][j].getColorI() < Tile.getArrColors().length);
				//if p2 start
				if(i == 0 && j == g.getBoardSize() -1) {
					//grouping value p2 = 1
					assertEquals(1,g.getGroupedBoard()[i][j]);
					//if p1 start
				}else if (i == g.getBoardSize() -1 && j == 0) {
					//groupingVal p1 = -1
					assertEquals(-1, g.getGroupedBoard()[i][j]);
					//if ungrouped
				}else {
					//grouping val ungrouped = 0
					assertEquals(0, g.getGroupedBoard()[i][j]);
				}
			}
		}
		//Custom Size GameBaord Constructor
		int size = 30;
		g = new GameBoard(size);
		assertEquals(size,g.getBoardSize());
		assertEquals(size,g.getTileBoard().length);
		assertEquals(size, g.getTileBoard()[0].length);
		//CopyConstructor
		
	}
	@Test
	public void testGameBoardCompareTo() {
		
	}
	@Test
	public void testContainsUngrouped() {
		GameBoard g = new GameBoard();
		assertTrue(g.containsUnGrouped());
		for(int i =0; i < g.getBoardSize(); i ++) {
			for( int j =0; j < g.getBoardSize(); j++) {
				g.getGroupedBoard()[i][j] = 1;
			}
		}
		assertFalse(g.containsUnGrouped());
		g.getGroupedBoard()[0][0] = 0;
		assertTrue(g.containsUnGrouped());
		
		
	}
	
	@Test
	public void testContainsBlanks() {
		GameBoard g = new GameBoard();
		assertFalse(g.containsBlanks());
		g = new GameBoard(10);
		assertFalse(g.containsBlanks());
		g = new GameBoard(g);
		assertFalse(g.containsBlanks());
		g.getTileBoard()[0][1] = new Tile();
		assertTrue(g.containsBlanks());
		
		
		

	}
	@Test
	public void testRandomizeTileColors() {

	}
	@Test
	public void testPrintTileColor() {
		
	}
	@Test
	public void testPrintGourpedBoard() {
		
	}
	@Test
	public void testPlayMove() {
		GameBoard g = new GameBoard();
		GameBoard cpg = new GameBoard(g);
		g.playMove(2);
		if(cpg.getPlayer1Turn()) {
			cpg.setP1ColorI(2);
		}else {
			cpg.setP2ColorI(2);
		}
		cpg.setGroupColor(2);
		cpg.group();
		cpg.updateScore();
		assertTrue(GameBoard.compareTo(g, cpg));
		
		
		
		
	}
	@Test
	public void testSetGroupColor() {
	GameBoard g = new GameBoard();
	GPFillerJunitTest.setRainbow(g);
	g.setGroupColor(1);
	for(int i =0; i < g.getBoardSize(); i++) {
		assertTrue(g.getTileBoard()[i][0].getColorI() == 1);
	}
	g.setPlayer1Turn(false);
	g.setGroupColor(5);
	for (int i = 0; i < g.getBoardSize(); i++) {
		assertTrue(g.getTileBoard()[i][g.getBoardSize() -1].getColorI() == 5);
	}
	
	}
	@Test
	public void testGroup() {
		GameBoard g = new GameBoard();
		GPFillerJunitTest.setRainbow(g);
		g.setGroupColor(1);
		g.group();
		for(int i = 0; i < g.getBoardSize(); i++) {
			assertTrue(g.getGroupedBoard()[i][0] == -1);
			assertTrue(g.getGroupedBoard()[i][1] == -1);

			for(int j = 0; j < g.getBoardSize(); j++) {
				if(j > 1) {
					assertFalse(g.getGroupedBoard()[i][j] == -1);
				}
			}
		}
		g = new GameBoard();
		GPFillerJunitTest.setRainbow(g);
		g.setPlayer1Turn(false);
		g.setGroupColor(0);
		g.group();
		for(int i = 0; i < g.getBoardSize(); i++) {
			assertTrue(g.getGroupedBoard()[i][g.getBoardSize() -1] == 1);
			assertTrue(g.getGroupedBoard()[i][g.getBoardSize() -2] == 1);

			for(int j = 0; j < g.getBoardSize(); j++) {
				if(j > 1) {
					assertFalse(g.getGroupedBoard()[i][j] == -1);
				}
			}
		}
	
		
	}
	@Test
	public void testUpdateScore() {
		GameBoard g = new GameBoard();
		GPFillerJunitTest.setRainbow(g);
		g.updateScore();
		assertEquals(g.getP2Score(), g.getBoardSize());
		assertEquals(g.getP1Score(), g.getBoardSize());
		g = new GameBoard();
		GPFillerJunitTest.setP1Lost(g);
		g.updateScore();
		assertTrue(g.getP2Score() > g.getP1Score());
		g = new GameBoard();
		GPFillerJunitTest.setP2Lost(g);
		g.updateScore();
		assertTrue(g.getP2Score() < g.getP1Score());
		
		
		
		
		
	}
	@Test
	public void testPrintScore() {
		
	}
	
		//Getters/Setters Tests

	@Test
	public void testGetMovesPlayed() {
		
	}
	@Test
	public void testGetPlayer1Turn() {
		
	}
	@Test
	public void testGetPlayer1ColorI() {
		
	}
	@Test
	public void testGetP2ColorI() {
		
	}
	@Test
	public void testGetP1Score() {
		
	}
	@Test
	public void testGetP2Score() {
		
	}
	@Test
	public void testGetBoardSize() {
		GameBoard g = new GameBoard();
		assertEquals(g.getBoardSize(),8);
		g = new GameBoard(7);
		assertEquals(g.getBoardSize(),7);
		g = new GameBoard(75);
		assertEquals(g.getBoardSize(),75);


	}
	@Test
	public void testGetTileBoard() {
		
	}
	@Test
	public void testGetGroupedBoard() {
		
	}
	@Test
	public void testGetGameBoard() {
		GameBoard g = new GameBoard();
		assertEquals(g, g.getGameBoard());
	}
	@Test
	public void testGetPlayableMove() {
		GameBoard g = new GameBoard();
		g.setP1ColorI(0);
		g.setP2ColorI(1);
		assertTrue(g.getPlayableMoves().contains(2));
		assertTrue(g.getPlayableMoves().contains(3));
		assertTrue(g.getPlayableMoves().contains(4));
		assertTrue(g.getPlayableMoves().contains(5));
		assertEquals(g.getPlayableMoves().size(), Tile.getArrColors().length -2);
		g.setP1ColorI(5);
		assertTrue(g.getPlayableMoves().contains(2));
		assertTrue(g.getPlayableMoves().contains(3));
		assertTrue(g.getPlayableMoves().contains(4));
		assertTrue(g.getPlayableMoves().contains(0));
		assertEquals(g.getPlayableMoves().size(), Tile.getArrColors().length -2);
		g.setP2ColorI(4);
		assertTrue(g.getPlayableMoves().contains(2));
		assertTrue(g.getPlayableMoves().contains(3));
		assertTrue(g.getPlayableMoves().contains(1));
		assertTrue(g.getPlayableMoves().contains(0));
		assertEquals(g.getPlayableMoves().size(), Tile.getArrColors().length -2);

	}
	
	@Test
	public void testSetmovesPlayed() {
		
	}
	@Test
	public void testSetPlayer1Turn() {
		GameBoard g = new GameBoard();
		assertTrue(g.getPlayer1Turn());
		g.setPlayer1Turn(false);
		assertFalse(g.getPlayer1Turn());
	}
	@Test
	public void testSetP1ColorI() {
		GameBoard g = new GameBoard();
		g.setP1ColorI(0);
		assertEquals(0, g.getP1ColorI());
		g.setP1ColorI(50);
		assertEquals(50, g.getP1ColorI());
		g.setP1ColorI(2);
		assertEquals(2, g.getP1ColorI());
	}
	@Test
	public void testSetP2ColorI() {
	GameBoard g = new GameBoard();
	g.setP2ColorI(0);
	assertEquals(0, g.getP2ColorI());
	g.setP2ColorI(50);
	assertEquals(50, g.getP2ColorI());
	g.setP2ColorI(2);
	assertEquals(2, g.getP2ColorI());
	}
	@Test
	public void testSetP1Score() {
		GameBoard g = new GameBoard();
		g.setP1Score(1000);
		assertEquals(1000, g.getP1Score());
		g.setP1Score(0);
		assertEquals(0, g.getP1Score());

	}
	@Test
	public void testSetP2Score() {
		GameBoard g = new GameBoard();
		g.setP2Score(1000);
		assertEquals(1000, g.getP2Score());
		g.setP2Score(0);
		assertEquals(0, g.getP2Score());

	}


	
	//Test MCTSNode.java-------------------------------------------------------------------------------------
	@Test
	public void testMCTSNode() {
		//default Constructor
		MCTSNode n = new MCTSNode();
		assertNotNull(n.getState());
		assertNotNull(n.getChildArray());
		assertNull(n.getParent());
		assertTrue(n.getChildArray().isEmpty());
		
		//node with state constructor
		MCTSState s = new MCTSState(new GameBoard());
		n = new MCTSNode(s);
		assertEquals(s,n.getState());
		assertNotNull(n.getChildArray());
		assertTrue(n.getChildArray().isEmpty());
		assertNull(n.getParent());
		
		//node with state and parent constructor
		MCTSNode parent = new MCTSNode(new MCTSState(new GameBoard()));
		s = parent.getState();
		n = new MCTSNode(s,parent);
		assertEquals(s,n.getState());
		//assertTrue(MCTSState.compareTo(s, n.getState()));
		assertNotNull(n.getChildArray());
		assertEquals(parent,n.getParent());
		
		
		//complete override node
		List<MCTSNode> ra = new ArrayList<>();
		ra.add(new MCTSNode());
		ra.add(new MCTSNode());
		ra.add(new MCTSNode());
		n = new MCTSNode(s,parent,ra);
		assertEquals(s,n.getState());
		assertEquals(parent,n.getParent());
		assertEquals(ra,n.getChildArray());
		//copy constructor
		//TODO co constructor, recursive constructor 
	

		
		
		
	}
		//Getters/Setters Tests
	@Test
	public void testGetState() {
		MCTSState s = new MCTSState(new GameBoard());
		MCTSNode n = new MCTSNode(s);
		assertEquals(s,n.getState());
		s = new MCTSState(new GameBoard());
		n.setState(s);
		assertEquals(s,n.getState());
		
		
	}
	@Test
	public void testGetParent() {
		
	}
	@Test
	public void testGetChildArray() {
		
	}
	@Test
	public void testGetRandomChildNode() {
		
	}
	@Test
	public void testGetChildWithMaxScore() {
		
	}
	@Test
	public void testSetStat() {
		
	}
	@Test
	public void testSetParent() {
		
	}
	@Test
	public void testSetChildArray() {
		
	}

	
	//Test MCTSState.java-------------------------------------------------------------------------------------
	@Test
	public void testMCTSState() {
		//default constructor
		MCTSState s = new MCTSState();
		assertNull(s.getGameBoard());
		assertFalse(s.getPlayer1Turn());
		assertEquals(0,s.getVisitCount());
		assertEquals(0,s.getWinScore(), DELTA);
		assertEquals(0,s.getGroupingVal());
		
		//Copy Constructor()
		s.setGameBoard(new GameBoard());
		s.setPlayer1Turn(true); // careful
		//assertEquals(1,s.getGroupingVal());
		s.setVisitCount(10);
		s.setWinScore(11);
		s.setGroupingVal(-1); // careful
		MCTSState cp = new MCTSState(s);
		assertTrue(GameBoard.compareTo(s.getGameBoard(), cp.getGameBoard()));
		assertTrue(s.getPlayer1Turn());
		assertTrue(s.getGameBoard().getPlayer1Turn());
		assertEquals(10,s.getVisitCount());
		assertEquals(11,s.getWinScore(), DELTA);
		assertEquals(-1,s.getGroupingVal());
		//State plus gameBoard.
		cp = new MCTSState(s.getGameBoard());
		assertTrue(GameBoard.compareTo(cp.getGameBoard(), s.getGameBoard()));
		//TODO figure out why this fails 
		//assertEquals(cp.getGroupingVal(),s.getGroupingVal());
		
	}
	@Test
	public void testGetAllStates() {
		//TODO 
		//test for p2 get states 
		
	GameBoard g = new GameBoard();
	GameBoard cpg = new GameBoard(g);
	MCTSState s = new MCTSState(g);
	List<MCTSState> allStates = s.getAllStates();
	//Test getAllstates doesnt fuck with g
	assertTrue(GameBoard.compareTo(g, cpg));
	//test that player 2 turn flipped each state 
	assertFalse(allStates.get(0).getPlayer1Turn());
	assertFalse(allStates.get(1).getPlayer1Turn());
	assertFalse(allStates.get(2).getPlayer1Turn());
	assertFalse(allStates.get(3).getPlayer1Turn());
		
	//assert getAllStates = states 2,3,4,5 (playable moves for rainbow)
	//assert states have p1color of playable moves
	boolean two = false;
	boolean three = false;
	boolean four = false;
	boolean five = false;
	GPFillerJunitTest.setRainbow(g);
	cpg = new GameBoard(g);

	s = new MCTSState(g);
	allStates = s.getAllStates();
	for (MCTSState state : allStates) {
		assertFalse(state.getGameBoard().getP1ColorI() == 0);
		assertFalse(state.getGameBoard().getP1ColorI() == 1);
		assertTrue(state.getGameBoard().getP2ColorI() == 1);
		
		if(state.getGameBoard().getP1ColorI() == 2) {
			two = true;
		}else if(state.getGameBoard().getP1ColorI() == 3) {
			three = true;
		}else if(state.getGameBoard().getP1ColorI() == 4) {
			four = true;
		}else if(state.getGameBoard().getP1ColorI() == 5) {
			five = true;
		}
	}
	assertTrue(two && three && four && five);
	assertTrue(GameBoard.compareTo(g, cpg));

	
	
	}
	@Test
	public void testRandomMove() {
		GameBoard g = new GameBoard();
		GPFillerJunitTest.setRainbow(g);
		MCTSState s = new MCTSState(g);
		MCTSState cps = new MCTSState(g);
		List<MCTSState> allStates = s.getAllStates();
		//TODO test random move is move in allStates,
		//vertical stripes 
		//   0 1 2 3 4 5 0 1 
		//  -1 0 0 0 0 0 0 1 
		boolean zero = false;
		boolean one = false;
		boolean two= false;
		boolean three= false;
		boolean four= false;
		boolean five= false;
		//tests to see that in 50 random moves each playable state is chosen atleast once
		for(int i =0; i < 50; i++) {
			cps = new MCTSState(s);
			cps.randomMove();
			switch(cps.getGameBoard().getP1ColorI()) {
			case 0:
				zero = true;
				break;
			case 1:
				one = true;
				break;
			case 2:
				two = true;
				break;
			case 3:
				three = true;
				break;
			case 4:
				four = true;
				break;
			case 5:
				five = true;
				break;
			default:
				throw new IllegalArgumentException("this shit broken asl");
				
			}

		}
		assertTrue(!zero && !one && two && three && four && five);
		
	}
	@Test
	public void testAddScore() {
		//TODO
		//i forget what this tests, didnt comment at first, and now it looks like a bullshit test
		MCTSState s = new MCTSState();
		s.setWinScore(Integer.MIN_VALUE);
		s.addScore(10000);
		assertEquals(Integer.MIN_VALUE,s.getWinScore(), DELTA);
		s.setWinScore(0);
		s.addScore(100);
		assertEquals(100,s.getWinScore(),DELTA);
	}
	
	
	
	//Test Tile.java-------------------------------------------------------------------------------------
	@Test
	public void testTile() {
		//defaault constructor
		Tile t = new Tile();
		assertEquals("", t.getColorS());
		assertEquals(-1, t.getColorI());
		assertFalse(t.getGrouped());
		
		//constructor with specific color
		t = new Tile(5);
		assertEquals("BLUE", t.getColorS());
		assertEquals(5, t.getColorI());
		assertFalse(t.getGrouped());
		
		//copy constructor
		Tile cp = new Tile(2);
		cp.setGrouped(true);
		
		t = new Tile(cp);
		assertEquals("RED", t.getColorS());
		assertEquals(2, t.getColorI());
		assertTrue(t.getGrouped());
		
	}
	@Test
	public void testSetGrouped() {
		Tile t = new Tile();
		assertFalse(t.getGrouped());
		t.setGrouped(true);
		assertTrue(t.getGrouped());
		t.setGrouped(false);
		assertFalse(t.getGrouped());
	}
	@Test
	public void testSetColorI() {
		Tile t = new Tile();
		assertEquals(-1,t.getColorI());
		t.setColorI(0);
		assertEquals(0, t.getColorI());
		assertEquals("BLACK",t.getColorS());
	}
	@Test
	public void testSetColorS() {
		Tile t = new Tile();
		assertEquals(-1,t.getColorI());
		t.setColorS("RED");
		assertEquals(2, t.getColorI());
		assertEquals("RED",t.getColorS());
		//assertThrows(Throwable IllegalArgumentException, t.setColorS("blah"));
	}
	
	//Test Tree.java-------------------------------------------------------------------------------------
	@Test
	public void testTree() {
		//default constructor
		Tree t = new Tree();
		assertNotNull(t.getRoot());
		//constructor with rootnode
		MCTSNode node = new MCTSNode();
		t = new Tree(node);
		assertEquals(node, t.getRoot());
		
	}
	
	@Test
	public void testGetRoot() {
		Tree t = new Tree();
		MCTSNode node = new MCTSNode();
		t = new Tree(node);
		assertEquals(node, t.getRoot());
	}
	@Test
	public void testSetRoot() {
		MCTSNode A = new MCTSNode();
		MCTSNode B = new MCTSNode();
		Tree t = new Tree(A);
		assertEquals(A, t.getRoot());
		t.setRoot(B);
		assertEquals(B, t.getRoot());
	}
	@Test
	public void testAddChild() {
		MCTSNode A = new MCTSNode();
		MCTSNode B = new MCTSNode();
		Tree t = new Tree(A);
		assertEquals(0,t.getRoot().getChildArray().size());
		t.addChild(A, B);
		assertEquals(B,t.getRoot().getChildArray().get(0));
	}
	
	
	
}


