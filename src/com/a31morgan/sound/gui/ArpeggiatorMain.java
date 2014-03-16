package com.a31morgan.sound.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioFormat;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.a31morgan.sound.Pitch;

public class ArpeggiatorMain {
	
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}
	}

	public static void main(String[] args) throws Exception {
    	AudioFormat format = new AudioFormat(Pitch.SAMPLE_RATE, 8, 1, true, true);
    	final IPlayer player = new BackgroundPlayer(new Player(format));
    	
		JFrame frame = new JFrame("JArpeggiator");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setContentPane(new MainPanel(player));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				player.start();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				player.stop();
			}
		});
		frame.setVisible(true);
	}
}
