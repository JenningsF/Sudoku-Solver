import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.*;

import aima.core.logic.propositional.inference.DPLL;
import aima.core.logic.propositional.inference.DPLLSatisfiable;
import aima.core.logic.propositional.inference.WalkSAT;
import aima.core.logic.propositional.inference.OptimizedDPLL;
import aima.core.logic.propositional.kb.KnowledgeBase;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import aima.core.logic.propositional.visitors.SymbolCollector;


public class Solver {

	static ArrayList<String> dpll(String toParse)
	{
		DPLL dpll = new OptimizedDPLL();
		PLParser parser = new PLParser();
		Model model = new Model();
		Sentence sentence = parser.parse(toParse);
		Set <Clause> clauses =  ConvertToConjunctionOfClauses.convert(sentence).getClauses();
		List<PropositionSymbol> symbols = new ArrayList<PropositionSymbol>(
					SymbolCollector.getSymbolsFrom(sentence));
		boolean succeded = dpll.dpll(clauses,symbols, model);
		if(succeded)
		{
			return generateTrueList(model, symbols);
		}
		else
		{
			return null;
		}
	}
	
	static ArrayList<String> walkSAT(String toParse)
	{
		final int give_up = 100000;
		WalkSAT walk = new WalkSAT();
		PLParser parser = new PLParser();
		Sentence sentence = parser.parse(toParse);
		Set <Clause> clauses =  ConvertToConjunctionOfClauses.convert(sentence).getClauses();
		Model model = walk.walkSAT(clauses, 0.5, give_up);
		if(model == null)
		{
			return null;
		}
		else
		{	
			List<PropositionSymbol> symbols = new ArrayList<PropositionSymbol>(
					SymbolCollector.getSymbolsFrom(sentence));
			return generateTrueList(model, symbols);
		}
	}
	
	static ArrayList<String> generateTrueList(Model model, List<PropositionSymbol> symbols)
	{
		ArrayList<String> to_return = new ArrayList<String>();
		for(PropositionSymbol a : symbols)
		{
			if(model.isTrue(a))
			{
				to_return.add(a.toString());
			}
		}
		return to_return;
	}

}
