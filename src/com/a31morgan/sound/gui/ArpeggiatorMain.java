package com.a31morgan.sound.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioFormat;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.a31morgan.sound.Pitch;
import com.a31morgan.sound.player.BackgroundPlayer;
import com.a31morgan.sound.player.EchoFilter;
import com.a31morgan.sound.player.Filter;
import com.a31morgan.sound.player.IFilter;
import com.a31morgan.sound.player.IPlayer;
import com.a31morgan.sound.player.Player;

public class ArpeggiatorMain {
	
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {}
	}

	public static void main(String[] args) throws Exception {
    	AudioFormat format = new AudioFormat(Pitch.SAMPLE_RATE, 8, 1, true, true);
    	
    	IFilter[] filters = {
    			Filter.createScaleFilter(0.6),
    			Filter.createFadeInOutFilter(20, Pitch.SAMPLE_RATE),
        		EchoFilter.createMultiEchoFilter(new double[] { //in fractions
            			0.5, 0.4, 0.2, 0.1, 0.05,
            		}, new int[] { //frames
            			1500, 3000, 8000, 12000, 14000,
            		}),
    	};
    	
    	final IPlayer player = new BackgroundPlayer(new Player(format, filters));
    	
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
