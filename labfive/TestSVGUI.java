package labfive;

import javax.swing.JFrame;

public class TestSVGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public TestSVGUI(String title) {
		super(title);
		setLocationRelativeTo(null);
		setSize(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new TestSVGUI("Test 1");
	}

}
