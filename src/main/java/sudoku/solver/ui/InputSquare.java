package sudoku.solver.ui;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InputSquare extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputSquare() {
		setVisible(true);
		setHorizontalAlignment(SwingConstants.CENTER);
		setPreferredSize(new Dimension(20, 20));

	}

}
