package util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;

public class DatasetLoading {
	public static void main(String[]args) {
		try{
			Connection conn=DBConnection.getConnection();
			String sql="INSERT INTO chatbot_rules (course,subject,category,keywords, response) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt=conn.prepareStatement(sql);
			String file="merged.csv";
			CSVReader reader=new CSVReader(new FileReader(file));
			String[] line;
			reader.readNext();
			while((line=reader.readNext())!=null) {
				stmt.setString(1,line[0].trim());
				stmt.setString(2,line[1].trim());
				stmt.setString(3, line[2].trim());
				String keywords=generateKeywords(line[3].trim());
				stmt.setString(4, keywords);
				stmt.setString(5, line[4].trim());
				stmt.addBatch();
			}
			stmt.executeBatch();
			System.out.println("Dataset  successfully loaded in MySQL!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static String generateKeywords(String text) {
		if (text == null || text.isEmpty()) return "";
		Set<String> stopWords = new HashSet<>(Arrays.asList(
			    "what", "is", "are", "the", "define", "explain", "how", "to", "of", "in", 
			    "an", "a", "can", "you", "tell", "me", "about", "briefly", "discuss", 
			    "illustrate", "with", "examples", "diagram", "difference", "between"
			));

		return Arrays.stream(text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]","").split("\\s+")).filter(word->!stopWords.contains(word))
				.filter(word -> word.length() > 1).distinct().collect(Collectors.joining(", "));
	}
}
