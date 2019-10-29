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
	private JLabel[] matchPlayers;
	private JButton[][] waitingPlayers;
	private JButton done;
	// Display parameters
	private Dimension rulesDimension;
	private Dimension matchDimension;
	private Dimension waitingDimension;
	private Dimension windowDimension;
	// Parameters
	private final int maxPlayers = 4;
	private final int boxHeight = 80;
	private final int boxWidth = 200;
	private final int maxWaiting = 10;

    public PBSS() {
		// Initializations
		rule = new JLabel("<html>Rules: <br> 1. Your name will appear in the waiting list only once<br><html>" 
						+ "<html>2. You needs to click the \"Done\" button after you are done playing<br><html>" 
						+ "<html>3. You may only play 1 set (21 points to win with 2 points lead, 30 points to win) if there is someone waiting after you!<br><html>" 
						+ "<html>4. You may sign up at 2 courts at the same time, unless you have the timing on each court right<br><html>"
						, SwingConstants.LEFT);
		rule.setFont(new Font("Serif", Font.BOLD, 15));
		rule.setForeground(Color.red);
		rule.setBackground(Color.GRAY);
		matchPlayers = new JLabel[maxPlayers];
		waitingPlayers = new JButton[maxWaiting][maxPlayers];
		done = new JButton("Done");
		done.setFont(new Font("Sans Serif", Font.BOLD, 50));
		done.setForeground(Color.red);
		// Dimensions
		rulesDimension = new Dimension((maxPlayers+1)*boxWidth, boxHeight+30);
		matchDimension = new Dimension((maxPlayers+1)*boxWidth, boxHeight);
		waitingDimension = new Dimension(maxPlayers*boxWidth, maxWaiting*boxHeight);
		windowDimension = new Dimension((maxPlayers+1)*boxWidth, (maxWaiting+2)*boxHeight+50);

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
			matchPlayers[i] = new JLabel("Player", SwingConstants.CENTER);
			matchPlayers[i].setFont(new Font("Serif", Font.BOLD, 40));
			matchPlayers[i].setForeground(Color.ORANGE);
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
				waitingPlayers[x][y] = new JButton("Click to Add");
				waitingPlayers[x][y].setFont(new Font("Sans Serif", Font.PLAIN, 20));
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
	private void matchDone() {
		for (int i = 0; i < matchPlayers.length; i++) {
			if (waitingPlayers[0][i].getText().equals("Click to Add")) {
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
		for (int i = 0; i < waitingPlayers[9].length; i++) {
			// Reset last row
			waitingPlayers[9][i].setText("Click to Add");
		}
	}
	private class Done implements ActionListener {
		public void actionPerformed (ActionEvent p) {
			matchDone();
		}
	}

	// Button Add
	private void addPlayer(ActionEvent p) {
		for (int x = 0; x < waitingPlayers.length; x++) {
			for (int y = 0; y < waitingPlayers[x].length; y++) {
				if (p.getSource() == waitingPlayers[x][y] && waitingPlayers[x][y].getText() == "Click to Add") {
					String temp = JOptionPane.showInputDialog(null, "Enter your name");
					if (temp.equals("")) {
						// If cancel is clicked
						temp = "Click to Add";
					}
					waitingPlayers[x][y].setText(temp);
				}
			}
		}
	}
	private class AddPlayers implements ActionListener {
		public void actionPerformed (ActionEvent p) {
			addPlayer(p);
		}
	}

} // End of File