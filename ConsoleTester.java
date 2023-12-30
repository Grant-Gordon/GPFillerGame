package main;
import java.util.*;

public class ConsoleTester {
	private Scanner scan = new Scanner(System.in);
	private GameBoard board;
	private int numAI =0;
	private int AIPlayerNum;
	private int inputColorI;
	AIPlayer AI1;
	AIPlayer AI2;
	private boolean playing;
	private Stack<GameBoard> gameLog;

	//overriding array.toString
	public static String toString(String[] arr) {
		String out = "";
		for(String e: arr) {
			out += e + " ";
		}
		return out;
		
	}
	
	//default constructor 
	public ConsoleTester() {
		playing = true;
		board = new GameBoard();
		board.setP1ColorI(board.getTileBoard()[board.getBoardSize() -1][0].getColorI());
		board.setP2ColorI(board.getTileBoard()[0][board.getBoardSize()-1].getColorI());
		board.setPlayer1Turn(true);
	}
	
	public void updatePlaying() {
		
		//if board full 
		if(board.getP1Score() + board.getP2Score() == board.getBoardSize() * board.getBoardSize()) {
			playing = false;
		}
		
		//if mercy (one player has more than half the board. 
		if (board.getP1Score() > (board.getBoardSize() * board.getBoardSize())/ 2 || board.getP2Score() > (board.getBoardSize() * board.getBoardSize())/ 2 ) {
			playing = false;
		}
		
		
		
		
	}
	//main game running function
	public void run() {
		System.out.println("**************************************** \n"
				+ "|:| WELCOME TO FILLER |:|"
				+ "\n|:| The god awful game-pidgeon game thats no fun! |:|"
				+ "\n|:| Go ahead give it a try you'll see! |:|"
				+ "\n****************************************");
		//initial message
		System.out.println("How many AI players do you want (0,1,2)");

		//read num ai
		numAI = scan.nextInt();
		if(numAI > 2 || numAI < 0) {
			throw new IllegalArgumentException("invalid Input");
		}else if(numAI ==1) {
			AI1 = new AIPlayer();
			//check which player the AI is
			System.out.println("Ai player number(1 or 2)");
			AIPlayerNum = scan.nextInt();
			if(AIPlayerNum ==1) {
				AI1.setGroupingVal(-1);
			}else if(AIPlayerNum == 2) {
				AI1.setGroupingVal(1);
			}else {
				throw new IllegalArgumentException("Invalid Input");
			}
			//AI vs. AI
		}else {
			AI1 = new AIPlayer();
			AI2 = new AIPlayer();
		}
		
		//print starting board
		board.printTileColor(0);
		System.out.println("___________________________________");
		//board.printTileColor(1);
		
	//---------------------------------------------------------------------------------------------------
		
		//while game is running
		while(playing) {
			//state score
			board.printScore();
			
			
			//reading input
			
			switch(numAI) {
			//if no ai play as normal
			case 0:
				if(board.getPlayer1Turn()) {
					System.out.println("It is Player1's turn. Select a Color (Indexed 0) \n You cannot select the same color twice, or your opponents color");
				}else {
					System.out.println("It is Player2's turn. Select a Color (Indexed 0) \n You cannot select the same color twice, or your opponents color");
				}
				System.out.println("|:| " + ConsoleTester.toString(Tile.getArrColors()) + " |:|");
				 inputColorI = scan.nextInt();
				break;
			//if one AI
			case 1:
			
				if(AIPlayerNum == 1) {
					if(board.getPlayer1Turn()) {
						System.out.println("It is AI1 Turn");
						inputColorI = AI1.pickColorI(board, board.getPlayer1Turn());
						System.out.println("AI1 chose Color Index: " + inputColorI);
					}else {
						System.out.println("It is Player2's turn. Select a Color (Indexed 0) \n You cannot select the same color twice, or your opponents color");
						System.out.println("|:| " + ConsoleTester.toString(Tile.getArrColors()) + " |:|");
						 inputColorI = scan.nextInt();
					}
				}else if(AIPlayerNum == 2){
					if(board.getPlayer1Turn()) {
						System.out.println("It is Player1's turn. Select a Color (Indexed 0) \n You cannot select the same color twice, or your opponents color");
						System.out.println("|:| " + ConsoleTester.toString(Tile.getArrColors()) + " |:|");
						 inputColorI = scan.nextInt();
					}else {
						System.out.println("It is AI1 Turn");
						inputColorI = AI1.pickColorI(board, board.getPlayer1Turn());
						System.out.println("AI1 chose Color Index: " + inputColorI);
					}
				}else {
					throw new IllegalArgumentException("invalid input");
				}
				break;
			case 2: // if two AI
				if(board.getPlayer1Turn()) {
					System.out.println("It is AI1 Turn");
					inputColorI = AI1.pickColorI(board, board.getPlayer1Turn());
					System.out.println("AI1 chose Color Index: " + inputColorI);
				}else {
					System.out.println("It is AI2 Turn");
					inputColorI = AI2.pickColorI(board, board.getPlayer1Turn());
					System.out.println("AI2 chose Color Index: " + inputColorI);				}
				break;
			default:
				throw new IllegalArgumentException("invalid input");
			}
			
			
			
			
			
			//TODO if AI invalid input, have AI guess again
			//invalid input catching (not in color index or currently selected color)
			while(inputColorI > Tile.getArrColors().length -1 || inputColorI < 0 || inputColorI == board.getP1ColorI() || inputColorI == board.getP2ColorI()) {
				System.out.println("The number you selected is invalid. Try Again");
				inputColorI = scan.nextInt();
			}
			
			
			//update player colorsI
			board.playMove(inputColorI);
			
			//swap turns 
			if(!board.getPlayer1Turn()) {
				board.setPlayer1Turn(true);
			}else {
				board.setPlayer1Turn(false);
			}
			// console logs 
		//	this.gameController.getGameBoard().printGroupedBoard();
			System.out.println("___________________________________");

			board.printTileColor(0);
			System.out.println("___________________________________");
			//board.printTileColor(1);

			this.updatePlaying();

		}
		System.out.println("\n\n\n\n\n\n\n\n\n Game over" );
	}
	
	
	//tester execution
	public static void main(String[] args) {
			ConsoleTester game = new ConsoleTester();
			game.run();
	}
}
