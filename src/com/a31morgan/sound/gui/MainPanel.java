package com.a31morgan.sound.gui;

import static com.a31morgan.sound.Arpeggio.MINOR;
import static com.a31morgan.sound.Note.SIXTEENTH_NOTE;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.a31morgan.sound.Arpeggiator;
import com.a31morgan.sound.Arpeggio;
import com.a31morgan.sound.Melody;
import com.a31morgan.sound.Note.Length;
import com.a31morgan.sound.Pitch;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private final IPlayer player;
	private final JSlider tempoSlider;
	private final JComboBox<Arpeggiator> arpeggiatorComboBox;
	private final JButton megaButton;
	
	public MainPanel(IPlayer player) {
			super(new BorderLayout(3, 3));
			this.player = player;
			this.tempoSlider = new JSlider(60, 216, 129);
			this.arpeggiatorComboBox = new JComboBox<>(new Arpeggiator[] {
					Arpeggiator.UP,
					Arpeggiator.DOWN,
					Arpeggiator.UP_DOWN,
					Arpeggiator.DOWN_UP,
			});
			this.megaButton = new JButton("Play!");
			layoutComponents();
			bindActions();
	}
	
	private void layoutComponents() {
		this.add(this.tempoSlider, BorderLayout.PAGE_START);
		this.add(this.arpeggiatorComboBox, BorderLayout.PAGE_END);
		this.add(this.megaButton, BorderLayout.CENTER);
	}
	
	public void updateMelody() {
		Melody melody = getMelody();
		this.player.setMelody(melody);
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
        return arpeggiator.getArpeggio(getArpeggio(), getStartingPitch(), getNumOctaves());
	}
	
	public Arpeggiator getArpeggiator() {
		return (Arpeggiator)this.arpeggiatorComboBox.getSelectedItem(); //but what if nothing is selected? (that would return null)
	}
	
	public Arpeggio getArpeggio() {
		return MINOR;
	}
	
	public Pitch getStartingPitch() {
		return Pitch.C3;
	}
	
	public int getNumOctaves() {
		return 3;
	}
	
	private void bindActions() {
		this.megaButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMelody();
			}
		});
	}
}
