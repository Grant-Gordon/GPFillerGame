package main;
import java.util.*;

public class ConsoleTester {
	private GameController gameController;
	private Scanner scan = new Scanner(System.in);
	
	public static String toString(String[] arr) {
		String out = "";
		for(String e: arr) {
			out += e + " ";
		}
		return out;
		
	}
	
	public ConsoleTester() {
		gameController = new GameController();
	}
	public void run() {
		while(gameController.getPlaying()) {
			//state score/whos turn
			this.gameController.printScore();
			if(gameController.getPlayer1Turn()) {
				System.out.println("It is Player1's turn. Select a Color (Indexed 0)");
			}else {
				System.out.println("It is Player2's turn. Select a Color (Indexed 0)");
			}
			
			//list Color Options
			System.out.println("|:| " + ConsoleTester.toString(Tile.getArrColors()) + " |:|");
			int inputColorI = scan.nextInt();
			
			//actions called for turn
			this.gameController.setGroupColor(inputColorI);
			this.gameController.group();
			this.gameController.updateScore();
			this.gameController.updatePlaying();
			
			//swap turns 
			if(!this.gameController.getPlayer1Turn()) {
				this.gameController.setPlayer1Turn(true);
			}else {
				this.gameController.setPlayer1Turn(false);
			}
			
			// console logs 
			this.gameController.getGameBoard().printGroupedBoard();
			System.out.println("___________________________________");

			this.gameController.getGameBoard().printTileColor(0);
			System.out.println("___________________________________");
			this.gameController.getGameBoard().printTileColor(1);
		}
		System.out.println("\n\n\n\n\n\n\n\n\n Game over" );
	}
public static void main(String[] args) {
ConsoleTester game = new ConsoleTester();
game.run();
}
}
