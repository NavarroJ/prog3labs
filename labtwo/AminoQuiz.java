package labtwo;

import java.util.Random;

public class AminoQuiz {

	public static void main(String[] args) {
		
		Random random = new Random();
		
		String[] SHORT_NAMES = 
			{ "A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" };
		
		String[] FULL_NAMES = 
			{
			"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine",
			"glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine",
			"leucine",  "lysine", "methionine", 
			"phenylalanine", "proline", 
			"serine","threonine","tryptophan", 
			"tyrosine", "valine"};
		
		long end = System.currentTimeMillis() + 30000;

		int correct = 0;
		
		while (System.currentTimeMillis() < end) {
			int rand = random.nextInt(SHORT_NAMES.length - 1);
			System.out.println("Type the one letter code for: " + FULL_NAMES[rand]);
			String aString = System.console().readLine().toUpperCase();
			if (aString.equals(SHORT_NAMES[rand])) {
				System.out.println("Correct");
				correct += 1;
			} else if (aString.equals("QUIT")){
				System.out.println("Quitting...");
				break;
			} else {
				System.out.println("Incorrect: " + SHORT_NAMES[rand]);
				System.out.println("Ending.");
				break;
			
				}

		}
		
		System.out.println("Score: " + correct);
		
	}

}
