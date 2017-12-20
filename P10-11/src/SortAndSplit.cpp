/*
 * SimpleMetrics.cpp
 *
 *  Created on: 6 déc. 2017
 *      Authors: ZEGHLACHE Adel & ROUILLARD Charles
 */

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <set>
#include <algorithm>

using namespace std;

vector<string> split(string str){
	istringstream ss(str);
	string token;

	vector<string> playerInfoVector;
	while(std::getline(ss, token, ' ')) {
		playerInfoVector.push_back(token);
	}

	return playerInfoVector;
}

/*Our own method to use de sort function, which will sort a vector of vector of int by the first elements of each vector*/
bool sortFinal(vector<string> i, vector<string> j){return atoi(i[0].c_str()) < atoi(j[0].c_str());}

int main(int argc, char **argv){
	if(argc != 2){
		cout << "Usage: " << argv[0] << " <filename>" << endl;
		return -1;
	}

	string filename(argv[1]);

	ifstream readFile(filename);
	ofstream writeFile("data/sort_split.txt");

	vector<vector<string>> lines,final;

	if(readFile){
		string line,newC(""),newS("");

		/*put every vector lines on a vector*/
		while(getline(readFile,line)){
			vector<string> elts(split(line));
			lines.push_back(elts);
		}

		for(unsigned int i = 0;i<lines.size();i++){

			/*Here we generate all the lines for the creation of a link*/
			vector<string> tempC;
			tempC.push_back(lines[i][2]);
			tempC.push_back(lines[i][0]);
			tempC.push_back(lines[i][1]);
			tempC.push_back("C");

			/*Here for the deletion of a link*/
			vector<string> tempS;
			int newTe = atoi(lines[i][3].c_str())+1;
			tempS.push_back(to_string(newTe));
			tempS.push_back(lines[i][0]);
			tempS.push_back(lines[i][1]);
			tempS.push_back("S");

			/*Add every line to the final vector*/
			final.push_back(tempC);
			final.push_back(tempS);
		}

		/*then sort by time the new vector using our function*/
		sort(final.begin(),final.end(),sortFinal);

		/*then write the vector in our file*/
		for(unsigned int i = 0;i<final.size();i++){
			for(unsigned int j = 0;j<final[i].size();j++){
				writeFile << final[i][j] << ' ';
			}
			writeFile << "\n";
		}
		cout << "File data/sort_split.txt created." << endl;
 	}
 	else{
 		cout << "Error while reading the file" << endl;
 	}

 	readFile.close();
 	writeFile.close();

	return EXIT_SUCCESS;
}
