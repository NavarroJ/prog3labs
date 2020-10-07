package labthree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FastaParse {
	
	public static void alleleCounts(String pathIn, String pathOut) throws Exception {

		File fasta = new File(pathIn);
		File fastaCounts = new File(pathOut);
		
		BufferedReader reader = new BufferedReader(new FileReader(fasta));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fastaCounts));

		
		List<String> seqList = new ArrayList<String>();
		List<String> headList = new ArrayList<String>();
		
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
		
		String header = "Seq_ID\tA_Count\tT_Count\tC_Count\tG_Count\n";
		writer.write(header);
		for (int x = 0; x < seqList.size(); x++) {
			int numA = 0;
			int numT = 0;
			int numC = 0;
			int numG = 0;
			for (int y = 0; y < seqList.get(x).length(); y++) {
				char al = seqList.get(x).charAt(y);
				if (al == 'A') {
					numA++;
				} else if (al == 'T') {
					numT++;
				} else if (al == 'C') {
					numC++;
				} else if (al == 'G') {
					numG++;
				} else {
					continue;
				}
			}
			writer.write(headList.get(x) + "\t" + numA + "\t" + numT + "\t" + numC + "\t" + numG + "\n");
		}
		
		reader.close();
		writer.flush(); writer.close();
	}
	
	public static void main(String[] args) throws Exception {
		String in1 = "./test_files/GCF_003019965.1_ASM301996v1_genomic.fna";
		String out1 = "./test_files/burkCounts.txt";
		String in2 = "./test_files/test.fasta";
		String out2 = "./test_files/testCounts.txt";
		alleleCounts(in1,out1);
		alleleCounts(in2,out2);
		System.out.println("Done");
		

		}
	}


