package labseven;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SlowGUI extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private final JButton startButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton submitButton = new JButton("Submit");
	private final JButton backButton = new JButton("Back");
	private JTextField digitEnter = new JTextField(5);
	private JTextField longBoiEnter = new JTextField(20);
	private JTextArea progDisplay = new JTextArea(200,30);
	private JTextArea valueDisplay = new JTextArea(200,30);
	private final JScrollPane scroller = new JScrollPane(valueDisplay);
	private JPanel cards;
	private volatile long count = 0;
	private volatile long endNum = 0;
	private volatile int digit = 0;
	private volatile boolean cancel = false;

	public SlowGUI(String title) {
		super(title);
		setLocationRelativeTo(null);
		setSize(1000,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());
		cards.add(startPanel(), "start");
		cards.add(progPanel(), "prog");
		cards.add(scorePanel(),"score");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(cards, BorderLayout.CENTER);
		setVisible(true);
	}
	
	private JPanel progPanel() {
		JPanel allPanels = new JPanel();
		JPanel sub1 = new JPanel();
		sub1.setLayout(new GridLayout(0,2));
		sub1.add(startButton);
		sub1.add(cancelButton);
		allPanels.setLayout(new BorderLayout());
		progDisplay.setEditable(false);
		allPanels.add(progDisplay, BorderLayout.CENTER);
		allPanels.add(sub1,BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancel = false;
				startButton.setEnabled(false);
				cancelButton.setEnabled(true);
				new Thread(new SlowCalc()).start();
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancel = true;
			}
		});
		

		
		return allPanels;
	}
	
	private JPanel scorePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		valueDisplay.setEditable(false);
		panel.add(scroller);
		panel.add(backButton);
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				progDisplay.setText("");
				valueDisplay.setText("");
				startButton.setEnabled(true);
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "start");
			}
			
		});
		return panel;

	}
	
	private JPanel startPanel() {
		JPanel panel = new JPanel();
		JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new FlowLayout());
		JLabel firstLab = new JLabel("Enter a number 0-9.");
		submitPanel.add(firstLab);
		submitPanel.add(digitEnter);
		
		submitPanel.setLayout(new FlowLayout());
		submitPanel.add(new JLabel("Enter a number."));
		submitPanel.add(longBoiEnter);
		
		panel.setLayout(new BorderLayout());
		JLabel progDescrip = new JLabel("<html>"+"With a large number as an input, this calculation finds all numbers between 0 and the inputted number that contains the digit of your choosing." + " The calculation won't start if the digit specified is not 0-9 or if you do not input numbers at all."+ "</html>");
		progDescrip.setFont(new Font("Monaco", Font.BOLD + Font.ITALIC, 20));
		panel.add(progDescrip,BorderLayout.NORTH);
		panel.add(submitPanel,BorderLayout.CENTER);
		panel.add(submitButton,BorderLayout.SOUTH);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					digit = Integer.parseInt(digitEnter.getText());
					endNum = Long.parseLong(longBoiEnter.getText());
					if (digit > 0 && digit < 9) {
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, "prog");
						valueDisplay.setText("");
					}
					
				}
				catch(Exception ex) {
					CardLayout cl = (CardLayout)(cards.getLayout());
					cl.show(cards, "start");
				}

			}
			
		});
		return panel;
	}
	
	private class SlowCalc implements Runnable {

		@Override
		public void run() {
			try {
				count = 0;
				valueDisplay.setText("");
				progDisplay.setText("");
				long i = 0;
				long startTime = System.currentTimeMillis();
				long endTime = 0;
				float calcTime = 0;
				long toPrint = 10000;
				while(cancel == false && i < endNum) {
					long j = i;
					while(j > 0) {
						if(j % 10 == digit) {
							valueDisplay.append(String.valueOf(i) + "\n" + "\n");
							count++;
							break;
						}
						j = j/10;
					}
					if (i == toPrint) {
						long midTime = System.currentTimeMillis();
						float midCalc = (midTime - startTime) / (float) 1000;
						progDisplay.setText(String.valueOf(count) + " found in " + String.valueOf(midCalc));
						toPrint += toPrint;

					}
					i++;
				}
				endTime = System.currentTimeMillis();
				calcTime = (endTime - startTime) / (float) 1000;
				valueDisplay.append("Total " + String.valueOf(digit) + " counts: " + String.valueOf(count)+ " from " + String.valueOf(endNum) + "\n" + "\n");
				valueDisplay.append("Time: " + String.valueOf(calcTime) + " seconds" + "\n");
			
			}
			catch(Exception ex) {
				valueDisplay.setText(ex.getMessage());
				ex.printStackTrace();
			}
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						CardLayout cl = (CardLayout)(cards.getLayout());
						cl.show(cards, "score");
					}
					
				});
			}
			catch(Exception ex) {
			}
		}
		
	}

	public static void main(String[] args) {
		new SlowGUI("Slow GUI");
	}

}
