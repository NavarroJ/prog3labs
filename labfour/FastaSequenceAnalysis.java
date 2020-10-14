package labfour;

import java.util.List;

public class FastaSequenceAnalysis {

	public static void main(String[] args) throws Exception {
		String in1 = "./test_files/test.fasta";
		String out1 = "./test_files/unique_test.fasta";
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(in1);
		
		for (FastaSequence f : fastaList) {
			System.out.println(f.getHeader());
			System.out.println(f.getSequence());
			System.out.println(f.getGCRatio());
			
		}
		
		FastaSequence.writeUnique(in1, out1);
	}

}
