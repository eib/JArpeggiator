package com.a31morgan.sound.gui;

import static com.a31morgan.sound.Arpeggio.MINOR;
import static com.a31morgan.sound.Note.SIXTEENTH_NOTE;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.a31morgan.sound.Arpeggiator;
import com.a31morgan.sound.Arpeggio;
import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Note.Length;
import com.a31morgan.sound.player.IPlayer;
import com.a31morgan.sound.Pitch;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private final IPlayer player;
	private final JSlider tempoSlider;
	private final JComboBox<Arpeggiator> arpeggiatorComboBox;
	private final JButton megaButton;
	private Pitch startingPitch;
	private KeyboardLayout keyboard = KeyboardLayout.getDefaultLayout();
	
	public MainPanel(IPlayer player) {
			super(new BorderLayout(3, 3));
			this.player = player;
			this.tempoSlider = new JSlider(60, 216, 129);
			this.arpeggiatorComboBox = new JComboBox<>(new Arpeggiator[] {
					Arpeggiator.UP_DOWN,
					Arpeggiator.DOWN_UP,
					Arpeggiator.UP,
					Arpeggiator.DOWN,
			});
			this.megaButton = new JButton("Play!");
			this.startingPitch = Pitch.C4;
			layoutComponents();
			bindActions();
	}
	
	private void layoutComponents() {
		this.add(this.tempoSlider, BorderLayout.PAGE_START);
		this.add(this.arpeggiatorComboBox, BorderLayout.PAGE_END);
		this.add(this.megaButton, BorderLayout.CENTER);
	}
	
	public void playMelody() {
		this.player.play(getMelody());
	}
	
	public void pauseMelody() {
		this.player.pause();
	}
	
	public Melody getMelody() {
		int bpm = this.tempoSlider.getValue();
		Melody melody = new Melody(bpm);
        melody.addAll(getPitches(), getNoteLength());
		return melody;
	}
	
	public Length getNoteLength() {
		return SIXTEENTH_NOTE;
	}

	public List<Pitch> getPitches() {
		Arpeggiator arpeggiator = getArpeggiator();
        return arpeggiator.listArpeggio(getArpeggio(), getStartingPitch(), getNumOctaves());
	}
	
	public Arpeggiator getArpeggiator() {
		return (Arpeggiator)this.arpeggiatorComboBox.getSelectedItem(); //but what if nothing is selected? (that would return null)
	}
	
	public Arpeggio getArpeggio() {
		return MINOR;
	}
	
	public Pitch getStartingPitch() {
		return startingPitch;
	}
	
	public int getNumOctaves() {
		return 3;
	}
	
	private void bindActions() {
		this.megaButton.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				int interval = keyboard.getPitchOffsetInterval(e);
				keyboard.setOffsetPitch(interval);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				pauseMelody();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Key code: " + e.getKeyCode());
				int interval = keyboard.getPitchOffsetInterval(e);
				keyboard.setOffsetPitch(interval);
				
				Pitch newPitch = keyboard.keyToPitch(e);
				if (newPitch != Pitch.REST) {
					startingPitch = newPitch;
					playMelody();
				}
			}
		});
		this.megaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pauseMelody();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				pauseMelody();
			}
		});
	}
}
