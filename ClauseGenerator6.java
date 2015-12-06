import java.lang.*;
import java.util.*;
import java.io.*;

public class ClauseGenerator6 {
	public static int[][] puzzle; 
	public static int n;
	public static int n2;
	
	// read in puzzle
	public static void getPuzzle(String filename){
		File f = new File(filename);
		try{
			Scanner sc = new Scanner(f);    
			for(int r = 1; r <= n2; ++r){
				String input = sc.next();	 
				for(int c = 1; c <= n2; ++c){
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
		for(int r = 1; r <=n2; ++r){
			for(int c = 1; c <= n2; ++c){
				System.out.print(puzzle[r][c]);
			}
			System.out.println();
		}
	}
	
	public static void finishPuzzle(String in){
		// String filename = "result.txt";
		
		// System.out.println(in);
		in = in.replaceAll(" ","\n");
		// System.out.println(in);
		
		if(in.equals("~")){
			System.out.println("FAILED!!");
			return;
		}
		
			// File f = new File(filename);
			Scanner input = new Scanner(in);
			input.nextLine();
            while (input.hasNextLine()) {
                String line = input.nextLine();

				// if(line == "" || line == " " || line.length() == 0){
					// System.out.println("\n\n\n\nHOWDY!!!!\n\n\n\n");
				// }
				if(line.charAt(0) != '~'){
					int cPos = line.indexOf('c');
					int underscorePos = line.indexOf('_');
					
					String r = line.substring(1,cPos);
					int row = Integer.parseInt(r);
					String c = line.substring(cPos + 1,underscorePos);
					int col = Integer.parseInt(c);
					
					String value = line.substring(underscorePos+1);
					int val = Integer.parseInt(value);
					
					puzzle[row][col] = val;
				}
            }
            input.close();
		System.out.println("result:");
		printPuzzle();
	}
	
	// generate bool expression for a certain r,c entry
	public static String knownCell(int r, int c, int value){
		String s = "r" + r + "c" + c + "_" + value + "\n";
		for(int i = 1; i <=n2; ++i){
			if(i != value)
				s += "~r" + r + "c" + c + "_" + i + "\n";
		}
		s += "\n";
		return s;
	}
	
	// generate bool expression for a row
	public static String knownRow(int r, int c, int value){
		String s = "";
		for(int col = 1; col <= n2; ++col){
			if(col != c)
				s += "~r" + r + "c" + col + "_" + value + "\n";
		}
		s += "\n";
		return s;
	}
	
	// generate bool expression for a col
	public static String knownCol(int r, int c, int value){
		String s = "";
		for(int row = 1; row <= n2; ++row){
			if(row != r)
				s += "~r" + row + "c" + c + "_" + value + "\n";
		}
		s += "\n";
		return s;
	}
	
	// bool expressions for block restrictions
	public static String knownBlock(int row, int col, int value){	
		String s = "";
		int squareRow = -1, squareCol = -1;
		boolean rowFound = false;
		boolean colFound = false;
		int r = 1+n;
		while((r <= (1+n2)) && (!rowFound || !colFound)){
			int c = 1+n;
			while((c <= (1+n2)) && (!rowFound || !colFound)){
				if(row < r && !rowFound){
					squareRow = r-n;
					rowFound = true;
				}
				if(col < c && !colFound){
					squareCol = c-n;
					colFound = true;
				}
				c+=n;
			}
			r+=n;
		}
		for(r = squareRow; r < (squareRow+n); ++r){
			for(int c = squareCol; c < (squareCol+n); ++c){
				if(!(r == row || c == col))
					s += "~r" + r + "c" + c + "_" + value + "\n";
			}
		}
		s += "\n";
		return s;
	}
	
	
	// bool expressions for the known values
	public static String getKnown(){
		String s = "";
		//go through every entry in puzzle
		for(int r = 1; r <=n2; ++r){
			for(int c = 1; c <= n2; ++c){
				if(puzzle[r][c] != 0){
					// s += "# known value (" + r + "," + c + ") " + puzzle[r][c] + ":\n";
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
	
	// general instructions
	public static String extra(){
		String s = "";
		for(int r = 1; r <= n2; ++r){
			for(int c = 1; c <= n2; ++c){
				for(int i = 1; i < n2; ++i){
					for(int j = i+1; j <= n2; ++j){
						s += "~r" + r + "c" + c + "_" + i + " " + "~r" + r + "c" + c + "_" + j + "\n";
					}
				}
				s += "\n";
			}
		}
		return s;	
	}
	
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
		String s = "";
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
		String s = "";
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
		String s = "";
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
		String s = "";
		
		for(int r = 1; r <= n2; r += n){
			for(int c = 1; c <= n2; c += n){
				s += square(r,c);
			}
		}
		s += "\n";		
		return s;
	}
	
	public static void main(String [] args) throws IOException{
		String filename = args[0];
		n = Integer.parseInt(args[1]);
		n2 = n*n;
		puzzle = new int[n2+1][n2+1];
		System.out.println("original:");
		getPuzzle(filename);
		printPuzzle();
		System.out.println("*********");
		String ss = getGeneric();
		ss += getKnown();
		ss += extra();
		try{
			PrintWriter out =  new PrintWriter(args[2]);
			out.print(ss);
			out.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println("ERROR! File not found!");
			System.exit(0);
		} 
		// System.out.println(ss);
		
		
		String result = Solver.SAT(ss);
		finishPuzzle(result);
		
	}
}