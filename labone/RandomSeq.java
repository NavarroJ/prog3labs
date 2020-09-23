package labone;

import java.util.Random;

/**
 * Generates 10000 3mers at both same and different AF
 * 
 * Used both nextInt and nextFloat for practice
 * 
 * Future practice could include making methods to perform this after learning
 * 
 * Started EC but did not finish generating p-value
 */

public class RandomSeq {

	public static void main(String[] args) {
		int n = 10000;
		Random random = new Random();
		float countOne = 0;
		float expFreqOne = (float) (0.25 * 0.25 * 0.25);
		for (int x = 0 ; x < n; x++) {
			String s1 = "";
			for (int y = 0 ; y < 3; y++) {
				int randAll = random.nextInt(101);
				if (randAll <= 25) {
					s1 += "A";
				} else if (randAll > 25 && randAll <= 50) {
					s1 += "T";
				} else if (randAll > 50 && randAll <= 75) {
					s1 += "C";
				} else {
					s1 += "G";
				}
			}
			if (s1.equals("AAA")) {
				countOne += 1;
				
			}
		}
		float obsFreqOne = countOne / n;
		System.out.println("Expected freq part 1: " + expFreqOne);
		System.out.println("Observed freq part 1: " + obsFreqOne);
		
		
		int chiSq = 0;
		
		
		// Similar to above but looped to generate chi sq
		
		for (int a = 0; a < n; a++) {
			float countTwo = 0;
			float expFreqTwo = (float) (0.12 * 0.12 * 0.12);
			float expNumTwo = expFreqTwo * n;
			for (int x = 0 ; x < n; x++) {
				String s2 = "";
				for (int y = 0 ; y < 3; y++) {
					float randAllFloat = random.nextFloat();
					if (randAllFloat <= .12) {
						s2 += "A";
					} else if (randAllFloat > .12 && randAllFloat <= .23) {
						s2 += "T";
					} else if (randAllFloat > .23 && randAllFloat <= .61) {
						s2 += "C";
					} else {
						s2 += "G";
					}
				}
				if (s2.equals("AAA")) {
					countTwo += 1;
					
				}
			}
			float obsFreqTwo = countTwo / n;
			if (a == n-1) {
				System.out.println("Expected freq part 2: " + expFreqTwo);
				System.out.println("Observed freq part 2: " + obsFreqTwo);
			}

			chiSq += Math.pow(countTwo - expNumTwo, 2) / expNumTwo;			
			
		
		}
		System.out.println("Chi sq equals: " + chiSq);
	}

}
