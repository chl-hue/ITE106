import java.io.*;
import java.util.*;

public class textanalyzer {
	public static void main(String[] args) {
		try {
			File file = new File ("chelo.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder text = new StringBuilder();
			String line;
			int lineNumber = 1;
			
			while ((line = reader.readLine()) != null) {
				System.out.println("Line " + lineNumber + ": " + line);
				text.append(line).append(" ");
				lineNumber++;
			}	
			reader.close();
			
			String content = text.toString();
			
			String [] words = content.split("\\s+");
			int wordCount = words.length;
			
			String [] sentences = content.split("[.!?]");
			int sentenceCount = sentences.length;
			
			String upperCaseContent = content.toUpperCase(); 
			
			String longestWord = "";
			for (String word : words) {
				word = word.replaceAll("[^a-zA-Z]", "");
				if (word.length() > longestWord.length()) {
					longestWord = word;
				}
			}
			HashMap<String, Integer> wordFrequency = new HashMap<>();
			for (String word : words) {
				word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
				wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
			}
			
			System.out.println("Word Count: " + wordCount);
			System.out.println("Sentence Count: " + sentenceCount);
			System.out.println("Text in Uppercase:\n" + upperCaseContent);
			System.out.println("Longest Word: " + longestWord);
			System.out.println("Word Frequency: " + wordFrequency);
			
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
}		
