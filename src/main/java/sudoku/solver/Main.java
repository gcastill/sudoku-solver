package sudoku.solver;

import sudoku.solver.ui.Grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Main {

	public static void main(String... args) {

		JFrame frame = new JFrame("suduko solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new GridBagLayout());
		frame.setVisible(true);
		frame.setBackground(Color.WHITE);

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 100;
		frame.add(new Grid(), c);

		c.gridx = 1;
		JSeparator comp = new JSeparator();
		comp.setPreferredSize(new Dimension(10, 0));
		comp.setOrientation(SwingConstants.VERTICAL);

		frame.add(comp, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		c.ipadx = 100;
		frame.add(new Grid(), c);

		frame.pack();

	}

}
