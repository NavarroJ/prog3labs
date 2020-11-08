package labsix;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class AAQuiz extends JFrame {

	private static final long serialVersionUID = 1L;
	private int countdown = 30;
	private int correct = 0;
	private int incorrect = 0; 
	private boolean cancel = false;
	private JPanel cards;
	private JButton startButton = new JButton("Start");
	private JButton cancelButton = new JButton("Cancel");
	private JButton submitButton = new JButton("Submit");
	private JLabel timeLabel = new JLabel();
	private JLabel aminoLabel = new JLabel();
	private JLabel corLabel = new JLabel();
	private JLabel incorLabel = new JLabel();
	private JTextField answer = new JTextField(20);
	private Timer timer;		
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

	public AAQuiz(String title) {
		super(title);
		setLocationRelativeTo(null);
		setSize(1000,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				countdown--;
				timeLabel.setText("Time Remaining: " + String.valueOf(countdown));
				if(countdown <= 0) {
					timer.stop();
					System.out.println(correct);
				    CardLayout cl = (CardLayout)(cards.getLayout());
				    cl.show(cards, "start");

				}
			}
			
		});
		cards = new JPanel(new CardLayout());
		cards.add(startPanel(), "start");
		cards.add(quizPanel(), "quiz");
		cards.add(scorePanel(), "score");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(cards, BorderLayout.CENTER);
		setVisible(true);
	}
	
	private JPanel quizPanel() {
		
		JPanel panelMain = new JPanel();
		JPanel sub1 = new JPanel();
		JPanel sub2 = new JPanel();
		panelMain.setLayout(new BorderLayout());
		sub1.setLayout(new FlowLayout());
		sub1.add(aminoLabel);
		sub1.add(answer);
		sub1.add(submitButton);
		sub1.add(corLabel);
		sub1.add(incorLabel);
		sub2.setLayout(new FlowLayout());
		sub2.add(cancelButton);
		panelMain.add(timeLabel, BorderLayout.NORTH);
		panelMain.add(sub1,BorderLayout.CENTER);
		panelMain.add(sub2, BorderLayout.SOUTH);
		int randOuter = random.nextInt(SHORT_NAMES.length - 1);
		aminoLabel.setText(FULL_NAMES[randOuter]);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new submitAction()).start();

		
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				countdown = 30;
				cancel = true;
			    CardLayout cl = (CardLayout)(cards.getLayout());
			    cl.show(cards, "start");
				

			}
			
		});
		
	
		return panelMain;
	}
	
	private JPanel startPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Press start to begin the quiz."), BorderLayout.CENTER);
		panel.add(startButton, BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
			    CardLayout cl = (CardLayout)(cards.getLayout());
			    cl.show(cards, "quiz");

				
			}
			
		});
		

		return panel;
		
	}
	
	private JPanel scorePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("You got: " + String.valueOf(correct) + " correct and " + String.valueOf(incorrect) + " incorrect."));
		return panel;
	}
	
	private class submitAction implements Runnable {

		@Override
		public void run() {
			try {
				while(timer.isRunning() || ! cancel) {
					String s = answer.getText();
					String q = SHORT_NAMES[Arrays.asList(FULL_NAMES).indexOf(aminoLabel.getText())];

					if (q.equals(s.toUpperCase())) {
						correct++;
						corLabel.setText("Correct: " + String.valueOf(correct));
						answer.setText("");
					} else {
						incorrect++;
						incorLabel.setText("Incorrect: " + String.valueOf(incorrect));
						answer.setText("");
						}
					int randInner = random.nextInt(SHORT_NAMES.length - 1);
					aminoLabel.setText(FULL_NAMES[randInner]);
					Thread.sleep(40000);
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}


		}
		
	}
	

	public static void main(String[] args) {
		new AAQuiz("Amino Acid Quiz");
	}

}

