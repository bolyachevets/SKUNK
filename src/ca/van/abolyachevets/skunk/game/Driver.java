package ca.van.abolyachevets.skunk.game;

import java.awt.EventQueue;

import ca.van.abolyachevets.skunk.ui.MainFrame;

public class Driver {

	public static void main(String[] args) {

		createUI();
	}

	/**
	 * Launch the application.
	 */
	private static void createUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		});
	}

}
