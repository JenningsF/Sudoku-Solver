import java.lang.*;
import java.util.*;
import java.io.*;

public class ClauseGenerator2 {
	public static int[][] puzzle = new int[10][10];
	public static int n;
	public static int n2;
	
	// read in puzzle
	public static void getPuzzle(){
		File f = new File("input.txt");
		try{
			Scanner sc = new Scanner(f);    
			
			for(int r = 1; r <= 9; ++r){
				String input = sc.next();	 
				
				for(int c = 1; c <= 9; ++c){
					char a = input.charAt(c-1);
					if(a != '.'){
						String a2 = ""+a;
						int b = Integer.parseInt(a2);
						puzzle[r][c] = b;
					}
				}
			}
		} 
		catch (FileNotFoundException ex) {
			System.out.println("ERROR! File not found!");
			System.exit(0);
		} 
	}
	
	// print out the puzzle
	public static void printPuzzle(){
		for(int r = 1; r <=9; ++r){
			for(int c = 1; c <= 9; ++c){
				System.out.print(puzzle[r][c]);
			}
			System.out.println();
		}
	}
	
	// generate bool expression for a certain r,c entry
	public static String knownCell(int r, int c, int value){
		String s = "r" + r + "c" + c + "_" + value + "\n";
		for(int i = 1; i <=9; ++i){
			if(i != value)
				s += "-r" + r + "c" + c + "_" + i + "\n";
		}
		s += "\n";
		return s;
	}
	
	// generate bool expression for a row
	public static String knownRow(int r, int c, int value){
		String s = "";
		for(int col = 1; col <= 9; ++col){
			if(col != c)
				s += "-r" + r + "c" + col + "_" + value + "\n";
		}
		s += "\n";
		return s;
	}
	
	// generate bool expression for a col
	public static String knownCol(int r, int c, int value){
		String s = "";
		for(int row = 1; row <= 9; ++row){
			if(row != r)
				s += "-r" + row + "c" + c + "_" + value + "\n";
		}
		s += "\n";
		return s;
	}
	
	// bool expressions for block restrictions
	public static String knownBlock(int row, int col, int value){	
		
		String s = "";
		
		int squareRow, squareCol;
		
		if(row < 4){	
			squareRow = 1;
			if(col < 4){
				squareCol = 1;
			}
			else if(col < 7){
				squareCol = 4;
			}
			else{
				squareCol = 7;
			}
			
		}
		else if(row < 7){
			squareRow = 4;
			if(col < 4){
				squareCol = 1;
			}
			else if(col < 7){
				squareCol = 4;
			}
			else{	
				squareCol = 7;
			}
		}
		else{
			squareRow = 7;
			if(col < 4){
				squareCol = 1;
			}
			else if(col < 7){		
				squareCol = 4;
			}
			else{	
				squareCol = 7;
			}
		}
		
		for(int r = squareRow; r < (squareRow+n); ++r){
			for(int c = squareCol; c < (squareCol+n); ++c){
				if(!(r == row || c == col))
					s += "-r" + r + "c" + c + "_" + value + "\n";
			}
		}
		s += "\n";
		
		
		return s;
	}
	
	
	// bool expressions for the known values
	public static String getKnown(){
		String s = "# bool expressions for the known values\n";
		
		//go through every entry in puzzle
		for(int r = 1; r <=9; ++r){
			for(int c = 1; c <= 9; ++c){
				if(puzzle[r][c] != 0){
					s += "# known value (" + r + "," + c + ") " + puzzle[r][c] + ":\n";
					s += knownCell(r,c,puzzle[r][c]);
					s += knownRow(r,c,puzzle[r][c]);
					s += knownCol(r,c,puzzle[r][c]);
					s += knownBlock(r,c,puzzle[r][c]);
				}
			}
		}
		return s;
	}
	
	/* 
		***********************************************************
		GENERIC SUDOKU RULES
		***********************************************************
	*/
	
	// get generic bool expressions
	public static String getGeneric(){
		String s = cells();
		s += rows();
		s += cols();
		s += blocks();
		return s;
	}
	
	// bool expressions for the values each cell can have
	public static String cells(){
		String s = "# each individual cell can have value 1 - 9\n";
		for(int r = 1; r <= n2; ++r){
			for(int c = 1; c <= n2; ++c){
				for(int i = 1; i <= n2; ++i){
					s += "r" + r + "c" + c + "_" + i + " ";
				}
				s += "\n";
			}
		}
		return s;
	}
	
	// bool expressions for row restrictions
	public static String rows(){
		String s = "# bool expressions for row restrictions\n";
		
		for(int r = 1; r <= n2; ++r){
			for(int i = 1; i <= n2; ++i){
				for(int c = 1; c <= n2; ++c){
					s += "r" + r + "c" + c + "_" + i + " ";
				}
				s += "\n";
			}
			s += "\n";
		}
		return s;
	}
	
	// bool expressions for col restrictions
	public static String cols(){
		String s = "# bool expressions for col restrictions\n";
		
		for(int c = 1; c <= n2; ++c){
			for(int i = 1; i <= n2; ++i){
				for(int r = 1; r <= n2; ++r){
					s += "r" + r + "c" + c + "_" + i + " ";
				}
				s += "\n";
			}
			s += "\n";
		}
		return s;
	}
	
	// generate expressions for a block
	public static String square(int row, int col){
		String s = "";
		for(int i = 1; i <= n2; ++i){
			for(int r = row; r < (row + n); ++r){
				for(int c = col; c < (col + n); ++c){
					s += "r" + r + "c" + c + "_" + i + " ";
				}
			}
			s += "\n";
		}
		s += "\n";
		return s;
	}
	
	// bool expressions for block restrictions
	public static String blocks(){
		String s = "# bool expressions for block restrictions\n";
		
		// number of blocks
		// for(int k = 0; k < n; ++k){
			// for(int i = 1; i <= n2; ++i){	
				// for(int r = k*n; r < (k*n+n); ++r){
					// for(int c = k*n; c < (k*n+n); ++c){
						// s += "r" + (r+1) + "c" + (c+1) + "_" + i + " ";
					// }
				// }
				// s += "\n";
			// }
			// s += "\n";
		// }
		
		
		for(int r = 1; r <= n2; r += n){
			for(int c = 1; c <= n2; c += n){
				s += square(r,c);
			}
		}
		s += "\n";

		
		return s;
	}
	
	public static void main(String [] args){
		n = 3;
		n2 = n*n;
		getPuzzle();
		printPuzzle();
		String ss = getGeneric();
		ss += getKnown();

		System.out.println(ss);
	}
}