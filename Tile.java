package main;

public class Tile {
	private int colorI;
	private String colorS;
	private boolean grouped;

	private static final String[] arrColors = {"black", "purple", "red", "yellow", "green", "blue"};
	
	public Tile() {
		this.colorS = "";
		this.colorI = -1;
		this.grouped = false;
	}
	public Tile(int n) {
		this.colorS = "";
		this.colorI = n;
		this.grouped = false;

	}
	public Tile(String s) {
		this.colorS = s;
		this.colorI = -1;
		this.grouped = false;

	}
	
	
	// Setters

	public void setGrouped(boolean b) {
		this.grouped = b;
	}
	public void setColorI(int I) {
		if(I >= arrColors.length) {
			throw new IllegalArgumentException();
		}
		this.colorI = I;
		this.colorS = arrColors[I];
	}
	public void setColorS(String s) {
		//Iterates arrColors if input is valid color, it is assigned, else exception thrown.
		for (int i =0; i < arrColors.length; i++) {
			if(s.equals(arrColors[i])) {
				this.colorS = s;
				this.colorI = i;
			}else {
				throw new IllegalArgumentException();
			}
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
