import java.util.Scanner;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Solver {

	static String SAT(String input) throws IOException
	{
		Process process = new ProcessBuilder("./SAT").start();
		OutputStream stdin = process.getOutputStream();
		InputStream stdout = process.getInputStream();
		
		//BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
		
		writer.write(input);
		writer.write("\n");
		writer.flush();
		writer.close();
		
		Scanner scan = new Scanner(stdout);
		
		return scan.nextLine();
	}

}
