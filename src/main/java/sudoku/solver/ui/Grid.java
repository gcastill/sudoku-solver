package sudoku.solver.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class Grid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Grid() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		int offsetX = 0;
		int offsetY = 0;
		newHorizontalSeparator(this, 3);
		newHorizontalSeparator(this, 7);
		newVerticalSeparator(this, 3);
		newVerticalSeparator(this, 7);

		for (int y = 0; y < 9; y++) {

			offsetY = adjustGridLocation(y);

			for (int x = 0; x < 9; x++) {

				offsetX = adjustGridLocation(x);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.gridx = offsetX;
				c.gridy = offsetY;
				c.gridwidth = 1;
				c.gridheight = 1;

				add(new InputSquare(), c);

			}

		}

	}

	int adjustGridLocation(int value) {
		if (value >= 6) {
			return value + 2;

		} else if (value >= 3) {
			return value + 1;

		} else {
			return value;
		}
	}

	void newHorizontalSeparator(JPanel panel, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = y;
		c.gridwidth = 11;
		c.fill = GridBagConstraints.HORIZONTAL;
		JSeparator comp = new JSeparator();
		comp.setOrientation(SwingConstants.HORIZONTAL);
		comp.setPreferredSize(new Dimension(10, 10));
		panel.add(comp, c);
	}

	void newVerticalSeparator(JPanel panel, int x) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = 0;
		c.gridheight = 11;
		c.fill = GridBagConstraints.VERTICAL;
		JSeparator comp = new JSeparator();
		comp.setPreferredSize(new Dimension(10, 10));
		comp.setOrientation(SwingConstants.VERTICAL);
		panel.add(comp, c);
	}
}
