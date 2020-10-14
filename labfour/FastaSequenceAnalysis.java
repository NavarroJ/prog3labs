package labfour;

import java.util.List;

public class FastaSequenceAnalysis {

	public static void main(String[] args) throws Exception {
		String in1 = "./test_files/test.fasta";
		String out1 = "./test_files/unique_test.fasta";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(in1);
		
		for (FastaSequence f : fastaList) {
			System.out.println(f.getGCRatio());
			System.out.println(f.getSeq().length());
			System.out.println(f.getHeader());
			
		}
		FastaSequence.writeUnique(in1, out1);
	}

}
