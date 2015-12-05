import java.util.ArrayList;
import java.util.Collections;

public class Solution 
{
	ArrayList<Pair<String, Boolean>> masterModel;
	int iterations;
	Solution(ArrayList<Pair<String, Boolean>> m, int i)
	{
		masterModel = m;
		iterations = i;
	}
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		if(masterModel.size() == 0)
		{
			output.append("Failure, no solution could be found.\n");
		}
		else
		{
			Collections.sort(masterModel, new Pair_String());
			output.append("Solution (only true values):\n");
			for(int j = 0; j < masterModel.size(); ++j)
			{
				if(masterModel.get(j).elem1 == true)
				{
					output.append(masterModel.get(j).elem0);
					output.append("\n");
				}
			}
		}
		output.append("total iterations = ").append(iterations);
		return output.toString();
	}
}
