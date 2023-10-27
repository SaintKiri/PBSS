/*
PBSS.java
By SaintKiri

Side-project: 
Made to substitute Pitt's Badminton sign-up sheet

All the actual code is in PBSS.java, which is required to run this program
*/

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PBSSdriver {
	public static void main(String[] args) {
		// Display version
		String welcome = "Welcome to the PBSS (Pitt Badminton Sign-up System)" + "\nVersion 1.15\n"
				+ "\n Patch notes can be found on the github page";
		JOptionPane.showMessageDialog(null, welcome, "Welcome", JOptionPane.INFORMATION_MESSAGE);

		// The window & properties
		JFrame PBSSWindow = new JFrame("Pitt Badminton Sign-up System | Version: 1.15");
		PBSSWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PBSSWindow.setResizable(false);

		PBSSWindow.getContentPane().add(new PBSS());

		PBSSWindow.pack();
		PBSSWindow.setVisible(true);
	}
}