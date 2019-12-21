/*
PBSS.java
By SaintKiri

Side-project: 
Made to substitute Pitt's Badminton sign-up sheet

Driver for this file is required to run
*/

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;

public class PBSS extends JPanel {

	// Panels
	private JPanel rules;
	private JPanel match;
	private JPanel waiting;
	// Displays
	private JLabel rule;
	private JButton[] matchPlayers;
	private JButton[][] waitingPlayers;
	private JButton done;
	// Display parameters
	private Dimension rulesDimension;
	private Dimension matchDimension;
	private Dimension waitingDimension;
	private Dimension windowDimension;
	private final String CLICKTOADD = "Click to Add";
	private Font ADDFONT;
	private Font PLAYERFONT;
	// Parameters
	private final int maxPlayers = 4;
	private final int boxHeight = 80;
	private final int boxWidth = 200;
	private final int maxWaiting = 7;

    public PBSS() {
		// Initializations
		rule = new JLabel("<html>Rules: <br><html>" 
						+ "<html> You needs to click the \"Done\" button after you are done playing <br><html>" 
						+ "<html> You may only play 1 set if there is someone waiting after you! <br><html>" 
						+ "<html> You may not sign up at 2 courts at the same time, unless you have the timing on each court right <br><html>");
		rule.setFont(new Font("Serif", Font.BOLD, 15));
		rule.setForeground(Color.red);
		matchPlayers = new JButton[maxPlayers];
		waitingPlayers = new JButton[maxWaiting][maxPlayers];
		done = new JButton("Done");
		done.setFont(new Font("Sans Serif", Font.BOLD, 50));
		done.setForeground(Color.red);
		// Dimensions
		rulesDimension = new Dimension((maxPlayers+1)*boxWidth, boxHeight+20);
		matchDimension = new Dimension((maxPlayers+1)*boxWidth, boxHeight);
		waitingDimension = new Dimension(maxPlayers*boxWidth, maxWaiting*boxHeight);
		windowDimension = new Dimension((maxPlayers+1)*boxWidth, (maxWaiting+2)*boxHeight+40);
		// Fonts
		ADDFONT = new Font("Sans Serif", Font.PLAIN, 30);
		PLAYERFONT = new Font("Sans Serif", Font.BOLD, 35);

		// Rules Panel
		rules = new JPanel();
		// Add Stuff
		rules.add(rule);
		// Properties
		rules.setBackground(Color.white);
		rules.setPreferredSize(rulesDimension);

		// Match Panel
		match = new JPanel();
		// Add Stuff
		for (int i = 0; i < matchPlayers.length; i++) {
			matchPlayers[i] = new JButton("Player");
			matchPlayers[i].setFont(PLAYERFONT);
			matchPlayers[i].setForeground(Color.ORANGE);
			matchPlayers[i].setBackground(Color.WHITE);
			match.add(matchPlayers[i]);
		}
		done.addActionListener(new Done());
		match.add(done);
		// Properties
		match.setBackground(Color.BLACK);
		match.setLayout(new GridLayout(1,maxPlayers));
		match.setPreferredSize(matchDimension);

		// Line Panel
		waiting = new JPanel();
		// Add Stuff
		for (int x = 0; x < waitingPlayers.length; x++) {
			for (int y = 0; y < waitingPlayers[x].length; y++) {
				waitingPlayers[x][y] = new JButton(CLICKTOADD);
				waitingPlayers[x][y].setFont(ADDFONT);
				waitingPlayers[x][y].setBackground(Color.WHITE);
				waitingPlayers[x][y].addActionListener(new AddPlayers());
				waiting.add(waitingPlayers[x][y]);
			}
		}
		// Properties
		waiting.setLayout(new GridLayout(maxWaiting,maxPlayers));
		waiting.setPreferredSize(waitingDimension);

		// Add everything
		add(rules);
		add(match);
		add(waiting);
		// Properties
		setPreferredSize(windowDimension);
		setBackground(Color.white);
	}
	
	// Button Done
	private class Done implements ActionListener {
		public void actionPerformed (ActionEvent p) {
			for (int i = 0; i < matchPlayers.length; i++) {
				if (waitingPlayers[0][i].getText().equals(CLICKTOADD)) {
					// Catch empty spots
					matchPlayers[i].setText("Empty");
				} else {
					// Move the first waiting pairs to the playing pairs
					matchPlayers[i].setText(waitingPlayers[0][i].getText());
				}
			}
			for (int i = 0; i < waitingPlayers.length-1; i++) {
				for (int n = 0; n < waitingPlayers[i].length; n++) {
					// Move everyone 1 spot forward
					waitingPlayers[i][n].setText(waitingPlayers[i+1][n].getText());
				}
			}
			for (int i = 0; i < waitingPlayers[maxWaiting-1].length; i++) {
				waitingPlayers[maxWaiting-1][i].setText(CLICKTOADD); // Reset last row
			}
		}
	}

	// Button Add
	private class AddPlayers implements ActionListener {
		public void actionPerformed (ActionEvent p) {
			for (int x = 0; x < waitingPlayers.length; x++) {
				for (int y = 0; y < waitingPlayers[x].length; y++) {
					if (p.getSource() == waitingPlayers[x][y] && waitingPlayers[x][y].getText() == CLICKTOADD) {
						String temp = JOptionPane.showInputDialog(null
																, "Enter your name"
																, "Name"
																, JOptionPane.INFORMATION_MESSAGE);
						if (temp == null)
							temp = CLICKTOADD;
						else 
							temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
						if (checkRepeat(temp) && temp != CLICKTOADD)
							JOptionPane.showMessageDialog(null
														, "You cannot enter your name twice!"
														, "Input error"
														, JOptionPane.ERROR_MESSAGE);
						else 
							waitingPlayers[x][y].setText(temp);
						break; // No need to run through entire list after adding name
					} else if (p.getSource() == waitingPlayers[x][y] && waitingPlayers[x][y].getText() != CLICKTOADD) {
						waitingPlayers[x][y].setText(CLICKTOADD);
						break; // No need to run through entire list after adding name
					}
				}
			}
		}
	}
	private boolean checkRepeat(String name) { // Helper method
		for (int x = 0; x < waitingPlayers.length; x++) {
			for (int y = 0; y < waitingPlayers[x].length; y++) {
				if (waitingPlayers[x][y].getText().equals(name))
					return true;
			}
		}
		return false;
	}

} // End of File