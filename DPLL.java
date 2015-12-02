import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;

public class DPLL {

	public static void main(String[] args) throws FileNotFoundException 
	{
		String filename;
		int heuristic = 0;
		boolean verbose = false;
		if(args.length > 1)
		{
			filename = args[0];
			ArrayList<Pair<String, Boolean>> model = parseModel(args[1]);
			for(int i = 2; i < args.length; ++i)
			{
				int option = parseHeur(args[i]);
				switch(option)
				{
					case ALL:
					case PURE:
					case UNIT:
					case NONE: heuristic = option; break;
					case VERBOSE: verbose = true; break;
					default: System.out.println("Invalid argument: " + args[i]); System.out.println(help); return;
				}
			}
			ArrayList<Clause> clauses = generateClause(filename);
			ArrayList<String> symbols = generateSymbols(clauses, model);
			//TODO actually respect heuristic choice
			System.out.println(dpll(clauses, symbols, model, heuristic, verbose));
		}
		else
		{
			System.out.println(help);
		}
	}
	
	static int parseHeur(String input)
	{
		if(input.equals("all"))
		{
			return ALL;
		}
		else if(input.equals("pure"))
		{
			return PURE;
		}
		else if(input.equals("unit"))
		{
			return UNIT;
		}
		else if(input.equals("none"))
		{
			return NONE;
		}
		else if(input.equals("verbose"))
		{
			return VERBOSE;
		}
		//default case
		return -1;
	}
	
	static ArrayList<Pair<String, Boolean>> parseModel(String args)
	{
		ArrayList<Pair<String, Boolean>> model = new ArrayList<Pair<String, Boolean>>();
		int index = 0;
		while(index >= 0 && args.length() > 0)
		{
			index = args.indexOf(" ");
			if(index == -1)
			{
				model.add(new Pair<String, Boolean>(args, true));
			}
			else
			{
				model.add(new Pair<String, Boolean>(args.substring(0, index), true));
				args = args.substring(index + 1);
			}
		}
		return model;
	}
	
	static Solution dpll(ArrayList <Clause> clauses, List<String> symbols, ArrayList<Pair<String, Boolean>> model, int heuristic, boolean verbose)
	{
		//ugly hack to pass integer by reference
		int iterations[] = new int[1];
		iterations[0] = 0;
		//the ArrayList that shall contain the final, solved model
		ArrayList<Pair<String, Boolean>> masterModel = new ArrayList<Pair<String, Boolean>>();
		dpllInternal(clauses, symbols, model, iterations, masterModel, heuristic, verbose);
		return new Solution(masterModel, iterations[0]);
	}
	
	static boolean dpllInternal(ArrayList <Clause> clauses, List<String> symbols, ArrayList<Pair<String, Boolean>> model, int iterations[],
			ArrayList<Pair<String, Boolean>> masterModel, int heuristic, boolean verbose)
	{
		++iterations[0];
		boolean all_true = true;
		//check to see if every clause is true or if there exists a clause that is false
		for(int i = 0; i < clauses.size(); ++i)
		{
			Boolean evalResult = clauses.get(i).evaluate(model);
			//exists a partial assignment
			if(evalResult == null)
			{
				all_true = false;
			}
			else if(evalResult == false)
			{
				//exists a clauses that is false
				return false;
			}
		}
		//all clauses evaluate to true
		if(all_true)
		{
			masterModel.addAll(model);
			return true;
		}
		
		Pair<String, Boolean> pureSymbol = null;
		if(heuristic == ALL || heuristic == PURE)
		{
			pureSymbol = findPureSymbol(symbols, clauses, model);
		}
		if(pureSymbol != null)
		{
			List<String> rest = new ArrayList<String>(symbols);
			//remove the found symbol
			rest.remove(rest.indexOf(pureSymbol.elem0));
			ArrayList<Pair<String, Boolean>> modelPure = new ArrayList<Pair<String, Boolean>>(model);
			//add the found pure symbol to the model
			modelPure.add(pureSymbol);
			if(verbose)
			{
				System.out.println("Pure symbol on: " + pureSymbol.elem0 + " = " + pureSymbol.elem1);
				System.out.println(printModel(model));
			}
			return dpllInternal(clauses, rest, modelPure, iterations, masterModel, heuristic, verbose);
		}
		
		if(heuristic == ALL || heuristic == UNIT)
		{
			pureSymbol = findUnitClause(clauses, model);
		}
		if(pureSymbol != null)
		{
			List<String> rest = new ArrayList<String>(symbols);
			//remove the found symbol
			rest.remove(rest.indexOf(pureSymbol.elem0));
			ArrayList<Pair<String, Boolean>> modelPure = new ArrayList<Pair<String, Boolean>>(model);
			//add the found pure symbol to the model
			modelPure.add(pureSymbol);
			if(verbose)
			{
				System.out.println("Unit Clause on: " + pureSymbol.elem0 + " = " + pureSymbol.elem1);
				System.out.println(printModel(model));
			}
			return dpllInternal(clauses, rest, modelPure, iterations, masterModel, heuristic, verbose);
		}
		
		//get the next symbol
		String next_symbol = symbols.get(symbols.size()-1);
		List<String> rest = symbols.subList(0, symbols.size() - 1);
		//create a model that contains the symbol bound with True and False
		ArrayList<Pair<String, Boolean>> modelT = new ArrayList<Pair<String, Boolean>>(model);
		ArrayList<Pair<String, Boolean>> modelF = new ArrayList<Pair<String, Boolean>>(model);
		modelT.add(new Pair<String, Boolean>(next_symbol, true));
		modelF.add(new Pair<String, Boolean>(next_symbol, false));
		if(verbose)
		{
			System.out.println("Trying element: " + next_symbol);
			System.out.println(printModel(model));
		}
		return dpllInternal(clauses, rest, modelT, iterations, masterModel, heuristic, verbose) || 
				dpllInternal(clauses, rest, modelF, iterations, masterModel, heuristic, verbose);
	}
	
	static Pair<String, Boolean> findPureSymbol(List<String> symbols, ArrayList<Clause> clauses, ArrayList<Pair<String, Boolean>> model)
	{
		HashSet<String> seenSymbols = new HashSet<String>();
		HashMap<String, Pair<String, Boolean>> value = new HashMap<String, Pair<String, Boolean>>();
		for(int i = 0; i < clauses.size(); ++i)
		{
			Boolean alreadyTrue = clauses.get(i).evaluate(model);
			//only check for purity if this clause is not already true
			if(alreadyTrue == null)
			{
				for(int j = 0; j < clauses.get(i).data.size(); ++j)
				{
					Pair<String, Boolean> symbol = clauses.get(i).data.get(j);
					//already seen, check if dirty
					if(seenSymbols.contains(symbol.elem0))
					{
						Pair<String, Boolean> comp = value.get(symbol.elem0);
						if(comp == null)
						{
							//do nothing, already determined to be dirty
						}
						else if(comp.elem1.equals(symbol.elem1))
						{
							//do nothing, still is pure
						}
						else
						{
							//mismatch, is dirty symbol, remove
							value.remove(symbol.elem0);
						}
					}
					//never seen before, add
					else
					{
						seenSymbols.add(symbol.elem0);
						value.put(symbol.elem0, symbol);
					}
				}
			}
		}
		Iterator<Pair<String, Boolean>> iter = value.values().iterator();
		while(iter.hasNext())
		{
			Pair<String, Boolean> symbol = iter.next();
			//symbol is not already in use
			if(Collections.binarySearch(symbols, symbol.elem0) >= 0)
			{
				return symbol;
			}
		}
		//no valid pure symbols found
		return null;
	}
	
	static Pair<String, Boolean> findUnitClause(ArrayList<Clause> clauses, ArrayList<Pair<String, Boolean>> model)
	{
		//determine which symbols are held in model
		HashSet<String> seenSymbols = new HashSet<String>();
		for(int i = 0; i < model.size(); ++i)
		{
			seenSymbols.add(model.get(i).elem0);
		}
		for(int i = 0; i < clauses.size(); ++i)
		{
			if(clauses.get(i).evaluate(model) == null)
			{
				int notSeenIndex = -1;
				//check to see if only one element not set by model
				for(int j = 0; j<clauses.get(i).data.size(); ++j)
				{
					Pair<String, Boolean> element = clauses.get(i).data.get(j);
					if(seenSymbols.contains(element))
					{
						//element set do nothing
					}
					else
					{
						if(notSeenIndex != -1)
						{
							//multiple clauses not set by model, not unit clause
							notSeenIndex = -1;
							break;
						}
						else
						{
							//found an element that has not been set
							notSeenIndex = j;
						}
					}
				}
				//unit clause found
				if(notSeenIndex != -1)
				{
					return clauses.get(i).data.get(notSeenIndex);
				}
			}
		}
		return null;
	}
	
	static ArrayList<String> generateSymbols(ArrayList<Clause> clauses, ArrayList<Pair<String, Boolean>> model)
	{
		ArrayList<String> symbols = new ArrayList<String>();
		HashSet<String> seenBefore = new HashSet<String>();
		//prevents elements in model from being put in available symbols
		for(int i = 0; i < model.size(); ++i)
		{
			seenBefore.add(model.get(i).elem0);
		}
		for(int i = 0; i < clauses.size(); ++i)
		{
			for(int j = 0; j < clauses.get(i).data.size(); ++j)
			{
				String symbol = clauses.get(i).data.get(j).elem0;
				if(seenBefore.contains(symbol))
				{
					//do nothing, already contained in symbol
				}
				else
				{
					symbols.add(symbol);
					seenBefore.add(symbol);
				}
			}
		}
		//sorted symbols make for nicer output
		Collections.sort(symbols);
		return symbols;
	}
	
	static ArrayList<String> generateSymbols(ArrayList<Clause> clauses)
	{
		ArrayList<Pair<String,Boolean>> model = new ArrayList<Pair<String,Boolean>>();
		return generateSymbols(clauses, model);
	}
	
	static ArrayList<Clause> generateClause(String filename) throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File(filename));
		ArrayList<Clause> clause = new ArrayList<Clause>();
		while(scan.hasNext())
		{
			String line = new String(scan.nextLine());
			if(line.isEmpty() || line.charAt(0) == '#')
			{
				//do nothing as this is a comment
			}
			else
			{
				Clause current = new Clause(line);
				clause.add(current);
			}
		}
		scan.close();
		return clause;
	}
	
	static String printModel(ArrayList<Pair<String, Boolean>> model)
	{
		StringBuilder output = new StringBuilder();
		output.append("model = {");
		for(int i = 0; i < model.size(); ++i)
		{
			if(i > 0)
			{
				output.append(", ");
			}
			Pair<String, Boolean> element = model.get(i);
			output.append(element.elem0).append(" = ").append(element.elem1);
		}
		output.append("}\n");
		return output.toString();
	}
	//help string
	private static final String help = new StringBuilder().append("Please run program in the format:\n")
								.append("java DPLL <filename> \"<facts>\" <heuristic> <verbose>\n")
								.append("\tWhere <filename> is the knowledge base file\n")
								.append("\tWhere <facts> are the propositional facts that are asserted\n")
								.append("\t\tNOTE: <facts> must be surrounded by quotes\n")
								.append("\t\tNOTE: <facts> is mandatory, even if it is empty, that is \"\"\n")
								.append("\tWhere <heuristic> is written as \"all\", \"pure\", \"unit\", or \"none\" (no quotes)\n")
								.append("\t\tNOTE: if nothing is written for heuristic, default is all\n")
								.append("\tWhere <verbose> is written as verbose for full trace and blank for just the resolution\n")
								.append("Example: java DPLL Example.kb \"\" unit verbose").toString();
	//constants
	final static int ALL = 0;
	final static int PURE = 1;
	final static int UNIT = 2;
	final static int NONE = 3;
	final static int VERBOSE = 4;
}