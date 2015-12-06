import java.lang.*;
import java.util.*;
import java.io.*;

public class ClauseGenerator7 {
	public static char[][] puzzle; 
	public static char[] validValues;
	public static char[] validValues3 = {'1','2','3','4','5','6','7','8','9'};
	public static char[] validValues5 = {'1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P'};
	public static char[] validValues2 = {'1','2','3','4'};
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
						// String a2 = ""+a;
						// int b = Integer.parseInt(a2);
						puzzle[r][c] = a;
					}
					else
						puzzle[r][c] = ' ';
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
					
					char value = line.charAt(underscorePos+1);
					// int val = Integer.parseInt(value);
					
					puzzle[row][col] = value;
				}
            }
            input.close();
		System.out.println("result:");
		printPuzzle();
	}
	
	// generate bool expression for a certain r,c entry
	public static StringBuilder knownCell(int r, int c, char value){
		StringBuilder s = new StringBuilder().append("r").append(r).append("c").append(c).append("_").append(value).append("\n");
		for(int i = 0; i < validValues.length; ++i){
			if(validValues[i] != value)
				s.append("~r").append(r).append("c").append(c).append("_").append(validValues[i]).append("\n");
		}
		s.append("\n");
		return s;
	}
	
	// generate bool expression for a row
	public static StringBuilder knownRow(int r, int c, char value){
		StringBuilder s = new StringBuilder();
		for(int col = 1; col <= n2; ++col){
			if(col != c)
				s.append("~r").append(r).append("c").append(col).append("_").append(value).append("\n");
		}
		s.append("\n");
		return s;
	}
	
	// generate bool expression for a col
	public static StringBuilder knownCol(int r, int c, char value){
		StringBuilder s = new StringBuilder();
		for(int row = 1; row <= n2; ++row){
			if(row != r)
				s.append("~r").append(row).append("c").append(c).append("_").append(value).append("\n");
		}
		s.append("\n");
		return s;
	}
	
	// bool expressions for block restrictions
	public static StringBuilder knownBlock(int row, int col, char value){	
		StringBuilder s = new StringBuilder();
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
					s.append("~r").append(r).append("c").append(c).append("_").append(value).append("\n");
			}
		}
		s.append("\n");
		return s;
	}
	
	
	// bool expressions for the known values
	public static StringBuilder getKnown(){
		StringBuilder s = new StringBuilder();
		//go through every entry in puzzle
		for(int r = 1; r <=n2; ++r){
			for(int c = 1; c <= n2; ++c){
				if(puzzle[r][c] != ' '){
					// s += "# known value (" + r + "," + c + ") " + puzzle[r][c] + ":\n";
					s.append(knownCell(r,c,puzzle[r][c]));
					s.append(knownRow(r,c,puzzle[r][c]));
					s.append(knownCol(r,c,puzzle[r][c]));
					s.append(knownBlock(r,c,puzzle[r][c]));
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
	public static StringBuilder extra(){
		StringBuilder s = new StringBuilder();
		for(int r = 1; r <= n2; ++r){
			for(int c = 1; c <= n2; ++c){
				for(int i = 0; i < validValues.length-1; ++i){
					for(int j = i+1; j < validValues.length; ++j){
						s.append("~r").append(r).append("c").append(c).append("_").append(validValues[i]).append(" ").append("~r").append(r)
							.append("c").append(c).append("_").append(validValues[j]).append("\n");
					}
				}
				s.append("\n");
			}
		}
		return s;	
	}
	
	// get generic bool expressions
	public static StringBuilder getGeneric(){
		StringBuilder s = cells();
		s.append(rows());
		s.append(cols());
		s.append(blocks());
		return s;
	}
	
	// bool expressions for the values each cell can have
	public static StringBuilder cells(){
		StringBuilder s = new StringBuilder();
		for(int r = 1; r <= n2; ++r){
			for(int c = 1; c <= n2; ++c){
				for(int i = 0; i < validValues.length; ++i){
					s.append("r").append(r).append("c").append(c).append("_").append(validValues[i]).append(" ");
				}
				s.append("\n");
			}
		}
		return s;
	}
	
	// bool expressions for row restrictions
	public static StringBuilder rows(){
		StringBuilder s = new StringBuilder();
		for(int r = 1; r <= n2; ++r){
			for(int i = 0; i < validValues.length; ++i){
				for(int c = 1; c <= n2; ++c){
					s.append("r").append(r).append("c").append(c).append("_").append(validValues[i]).append(" ");
				}
				s.append("\n");
			}
			s.append("\n");
		}
		return s;
	}
	
	// bool expressions for col restrictions
	public static StringBuilder cols(){
		StringBuilder s = new StringBuilder();
		for(int c = 1; c <= n2; ++c){
			for(int i = 0; i < validValues.length; ++i){
				for(int r = 1; r <= n2; ++r){
					s.append("r").append(r).append("c").append(c).append("_").append(validValues[i]).append(" ");
				}
				s.append("\n");
			}
			s.append("\n");
		}
		return s;
	}
	
	// generate expressions for a block
	public static StringBuilder square(int row, int col){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < validValues.length; ++i){
			for(int r = row; r < (row + n); ++r){
				for(int c = col; c < (col + n); ++c){
					s.append("r").append(r).append("c").append(c).append("_").append(validValues[i]).append(" ");
				}
			}
			s.append("\n");
		}
		s.append("\n");
		return s;
	}
	
	// bool expressions for block restrictions
	public static StringBuilder blocks(){
		StringBuilder s = new StringBuilder();
		
		for(int r = 1; r <= n2; r += n){
			for(int c = 1; c <= n2; c += n){
				s.append(square(r,c));
			}
		}
		s.append("\n");		
		return s;
	}
	
	public static void main(String [] args) throws IOException{
		String filename = args[0];
		n = Integer.parseInt(args[1]);
		n2 = n*n;
		
		if(n == 2){
			validValues = validValues2;
		}
		else if(n == 3){
			validValues = validValues3;
		}
		else if(n == 5){
			validValues = validValues5;
		}
		
		
		puzzle = new char[n2+1][n2+1];
		System.out.println("original:");
		getPuzzle(filename);
		printPuzzle();
		System.out.println("*********");
		StringBuilder ss = getGeneric();
		ss.append(getKnown());
		ss.append(extra());
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
		
		
		String result = Solver.SAT(ss.toString());
		finishPuzzle(result);
		
	}
}