/*
	PDDL Parser
	Created by Jennings Fairchild

	Parses txt files into a pddl problem file.
	Sudoku board txt files should be in the following format:

	....
	12..
	3...
	...1

	With '.' acting as blank cells.

	Compile: g++ -std=c++11 pddl_parser.cpp -o parser
	Run: ./parser
*/

#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <cstdio>
#include <cstdlib>

using namespace std;

int main()
{
	cout << "*----------------------------------------*" << endl;
	cout << "\tRunning Sudoku PDDL Parser\n" << endl;
	cout << "Can work with sudoku puzzles up to 30x30." << endl;
	cout << "Uses only 1-9 and then letters starting" << endl;
	cout << "at 'A' and going to a maximum of 'U'." << endl;
	cout << "*----------------------------------------*" << endl;
	// Ask for problem number for pddl problem file
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

	// Checks if input file is opened
	cout << "\nOpening input file...";
	ifstream fileIn;
	fileIn.open(inFileName);
	if (!fileIn.is_open()) {
		cout << "Failed." << endl;
		cout << "Error: Unable to open file" << endl;
		cout << "Exiting program now." << endl;
		exit(EXIT_FAILURE);
	}
	else cout << "Success!" << endl;

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

	// Enumerates integers to corresponding strings
	map<int, string> numbers;
	numbers[0] = "."; numbers[1] = "one"; numbers[2] = "two"; numbers[3] = "three";
	numbers[4] = "four"; numbers[5] = "five"; numbers[6] = "six"; numbers[7] = "seven";
	numbers[8] = "eight"; numbers[9] = "nine"; numbers[10] = "ten"; numbers[11] = "eleven";
	numbers[12] = "twelve"; numbers[13] = "thirteen"; numbers[14] = "fourteen";
	numbers[15] = "fifteen"; numbers[16] = "sixteen"; numbers[17] = "seventeen";
	numbers[18] = "eighteen"; numbers[19] = "nineteen"; numbers[20] = "twenty";
	numbers[21] = "twenty-one"; numbers[22] = "twenty-two"; numbers[23] = "twenty-three";
	numbers[24] = "twenty-four"; numbers[25] = "twenty-five"; numbers[26] = "twenty-six";
	numbers[27] = "twenty-seven"; numbers[28] = "twenty-eight"; numbers[29] = "twenty-nine";
	numbers[30] = "thirty";
	
	// Creating .pddl file
	ofstream outFile;
	string outFileName = "sudoku_" + to_string(probNum) + ".pddl";
	outFile.open(outFileName);

	// Checks if the boardSize actually matches the input file
	// If it doesn't, delete file that was created
	string testLine;
	getline(fileIn, testLine);
	// cout << "boardSize: " << boardSize << endl;
	// cout << "testLine.size(): " << testLine.size() << endl;
	if (boardSize != testLine.size()) {
		cout << "Error: Inputted board size does not match input file board." << endl;
		cout << "Deleting file that was created...";
		int removeMarker = remove(outFileName.c_str());
		if (removeMarker != 0) {
			cout << "Failed." << endl;
			cout << "Error: Problem with deleting file.";
			cout << "Exiting program now." << endl;
			exit(EXIT_FAILURE);
		}
		else cout << "Success!" << endl;
		cout << "Exiting program now." << endl;
		exit(EXIT_FAILURE);
	}

	// Start writing to .pddl file
	outFile << "(define\n";
	outFile << "\t(problem sudoku_" << to_string(probNum) << ")\n";
	outFile << "\t(:domain sudoku)\n";
	outFile << "\t(:init\n";

	for (int i = 0; i < boardSize; ++i) {
		if (i < 9)
			outFile << "\t\t(= (value_of " << numbers[i + 1] << ") " << i + 1 << ")\n";
		else if (i >= 9)
			outFile << "\t\t(= (value_of " << numbers[i + 1] << ") " << (char)(i + 56) << ")\n";
	}
	outFile << "\n";

	// Produces cell_value clauses
	string line;
	int rowNum = 0;
	fileIn.close();
	fileIn.open(inFileName);
	while (fileIn.good()) {
		while (!fileIn.eof()) {
			getline(fileIn, line);
			++rowNum;
			for (int i = 0; i < line.size(); ++i) {
				outFile << "\t\t(= (cell_value " << numbers[rowNum] << " " << numbers[i + 1] << ") ";
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
					outFile << "\t\t(value_assigned " << numbers[rowNum] << " " << numbers[i + 1] << ")\n";
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
			
			outFile << "\t\t(value_assigned " << numbers[i + 1] << " " << numbers[j + 1] << ")";
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