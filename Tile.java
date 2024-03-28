package main;

public class Tile {
	private int colorI;
	private String colorS;
	private boolean grouped;

	private static final String[] arrColors = {"BLACK", "PURPLE", "RED", "YELLOW", "GREEN", "BLUE"};
	
	//default constructor 
	public Tile() {
		this.colorS = "";
		this.colorI = -1;
		this.grouped = false;
	}
	//constructor with specific color
	public Tile(int n) {
		this.colorS = Tile.arrColors[n];
		this.colorI = n;
		this.grouped = false;

	}
	public Tile(Tile t) {
		this.colorI = t.getColorI();
		this.colorS = t.getColorS();
		this.grouped = t.getGrouped();
	}
	
	
	// Setters

	public void setGrouped(boolean b) {
		this.grouped = b;
	}
	public void setColorI(int I) {
		if(I >= arrColors.length || I<0) {
			throw new IllegalArgumentException();
		}
		this.colorI = I;
		this.colorS = arrColors[I];
	}
	public void setColorS(String s) {
		//Iterates arrColors if input is valid color, it is assigned, else exception thrown.
		boolean found = false; //makes sure S is in arrColors
		for (int i =0; i < arrColors.length; i++) {
			if(s.equals(arrColors[i])) {
				found = true;
				this.colorS = s;
				this.colorI = i;
			}
		}
		if(!found ) {
			throw new IllegalArgumentException();
		}
	}
	//Getters
	public static String[] getArrColors() {
		return arrColors;
	}
	
	public boolean getGrouped() {
		return this.grouped;
	}
	public String getColorS() {
		return this.colorS;
	}
	public int getColorI() {
		return this.colorI;
	}
	
}
