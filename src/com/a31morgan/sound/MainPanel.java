package com.a31morgan.sound;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private JSlider tempoSlider;
	private JComboBox<Arpeggiator> arpeggiatorComboBox;
	private JButton megaButton;
	
	public MainPanel() {
			super(new BorderLayout(3, 3));
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
	
	private void bindActions() {
		this.megaButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Pressed.");
			}
		});
		
		this.tempoSlider.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("Tempo: " + MainPanel.this.tempoSlider.getValue());
			}
		});
		
		this.arpeggiatorComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Arpeggio: " + MainPanel.this.arpeggiatorComboBox.getSelectedItem());
				}
			}
		});
	}
}
