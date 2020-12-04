package labseven;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
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
import javax.swing.Timer;

public class SlowGUI extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private final JButton startButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton submitButton = new JButton("Submit");
	private JTextField dataEnter = new JTextField(20);
	private JTextArea valueDisplay = new JTextArea(200,200);
	private final JScrollPane scroller = new JScrollPane(valueDisplay);
	private JPanel cards;
	private AtomicInteger startNum = new AtomicInteger(0);
	public volatile boolean cancel = false;

	public SlowGUI(String title) {
		super(title);
		setLocationRelativeTo(null);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());
		cards.add(startPanel(), "start");
		cards.add(numbersPanel(), "numbers");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(cards, BorderLayout.CENTER);
		setVisible(true);
	}
	
	private JPanel numbersPanel() {
		JPanel allPanels = new JPanel();
		JPanel sub1 = new JPanel();
		sub1.setLayout(new GridLayout(0,2));
		sub1.add(startButton);
		sub1.add(cancelButton);
		allPanels.setLayout(new BorderLayout());
		valueDisplay.setEditable(false);
//		allPanels.add(valueDisplay,BorderLayout.CENTER);
		allPanels.add(scroller, BorderLayout.CENTER);
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
				valueDisplay.append("You canceled the calculation.");
			}
			
		});
		
		return allPanels;
	}
	
	private JPanel startPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("Enter a number between 1-10"));
		panel.add(dataEnter);
		panel.add(submitButton);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(cards.getLayout());
				cl.show(cards, "numbers");
				if (Integer.valueOf(dataEnter.getText()) >= 0 && Integer.valueOf(dataEnter.getText()) < 10) {
					startNum.set(Integer.valueOf(dataEnter.getText()));
				} else {
					startNum.set(0);
				}
			}
			
		});
		return panel;
	}
	
	private class SlowCalc implements Runnable {

		@Override
		public void run() {
			try {
				while(cancel == false && startNum.get() < 100) {
					valueDisplay.append(String.valueOf(startNum) + "\n");
					startNum.incrementAndGet();
				}
			}
			catch(Exception ex) {
				valueDisplay.setText(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		new SlowGUI("Slow GUI");
	}

}
