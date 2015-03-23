package cpsc320;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
	static String[] tokens;
	static int[] OPT;
	static int optimalLineLength = 10;
	static int numOfWords;
	static int[][] slack;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(args[0]);
		FileReader in = new FileReader(file);
		char[] buffer = new char[500];
		while (in.read(buffer) != -1)
			;
		in.close();
		String words = String.copyValueOf(buffer);
		String delims = "[ ]+";
		tokens = words.split(delims);
		numOfWords= tokens.length;
		OPT = new int [numOfWords];
		slack = new int [numOfWords][numOfWords];
		findSlack();
		int x = FindOptRecursive(numOfWords-1);
		System.out.println(x);

	}

	public static int FindOptRecursive(int n) {
		if (n == 0) {
			return 0;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < i; j++) {
				OPT[j] = (slack[i][j] + FindOptRecursive( j - 1));
			}
		}
		return OPT[n];
	}
	
	
	public static int Memoization(int n) {
		OPT[0] = 0;
		for (int i=0; i < n; i++) {
			for (int j = 0; j < i; j++ ) {
				OPT[i] = slack[i][j]+ OPT[j -1];
			}
		}
		return OPT[n];
	}
	
	public static void findSlack() {
		int total;
		int slackPerLine;
		for (int i = 0; i < slack.length ; i++) {
			total = 0;
			for (int j = i; j <slack[i].length ; j++) {
				total += tokens[j].length();
				slackPerLine = optimalLineLength - total;
				if (slackPerLine < 0) {
					slack[i][j] = 10000; // invalidate it
				} else {
					slack[i][j] =slackPerLine;// * slackPerLine;
					
				}
			}
		}
	}

}
