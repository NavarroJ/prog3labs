package labfive;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TestSVGUI extends JFrame {
	
	private JPanel cards;
	private static final long serialVersionUID = 1L;
	private final String IGV = "IGV Displayer";
	private final String CC = "Computer Coverage";
	private final String CST = "Color Sample Table";
	
	public TestSVGUI(String title) {
		super(title);
		setLocationRelativeTo(null);
		setSize(1000,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cards = new JPanel(new CardLayout());
		cards.add(igvDisplayPanel(),IGV);
		cards.add(compCovPanel(),CC);
		cards.add(comparePanel(),CST);
		getContentPane().add(toolsPanel(), BorderLayout.NORTH);
		getContentPane().add(cards, BorderLayout.CENTER);
		setVisible(true);
	}

    
	private JPanel toolsPanel() {
		JPanel panel = new JPanel();
		String[] toolList = {IGV, CC, CST};
		JComboBox<String> toolCombo = new JComboBox<String>(toolList);
		JLabel toolText = new JLabel("Tools:");
		ItemListener toolItemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
			    CardLayout cl = (CardLayout)(cards.getLayout());
			    cl.show(cards, (String)evt.getItem());
			}
		};
		toolCombo.setEditable(false);
		toolCombo.addItemListener(toolItemListener);
		panel.setLayout(new FlowLayout());
		panel.add(toolText);
		panel.add(toolCombo);
		return panel;
	}
	
	private JPanel igvDisplayPanel() {
		JPanel panel = new JPanel();
		JLabel sampleOrder = new JLabel("Sample Order: ");
		panel.add(sampleOrder);
		panel.add(new JTextField(20));

		return panel;
	}
	
	private JPanel compCovPanel() {
		JPanel panel = new JPanel();
		panel.add(new JTextField("Compute Coverage tools to be displayed here."));
		return panel;
	}
	
	private JPanel comparePanel() {
		String[] colors = {"Pink", "Red", "Blue", "Green"};
		JPanel panel = new JPanel();
		JLabel colorLabel = new JLabel("Highlight Color");
		JComboBox<String> colorCombo = new JComboBox<String>(colors);
		panel.setLayout(new FlowLayout());
		panel.add(colorLabel);
		panel.add(colorCombo);
		return panel;
	}


	
	public static void main(String[] args) {
		new TestSVGUI("SV Tools");
	}

}
