package labfour;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FastaSequence {
	
	private final String header;
	private final String seq;
	
	public FastaSequence(String header, String seq) {
		this.header = header.substring(1);
		this.seq = seq;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public String getSeq() {
		return this.seq;
	}
	
	public float getGCRatio() {
		
		Map<Character, Integer> alleleCounts = new HashMap<Character, Integer>();
		
		for (int x = 0; x < this.seq.length(); x++) {
			Integer count = alleleCounts.get(seq.charAt(x));
			if (count == null) {
				count = 0;
			}
			count++;
			alleleCounts.put(this.seq.charAt(x), count);
			
		}
		if (alleleCounts.get('G') == null) {
			alleleCounts.put('G', 0);
		}
		if (alleleCounts.get('C') == null) {
			alleleCounts.put('C',0);
		}
		int gctotal = alleleCounts.get('G') + alleleCounts.get('C');
		float ratio = (float) gctotal / this.seq.length();
		return ratio;
		
	}
	
	public static void writeUnique(String pathIn, String pathOut) throws Exception {
		File fastaIn = new File(pathIn);
		File uniqueOut = new File(pathOut);
		
		BufferedReader reader = new BufferedReader(new FileReader(fastaIn));
		BufferedWriter writer = new BufferedWriter(new FileWriter(uniqueOut));
		
		Map<Integer,String> seqCounts = new TreeMap<Integer,String>(); 
		
		for (String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine()) {
			if (nextLine.startsWith(">") == false) {
				System.out.println(nextLine);
			}
		}
		reader.close();
		writer.flush(); writer.close();
	}
		
	public static List<FastaSequence> readFastaFile(String path) throws Exception {
		File fasta = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(fasta));
		
		List<String> seqList = new ArrayList<String>();
		List<String> headList = new ArrayList<String>();
		List<FastaSequence> fastaList = new ArrayList<FastaSequence>();
		StringBuffer s = new StringBuffer();

		for (String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine()) {
			
			if (nextLine.startsWith(">")) {
				headList.add(nextLine);
				if (s.length() > 0) {
					seqList.add(s.toString());
					s.delete(0,s.length());
				}
				continue;	
			} else {
				s.append(nextLine);
			}
		}
		seqList.add(s.toString());
		
		for (int x = 0; x < seqList.size(); x++) {
			FastaSequence f = new FastaSequence(headList.get(x),seqList.get(x));
			fastaList.add(f);
		}
		reader.close();
		return fastaList;
	}
	

}
