package com.hoangvu1203.similarity.preprocess;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TextPreprocessor {
	private static Set<String> stopwords = new HashSet<>();
	static {
		try (InputStream in = SimilaritySearch.class.getResourceAsStream("/stopwords.txt");
			 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stopwords.add(line.trim().toLowerCase());
			}
		} catch (Exception e) {
			System.err.println("Could not load stopwords: " + e.getMessage());
		}
	}

	public static String clean(String line) {
		// Strip Gutenberg headers is complex — basic placeholder
		String cleaned = line.toLowerCase().replaceAll("[^a-z\\s]", " ");
		StringBuilder sb = new StringBuilder();
		for (String word : cleaned.split("\\s+")) {
			if (!stopwords.contains(word) && word.length() > 2) {
				sb.append(word).append(" ");
			}
		}
		return sb.toString().trim();
	}
}
