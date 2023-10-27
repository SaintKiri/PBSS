/*
PBSS.java
by SaintKiri

Made to substitute Pitt's Badminton sign-up sheet

Driver for this file is required to run
*/

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;

public class PBSS extends JPanel {
	private static final long serialVersionUID = 1L;

	// Panels
	private JPanel match;
	private JPanel waiting;
	// Displays
	private JButton[] matchPlayers;
	private JButton[][] waitingPlayers;
	private JButton done;
	// Display parameters
	private Dimension matchDimension;
	private Dimension waitingDimension;
	private Dimension windowDimension;
	private final String ADD = "Add";
	private final String EMPTY = "Empty";
	private final Font ADDFONT;
	private Font PLAYERFONT;
	// Display parameters (numbers)
	private final int maxPlayers = 4;
	private final int boxHeight = 60;
	private final int boxWidth = 200;
	private final int maxWaiting = 8;

	public PBSS() {
		// Initializations
		matchPlayers = new JButton[maxPlayers];
		waitingPlayers = new JButton[maxWaiting][maxPlayers];
		done = new JButton("DONE");
		done.setFont(new Font("JetBrains Mono", Font.BOLD, 50));
		done.setForeground(Color.RED);
		done.setBackground(Color.WHITE);
		// Dimensions
		matchDimension = new Dimension(maxPlayers * boxWidth + 30, boxHeight);
		waitingDimension = new Dimension(maxPlayers * boxWidth, maxWaiting * boxHeight);
		windowDimension = new Dimension(maxPlayers * boxWidth + 30, (maxWaiting + 1) * boxHeight + 15);
		// Fonts
		ADDFONT = new Font("Roboto", Font.PLAIN, 30);
		PLAYERFONT = new Font("Roboto", Font.BOLD, 35);

		// Match Panel
		match = new JPanel();
		// Add Stuff
		for (int i = 0; i < matchPlayers.length; i++) {
			matchPlayers[i] = new JButton(EMPTY);
			matchPlayers[i].setFont(PLAYERFONT);
			matchPlayers[i].setForeground(Color.GREEN);
			matchPlayers[i].setBackground(Color.WHITE);
			match.add(matchPlayers[i]);
		}
		done.addActionListener(new Done());
		match.add(done);
		// Properties
		match.setLayout(new GridLayout(1, maxPlayers));
		match.setPreferredSize(matchDimension);

		// Line Panel
		waiting = new JPanel();
		// Add Stuff
		for (int x = 0; x < waitingPlayers.length; x++) {
			for (int y = 0; y < waitingPlayers[x].length; y++) {
				waitingPlayers[x][y] = new JButton(ADD);
				waitingPlayers[x][y].setFont(ADDFONT);
				waitingPlayers[x][y].setForeground(Color.GREEN);
				waitingPlayers[x][y].setBackground(Color.WHITE);
				waitingPlayers[x][y].addActionListener(new AddPlayers());
				waiting.add(waitingPlayers[x][y]);
			}
		}
		// Properties
		waiting.setLayout(new GridLayout(maxWaiting, maxPlayers));
		waiting.setPreferredSize(waitingDimension);

		// Add everything
		add(match);
		add(waiting);
		// Properties
		setPreferredSize(windowDimension);
		setBackground(Color.WHITE);
	}

	// Button Done
	private class Done implements ActionListener {
		public void actionPerformed(ActionEvent p) {
			for (int i = 0; i < matchPlayers.length; i++) {
				if (waitingPlayers[0][i].getText().equals(ADD)) {
					// Catch empty spots
					matchPlayers[i].setText("Empty");
					matchPlayers[i].setForeground(Color.GREEN);
				} else {
					// Move the first waiting pairs to the playing pairs
					matchPlayers[i].setText(waitingPlayers[0][i].getText());
					matchPlayers[i].setForeground(Color.ORANGE);
				}
			}
			for (int i = 0; i < waitingPlayers.length - 1; i++) {
				for (int n = 0; n < waitingPlayers[i].length; n++) {
					// Move everyone 1 spot forward
					waitingPlayers[i][n].setText(waitingPlayers[i + 1][n].getText());
					if (waitingPlayers[i][n].getText().equals(ADD))
						waitingPlayers[i][n].setForeground(Color.GREEN);
				}
			}
			for (int i = 0; i < waitingPlayers[maxWaiting - 1].length; i++) {
				waitingPlayers[maxWaiting - 1][i].setText(ADD); // Reset last row
			}
		}
	}

	// Button Add
	private class AddPlayers implements ActionListener {
		public void actionPerformed(ActionEvent p) {
			for (int x = 0; x < waitingPlayers.length; x++) {
				for (int y = 0; y < waitingPlayers[x].length; y++) {
					JButton theButton = waitingPlayers[x][y];
					if (p.getSource().equals(theButton) && !theButton.getText().equals(ADD)) {
						theButton.setText(ADD);
						theButton.setForeground(Color.GREEN);
						break;
					}

					if (p.getSource().equals(theButton)) {
						String input = JOptionPane.showInputDialog(null, "Enter your name", "Name",
								JOptionPane.INFORMATION_MESSAGE);

						if (input == null || input.isEmpty())
							break;
						else
							input = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

						if (checkIllegalInput(input))
							JOptionPane.showMessageDialog(null, "Illegal input", "Input error",
									JOptionPane.ERROR_MESSAGE);
						else if (checkRepeat(input))
							JOptionPane.showMessageDialog(null, "You cannot enter your name twice!", "Input error",
									JOptionPane.ERROR_MESSAGE);
						else {
							theButton.setText(input);
							theButton.setForeground(Color.BLACK);
						}
						break;
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

	private boolean checkIllegalInput(String name) { // Helper method
		if (name.equals(ADD) || name.equals(EMPTY) || name.matches("[0-9]+")) {
			return true;
		}
		return false;
	}

} // End of File