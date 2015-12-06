#include <iostream>
#include <fstream>
#include <string>
#include <map>

using namespace std;

const int MAX_CHARS_PER_LINE = 512;
const int MAX_TOKENS_PER_LINE = 20;
const char* const DELIMITER = " ";

int main()
{
	cout << "*----------------------------------------*" << endl;
	cout << "\tRunning Sudoku PDDL Parser" << endl;
	cout << "*----------------------------------------*" << endl;
	cout << "\nPlease enter problem number:" << endl;
	int probNum;
	cin >> probNum;
	while (cin.fail()) {
		cout << "Error: Input was not an integer" << endl;
		cout << "Please enter problem number: ";
		cin.clear();
		cin.ignore(256,'\n');
		cin >> probNum;
	}

	// Creating .pddl file
	ofstream outFile;
	string outFileName = "sudoku_" + to_string(probNum) + ".pddl";
	outFile.open(outFileName);

	// Start writing to .pddl file
	outFile << "(define\n";
	outFile << "\t(problem sudoku_" + to_string(probNum) + ")\n";
	outFile << "\t(:domain sudoku)\n";
	outFile << "\t(:init\n";
	outFile << "\t\t(= (value_of one) 1)\n";
	outFile << "\t\t(= (value_of two) 2)\n";
	outFile << "\t\t(= (value_of three) 3)\n";
	outFile << "\t\t(= (value_of four) 4)\n";
	outFile << "\t\t(= (value_of five) 5)\n";
	outFile << "\t\t(= (value_of six) 6)\n";
	outFile << "\t\t(= (value_of seven) 7)\n";
	outFile << "\t\t(= (value_of eight) 8)\n";
	outFile << "\t\t(= (value_of nine) 9)\n\n";

	// Enumerates integers to corresponding strings
	map<int, string> numbers;
	numbers[0] = "."; numbers[1] = "one"; numbers[2] = "two"; numbers[3] = "three";
	numbers[4] = "four"; numbers[5] = "five"; numbers[6] = "six";
	numbers[7] = "seven"; numbers[8] = "eight"; numbers[9] = "nine";

	// Acquire input file name
	cout << "\nWhat file are you reading from?" << endl;
	string inFileName;
	cin.clear();
	cin >> inFileName;
	while (cin.fail()) {
		cout << "Error: Input was not a proper filename" << endl;
		cout << "What file are you reading from?" << endl;
		cin.clear();
		cin.ignore(256,'\n');
		cin >> inFileName;
	}

	// Acquire size of board
	cout << "\nPlease enter size of the board:" << endl;
	int boardSize;
	cin.clear();
	cin >> boardSize;
	while (cin.fail()) {
		cout << "Error: Input was not an integer" << endl;
		cout << "Please enter problem number: ";
		cin.clear();
		cin.ignore(256,'\n');
		cin >> boardSize;
	}

	// Checks if input file is opened
	cout << "\nOpening input file...";
	ifstream fileIn;
	fileIn.open(inFileName);
	if (!fileIn.is_open()) {
		cout << "Failed." << endl;
		cout << "Error: Unable to open file" << endl;
	}
	else cout << "Success!" << endl;

	// Produces cell_value clauses
	string line;
	int rowNum = 0;
	while (fileIn.good()) {
		while (!fileIn.eof()) {
			getline(fileIn, line);
			++rowNum;
			for (int i = 0; i < line.size(); ++i) {
				outFile << "\t\t(= (cell_value " + numbers[rowNum] + " " + numbers[i + 1] + ") ";
				if (line[i] == '.')
					outFile << "0)\n";
				else {
					outFile << line[i];
					outFile << ")\n";
				}
			}
			if (line.size() > 0)
				outFile << "\n";
		}
	}

	// Produces value_assigned clauses
	rowNum = 0;
	fileIn.close();
	fileIn.open(inFileName);
	while (fileIn.good()) {
		while (!fileIn.eof()) {
			getline(fileIn, line);
			++rowNum;
			for (int i = 0; i < line.size(); ++i) {
				if (line[i] != '.')
					outFile << "\t\t(value_assigned " + numbers[rowNum] + " " + numbers[i + 1] + ")\n";
			}
			if (line.size() > 0)
				outFile << "\n";
		}
	}

	// Finishes writing to .pddl file
	outFile << "\t)\n\n";
	outFile << "\t(:goal (and\n";
	for (int i = 0; i < boardSize; ++i){
		for (int j = 0; j < boardSize; ++j)
		{
			
			outFile << "\t\t(value_assigned " + numbers[i + 1] + " " + numbers[j + 1] + ")";
			if (i == 8 && j == 8)
				outFile << ")\n";
			else if (j == 8)
				outFile << "\n\n";
			else outFile << "\n";
		}
	}
	outFile << "\t)\n";
	outFile << ")\n";

	cout << "Parsing complete!" << endl;
}