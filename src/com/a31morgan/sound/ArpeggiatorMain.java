package com.a31morgan.sound;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class ArpeggiatorMain {
	
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("JArpeggiator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(new MainPanel());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
